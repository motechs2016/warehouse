package cn.edu.zucc.warehouse.model;
import java.util.Date;

public class BeanInbound {
	   private String in_id ;
	   private String m_id  ;
	   private String waid  ;
	   private String worker_id       ;
	   private String in_time           ;
	   private String material_type   ;
	   private int material_count  ;
	   private float average_cost;
	   private String stock_id;
	   private String batch;
	   
	 public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getStock_id() {
		return stock_id;
	}
	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}
	public float getAverage_cost() {
		return average_cost;
	}
	public void setAverage_cost(float average_cost) {
		this.average_cost = average_cost;
	}
	
	   
	   
	   public String getIn_id() {
		return in_id;
	}
	public void setIn_id(String in_id) {
		this.in_id = in_id;
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
	public String getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(String worker_id) {
		this.worker_id = worker_id;
	}
	public String getIn_time() {
		return in_time;
	}
	public void setIn_time(String in_time) {
		this.in_time = in_time;
	}
	public String getMaterial_type() {
		return material_type;
	}
	public void setMaterial_type(String material_type) {
		this.material_type = material_type;
	}
	public int getMaterial_count() {
		return material_count;
	}
	public void setMaterial_count(int material_count) {
		this.material_count = material_count;
	}
	
	
}
