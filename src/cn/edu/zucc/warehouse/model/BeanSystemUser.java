package cn.edu.zucc.warehouse.model;

import java.util.Date;

public class BeanSystemUser {
	private String  user_id       ;
	private String  user_name      ;
	private String  user_password  ;
	private String  status         ;
	private String  user_type      ;
	
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String ueser_id) {
		this.user_id = ueser_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	
}
