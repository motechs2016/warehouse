package cn.edu.zucc.warehouse.model;
import java.util.Date;
public class BeanMovebound {
	   private String move_id       ;
	   private String  worker_id;
	   private String waid          ;
	   private String m_id          ;
	   private String move_time         ;
	   private String  material_type  ;
	   private int  material_count ;
	   private int in_waid            ;
	   private String  out_waid       ;
	   private String  batch       ;
	   private String  stock_id;
	   private float  average_cost;
	public float getAverage_cost() {
		return average_cost;
	}
	public void setAverage_cost(float average_cost) {
		this.average_cost = average_cost;
	}
	public String getStock_id() {
		return stock_id;
	}
	public void setStock_id(String stock_id) {
		this.stock_id = stock_id;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getMove_id() {
		return move_id;
	}
	public void setMove_id(String move_id) {
		this.move_id = move_id;
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
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getMove_time() {
		return move_time;
	}
	public void setMove_time(String move_time) {
		this.move_time = move_time;
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
	public int getIn_waid() {
		return in_waid;
	}
	public void setIn_waid(int in_waid) {
		this.in_waid = in_waid;
	}
	public String getOut_waid() {
		return out_waid;
	}
	public void setOut_waid(String out_waid) {
		this.out_waid = out_waid;
	}
	
}
