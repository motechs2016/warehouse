package cn.edu.zucc.warehouse.model;

import java.util.Date;

public class BeanStock {
	   private String stock_id;
	   private String m_id;
	   private String waid;
	   private float average_cost;
	   private int m_count ;
	   private String flag;
	   
	   
	   
	   
	   public String getStock_id() {
		return stock_id;
	}
	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
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
	public float getAverage_cost() {
		return average_cost;
	}
	public void setAverage_cost(float average_cost) {
		this.average_cost = average_cost;
	}
	public int getM_count() {
		return m_count;
	}
	public void setM_count(int m_count) {
		this.m_count = m_count;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	private String batch ;
	 

}
