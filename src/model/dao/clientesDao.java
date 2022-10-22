package model.dao;

import java.util.List;

import model.entities.clientes;

public interface clientesDao {
	
	void insert(clientes obj);
	void update(clientes obj);
	void deleteById(Integer id);
	clientes findById(Integer id);
	List<clientes> findAll();
	List<clientes> findByName(String clientes);

}
