




package cn.edu.zucc.warehouse.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.zucc.warehouse.model.BeanBackbound;
import cn.edu.zucc.warehouse.model.BeanInbound;
import cn.edu.zucc.warehouse.model.BeanOutbound;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;
import cn.edu.zucc.warehouse.util.DbException;

public class OutboundManager {
	public static BeanOutbound currentOutbound=null;
	public List<BeanOutbound> loadAllOutbound()throws BaseException{
		List<BeanOutbound> result=new ArrayList<BeanOutbound>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select out_id,m_id,waid,worker_id,out_time,material_type,material_count,average_cost,batch from outbound ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanOutbound m=new BeanOutbound();
				m.setOut_id(rs.getString(1));
				m.setM_id(rs.getString(2));
			
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setOut_time(rs.getString(5));
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
	
	
	
	
	
	
	public boolean createOutbound(BeanOutbound outbound)throws BaseException{
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from outbound where out_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,outbound.getOut_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("出库单已经存在");
			rs.close();
			pst.close();
			
			String sql1="select m_count from stock where m_id=? and waid=? and batch=?";
			java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
			pst1.setString(1, outbound.getM_id());
			pst1.setString(2, outbound.getWaid());
			pst1.setString(3, outbound.getBatch());
			java.sql.ResultSet rs1=pst1.executeQuery();
			while(rs1.next()){
				 int m_count=rs1.getInt(1);
				 if(m_count<outbound.getMaterial_count())
					{
					 return false;
					//throw new BusinessException("该物料库存不足，无法出库");
					}
				 else{
					 String sql2="insert into outbound(out_id,m_id,waid,worker_id,out_time,material_type,material_count,batch,average_cost) values(?,?,?,?,?,?,?,?,?)";
					 java.sql.PreparedStatement pst2=conn.prepareStatement(sql2);
			          pst2.setString(1, outbound.getOut_id());
			          pst2.setString(2, outbound.getM_id());
			          pst2.setString(3,outbound.getWaid());
		              pst2.setString(4,outbound.getWorker_id());
		              pst2.setString(5, new java.sql.Date(System.currentTimeMillis()).toString());
		              pst2.setString(6, outbound.getMaterial_type());
		              pst2.setInt(7, outbound.getMaterial_count());
		              pst2.setString(8, outbound.getBatch());
		              pst2.setFloat(9, outbound.getAverage_cost());
			          pst2.execute();
			          pst2.close();
				}
				 
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
		
		
		return true;
	}


	public void BackOutbound(BeanBackbound outbound)throws BaseException{
		
		Connection conn=null;
		try {
		conn=DBUtil.getConnection();
		 String sql2="update  outbound  set material_count=material_count-? where out_id=? ";
		 java.sql.PreparedStatement pst2=conn.prepareStatement(sql2);
		  pst2.setInt(1, outbound.getMaterial_count());
          pst2.setString(2, outbound.getOut_id());
 
          pst2.execute();
          pst2.close();
		}
		catch (SQLException e) {
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
	
	
	
public  List<BeanOutbound>  searchbound(String in_id, String waid,String time)throws BaseException{
		
		List<BeanOutbound> result=new ArrayList<BeanOutbound>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
		
			
			String sql=" select out_id,m_id,waid,worker_id,out_time,material_type,material_count,average_cost,batch ";
			sql+=" from outbound where out_id=? and waid=? and out_time=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,in_id);
			pst.setString(2,waid );
			pst.setString(3,time );
			
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanOutbound m=new BeanOutbound();
				m.setOut_id(rs.getString(1));
				m.setM_id(rs.getString(2));
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setOut_time(rs.getString(5));
				
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

public  List<BeanOutbound>  searchbound1(String in_id, String waid,String time)throws BaseException{
	
	List<BeanOutbound> result=new ArrayList<BeanOutbound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select out_id,m_id,waid,worker_id,out_time,material_type,material_count,average_cost,batch ";
		sql+=" from outbound where out_id=? and waid=? ";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,in_id);
		pst.setString(2,waid );
		
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanOutbound m=new BeanOutbound();
			m.setOut_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setOut_time(rs.getString(5));
			
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


public  List<BeanOutbound>  searchbound2(String in_id, String waid,String time)throws BaseException{
	
	List<BeanOutbound> result=new ArrayList<BeanOutbound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select out_id,m_id,waid,worker_id,out_time,material_type,material_count,average_cost,batch ";
		sql+=" from outbound where waid=?";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		
		pst.setString(1,waid );
	
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanOutbound m=new BeanOutbound();
			m.setOut_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setOut_time(rs.getString(5));
			
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



public  List<BeanOutbound>  searchbound3(String in_id, String waid,String time)throws BaseException{
	
	List<BeanOutbound> result=new ArrayList<BeanOutbound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select out_id,m_id,waid,worker_id,out_time,material_type,material_count,average_cost,batch ";
		sql+=" from outbound where  waid=? and out_time=?";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		
		pst.setString(1,waid );
		pst.setString(2,time );
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanOutbound m=new BeanOutbound();
			m.setOut_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setOut_time(rs.getString(5));
			
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

