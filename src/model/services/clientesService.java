package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.clientesDao;
import model.entities.clientes;

public class clientesService {
	
	private clientesDao dao = DaoFactory.createClientsDao();
	
	public List<clientes> findAll() {
		return dao.findAll();
	}
	
	public void saveOrUpdate(clientes obj) {
		if (obj.getIdClient()==null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(clientes obj) {
		dao.deleteById(obj.getIdClient());
	}
	
	public List<clientes> findClientByName(String Cliente) {
		return dao.findByName(Cliente);
	}

}
