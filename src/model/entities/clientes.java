package model.entities;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class clientes implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idClient;
	private String clientName;
	private String clientHostname;
	private Integer idType;
	private Integer idOwner;
	private String uuid;
	
	// Campos para as tabelas externas
		
	private clientType clientType;
	private owner owner;
	
	public clientes() {
	}
	public clientes(Integer idClient, String clientName, String clientHostname, Integer idType, Integer idOwner,
			String uuid, model.entities.clientType clientType, model.entities.owner owner) {
		this.idClient = idClient;
		this.clientName = clientName;
		this.clientHostname = clientHostname;
		this.idType = idType;
		this.idOwner = idOwner;
		this.uuid = uuid;
		this.clientType = clientType;
		this.owner = owner;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idClient == null) ? 0 : idClient.hashCode());
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
		clientes other = (clientes) obj;
		if (idClient == null) {
			if (other.idClient != null)
				return false;
		} else if (!idClient.equals(other.idClient))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "clientes [idClient=" + idClient + ", clientName=" + clientName + ", clientHostname=" + clientHostname
				+ ", idType=" + idType + ", idOwner=" + idOwner + ", uuid=" + uuid + ", clientType=" + clientType
				+ ", owner=" + owner + "]";
	}
	public Integer getIdClient() {
		return idClient;
	}
	public void setIdClient(Integer idClient) {
		this.idClient = idClient;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getClientHostname() {
		return clientHostname;
	}
	public void setClientHostname(String clientHostname) {
		this.clientHostname = clientHostname;
	}
	public Integer getIdType() {
		return idType;
	}
	public void setIdType(Integer idType) {
		this.idType = idType;
	}
	public Integer getIdOwner() {
		return idOwner;
	}
	public void setIdOwner(Integer idOwner) {
		this.idOwner = idOwner;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public clientType getClientType() {
		return clientType;
	}
	public void setClientType(clientType clientType) {
		this.clientType = clientType;
	}
	public owner getOwner() {
		return owner;
	}
	public void setOwner(owner owner) {
		this.owner = owner;
	}
	
	
	
}
