package model.entities;

import java.io.Serializable;

public class tags implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	
	private int idTag;
	private String tagName;
	private double tagPrice;
	
	
	public tags() {
	}
	

	public tags(int idTag, String tagName, double tagPrice) {
		this.idTag = idTag;
		this.tagName = tagName;
		this.tagPrice = tagPrice;
	}
	
	public int getIdTag() {
		return idTag;
	}
	public void setIdTag(int idTag) {
		this.idTag = idTag;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public double getTagPrice() {
		return tagPrice;
	}
	public void setTagPrice(double tagPrice) {
		this.tagPrice = tagPrice;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idTag;
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
		tags other = (tags) obj;
		if (idTag != other.idTag)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "tags [idTag=" + idTag + ", tagName=" + tagName + ", tagPrice=" + tagPrice + "]";
	}



}
