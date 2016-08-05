package cn.edu.zucc.warehouse.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;
import cn.edu.zucc.warehouse.util.DbException;

public class SystemUserManager {
	public static BeanSystemUser currentUser=null;
	public List<BeanSystemUser> loadAllUsers(boolean withDeletedUser)throws BaseException{
		List<BeanSystemUser> result=new ArrayList<BeanSystemUser>();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_id,user_name,user_type,status from user_table ";
			sql+=" order by user_id";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				BeanSystemUser u=new BeanSystemUser();
				u.setUser_id(rs.getString(1));
				u.setUser_name(rs.getString(2));
				u.setUser_type(rs.getString(3));
				u.setStatus(rs.getString(4));
				result.add(u);
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
	
	
	public void deleteUser(String userid)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from user_table where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
			rs.close();
			pst.close();
			sql="delete from user_table   where user_id=?";
			pst=conn.prepareStatement(sql);
		
			pst.setString(1, userid);
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
	
	
	
	
	
	public void createUser(BeanSystemUser user)throws BaseException{
		if(user.getUser_id()==null || "".equals(user.getUser_id()) || user.getUser_id().length()>20){
			throw new BusinessException("登陆账号必须是1-20个字");
		}
		if(user.getUser_name()==null || "".equals(user.getUser_name()) || user.getUser_name().length()>50){
			throw new BusinessException("账号名称必须是1-50个字");
		}
		if(!"管理员".equals(user.getUser_type()) && !"普通用户".equals(user.getUser_type())){
			throw new BusinessException("用户类别 必须是普通用户或管理员");
		}
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from user_table where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("登陆账号已经存在");
			rs.close();
			pst.close();
			sql="insert into user_table(user_id,user_name,user_password,user_type,status) values(?,?,?,?,'正常')";
			pst=conn.prepareStatement(sql);
			pst.setString(1, user.getUser_id());
			pst.setString(2, user.getUser_name());
			pst.setString(3,user.getUser_password());
			pst.setString(4, user.getUser_type());
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
		if(oldPwd==null) throw new BusinessException("原始密码不能为空");
		if(newPwd==null || "".equals(newPwd) || newPwd.length()>16) throw new BusinessException("必须为1-16个字符");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_password from user_table where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
			if(!oldPwd.equals(rs.getString(1))) throw new BusinessException("原始密码错误");
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
			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
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
	
	public void changeUserStatus(BeanSystemUser user)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from user_table where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUser_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
			rs.close();
			pst.close();
			sql="update user_table set  status=? where user_id=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, user.getStatus());
			pst.setString(2, user.getUser_id());
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
			String sql="select user_id,user_name,user_password,user_type,status from user_table where user_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不 存在");
			BeanSystemUser u=new BeanSystemUser();
			u.setUser_id(rs.getString(1));
			u.setUser_name(rs.getString(2));
			u.setUser_password(rs.getString(3));
			u.setUser_type(rs.getString(4));
			u.setStatus(rs.getString(5));
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
		user.setUser_password("admin");
		user.setUser_name("系统管理员");
		user.setUser_type("管理员");
		user.setStatus("正常");
		try {
			new SystemUserManager().createUser(user);
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
