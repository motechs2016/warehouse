package cn.edu.zucc.warehouse.model;

import java.util.Date;

public class BeanWarehouse {

	   private String waname ;      
	   private String waid ;
	   private String ueser_id ;
	   private String  address;   
	   private String  warman ;    
	   private Date w_time ;
	   private int sum_count ;
	   private String waretype;
	   private String  flag ;        
	   private int  in_count ;
	   private int out_count  ;
	   private String   max_size ;
	   private String status;
	   
	  public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWaretype() {
		return waretype;
	}
	public void setWaretype(String waretype) {
		this.waretype = waretype;
	}
	
	public String getWaname() {
		return waname;
	}
	public void setWaname(String waname) {
		this.waname = waname;
	}
	public String getWaid() {
		return waid;
	}
	public void setWaid(String waid) {
		this.waid = waid;
	}
	public String getUeser_id() {
		return ueser_id;
	}
	public void setUeser_id(String ueser_id) {
		this.ueser_id = ueser_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getWarman() {
		return warman;
	}
	public void setWarman(String warman) {
		this.warman = warman;
	}
	public Date getW_time() {
		return w_time;
	}
	public void setW_time(Date w_time) {
		this.w_time = w_time;
	}
	public int getSum_count() {
		return sum_count;
	}
	public void setSum_count(int sum_count) {
		this.sum_count = sum_count;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getIn_count() {
		return in_count;
	}
	public void setIn_count(int in_count) {
		this.in_count = in_count;
	}
	public int getOut_count() {
		return out_count;
	}
	public void setOut_count(int out_count) {
		this.out_count = out_count;
	}
	public String getMax_size() {
		return max_size;
	}
	public void setMax_size(String max_size) {
		this.max_size = max_size;
	}
	
	
	
	
	
}
