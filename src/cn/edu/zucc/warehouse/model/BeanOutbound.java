package cn.edu.zucc.warehouse.model;
import java.util.Date;
public class BeanOutbound {
	 private String out_id  ;
	 private String   m_id  ;
	 private String worker_id  ;
	 private String waid          ;
	 private String   out_time   ;
	 private String material_type    ;
	 private int material_count   ;
	 private float average_cost;
	 private String batch          ;
	 
	 
	 public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public float getAverage_cost() {
		return average_cost;
	}
	public void setAverage_cost(float average_cost) {
		this.average_cost = average_cost;
	}
	public String getOut_id() {
		return out_id;
	}
	public void setOut_id(String out_id) {
		this.out_id = out_id;
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
	public String getWaid() {
		return waid;
	}
	public void setWaid(String waid) {
		this.waid = waid;
	}
	public String getOut_time() {
		return out_time;
	}
	public void setOut_time(String out_time) {
		this.out_time = out_time;
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
