package model.dao;

import java.util.List;

import model.entities.clientType;

public interface clientTypeDao {
	
	void insert(clientType obj);
	void update(clientType obj);
	void deleteById(Integer id);
	clientType findById(Integer id);
	List<clientType> findAll();
	clientType findByName(String clientTypeName);
	boolean searchByName(String clientTypeName);
	
	

}
