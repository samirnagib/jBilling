package model.dao;

import java.util.List;

import model.entities.owner;

public interface OwnerDao {

	void insert(owner obj);
	void update(owner obj);
	void deleteById(Integer id);
	owner findById(Integer id);
	List<owner> findAll();
	owner findByName(String owner);
	

}
