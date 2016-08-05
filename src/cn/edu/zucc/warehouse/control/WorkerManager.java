




package cn.edu.zucc.warehouse.control;



import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.model.BeanWorker;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;
import cn.edu.zucc.warehouse.util.DbException;

public class WorkerManager  {
	public static BeanWorker currentUser=null;
	public List<BeanWorker> loadAllWorker()throws BaseException{
		List<BeanWorker> result=new ArrayList<BeanWorker>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from worker ";
		
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanWorker w=new BeanWorker();
				w.setWorker_id(rs.getString(1));
				w.setWorker_name(rs.getString(2));
				
			
				
				result.add(w);
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
	
	
	public void deleteWorker(String worker_id)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from worker where worker_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,worker_id);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��Ա���� ����");
			rs.close();
			pst.close();
			
			
			sql="select* from inbound  where worker_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,worker_id);
			 rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("��Ա������Ӧ��������޷�ɾ��");
			rs.close();
			pst.close();
			
			
			sql="select* from outbound  where worker_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,worker_id);
			 rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("��Ա������Ӧ��������޷�ɾ��");
			rs.close();
			pst.close();
			
			
			
			sql="select* from movebound  where worker_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,worker_id);
			 rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("��Ա������Ӧ��������޷�ɾ��");
			rs.close();
			pst.close();
			
			
			
			sql="select* from scrap_bound  where worker_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,worker_id);
			 rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("��Ա������Ӧ��������޷�ɾ��");
			 rs.close();
				pst.close();
			
			sql="select* from backbound  where worker_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,worker_id);
			 rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("��Ա������Ӧ��������޷�ɾ��");
			rs.close();
			pst.close();
			
			String sql1="select worker_name from worker where worker_id=?";
			java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
			pst1.setString(1,worker_id);
			java.sql.ResultSet rs1=pst1.executeQuery();
			String worker_name=null;
			if(rs1.next())
				{
				 worker_name=rs1.getString(1);
				}
			rs1.close();
			pst1.close();
			
			
			
			sql="select* from warehouse  where warman=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,worker_name);
			 rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("��Ա���й�����Ӧ�ֿ⣬�޷�ɾ��");
			rs.close();
			pst.close();
			
			
			sql="delete from worker  where worker_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, worker_id);

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
	
	
	
	
	
	public void createUser(BeanWorker worker)throws BaseException{
		
		if(worker.getWorker_name()==null || "".equals(worker.getWorker_name()) || worker.getWorker_name().length()>50){
			throw new BusinessException("�˺����Ʊ�����1-50����");
		}
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from worker where worker_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,worker.getWorker_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("Ա������Ѿ�����");
			rs.close();
			pst.close();
			sql="insert into worker(worker_id,worker_name) values(?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, worker.getWorker_id());
			pst.setString(2, worker.getWorker_name());
		
		
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

	
	
	public void changeWorker(BeanWorker worker )throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
	
			
			String sql="update worker set  worker_name=?  where worker_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,worker.getWorker_name());
			pst.setString(2,worker.getWorker_id());
			
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
		BeanSystemUser user=new BeanSystemUser();
		user.setUser_id("admin");
		user.setUser_name("ϵͳ����Ա");
		user.setUser_type("����Ա");
		try {
			new SystemUserManager().createUser(user);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
