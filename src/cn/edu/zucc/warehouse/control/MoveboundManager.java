package cn.edu.zucc.warehouse.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.warehouse.model.BeanInbound;
import cn.edu.zucc.warehouse.model.BeanMovebound;
import cn.edu.zucc.warehouse.model.BeanOutbound;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;
import cn.edu.zucc.warehouse.util.DbException;

public class MoveboundManager {
	public static BeanMovebound currentMovebound=null;
	public List<BeanMovebound> loadAllMovebound()throws BaseException{
		List<BeanMovebound> result=new ArrayList<BeanMovebound>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select move_id,m_id,waid,worker_id,move_time,material_type,material_count,out_waid,stock_id,batch,average_cost from movebound ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanMovebound m=new BeanMovebound();
				m.setMove_id(rs.getString(1));
				m.setM_id(rs.getString(2));
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setMove_time(rs.getString(5));
				m.setMaterial_type(rs.getString(6));
				m.setMaterial_count(rs.getInt(7));
				m.setOut_waid(rs.getString(8));
			    m.setStock_id(rs.getString(9));
			    m.setBatch(rs.getString(10));
		         m.setAverage_cost(rs.getFloat(11));
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
	
	
	
public void createMovebound(BeanMovebound movebound)throws BaseException{
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from movebound where move_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,movebound.getMove_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("移库单已经存在");
			rs.close();
			pst.close();
			sql="insert into movebound(move_id,m_id,waid,worker_id,move_time,material_type,material_count,average_cost,batch,out_waid) values(?,?,?,?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, movebound.getMove_id());
			pst.setString(2, movebound.getM_id());
			pst.setString(3,movebound.getWaid());
			pst.setString(4,movebound.getWorker_id());
			pst.setString(5, new java.sql.Date(System.currentTimeMillis()).toString());
			pst.setString(6, movebound.getMaterial_type());
			pst.setInt(7, movebound.getMaterial_count());
			pst.setFloat(8, movebound.getAverage_cost());
			pst.setString(9, movebound.getBatch());
			pst.setString(10, movebound.getOut_waid());
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


public  List<BeanMovebound>  searchbound(String in_id, String waid,String time)throws BaseException{
	
	List<BeanMovebound> result=new ArrayList<BeanMovebound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select move_id,m_id,waid,worker_id,move_time,material_type,material_count,average_cost,batch ";
		sql+=" from movebound where move_id=? and waid=? and move_time=?";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,in_id);
		pst.setString(2,waid );
		pst.setString(3,time );
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanMovebound m=new BeanMovebound();
			m.setMove_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setMove_time(rs.getString(5));
			
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



public  List<BeanMovebound>  searchbound1(String in_id, String waid,String time)throws BaseException{
	
	List<BeanMovebound> result=new ArrayList<BeanMovebound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select move_id,m_id,waid,worker_id,move_time,material_type,material_count,average_cost,batch ";
		sql+=" from movebound where move_id=? and waid=? ";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,in_id);
		pst.setString(2,waid );
		
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanMovebound m=new BeanMovebound();
			m.setMove_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setMove_time(rs.getString(5));
			
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






public  List<BeanMovebound>  searchbound2(String in_id, String waid,String time)throws BaseException{
	
	List<BeanMovebound> result=new ArrayList<BeanMovebound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select move_id,m_id,waid,worker_id,move_time,material_type,material_count,average_cost,batch ";
		sql+=" from movebound where  waid=? ";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		
		pst.setString(1,waid );
		
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanMovebound m=new BeanMovebound();
			m.setMove_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setMove_time(rs.getString(5));
			
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







public  List<BeanMovebound>  searchbound3(String in_id, String waid,String time)throws BaseException{
	
	List<BeanMovebound> result=new ArrayList<BeanMovebound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select move_id,m_id,waid,worker_id,move_time,material_type,material_count,average_cost,batch ";
		sql+=" from movebound where  waid=? and move_time=?";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		
		pst.setString(1,waid );
		pst.setString(2,time );
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanMovebound m=new BeanMovebound();
			m.setMove_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setMove_time(rs.getString(5));
			
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



	
	
}
