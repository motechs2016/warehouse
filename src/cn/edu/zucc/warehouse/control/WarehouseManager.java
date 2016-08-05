package cn.edu.zucc.warehouse.control;



	import java.sql.Connection;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

	import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.model.BeanWarehouse;
	import cn.edu.zucc.warehouse.util.BaseException;
	import cn.edu.zucc.warehouse.util.BusinessException;
	import cn.edu.zucc.warehouse.util.DBUtil;
import cn.edu.zucc.warehouse.util.DbException;

	public class WarehouseManager {
		public static BeanWarehouse currentUser=null;
		public List<BeanWarehouse> loadAllWarehouse(boolean withDeletedUser)throws BaseException{
			List<BeanWarehouse> result=new ArrayList<BeanWarehouse>();
			Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql="select waid,waname,warman,address,w_time from warehouse ";
			
				java.sql.Statement st=conn.createStatement();
				java.sql.ResultSet rs=st.executeQuery(sql);
				while(rs.next()){
					BeanWarehouse w=new BeanWarehouse();
					w.setWaid(rs.getString(1));
					w.setWaname(rs.getString(2));
					w.setWarman(rs.getString(3));
					w.setAddress(rs.getString(4));
					w.setW_time(rs.getTimestamp(5));
				
					
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
		
		
		public void deleteWarehouse(String waid)throws BaseException{
			Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql="select * from warehouse where waid=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,waid);
				java.sql.ResultSet rs=pst.executeQuery();
				if(!rs.next()) throw new BusinessException("�ֿⲻ����");
				rs.close();
				pst.close();
				
				sql="select* from stock  where waid=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1,waid);
				 rs=pst.executeQuery();
				if(rs.next()) throw new BusinessException("�òֿ⻹�����ϣ��޷�ɾ��");
				
				
				rs.close();
				pst.close();
				
				sql="delete from warehouse  where waid=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1, waid);
	
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
		
		
		
		
		
		public void createUser(BeanWarehouse warehouse)throws BaseException{
			
			if(warehouse.getWaname()==null || "".equals(warehouse.getWaname()) || warehouse.getWaname().length()>50){
				throw new BusinessException("�˺����Ʊ�����1-50����");
			}
			
			Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql="select * from warehouse where waid=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,warehouse.getWaid());
				java.sql.ResultSet rs=pst.executeQuery();
				if(rs.next()) throw new BusinessException("�ֿ��Ѿ�����");
				rs.close();
				pst.close();
				sql="insert into warehouse(waid,waname,address,warman,w_time) values(?,?,?,?,?)";
				pst=conn.prepareStatement(sql);
				pst.setString(1, warehouse.getWaid());
				pst.setString(2, warehouse.getWaname());
				pst.setString(3, warehouse.getAddress());
				pst.setString(4, warehouse.getWarman());
				pst.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
			
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
		public void changeUserPwd(String userid,String oldPwd,String newPwd)throws BaseException{
			if(oldPwd==null) throw new BusinessException("ԭʼ���벻��Ϊ��");
			if(newPwd==null || "".equals(newPwd) || newPwd.length()>16) throw new BusinessException("����Ϊ1-16���ַ�");
			Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql="select user_password from user_table where user_id=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,userid);
				java.sql.ResultSet rs=pst.executeQuery();
				if(!rs.next()) throw new BusinessException("�ֿⲻ ����");
				if(!oldPwd.equals(rs.getString(1))) throw new BusinessException("ԭʼ�������");
				rs.close();
				pst.close();
				sql="update user_table set user_password=? where user_id=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1, newPwd);
				pst.setString(2, userid);
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
		public void resetUserPwd(String userid)throws BaseException{
			Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql="select * from user_table where user_id=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,userid);
				java.sql.ResultSet rs=pst.executeQuery();
				if(!rs.next()) throw new BusinessException("�ֿⲻ ����");
				rs.close();
				pst.close();
				sql="update user_table set user_password=? where user_id=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1, userid);
				pst.setString(2, userid);
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
		
		public void changeWareStatus(BeanWarehouse warehouse)throws BaseException{
			Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql="select * from warehouse where waid=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,warehouse.getWaid());
				java.sql.ResultSet rs=pst.executeQuery();
				if(!rs.next()) throw new BusinessException("�ֿⲻ ����");
				rs.close();
				pst.close();
				sql="update warehouse set  waname=? , address=? , warman=? where waid=?";
				pst=conn.prepareStatement(sql);
				pst.setString(1, warehouse.getWaname());
				pst.setString(2, warehouse.getAddress());
				pst.setString(3, warehouse.getWarman());
				pst.setString(4, warehouse.getWaid());
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

		public BeanSystemUser loadUser(String userid)throws BaseException{
			Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql="select user_id,user_name,user_password,user_type from user_table where user_id=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,userid);
				java.sql.ResultSet rs=pst.executeQuery();
				if(!rs.next()) throw new BusinessException("��½�˺Ų� ����");
				BeanSystemUser u=new BeanSystemUser();
				u.setUser_id(rs.getString(1));
				u.setUser_name(rs.getString(2));
				u.setUser_password(rs.getString(3));
				u.setUser_type(rs.getString(4));
				
				rs.close();
				pst.close();
				return u;
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
