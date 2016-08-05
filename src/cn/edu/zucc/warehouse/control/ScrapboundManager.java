
package cn.edu.zucc.warehouse.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.zucc.warehouse.model.BeanInbound;
import cn.edu.zucc.warehouse.model.BeanScrapbound;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;
import cn.edu.zucc.warehouse.util.DbException;

public class ScrapboundManager   {
	public static BeanScrapbound currentInbound=null;
	public List<BeanScrapbound> loadAllScrapbound()throws BaseException{
		List<BeanScrapbound> result=new ArrayList<BeanScrapbound>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select sc_id,m_id,waid,worker_id,sc_time,batch,material_count from scrap_bound ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanScrapbound m=new BeanScrapbound();
				m.setSc_id(rs.getString(1));
				m.setM_id(rs.getString(2));
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setSc_time(rs.getString(5));
	            m.setBatch(rs.getString(6));
			    m.setMaterial_count(rs.getInt(7));
				
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
	
	
	
	
	
	
	public void createScrapbound(BeanScrapbound scrapbound)throws BaseException{
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from scrap_bound where sc_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,scrapbound.getSc_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) 
				{
				throw new BusinessException("报废单已经存在");
				
				
				}
			else{
		
			
			sql="insert into scrap_bound(sc_id,m_id,waid,worker_id,sc_time,material_type,material_count,batch) values(?,?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, scrapbound.getSc_id());
			pst.setString(2, scrapbound.getM_id());
			pst.setString(3,scrapbound.getWaid());
			pst.setString(4,scrapbound.getWorker_id());
			pst.setString(5, new java.sql.Date(System.currentTimeMillis()).toString());
			pst.setString(6, scrapbound.getMaterial_type());
			pst.setInt(7, scrapbound.getMaterial_count());
			pst.setString(8, scrapbound.getBatch());
			pst.execute();
			pst.close();
			
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
	}

public  List<BeanScrapbound>  searchbound(String in_id, String waid,String time)throws BaseException{
		
		List<BeanScrapbound> result=new ArrayList<BeanScrapbound>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
		
			
			String sql=" select sc_id,m_id,waid,worker_id,sc_time,material_type,material_count,batch ";
			sql+=" from scrap_bound where sc_id=? and waid=? and sc_time=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,in_id);
			pst.setString(2,waid );
			pst.setString(3,time );
			
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanScrapbound m=new BeanScrapbound();
				m.setSc_id(rs.getString(1));
				m.setM_id(rs.getString(2));
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setSc_time(rs.getString(5));
				
				m.setMaterial_type(rs.getString(6));
				m.setMaterial_count(rs.getInt(7));
			   
			
			    m.setBatch(rs.getString(8));
			    
		 
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
	

public  List<BeanScrapbound>  searchbound1(String in_id, String waid,String time)throws BaseException{
	
	List<BeanScrapbound> result=new ArrayList<BeanScrapbound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select sc_id,m_id,waid,worker_id,sc_time,material_type,material_count,batch ";
		sql+=" from scrap_bound where sc_id=? and waid=? ";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,in_id);
		pst.setString(2,waid );
		
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanScrapbound m=new BeanScrapbound();
			m.setSc_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setSc_time(rs.getString(5));
			
			m.setMaterial_type(rs.getString(6));
			m.setMaterial_count(rs.getInt(7));
		   
		
		    m.setBatch(rs.getString(8));
		    
	 
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
	
public  List<BeanScrapbound>  searchbound2(String in_id, String waid,String time)throws BaseException{
	
	List<BeanScrapbound> result=new ArrayList<BeanScrapbound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select sc_id,m_id,waid,worker_id,sc_time,material_type,material_count,batch ";
		sql+=" from scrap_bound where waid=? ";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		
		pst.setString(1,waid );
		
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanScrapbound m=new BeanScrapbound();
			m.setSc_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setSc_time(rs.getString(5));
			
			m.setMaterial_type(rs.getString(6));
			m.setMaterial_count(rs.getInt(7));
		   
		
		    m.setBatch(rs.getString(8));
		    
	 
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



public  List<BeanScrapbound>  searchbound3(String in_id, String waid,String time)throws BaseException{
	
	List<BeanScrapbound> result=new ArrayList<BeanScrapbound>();
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
	
		
		String sql=" select sc_id,m_id,waid,worker_id,sc_time,material_type,material_count,batch ";
		sql+=" from scrap_bound where  waid=? and sc_time=?";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		
		pst.setString(1,waid );
		pst.setString(2,time );
		
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			BeanScrapbound m=new BeanScrapbound();
			m.setSc_id(rs.getString(1));
			m.setM_id(rs.getString(2));
			m.setWaid(rs.getString(3));
			m.setWorker_id(rs.getString(4));
			m.setSc_time(rs.getString(5));
			
			m.setMaterial_type(rs.getString(6));
			m.setMaterial_count(rs.getInt(7));
		   
		
		    m.setBatch(rs.getString(8));
		    
	 
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

