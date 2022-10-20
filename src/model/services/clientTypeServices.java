package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.clientTypeDao;
import model.entities.clientType;

public class clientTypeServices {
	
	private clientTypeDao dao = DaoFactory.createClientTypeDao();
	
	public List<clientType> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(clientType obj) {
		if ( obj.getIdClientType() == null ) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(clientType obj) {
		dao.deleteById(obj.getIdClientType());
	}
	
	public boolean searchByName(String clientTypeName) {
		
		return searchByName(clientTypeName);
	}

}
