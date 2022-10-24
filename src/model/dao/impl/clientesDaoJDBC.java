package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DbException;
import db.db;
import model.dao.clientesDao;

import model.entities.clientType;
import model.entities.clientes;
import model.entities.owner;

public class clientesDaoJDBC implements clientesDao {
	
	private Connection conexao;
	
	public clientesDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}
	
	@Override
	public void insert(clientes obj) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement("INSERT INTO clientes ( clientName, clientHostname, idType , idOwner, UUID) VALUES ( ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getClientName());
			st.setString(2,obj.getClientHostname());
			st.setInt(3, obj.getClientType().getIdClientType());
			st.setInt(4, obj.getOwner().getIdOwner());
			st.setString(5, obj.getUuidClient());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdClient(id);;
				}
				db.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error! No rows affected!");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			db.closeStatement(st);
		}
		
	}

	@Override
	public void update(clientes obj) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement("UPDATE clientes SET clientName = ?, clientHostname = ?, idType = ? , idOwner = ?, UUID = ? WHERE idClient = ?");
			
			st.setString(1, obj.getClientName());
			st.setString(2, obj.getClientHostname());
			st.setInt(3, obj.getClientType().getIdClientType());
			st.setInt(4, obj.getOwner().getIdOwner());
			st.setString(5, obj.getUuidClient());
			st.setInt(6, obj.getIdClient());
			
			st.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			db.closeStatement(st);
			
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement("DELETE FROM clientes WHERE idClient = ?");
			st.setInt(1, id);
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			db.closeStatement(st);
			
		}
		
	}

	@Override
	public clientes findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement("SELECT idClient, clientName, clientHostname,clienttype.typeName,towner.owName, towner.owAR, UUID FROM clientes INNER JOIN clientType ON clientes.idType = clientType.idType INNER JOIN towner ON clientes.idOwner = towner.idOwner WHERE clientes.idClient = ");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				clientes client = instantiateClients(rs);
				return client;
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			db.closeStatement(st);
			db.closeResultSet(rs);
		}
	}

	@Override
	public List<clientes> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement("SELECT idClient, clientName, clientHostname,clientes.idType,clientes.idOwner,clienttype.typeName,towner.owName, towner.owAR, UUID FROM clientes INNER JOIN clienttype ON clientes.idType = clienttype.idType INNER JOIN towner ON clientes.idOwner = towner.idOwner order by clientes.idClient");
			rs = st.executeQuery();
			List<clientes> list = new ArrayList<>();
			while (rs.next()) {
				clientes cliente = instantiateClients(rs);
				list.add(cliente);
			}
			return list;
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			db.closeStatement(st);
			db.closeResultSet(rs);
		}

	}

		
	//----
	
	private clientes instantiateClients(ResultSet rs) throws SQLException {
		clientes cliente = new clientes();
		clientType clientType = new clientType();
		owner owner = new owner();
		
		clientType.setIdClientType(rs.getInt("idType"));
		clientType.setTypeName(rs.getString("clienttype.typeName"));
		
		owner.setIdOwner(rs.getInt("idOwner"));
		owner.setOwName(rs.getString("towner.owName"));
		owner.setOwAR(rs.getString("towner.owAR"));
		
		cliente.setIdClient(rs.getInt("idClient"));
		cliente.setClientName(rs.getString("clientName"));
		cliente.setClientHostname(rs.getString("clientHostname"));
		cliente.setUuidClient(rs.getString("UUID"));
		cliente.setClientType(clientType);
		cliente.setOwner(owner);

		return cliente;
	}

	@Override
	public List<clientes> findByName(String clientes) {
		PreparedStatement st = null;
		ResultSet rs = null;
		String query = "SELECT idClient, clientName, clientHostname,clienttype.typeName,clientes.idType,clientes.idOwner,towner.owName, towner.owAR, UUID FROM clientes INNER JOIN clientType ON clientes.idType = clientType.idType INNER JOIN towner ON clientes.idOwner = towner.idOwner WHERE clientes.clientName LIKE ? ESCAPE '!'";
		try {
			clientes = clientes
				    .replace("!", "!!")
				    .replace("%", "!%")
				    .replace("_", "!_")
				    .replace("[", "![");
			st = conexao.prepareStatement( query );
			st.setString(1, clientes +"%");
			rs = st.executeQuery();
			List<clientes> list = new ArrayList<>();
			if (rs.next()) {
				clientes client = instantiateClients(rs);
				list.add(client);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			db.closeStatement(st);
			db.closeResultSet(rs);
		}
	}

}
