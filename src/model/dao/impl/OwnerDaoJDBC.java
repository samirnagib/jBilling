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
import model.dao.OwnerDao;
import model.entities.owner;

public class OwnerDaoJDBC implements OwnerDao {
	
	private Connection conexao;
	
	public OwnerDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(owner obj) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement("INSERT INTO towner ( owName, owEmail1, owEmail2, owProjectArea, owAR) VALUES ( ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getOwName());
			st.setString(2, obj.getOwEmail1());
			st.setString(3, obj.getOwEmail2());
			st.setString(4, obj.getOwProjetoArea());
			st.setString(5, obj.getOwAR());
			
			int rowsAffected = st.executeUpdate();
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdOwner(id);
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
	public void update(owner obj) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement("UPDATE towner SET owName = ?, owEmail1 = ?, owEmail2 = ?, owProjectArea = ?, owAR = ? WHERE idOwner = ?");
			
			st.setString(1, obj.getOwName());
			st.setString(2, obj.getOwEmail1());
			st.setString(3, obj.getOwEmail2());
			st.setString(4, obj.getOwProjetoArea());
			st.setString(5, obj.getOwAR());
			st.setInt(6, obj.getIdOwner());
			
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
			st = conexao.prepareStatement("DELETE FROM towner WHERE idOwner = ?");
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
	public owner findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement("SELECT * FROM towner WHERE idOwner = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				owner owner = instantiateOwner(rs);
				return owner;
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
	public List<owner> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement("SELECT * FROM towner order by owName");
			rs = st.executeQuery();
			List<owner> list = new ArrayList<>();
			while (rs.next()) {
				owner owner = instantiateOwner(rs);
				list.add(owner);
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
	public owner findByName(String ownerName) {
		PreparedStatement st = null;
		ResultSet rs = null;
	
		String query = "SELECT * FROM towner WHERE owName like ? ESCAPE '!'";
		try {
			ownerName = ownerName
				    .replace("!", "!!")
				    .replace("%", "!%")
				    .replace("_", "!_")
				    .replace("[", "![");
			st = conexao.prepareStatement( query );
			st.setString(1, ownerName +"%");
			rs = st.executeQuery();
			if (rs.next()) {
				owner owner = instantiateOwner(rs);
				return owner;
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

	private owner instantiateOwner(ResultSet rs) throws SQLException {
		owner owner = new owner();
		owner.setIdOwner(rs.getInt("idOwner"));
		owner.setOwName(rs.getString("owName"));
		owner.setOwEmail1(rs.getString("owEmail1"));
		owner.setOwEmail2(rs.getString("owEmail2"));
		owner.setOwProjetoArea(rs.getString("owProjectArea"));
		owner.setOwAR(rs.getString("owAR"));
		return owner;
	}
	
	
	
}
