package model.entities;

import java.io.Serializable;

public class owner implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idOwner;
	private String owName;
	private String owEmail1;
	private String owEmail2;
	private String owProjetoArea;
	private String owAR;
	
	public owner(int idOwner, String owName, String owEmail1, String owEmail2, String owProjetoArea, String owAR) {
		this.idOwner = idOwner;
		this.owName = owName;
		this.owEmail1 = owEmail1;
		this.owEmail2 = owEmail2;
		this.owProjetoArea = owProjetoArea;
		this.owAR = owAR;
	}
	public owner() {
	}
	public Integer getIdOwner() {
		return idOwner;
	}
	public void setIdOwner(Integer idOwner) {
		this.idOwner = idOwner;
	}
	public String getOwName() {
		return owName;
	}
	public void setOwName(String owName) {
		this.owName = owName;
	}
	public String getOwEmail1() {
		return owEmail1;
	}
	public void setOwEmail1(String owEmail1) {
		this.owEmail1 = owEmail1;
	}
	public String getOwEmail2() {
		return owEmail2;
	}
	public void setOwEmail2(String owEmail2) {
		this.owEmail2 = owEmail2;
	}
	public String getOwProjetoArea() {
		return owProjetoArea;
	}
	public void setOwProjetoArea(String owProjetoArea) {
		this.owProjetoArea = owProjetoArea;
	}
	public String getOwAR() {
		return owAR;
	}
	public void setOwAR(String owAR) {
		this.owAR = owAR;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idOwner;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		owner other = (owner) obj;
		if (idOwner != other.idOwner)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "owner [idOwner=" + idOwner + ", owName=" + owName + ", owEmail1=" + owEmail1 + ", owEmail2=" + owEmail2
				+ ", owProjetoArea=" + owProjetoArea + ", owAR=" + owAR + "]";
	}
	
	

}
