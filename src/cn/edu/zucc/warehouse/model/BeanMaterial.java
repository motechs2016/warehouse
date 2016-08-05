package cn.edu.zucc.warehouse.model;

import java.util.Date;

public class BeanMaterial {

   private String m_id ;
   private String waid ;
   private String m_name;
   private String m_type;
   private String type_id;
   private String manufacturer;
   
   
   
   public String getM_type() {
	return m_type;
}
public void setM_type(String m_type) {
	this.m_type = m_type;
}
public String getM_name() {
	return m_name;
}
public void setM_name(String m_name) {
	this.m_name = m_name;
}

   
   
  
	
	public String getM_id() {
	return m_id;
}
public void setM_id(String m_id) {
	this.m_id = m_id;
}
public String getWaid() {
	return waid;
}
public void setWaid(String waid) {
	this.waid = waid;
}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	
	
}
