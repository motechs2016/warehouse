package cn.edu.zucc.warehouse.model;
import java.util.Date;

public class BeanBackbound {
	   private String back_id ;
	   private String waid    ;
	   private String worker_id ;
	   private String m_id    ;
	   private String material_type  ;
	   private int material_count ;
	   private String back_time        ;
	   private String back_waid      ;
	   private float average_cost;
	   
	   private String batch;
	   public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getOut_id() {
		return out_id;
	}
	public void setOut_id(String out_id) {
		this.out_id = out_id;
	}
	private String out_id;
	   
	public float getAverage_cost() {
		return average_cost;
	}
	public void setAverage_cost(float macost) {
		this.average_cost = macost;
	}
	public String getBack_id() {
		return back_id;
	}
	public void setBack_id(String back_id) {
		this.back_id = back_id;
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
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
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
	public void setMaterial_count(int macount) {
		this.material_count = macount;
	}
	public String getBack_time() {
		return back_time;
	}
	public void setBack_time(String back_time) {
		this.back_time = back_time;
	}
	public String getBack_waid() {
		return back_waid;
	}
	public void setBack_waid(String back_waid) {
		this.back_waid = back_waid;
	}

}
