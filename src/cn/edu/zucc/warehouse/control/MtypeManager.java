


package cn.edu.zucc.warehouse.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.warehouse.model.BeanMtype;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;
import cn.edu.zucc.warehouse.util.DbException;

public class MtypeManager <BeanSystemUser> {
	public static BeanMtype currentMaterial=null;
	public List<BeanMtype> loadAllMaterial()throws BaseException{
		List<BeanMtype> result=new ArrayList<BeanMtype>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from mtype ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanMtype m=new BeanMtype();
			
			
				
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
	
	
	public void deleteMaterial(int mid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from mtype where m_type=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setInt(1,mid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("该类型不 存在");
			rs.close();
			pst.close();
			sql="delete from Material  where m_id=?";
			pst=conn.prepareStatement(sql);
			pst.setInt(1, mid);
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
	
	
	
	
	
	public void createMaterial(String m_type)throws BaseException{
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from mtype where m_type=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,m_type);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该物料类型已经存在");
			rs.close();
			pst.close();
			sql="insert into mtype(m_type) values(?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, m_type);
			
		
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


	
	
	


	public static void main(String[] args){
		
	}
}

