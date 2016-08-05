package cn.edu.zucc.warehouse.model;
import java.util.Date;
public class BeanScrapbound {
	
	   private String sc_id;
	   private String    waid;
	   private String   m_id;
	   private String  worker_id;
	   private String  material_type;
	   private int  material_count;
	   private String sc_time;
	   private String sc_waid;
	   private String batch;
	   private float average_cost;
	   public float getAverage_cost() {
		return average_cost;
	}
	public void setAverage_cost(float average_cost) {
		this.average_cost = average_cost;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getSc_id() {
		return sc_id;
	}
	public void setSc_id(String sc_id) {
		this.sc_id = sc_id;
	}
	public String getWaid() {
		return waid;
	}
	public void setWaid(String waid) {
		this.waid = waid;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(String worker_id) {
		this.worker_id = worker_id;
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
	public void setMaterial_count(int i) {
		this.material_count = i;
	}
	public String getSc_time() {
		return sc_time;
	}
	public void setSc_time(String sc_time) {
		this.sc_time = sc_time;
	}
	public String getSc_waid() {
		return sc_waid;
	}
	public void setSc_waid(String sc_waid) {
		this.sc_waid = sc_waid;
	}
	
	   
}
