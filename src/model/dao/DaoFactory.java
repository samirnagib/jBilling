package model.dao;

import db.db;
import model.dao.impl.*;

public class DaoFactory {
	
	public static clientTypeDao createClientTypeDao() {
		return new clientTypeDaoJDBC(db.getConnection());
	}
	
	public static OwnerDao createOwnerDao() {
		return new OwnerDaoJDBC(db.getConnection());
	}

	public static BillTagsDao createBillTagsDao() {
		return new BillTagsDaoJDBC(db.getConnection());
	}
	
	public static clientesDao createClientsDao() {
		return new clientesDaoJDBC(db.getConnection());
	}
	
}
