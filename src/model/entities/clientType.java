package model.entities;

import java.io.Serializable;

public class clientType implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private int idClientType;
	private String typeName;
	
	public clientType() {
	}

	public clientType(int idClientType, String typeName) {
		this.idClientType = idClientType;
		this.typeName = typeName;
	}

	public int getIdClientType() {
		return idClientType;
	}

	public void setIdClientType(int idClientType) {
		this.idClientType = idClientType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idClientType;
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
		clientType other = (clientType) obj;
		if (idClientType != other.idClientType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "clientType [idClientType=" + idClientType + ", typeName=" + typeName + "]";
	}
	
	

}
