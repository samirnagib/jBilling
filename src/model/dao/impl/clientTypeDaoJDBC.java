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
import model.dao.clientTypeDao;
import model.entities.clientType;

public class clientTypeDaoJDBC implements clientTypeDao {
	
private Connection conn;
	
	public clientTypeDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(clientType obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO clienttype ( typeName ) VALUES ( ? )", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getTypeName());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdClientType(id);
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
	public void update(clientType obj) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE clienttype SET typeName = ? WHERE idType = ?");
			
			st.setString(1, obj.getTypeName());
			st.setInt(2, obj.getIdClientType());
			
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
			st = conn.prepareStatement("DELETE FROM clienttype WHERE idType = ?");
			
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
	public clientType findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM clienttype WHERE idType = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				clientType ClientType = instantiateClientType(rs);
				
				return ClientType;
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

	private clientType instantiateClientType(ResultSet rs) throws SQLException {
		clientType clientType = new clientType();
		clientType.setIdClientType(rs.getInt("idType"));
		clientType.setTypeName(rs.getString("typeName"));
		return clientType;
	}

	@Override
	public List<clientType> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT * FROM clienttype");
			rs = st.executeQuery();
			List<clientType> list = new ArrayList<>();
			while (rs.next()) {
				clientType clientType = instantiateClientType(rs);
				list.add(clientType);
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

	@Override
	public clientType findByName(String clientTypeName) {
		PreparedStatement st = null;
		ResultSet rs = null;
	
		String query = "SELECT * FROM clientType WHERE typeName LIKE ? ESCAPE '!'";
		
		try {
			clientTypeName = clientTypeName
				    .replace("!", "!!")
				    .replace("%", "!%")
				    .replace("_", "!_")
				    .replace("[", "![");
			st = conn.prepareStatement( query );
			st.setString(1, clientTypeName +"%");
			rs = st.executeQuery();
			if (rs.next()) {
				
				clientType clientType = instantiateClientType(rs);
				
				
				return clientType;
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
	public boolean searchByName(String clientTypeName) {
		
		String search;
		String compare;
		clientType clientType = new clientType();
		
		search = clientTypeName;
		clientType = findByName(clientTypeName);
		
		if (clientType != null) {
			
			compare = clientType.getTypeName();
					
			if ( search.equalsIgnoreCase(compare)) {
				return true;
			}
		}
		return false;
	}

}
