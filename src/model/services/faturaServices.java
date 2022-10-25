package model.services;


import java.util.List;

import model.dao.DaoFactory;
import model.dao.FaturaDao;
import model.entities.fatura;

public class faturaServices {
	
	private FaturaDao dao = DaoFactory.createFaturaDao();
	
	public List<fatura> findByDate(String data){
		return dao.findByDate(data);
	}
	
	public List<fatura> findByPeriod(String Inicial, String Final){
		return dao.findByPeriod(Inicial, Final);
	}
	
	
	public void saveOrUpdate(fatura obj) {
		if (obj.getIdInputBill()==null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
	public void remove(fatura obj) {
		dao.deleteById(obj.getIdInputBill());
	}
	

}
