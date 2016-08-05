
package cn.edu.zucc.warehouse.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Date;

import cn.edu.zucc.warehouse.model.BeanInbound;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;
import cn.edu.zucc.warehouse.util.DbException;

public class InboundManager  {
	public static BeanInbound currentInbound=null;
	public List<BeanInbound> loadAllinbound()throws BaseException{
		List<BeanInbound> result=new ArrayList<BeanInbound>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from inbound ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanInbound m=new BeanInbound();
				m.setIn_id(rs.getString(1));
				m.setM_id(rs.getString(2));
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setIn_time(rs.getString(5));
				m.setMaterial_type(rs.getString(6));
				m.setMaterial_count(rs.getInt(7));
			    m.setAverage_cost(rs.getFloat(8));
			    m.setStock_id(rs.getString(9));
			    m.setBatch(rs.getString(10));
		
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
	
	
	
	
	
	
	public void createInbound(BeanInbound inbound)throws BaseException{
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from inbound where in_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,inbound.getIn_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("入库单已经存在");
			rs.close();
			pst.close();
			sql="insert into inbound(in_id,m_id,waid,worker_id,in_time,material_type,material_count,average_cost,batch) values(?,?,?,?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, inbound.getIn_id());
			pst.setString(2, inbound.getM_id());
			pst.setString(3,inbound.getWaid());
			pst.setString(4,inbound.getWorker_id());
			pst.setString(5, new java.sql.Date(System.currentTimeMillis()).toString());
			pst.setString(6, inbound.getMaterial_type());
			pst.setInt(7, inbound.getMaterial_count());
			pst.setFloat(8, inbound.getAverage_cost());
			pst.setString(9, inbound.getBatch());
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

	
	public  List<BeanInbound>  searchbound(String in_id, String waid,String time)throws BaseException{
		
		List<BeanInbound> result=new ArrayList<BeanInbound>();
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
		
			
			String sql=" select in_id,m_id,waid,worker_id,in_time,material_type,material_count,average_cost,batch ";
			sql+=" from inbound where in_id=? and waid=? and in_time=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,in_id);
			pst.setString(2,waid );
			pst.setString(3,time );
			
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanInbound m=new BeanInbound();
				m.setIn_id(rs.getString(1));
				m.setM_id(rs.getString(2));
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setIn_time(rs.getString(5));
				
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






	public List<BeanInbound> searchbound1(String danjuid, String waid,
			String time) throws DbException {
List<BeanInbound> result=new ArrayList<BeanInbound>();
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
		
			
			String sql=" select in_id,m_id,waid,worker_id,in_time,material_type,material_count,average_cost,batch ";
			sql+=" from inbound where in_id=? and waid=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,danjuid);
			pst.setString(2,waid );
			
			
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanInbound m=new BeanInbound();
				m.setIn_id(rs.getString(1));
				m.setM_id(rs.getString(2));
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setIn_time(rs.getString(5));
				
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
	
	public List<BeanInbound> searchbound2(String danjuid, String waid,
			String time) throws DbException {
List<BeanInbound> result=new ArrayList<BeanInbound>();
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
		
			
			String sql=" select in_id,m_id,waid,worker_id,in_time,material_type,material_count,average_cost,batch ";
			sql+=" from inbound where   waid=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			
			pst.setString(1,waid );
			
			
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanInbound m=new BeanInbound();
				m.setIn_id(rs.getString(1));
				m.setM_id(rs.getString(2));
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setIn_time(rs.getString(5));
				
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
	
	
	
	public List<BeanInbound> searchbound3(String danjuid, String waid,
			String time) throws DbException {
List<BeanInbound> result=new ArrayList<BeanInbound>();
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
		
			
			String sql=" select in_id,m_id,waid,worker_id,in_time,material_type,material_count,average_cost,batch ";
			sql+=" from inbound where   waid=? and in_time=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			
			pst.setString(1,waid );
			pst.setString(2,time );
			
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanInbound m=new BeanInbound();
				m.setIn_id(rs.getString(1));
				m.setM_id(rs.getString(2));
				m.setWaid(rs.getString(3));
				m.setWorker_id(rs.getString(4));
				m.setIn_time(rs.getString(5));
				
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

