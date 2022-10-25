package model.dao;

import java.util.List;

import model.entities.fatura;

public interface FaturaDao {

	void insert(fatura obj);
	void update(fatura obj);
	void deleteById(Integer id);
	fatura findById(Integer id);
	List<fatura> findByDate(String Data);
	List<fatura> findByPeriod(String Inicial, String Final);
	
}
