package cn.edu.zucc.warehouse.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;
import cn.edu.zucc.warehouse.util.DbException;

public class MaterialManager<BeanSystemUser> {
	public static BeanMaterial currentMaterial=null;
	public List<BeanMaterial> loadAllMaterial()throws BaseException{
		List<BeanMaterial> result=new ArrayList<BeanMaterial>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select m_id,m_type,type_id,m_name,manufacturer from Material ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanMaterial m=new BeanMaterial();
				m.setM_id(rs.getString(1));
				m.setM_type(rs.getString(2));
				m.setType_id(rs.getString(3));
				m.setM_name(rs.getString(4));
				m.setManufacturer(rs.getString(5));
				
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
	
	public BeanMaterial loadMaterial(String m_id)throws BaseException{
		BeanMaterial m=new BeanMaterial();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select m_id,m_type,type_id,m_name,manufacturer from Material where m_id=? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,m_id);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
			
				m.setM_id(rs.getString(1));
				m.setM_type(rs.getString(2));
				m.setType_id(rs.getString(3));
				m.setM_name(rs.getString(4));
				m.setManufacturer(rs.getString(5));
				
				
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
		return m;
	}
	
	public void deleteMaterial(String mid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from Material where m_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,mid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("该物料信息不 存在");
			rs.close();
			pst.close();
			
			sql="select* from stock  where m_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,mid);
			 rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该物料还有库存，无法删除");
			
			sql="delete from Material  where m_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, mid);
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
	
	
	
	
	
	public void createMaterial(BeanMaterial material)throws BaseException{
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from Material where m_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,material.getM_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该物料已经存在");
			rs.close();
			pst.close();
		
			
			
			
			
			
			
			sql="insert into Material(m_id,m_name,m_type,manufacturer) values(?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, material.getM_id());
			pst.setString(2, material.getM_name());
			pst.setString(3, material.getM_type());
			pst.setString(4,material.getManufacturer());
		
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


	
	public void modifyMaterial(BeanMaterial material)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from Material where m_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,material.getM_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
			
			rs.close();
			pst.close();
			sql="update Material set m_name=?,m_type=?,manufacturer=? where m_id=?";
			pst=conn.prepareStatement(sql);
			
			pst.setString(1, material.getM_name());
			pst.setString(2, material.getM_type());
			pst.setString(3, material.getManufacturer());
			pst.setString(4,material.getM_id());
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
