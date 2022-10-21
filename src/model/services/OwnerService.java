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

}
