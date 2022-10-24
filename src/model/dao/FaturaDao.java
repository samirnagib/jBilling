package model.dao;

import java.sql.Date;
import java.util.List;

import model.entities.fatura;

public interface FaturaDao {

	void insert(fatura obj);
	void update(fatura obj);
	void deleteById(Integer id);
	fatura findById(Integer id);
	List<fatura> findByDate(Date Data);
	List<fatura> findByPeriod(Date Inicial, Date Final);
	
}
