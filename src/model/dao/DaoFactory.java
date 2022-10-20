package model.dao;

import db.db;
import model.dao.impl.*;

public class DaoFactory {
	
	public static clientTypeDao createClientTypeDao() {
		return new clientTypeDaoJDBC(db.getConnection());
	}

}
