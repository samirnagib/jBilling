package model.services;

import java.util.ArrayList;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.OwnerDao;
import model.entities.owner;

public class OwnerService {
	
	private OwnerDao dao = DaoFactory.createOwnerDao();
	
	public List<owner> findAll() {
		return dao.findAll();
	}
	
	public void saverOrUpdate(owner obj) {
		if (obj.getIdOwner()==null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(owner obj) {
		dao.deleteById(obj.getIdOwner());
	}
	
	public owner findOwnerByName(String onwerName) {
		return dao.findByName(onwerName);
	}

}
