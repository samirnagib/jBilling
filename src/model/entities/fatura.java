package model.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class fatura  implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idInputBill;
	private Timestamp  ib_ano_mes;
	private Integer id_billTag;
	private Integer id_client;
	private String cv_agent;
	private String cv_instance;
	private String cv_backupset;
	private String cv_subclient;
	private String cv_storagepolicy;
	private String cv_copyname;
	private double cv_febackupsize;
	private double cv_fearchivesize;
	private double cv_primaryappsize;
	private double cv_protectedappsize;
	private double cv_mediasize;
	private double ib_taxcalculated;
	
	private tags tags;
	private clientes server;
	private clientType serverType;
	private owner dono;
	public fatura() {
	}
	public fatura(Integer idInputBill, Timestamp ib_ano_mes, Integer id_billTag, Integer id_client, String cv_agent,
			String cv_instance, String cv_backupset, String cv_subclient, String cv_storagepolicy, String cv_copyname,
			double cv_febackupsize, double cv_fearchivesize, double cv_primaryappsize, double cv_protectedappsize,
			double cv_mediasize, double ib_taxcalculated, model.entities.tags tags, clientes server,
			clientType serverType, owner dono) {
		this.idInputBill = idInputBill;
		this.ib_ano_mes = ib_ano_mes;
		this.id_billTag = id_billTag;
		this.id_client = id_client;
		this.cv_agent = cv_agent;
		this.cv_instance = cv_instance;
		this.cv_backupset = cv_backupset;
		this.cv_subclient = cv_subclient;
		this.cv_storagepolicy = cv_storagepolicy;
		this.cv_copyname = cv_copyname;
		this.cv_febackupsize = cv_febackupsize;
		this.cv_fearchivesize = cv_fearchivesize;
		this.cv_primaryappsize = cv_primaryappsize;
		this.cv_protectedappsize = cv_protectedappsize;
		this.cv_mediasize = cv_mediasize;
		this.ib_taxcalculated = ib_taxcalculated;
		this.tags = tags;
		this.server = server;
		this.serverType = serverType;
		this.dono = dono;
	}
	public Integer getIdInputBill() {
		return idInputBill;
	}
	public void setIdInputBill(Integer idInputBill) {
		this.idInputBill = idInputBill;
	}
	public Timestamp getIb_ano_mes() {
		return ib_ano_mes;
	}
	public void setIb_ano_mes(Timestamp ib_ano_mes) {
		this.ib_ano_mes = ib_ano_mes;
	}
	public Integer getId_billTag() {
		return id_billTag;
	}
	public void setId_billTag(Integer id_billTag) {
		this.id_billTag = id_billTag;
	}
	public Integer getId_client() {
		return id_client;
	}
	public void setId_client(Integer id_client) {
		this.id_client = id_client;
	}
	public String getCv_agent() {
		return cv_agent;
	}
	public void setCv_agent(String cv_agent) {
		this.cv_agent = cv_agent;
	}
	public String getCv_instance() {
		return cv_instance;
	}
	public void setCv_instance(String cv_instance) {
		this.cv_instance = cv_instance;
	}
	public String getCv_backupset() {
		return cv_backupset;
	}
	public void setCv_backupset(String cv_backupset) {
		this.cv_backupset = cv_backupset;
	}
	public String getCv_subclient() {
		return cv_subclient;
	}
	public void setCv_subclient(String cv_subclient) {
		this.cv_subclient = cv_subclient;
	}
	public String getCv_storagepolicy() {
		return cv_storagepolicy;
	}
	public void setCv_storagepolicy(String cv_storagepolicy) {
		this.cv_storagepolicy = cv_storagepolicy;
	}
	public String getCv_copyname() {
		return cv_copyname;
	}
	public void setCv_copyname(String cv_copyname) {
		this.cv_copyname = cv_copyname;
	}
	public double getCv_febackupsize() {
		return cv_febackupsize;
	}
	public void setCv_febackupsize(double cv_febackupsize) {
		this.cv_febackupsize = cv_febackupsize;
	}
	public double getCv_fearchivesize() {
		return cv_fearchivesize;
	}
	public void setCv_fearchivesize(double cv_fearchivesize) {
		this.cv_fearchivesize = cv_fearchivesize;
	}
	public double getCv_primaryappsize() {
		return cv_primaryappsize;
	}
	public void setCv_primaryappsize(double cv_primaryappsize) {
		this.cv_primaryappsize = cv_primaryappsize;
	}
	public double getCv_protectedappsize() {
		return cv_protectedappsize;
	}
	public void setCv_protectedappsize(double cv_protectedappsize) {
		this.cv_protectedappsize = cv_protectedappsize;
	}
	public double getCv_mediasize() {
		return cv_mediasize;
	}
	public void setCv_mediasize(double cv_mediasize) {
		this.cv_mediasize = cv_mediasize;
	}
	public double getIb_taxcalculated() {
		return ib_taxcalculated;
	}
	public void setIb_taxcalculated(double ib_taxcalculated) {
		this.ib_taxcalculated = ib_taxcalculated;
	}
	public tags getTags() {
		return tags;
	}
	public void setTags(tags tags) {
		this.tags = tags;
	}
	public clientes getServer() {
		return server;
	}
	public void setServer(clientes server) {
		this.server = server;
	}
	public clientType getServerType() {
		return serverType;
	}
	public void setServerType(clientType serverType) {
		this.serverType = serverType;
	}
	public owner getDono() {
		return dono;
	}
	public void setDono(owner dono) {
		this.dono = dono;
	}
	@Override
	public String toString() {
		return "fatura [idInputBill=" + idInputBill + ", ib_ano_mes=" + ib_ano_mes + ", id_billTag=" + id_billTag
				+ ", id_client=" + id_client + ", cv_agent=" + cv_agent + ", cv_instance=" + cv_instance
				+ ", cv_backupset=" + cv_backupset + ", cv_subclient=" + cv_subclient + ", cv_storagepolicy="
				+ cv_storagepolicy + ", cv_copyname=" + cv_copyname + ", cv_febackupsize=" + cv_febackupsize
				+ ", cv_fearchivesize=" + cv_fearchivesize + ", cv_primaryappsize=" + cv_primaryappsize
				+ ", cv_protectedappsize=" + cv_protectedappsize + ", cv_mediasize=" + cv_mediasize
				+ ", ib_taxcalculated=" + ib_taxcalculated + ", tags=" + tags + ", server=" + server + ", serverType="
				+ serverType + ", dono=" + dono + "]";
	}
	
	
	
	
	
}
