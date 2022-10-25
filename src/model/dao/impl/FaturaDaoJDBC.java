package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DbException;
import db.db;
import model.dao.FaturaDao;
import model.entities.BillTags;
import model.entities.clientType;
import model.entities.clientes;
import model.entities.fatura;
import model.entities.owner;

public class FaturaDaoJDBC implements FaturaDao {
	
private Connection conexao;
	
	public FaturaDaoJDBC(Connection conexao) {
		this.conexao = conexao;
	}
	

	@Override
	public void insert(fatura obj) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement("INSERT INTO inputbill (ib_ano_mes,id_billTag,id_client,cv_agent,cv_instance,cv_backupset,cv_subclient,cv_storagepolicy,cv_copyname,cv_febackupsize,cv_fearchivesize,cv_primaryappsize,cv_protectedappsize,cv_mediasize,ib_taxcalculated) VALUES ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			st.setDate(1, obj.getIb_ano_mes());
			st.setInt(2, obj.getId_billTag());
			st.setInt(3, obj.getId_client());
			st.setString(4, obj.getCv_agent());
			st.setString(5, obj.getCv_instance());
			st.setString(6, obj.getCv_backupset());
			st.setString(7, obj.getCv_subclient());
			st.setString(8, obj.getCv_storagepolicy());
			st.setString(9, obj.getCv_copyname());
			st.setDouble(10, obj.getCv_febackupsize());
			st.setDouble(11, obj.getCv_fearchivesize());
			st.setDouble(12, obj.getCv_primaryappsize());
			st.setDouble(13, obj.getCv_protectedappsize());
			st.setDouble(14, obj.getCv_mediasize());
			st.setDouble(15, obj.getIb_taxcalculated());
			int rowsAffected = st.executeUpdate();
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setIdInputBill(id);
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
	public void update(fatura obj) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement("UPDATE inputbill SET ib_ano_mes = ?, id_billTag = ?, id_client = ?, cv_agent = ?, cv_instance = ?, cv_backupset = ?, cv_subclient = ?, cv_storagepolicy = ?, cv_copyname = ?, cv_febackupsize = ?, cv_fearchivesize = ?, cv_primaryappsize = ?, cv_protectedappsize = ?, cv_mediasize = ?, ib_taxcalculated = ? WHERE idInputBill = ?");
			
			st.setDate(1, obj.getIb_ano_mes());
			st.setInt(2, obj.getServerType().getIdClientType() );
			st.setInt(3, obj.getServer().getIdClient());
			st.setString(4, obj.getCv_agent());
			st.setString(5, obj.getCv_instance());
			st.setString(6, obj.getCv_backupset());
			st.setString(7, obj.getCv_subclient());
			st.setString(8, obj.getCv_storagepolicy());
			st.setString(9, obj.getCv_copyname());
			st.setDouble(10, obj.getCv_febackupsize());
			st.setDouble(11, obj.getCv_fearchivesize());
			st.setDouble(12, obj.getCv_primaryappsize());
			st.setDouble(13, obj.getCv_protectedappsize());
			st.setDouble(14, obj.getCv_mediasize());
			st.setDouble(15, obj.getIb_taxcalculated());
			st.setInt(16, obj.getIdInputBill());
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
			st = conexao.prepareStatement("DELETE FROM inputBill WHERE idInputBill = ?");
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
	public fatura findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			st = conexao.prepareStatement("SELECT \r\n"
					+ "    inputBill.idInputBill,\r\n"
					+ "    inputBill.id_billTag,\r\n"
					+ "    inputBill.ib_ano_mes,\r\n"
					+ "    inputBill.id_client,\r\n"
					+ "    clientes.idClient,\r\n"
					+ "    clientes.clientName,\r\n"
					+ "    clientes.clientHostname,\r\n"
					+ "    billTags.idbillTag,\r\n"
					+ "    billTags.billtagName,\r\n"
					+ "    billTags.billPriceTB,\r\n"
					+ "    inputBill.cv_agent,\r\n"
					+ "    inputBill.cv_instance,\r\n"
					+ "    inputBill.cv_backupset,\r\n"
					+ "    inputBill.cv_subclient,\r\n"
					+ "    inputBill.cv_storagepolicy,\r\n"
					+ "    inputBill.cv_copyname,\r\n"
					+ "    inputBill.cv_febackupsize,\r\n"
					+ "    inputBill.cv_fearchivesize,\r\n"
					+ "    inputBill.cv_primaryappsize,\r\n"
					+ "    inputBill.cv_protectedappsize,\r\n"
					+ "    inputBill.cv_mediasize,\r\n"
					+ "    inputBill.ib_taxcalculated,\r\n"
					+ "    clientType.idType,\r\n"
					+ "    clientType.typeName,\r\n"
					+ "    towner.idOwner,\r\n"
					+ "    towner.owName,\r\n"
					+ "    towner.owProjectArea,\r\n"
					+ "    towner.owAR\r\n"
					+ "FROM\r\n"
					+ "    inputBill\r\n"
					+ "        INNER JOIN\r\n"
					+ "    billTags ON inputBill.id_billTag = billTags.idbillTag\r\n"
					+ "        INNER JOIN\r\n"
					+ "    clientes ON inputBill.id_client = clientes.idClient\r\n"
					+ "        INNER JOIN\r\n"
					+ "    clientType ON clientes.idType = clientType.idType\r\n"
					+ "        INNER JOIN\r\n"
					+ "    towner ON clientes.idOwner = towner.idOwner\r\n"
					+ "WHERE\r\n"
					+ "    idInputBill = ?") ;
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				BillTags bt = instatiateBilTags(rs);
				clientType ct = instatiateClientType(rs);
				owner ow = instatiateOwner(rs);
				clientes cl = instatiateClientes(rs, ct, ow);
				fatura ib = instatiateFatura(rs, bt, cl, ct, ow);
				return ib;
			}
		return null;
		
		}catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			db.closeStatement(st);
			db.closeResultSet(rs);
		}
	}

	@Override
	public List<fatura> findByDate(String Data) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			st = conexao.prepareStatement("SELECT \r\n"
					+ "    inputBill.idInputBill,\r\n"
					+ "    inputBill.id_billTag,\r\n"
					+ "    inputBill.ib_ano_mes,\r\n"
					+ "    inputBill.id_client,\r\n"
					+ "    clientes.idClient,\r\n"
					+ "    clientes.clientName,\r\n"
					+ "    clientes.clientHostname,\r\n"
					+ "    clientes.UUID,\r\n"
					+ "    billTags.idbillTag,\r\n"
					+ "    billTags.billtagName,\r\n"
					+ "    billTags.billPriceTB,\r\n"
					+ "    inputBill.cv_agent,\r\n"
					+ "    inputBill.cv_instance,\r\n"
					+ "    inputBill.cv_backupset,\r\n"
					+ "    inputBill.cv_subclient,\r\n"
					+ "    inputBill.cv_storagepolicy,\r\n"
					+ "    inputBill.cv_copyname,\r\n"
					+ "    inputBill.cv_febackupsize,\r\n"
					+ "    inputBill.cv_fearchivesize,\r\n"
					+ "    inputBill.cv_primaryappsize,\r\n"
					+ "    inputBill.cv_protectedappsize,\r\n"
					+ "    inputBill.cv_mediasize,\r\n"
					+ "    inputBill.ib_taxcalculated,\r\n"
					+ "    clientType.idType,\r\n"
					+ "    clientType.typeName,\r\n"
					+ "    towner.idOwner,\r\n"
					+ "    towner.owName,\r\n"
					+ "    towner.owEmail1,\r\n"
					+ "    towner.owEmail2,\r\n"
					+ "    towner.owProjectArea,\r\n"
					+ "    towner.owAR\r\n"
					+ "FROM\r\n"
					+ "    inputBill\r\n"
					+ "        INNER JOIN\r\n"
					+ "    billTags ON inputBill.id_billTag = billTags.idbillTag\r\n"
					+ "        INNER JOIN\r\n"
					+ "    clientes ON inputBill.id_client = clientes.idClient\r\n"
					+ "        INNER JOIN\r\n"
					+ "    clientType ON clientes.idType = clientType.idType\r\n"
					+ "        INNER JOIN\r\n"
					+ "    towner ON clientes.idOwner = towner.idOwner\r\n"
					+ "WHERE\r\n"
					+ "    inputBill.ib_ano_mes =  ?") ;
			st.setString(1, Data);
			rs = st.executeQuery();
			List<fatura> list = new ArrayList<>();
			Map<Integer, BillTags> mapTag = new HashMap<>();
			Map<Integer, clientes> mapClientes = new HashMap<>();
			Map<Integer, clientType> mapClientType = new HashMap<>();
			Map<Integer, owner> mapOwner = new HashMap<>();
			
			while (rs.next()) {
				BillTags tag = mapTag.get(rs.getInt("id_billTag"));
				if (tag == null) {
					tag = instatiateBilTags(rs);
					mapTag.put(rs.getInt("id_billTag"), tag);
				}
				
				clientType ct = mapClientType.get(rs.getObject("idType"));
				if (ct == null) {
					ct = instatiateClientType(rs);
					mapClientType.put(rs.getInt("idType"), ct);
				}
				owner ow = mapOwner.get(rs.getObject("idOwner"));
				if (ow == null ) {
					ow = instatiateOwner(rs);
					mapOwner.put(rs.getInt("idOwner"), ow);
				}
				clientes cl = mapClientes.get(rs.getInt("idClient"));
				if ( cl == null ) {
					cl = instatiateClientes(rs, ct, ow);
					mapClientes.put(rs.getInt("idClient"), cl);
				}
				
				fatura obj = instatiateFatura(rs, tag, cl, ct, ow);
				list.add(obj);
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

	@Override
	public List<fatura> findByPeriod(String Inicial, String Final) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			
			st = conexao.prepareStatement("SELECT \r\n"
					+ "    inputBill.idInputBill,\r\n"
					+ "    inputBill.id_billTag,\r\n"
					+ "    inputBill.ib_ano_mes,\r\n"
					+ "    inputBill.id_client,\r\n"
					+ "    clientes.idClient,\r\n"
					+ "    clientes.clientName,\r\n"
					+ "    clientes.clientHostname,\r\n"
					+ "    clientes.UUID,\r\n"
					+ "    billTags.idbillTag,\r\n"
					+ "    billTags.billtagName,\r\n"
					+ "    billTags.billPriceTB,\r\n"
					+ "    inputBill.cv_agent,\r\n"
					+ "    inputBill.cv_instance,\r\n"
					+ "    inputBill.cv_backupset,\r\n"
					+ "    inputBill.cv_subclient,\r\n"
					+ "    inputBill.cv_storagepolicy,\r\n"
					+ "    inputBill.cv_copyname,\r\n"
					+ "    inputBill.cv_febackupsize,\r\n"
					+ "    inputBill.cv_fearchivesize,\r\n"
					+ "    inputBill.cv_primaryappsize,\r\n"
					+ "    inputBill.cv_protectedappsize,\r\n"
					+ "    inputBill.cv_mediasize,\r\n"
					+ "    inputBill.ib_taxcalculated,\r\n"
					+ "    clientType.idType,\r\n"
					+ "    clientType.typeName,\r\n"
					+ "    towner.idOwner,\r\n"
					+ "    towner.owName,\r\n"
					+ "    towner.owEmail1,\r\n"
					+ "    towner.owEmail2,\r\n"
					+ "    towner.owProjectArea,\r\n"
					+ "    towner.owAR\r\n"
					+ "FROM\r\n"
					+ "    inputBill\r\n"
					+ "        INNER JOIN\r\n"
					+ "    billTags ON inputBill.id_billTag = billTags.idbillTag\r\n"
					+ "        INNER JOIN\r\n"
					+ "    clientes ON inputBill.id_client = clientes.idClient\r\n"
					+ "        INNER JOIN\r\n"
					+ "    clientType ON clientes.idType = clientType.idType\r\n"
					+ "        INNER JOIN\r\n"
					+ "    towner ON clientes.idOwner = towner.idOwner\r\n"
					+ "WHERE\r\n"
					+ "    inputBill.ib_ano_mes between ? and ?") ;
			st.setString(1, Inicial);
			st.setString(2, Final);
			rs = st.executeQuery();
			List<fatura> list = new ArrayList<>();
			Map<Integer, BillTags> mapTag = new HashMap<>();
			Map<Integer, clientes> mapClientes = new HashMap<>();
			Map<Integer, clientType> mapClientType = new HashMap<>();
			Map<Integer, owner> mapOwner = new HashMap<>();
			
			while (rs.next()) {
				BillTags tag = mapTag.get(rs.getInt("id_billTag"));
				if (tag == null) {
					tag = instatiateBilTags(rs);
					mapTag.put(rs.getInt("id_billTag"), tag);
				}
				
				clientType ct = mapClientType.get(rs.getObject("idType"));
				if (ct == null) {
					ct = instatiateClientType(rs);
					mapClientType.put(rs.getInt("idType"), ct);
				}
				owner ow = mapOwner.get(rs.getObject("idOwner"));
				if (ow == null ) {
					ow = instatiateOwner(rs);
					mapOwner.put(rs.getInt("idOwner"), ow);
				}
				clientes cl = mapClientes.get(rs.getInt("idClient"));
				if ( cl == null ) {
					cl = instatiateClientes(rs, ct, ow);
					mapClientes.put(rs.getInt("idClient"), cl);
				}
				
				fatura obj = instatiateFatura(rs, tag, cl, ct, ow);
				list.add(obj);
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
	
	private BillTags instatiateBilTags(ResultSet rs) throws SQLException {
		BillTags faixa = new BillTags();
		faixa.setIdbillTag(rs.getInt("idbillTag"));
		faixa.setBilltagName(rs.getString("billtagName"));
		faixa.setBillPriceTB(rs.getDouble("billPriceTB"));
		return faixa;
	}
	
	private clientType instatiateClientType(ResultSet rs) throws SQLException {
		clientType ct = new clientType();
		ct.setIdClientType(rs.getInt("idType"));
		ct.setTypeName(rs.getString("typeName"));
		return ct;
	}
	
	private owner instatiateOwner(ResultSet rs) throws SQLException {
		owner dono = new owner();
		dono.setIdOwner(rs.getInt("idOwner"));
		dono.setOwName(rs.getString("owName"));
		dono.setOwEmail1(rs.getString("owEmail1"));
		dono.setOwEmail2(rs.getString("owEmail2"));
		dono.setOwProjetoArea(rs.getString("owProjectArea"));
		dono.setOwAR(rs.getString("owAR"));
		return dono;
	}
	
	private clientes instatiateClientes(ResultSet rs, clientType clientType, owner owner) throws SQLException {
		clientes clientes = new clientes();
		clientes.setIdClient(rs.getInt("idClient"));
		clientes.setClientName(rs.getString("clientName"));
		clientes.setClientHostname(rs.getString("clientHostname"));
		clientes.setClientType(clientType);
		clientes.setOwner(owner);
		clientes.setUuidClient(rs.getString("UUID"));
		return clientes;
	}
	
	private fatura instatiateFatura(ResultSet rs, BillTags billTags, clientes client, clientType clientType, owner owner) throws SQLException {
		fatura f = new fatura();
		f.setIdInputBill(rs.getInt("idInputBill"));
		f.setIb_ano_mes(rs.getDate("ib_ano_mes"));
		f.setTags(billTags);
		f.setServer(client);
		f.setCv_agent(rs.getString("cv_agent"));
		f.setCv_instance(rs.getString("cv_instance"));
		f.setCv_backupset(rs.getString("cv_backupset"));
		f.setCv_subclient(rs.getString("cv_subclient"));
		f.setCv_storagepolicy(rs.getString("cv_storagepolicy"));
		f.setCv_copyname(rs.getString("cv_copyname"));
		f.setCv_febackupsize(rs.getDouble("cv_febackupsize"));
		f.setCv_fearchivesize(rs.getDouble("cv_fearchivesize"));
		f.setCv_primaryappsize(rs.getDouble("cv_primaryappsize"));
		f.setCv_protectedappsize(rs.getDouble("cv_protectedappsize"));
		f.setCv_mediasize(rs.getDouble("cv_mediasize"));
		f.setIb_taxcalculated(rs.getDouble("ib_taxcalculated"));
		f.setServerType(clientType);
		f.setDono(owner);
		return f;
	}
	

}
