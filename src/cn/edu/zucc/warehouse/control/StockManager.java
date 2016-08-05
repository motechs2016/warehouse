
package cn.edu.zucc.warehouse.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.zucc.warehouse.model.BeanOutbound;
import cn.edu.zucc.warehouse.model.BeanStock;
import cn.edu.zucc.warehouse.model.BeanStocknobatch;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;
import cn.edu.zucc.warehouse.util.DbException;

public class StockManager  {
	public static BeanStock currentStock=null;
	
	
	public BeanStock loadStock(String stock_id)throws BaseException{
		BeanStock s=new BeanStock();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select m_count,average_cost,batch from stock where stock_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,stock_id);
			
			
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				
				s.setM_count(rs.getInt(1));
				s.setAverage_cost(rs.getFloat(2));
				s.setBatch(rs.getString(3));
				
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
		return s;
	}
	
	
public  BeanStocknobatch  searchstock( String waid,String maid)throws BaseException{
		
		BeanStocknobatch result=new BeanStocknobatch();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
		
			int m_count=0;
			float average_cost=0;
			String sql=" select m_count,average_cost ";
			sql+=" from stock where  waid=? and m_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
	
			pst.setString(1,waid );
			pst.setString(2,maid );
			
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				BeanStocknobatch m=new BeanStocknobatch();
				int mcount1=rs.getInt(1);
				average_cost=average_cost*m_count;
				m_count+=mcount1;
				
				 float cost1=rs.getFloat(2);
				average_cost=(average_cost+cost1*mcount1)/m_count;
			    
				
			}
			
			result.setAverage_cost(average_cost);
			result.setM_count(m_count);
			
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
	
	
	public void createStock(BeanStock stock)throws BaseException{
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select * from stock where stock_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,stock.getM_id());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("该库存单号已经存在");
			rs.close();
			pst.close();
			
			
			
			
			sql="insert into stock(stock_id,m_id,waid,m_count,average_cost,batch) values(?,?,?,?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1, stock.getStock_id());
			pst.setString(2, stock.getM_id());
			pst.setString(3, stock.getWaid());
			pst.setInt(4, stock.getM_count());
			pst.setFloat(5,stock.getAverage_cost());
			pst.setString(6, stock.getBatch());
		
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
	public void backStock(BeanStock stock)throws BaseException{
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			
			String sql1="update stock set m_count=m_count+? where m_id=? and batch=? and waid=?";
			java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
			
			pst1.setInt(1, stock.getM_count());
			pst1.setString(2, stock.getM_id());
			pst1.setString(3, stock.getBatch());
			pst1.setString(4, stock.getWaid());
			pst1.execute();
			pst1.close();
			
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

	public Boolean ScrapStock(BeanStock stock)throws BaseException{
	
		Connection conn=null;
		try {
			
			conn=DBUtil.getConnection();
			String sql="select m_count from stock where m_id=? and waid=? and  batch=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1, stock.getM_id());
			pst.setString(2, stock.getWaid());
			pst.setString(3, stock.getBatch());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()){
				int m_count=rs.getInt(1);
				if(m_count<stock.getM_count())
					return false;

				else
				{
					String sql1="update stock set m_count=m_count-? where m_id=? and batch=? and waid=?";
					java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
					
					pst1.setInt(1, stock.getM_count());
					pst1.setString(2, stock.getM_id());
					pst1.setString(3, stock.getBatch());
					pst1.setString(4, stock.getWaid());
					pst1.execute();
					
					pst1.close();
				}
			}
			rs.close();
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
		return true;
	}

	
	
	public void OutStock(BeanStock stock)throws BaseException{
		Connection conn=null;
		try {
			
			conn=DBUtil.getConnection();
			
			
					String sql1="update stock set m_count=m_count-? where m_id=? and batch=? and waid=?";
					java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
					
					pst1.setInt(1, stock.getM_count());
					pst1.setString(2, stock.getM_id());
					pst1.setString(3, stock.getBatch());
					pst1.setString(4, stock.getWaid());
					pst1.execute();
					
					pst1.close();
			
			
			
			
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
		
    public boolean MoveOutStock(BeanStock stock)throws BaseException{
			Connection conn=null;
			try {
				
				conn=DBUtil.getConnection();
				
		String sql1="select m_count from stock where m_id=? and waid=? and batch=?";
	    java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
	    pst1.setString(1, stock.getM_id());
        pst1.setString(2, stock.getWaid());
	    pst1.setString(3, stock.getBatch());
		java.sql.ResultSet rs1=pst1.executeQuery();
	    while(rs1.next()){
					 int m_count=rs1.getInt(1);
		          if(m_count<stock.getM_count())
			     {
			          return false;
		            //throw new BusinessException("该物料库存不足，无法出库");
			      }
			     else{
						String sql2="update stock set m_count=m_count-? where m_id=? and batch=? and waid=?";
						java.sql.PreparedStatement pst2=conn.prepareStatement(sql2);
						
						pst2.setInt(1, stock.getM_count());
						pst2.setString(2, stock.getM_id());
						pst2.setString(3, stock.getBatch());
						pst2.setString(4, stock.getWaid());
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
		
	
    
    
    
    public int StaticStock(String waid)throws BaseException{
		int count=0;
		Connection conn=null;
		
			 conn=null;
			 try {
				 
				conn=DBUtil.getConnection();
				String sql="select distinct(m_id) from stock where waid=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,waid);
				java.sql.ResultSet rs=pst.executeQuery();
				while(rs.next()){
					count++;
					
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				
				}
				finally{
					if(conn!=null)
						try {
							conn.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
		
    	return count;
	}


	


	public int StaticInbound(String waid, String maid, String starttime,
			String endtime) {
		int incount=0;
		Connection conn=null;
		
		 conn=null;
		 try {
			 
			conn=DBUtil.getConnection();
			String sql="select material_count from inbound where waid=? and m_id=? and in_time>=? and in_time<=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,waid);
			pst.setString(2,maid);
			pst.setString(3,starttime);
			pst.setString(4,endtime);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				incount+=rs.getInt(1);
				
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			
			}
			finally{
				if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		return incount;
	}
	
	public int StaticOutbound(String waid, String maid, String starttime,
			String endtime) {
		int outcount=0;
		Connection conn=null;
		
		 conn=null;
		 try {
			 
			conn=DBUtil.getConnection();
			String sql="select material_count from outbound where waid=? and m_id=? and out_time>=? and out_time<=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,waid);
			pst.setString(2,maid);
			pst.setString(3,starttime);
			pst.setString(4,endtime);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				outcount+=rs.getInt(1);
				
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			
			}
			finally{
				if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		return outcount;
	}
	
	
	public int StaticMovein(String waid, String maid, String starttime,
			String endtime) {
		int moveincount=0;
		Connection conn=null;
		
		 conn=null;
		 try {
			 
			conn=DBUtil.getConnection();
			String sql="select material_count from movebound where waid=? and m_id=? and move_time>=? and move_time<=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,waid);
			pst.setString(2,maid);
			pst.setString(3,starttime);
			pst.setString(4,endtime);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				moveincount+=rs.getInt(1);
				
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			
			}
			finally{
				if(conn!=null)
					try {
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		return moveincount;
	}
	
	



public int StaticMoveout(String waid, String maid, String starttime,
		String endtime) {
	int moveoutcount=0;
	Connection conn=null;
	
	 conn=null;
	 try {
		 
		conn=DBUtil.getConnection();
		String sql="select material_count from movebound where out_waid=? and m_id=? and move_time>=? and move_time<=?";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,waid);
		pst.setString(2,maid);
		pst.setString(3,starttime);
		pst.setString(4,endtime);
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			moveoutcount+=rs.getInt(1);
			
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	return moveoutcount;
}


public int StaticScrap(String waid, String maid, String starttime,
		String endtime) {
	int scrapcount=0;
	Connection conn=null;
	
	 conn=null;
	 try {
		 
		conn=DBUtil.getConnection();
		String sql="select material_count from scrap_bound where waid=? and m_id=? and sc_time>=? and sc_time<=?";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,waid);
		pst.setString(2,maid);
		pst.setString(3,starttime);
		pst.setString(4,endtime);
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			scrapcount+=rs.getInt(1);
			
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	return scrapcount;
}





public int StaticBack(String waid, String maid, String starttime, String endtime) {
	int backcount=0;
	Connection conn=null;
	
	 conn=null;
	 try {
		 
		conn=DBUtil.getConnection();
		String sql="select material_count from backbound where waid=? and m_id=? and back_time>=? and back_time<=?";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,waid);
		pst.setString(2,maid);
		pst.setString(3,starttime);
		pst.setString(4,endtime);
		java.sql.ResultSet rs=pst.executeQuery();
		while(rs.next()){
			backcount+=rs.getInt(1);
		
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	return backcount;
}





public static void main(String[] args){
	
}


public void createMoveInStock(BeanStock stock1) throws DbException, BusinessException {
	Connection conn=null;
	try {
		conn=DBUtil.getConnection();
		String sql="select * from stock where waid=? and batch=?";
		java.sql.PreparedStatement pst=conn.prepareStatement(sql);
		pst.setString(1,stock1.getWaid());
		pst.setString(2,stock1.getBatch());
		java.sql.ResultSet rs=pst.executeQuery();
		if(rs.next()) 
		{
			String sql1="update stock set m_count=m_count+? where waid=? and batch=?";
			java.sql.PreparedStatement pst1=conn.prepareStatement(sql1);
			pst1.setInt(1,stock1.getM_count());
			pst1.setString(2,stock1.getWaid());
			pst1.setString(3,stock1.getBatch());
			pst1.execute();
		}
		
		else{
		rs.close();
		pst.close();
		sql="insert into stock(stock_id,m_id,waid,m_count,average_cost,batch) values(?,?,?,?,?,?)";
		pst=conn.prepareStatement(sql);
		pst.setString(1, stock1.getStock_id());
		pst.setString(2, stock1.getM_id());
		pst.setString(3, stock1.getWaid());
		pst.setInt(4, stock1.getM_count());
		pst.setFloat(5,stock1.getAverage_cost());
		pst.setString(6, stock1.getBatch());
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








}
