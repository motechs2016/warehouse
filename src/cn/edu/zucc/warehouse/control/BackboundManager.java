




package cn.edu.zucc.warehouse.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.zucc.warehouse.model.BeanBackbound;
import cn.edu.zucc.warehouse.model.BeanOutbound;
import cn.edu.zucc.warehouse.model.BeanScrapbound;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;
import cn.edu.zucc.warehouse.util.DbException;

public class BackboundManager   {
	public static BeanBackbound currentBackbound=null;
	public List<BeanBackbound> loadBackbound()throws BaseException{
		List<BeanBackbound> result=new ArrayList<BeanBackbound>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select back_id,m_id,waid,worker_id,back_time,material_type,material_count,average_cost,batch from backbound ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanBackbound m=new BeanBackbound();
				m.setBack_id(rs.getString(1));
				m.setM_id(rs.getString(2));
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setBack_time(rs.getString(5));
				m.setMaterial_type(rs.getString(6));
				m.setMaterial_count(rs.getInt(7));
			    m.setAverage_cost(rs.getFloat(8));
			    m.setBatch(rs.getString(9));
				result.add(m);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
	
	
	
	
	
	public void createBackbound(BeanBackbound backbound)throws BaseException{
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from backbound where back_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,backbound.getBack_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("入库单已经存在");
			rs.close();
			pst.close();
			sql="insert into backbound(back_id,m_id,waid,worker_id,back_time,material_type,material_count,out_id,average_cost,batch) values(?,?,?,?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, backbound.getBack_id());
			pst.setString(2, backbound.getM_id());
			pst.setString(3,backbound.getWaid());
			pst.setString(4,backbound.getWorker_id());
			pst.setString(5, new java.sql.Date(System.currentTimeMillis()).toString());
			pst.setString(6, backbound.getMaterial_type());
			pst.setInt(7, backbound.getMaterial_count());
			pst.setString(8, backbound.getOut_id());
			pst.setFloat(9, backbound.getAverage_cost());
			pst.setString(10, backbound.getBatch());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}


public  List<BeanBackbound>  searchbound(String in_id, String waid,String time)throws BaseException{
		
		List<BeanBackbound> result=new ArrayList<BeanBackbound>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
		
			
			String sql=" select back_id,m_id,waid,worker_id,back_time,material_type,material_count,average_cost,batch ";
			sql+=" from backbound where back_id=? and waid=? and back_time=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,in_id);
			pst.setString(2,waid );
			pst.setString(3,time );
			
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanBackbound m=new BeanBackbound();
				m.setBack_id(rs.getString(1));
				m.setM_id(rs.getString(2));
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setBack_time(rs.getString(5));
				
				m.setMaterial_type(rs.getString(6));
				m.setMaterial_count(rs.getInt(7));
			    m.setAverage_cost(rs.getFloat(8));
			
			    m.setBatch(rs.getString(9));
			    
		 
				result.add(m);

			}
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		return result;
	}
	
	
public  List<BeanBackbound>  searchbound1(String in_id, String waid,String time)throws BaseException{
	
	List<BeanBackbound> result=new ArrayList<BeanBackbound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select back_id,m_id,waid,worker_id,back_time,material_type,material_count,average_cost,batch ";
		sql+=" from backbound where back_id=? and waid=? ";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,in_id);
		pst.setString(2,waid );
		
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanBackbound m=new BeanBackbound();
			m.setBack_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setBack_time(rs.getString(5));
			
			m.setMaterial_type(rs.getString(6));
			m.setMaterial_count(rs.getInt(7));
		    m.setAverage_cost(rs.getFloat(8));
		
		    m.setBatch(rs.getString(9));
		    
	 
			result.add(m);

		}
		pst.execute();
		pst.close();
	} catch (SQLException e) {
		e.printStackTrace();
		throw new DbException(e);
	}
	finally{
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	return result;
}


public  List<BeanBackbound>  searchbound2(String in_id, String waid,String time)throws BaseException{
	
	List<BeanBackbound> result=new ArrayList<BeanBackbound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select back_id,m_id,waid,worker_id,back_time,material_type,material_count,average_cost,batch ";
		sql+=" from backbound where waid=? ";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,waid);
		
		
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanBackbound m=new BeanBackbound();
			m.setBack_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setBack_time(rs.getString(5));
			
			m.setMaterial_type(rs.getString(6));
			m.setMaterial_count(rs.getInt(7));
		    m.setAverage_cost(rs.getFloat(8));
		
		    m.setBatch(rs.getString(9));
		    
	 
			result.add(m);

		}
		pst.execute();
		pst.close();
	} catch (SQLException e) {
		e.printStackTrace();
		throw new DbException(e);
	}
	finally{
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	return result;
}



public  List<BeanBackbound>  searchbound3(String in_id, String waid,String time)throws BaseException{
	
	List<BeanBackbound> result=new ArrayList<BeanBackbound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select back_id,m_id,waid,worker_id,back_time,material_type,material_count,average_cost,batch ";
		sql+=" from backbound where back_id=? and back_time=? ";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,in_id);
		pst.setString(2,time);
		
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanBackbound m=new BeanBackbound();
			m.setBack_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setBack_time(rs.getString(5));
			
			m.setMaterial_type(rs.getString(6));
			m.setMaterial_count(rs.getInt(7));
		    m.setAverage_cost(rs.getFloat(8));
		
		    m.setBatch(rs.getString(9));
		    
	 
			result.add(m);

		}
		pst.execute();
		pst.close();
	} catch (SQLException e) {
		e.printStackTrace();
		throw new DbException(e);
	}
	finally{
		if(conn!=null)
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	return result;
}


	public static void main(String[] args){
		
	}
}

