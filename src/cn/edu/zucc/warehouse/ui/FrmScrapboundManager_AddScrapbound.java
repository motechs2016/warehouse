
package cn.edu.zucc.warehouse.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.edu.zucc.warehouse.control.ScrapboundManager;
import cn.edu.zucc.warehouse.control.StockManager;
import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.model.BeanScrapbound;
import cn.edu.zucc.warehouse.model.BeanStock;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;

 public class FrmScrapboundManager_AddScrapbound   extends JDialog implements ActionListener {
	private BeanScrapbound scrapbound=null;
	private BeanMaterial m=null;
	String m_id=null;
	String waid=null;
	String batch=null;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelInid = new JLabel("报废单号：");
	private JLabel labelMaid = new JLabel("物料编号：");
	private JLabel labelWaid = new JLabel("仓库编号：");
	private JLabel labelWorkerid = new JLabel("工人编号：");
	private JLabel labelManame= new JLabel("物料名称：");
	private JLabel labelMatype= new JLabel("物料类型：");
	private JLabel labelMacount = new JLabel("物料数量：");
	private JLabel labelWacost = new JLabel("物料单价：");
	private JLabel labelManufacturer= new JLabel("生产厂商：");
	private JLabel labelBatch= new JLabel("物料批次：");
	
	private JTextField edtInid = new JTextField(20);
	private JComboBox edtMaid = new JComboBox(new String[] {});
	private JComboBox edtWaid = new JComboBox(new String[] {});
	private JTextField edtManame = new JTextField(20);
	private JComboBox edtWorkerid =new JComboBox(new String[] {});
	private JTextField edtMacount=new JTextField(20);
	private JTextField edtWacost=new JTextField(20);
	private JTextField edtManufacturer=new JTextField(20);
	private JTextField edtMatype= new JTextField(20);
	private JComboBox edtBatch= new JComboBox(new String[] {});
	
	
	public FrmScrapboundManager_AddScrapbound(JDialog f, String s, boolean b) {
		super(f, s, b);
		edtMaid.setPreferredSize(new Dimension(220, 25));
		edtWaid.setPreferredSize(new Dimension(220, 25));
		edtWorkerid.setPreferredSize(new Dimension(220, 25));
		edtBatch.setPreferredSize(new Dimension(220, 25));
		Connection conn=null;
		
		
		
		try {
			conn=DBUtil.getConnection();
			String sql="select waid from warehouse ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				waid=rs.getString(1);
				edtWaid.addItem(waid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		
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
		
		
		
		
		
		

		
		
			try {
				conn=DBUtil.getConnection();
				String sql="select worker_id from worker ";
				java.sql.Statement st=conn.createStatement();
				java.sql.ResultSet rs=st.executeQuery(sql);
				while(rs.next()){
					
					edtWorkerid.addItem(rs.getString(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			
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

		
			 waid=this.edtWaid.getSelectedItem().toString();
			
			
			 conn=null;
			 try {
				 
					conn=DBUtil.getConnection();
					String sql="select distinct(m_id) from stock where waid=?";
					System.out.println(waid);
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					pst.setString(1,waid);
					java.sql.ResultSet rs=pst.executeQuery();
					while(rs.next()){
						
						edtMaid.addItem(rs.getString(1));
					
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
			
			 
		 
			 
			 m_id=this.edtMaid.getSelectedItem().toString();
		
				try {
				
					conn=DBUtil.getConnection();
					String sql="select m_type,m_name,manufacturer from material where m_id=?";
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					pst.setString(1,m_id);
					java.sql.ResultSet rs=pst.executeQuery();
					if(rs.next()){
						BeanMaterial m=new BeanMaterial();
						m.setM_type(rs.getString(1));
						m.setM_name(rs.getString(2));
						m.setManufacturer(rs.getString(3));
					
						edtManufacturer.setText(m.getManufacturer());
						edtMatype.setText(m.getM_type());
						edtManame.setText(m.getM_name());
					}
					
					
				}catch (SQLException e1) {
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
	
				
				 try {
					 
						conn=DBUtil.getConnection();
						String sql="select batch from stock where m_id=? and waid=?";
					
						java.sql.PreparedStatement pst=conn.prepareStatement(sql);
						pst.setString(1,m_id);
						pst.setString(2, waid);
						java.sql.ResultSet rs=pst.executeQuery();
						while(rs.next()){
							
							edtBatch.addItem(rs.getString(1));
						
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
				 
				 
				 edtBatch.setSelectedIndex(0);
				batch=this.edtBatch.getSelectedItem().toString();
				 
				 try {
						
						conn=DBUtil.getConnection();
						String sql="select m_count,average_cost from stock where batch=? and waid=?" ;
						
						java.sql.PreparedStatement pst=conn.prepareStatement(sql);
						pst.setString(1,batch);
						pst.setString(2,waid);
						java.sql.ResultSet rs=pst.executeQuery();
					
						while(rs.next()){
							edtMacount.setText(rs.getString(1));
							edtWacost.setText(rs.getString(2));
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
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelInid);
		workPane.add(edtInid);
		
	
		
		workPane.add(labelWaid);
		workPane.add(edtWaid);
	
		workPane.add(labelWorkerid);
		workPane.add(edtWorkerid);
		
		workPane.add(labelMaid);
		workPane.add(edtMaid);
		
		workPane.add(labelManame);
		workPane.add(edtManame);
		
		workPane.add(labelMatype);
		workPane.add(edtMatype);


		
		workPane.add(labelManufacturer);
		workPane.add(edtManufacturer);
		
		workPane.add(labelBatch);
		workPane.add(edtBatch);
		
		workPane.add(labelMacount);
		workPane.add(edtMacount);
		
		workPane.add(labelWacost);
		workPane.add(edtWacost);
		
		
		edtManufacturer.setEditable(false);
		edtMatype.setEditable(false);
		edtManame.setEditable(false);
		edtWacost.setEditable(false);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(330, 480);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.edtMaid.addActionListener(this);
		this.edtWaid.addActionListener(this);
		this.edtBatch.addActionListener(this);
		
		
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		
		
		else if(e.getSource()==this.edtMaid){
			Connection conn=null;
			edtBatch.setSelectedIndex(0);
			 m_id=this.edtMaid.getSelectedItem().toString();
			 
			 this.edtBatch.removeActionListener(this);
			 edtBatch.removeAllItems();
			
			 
			 try {
				
					conn=DBUtil.getConnection();
					String sql="select batch from stock where m_id=? and waid=?" ;
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					pst.setString(1,m_id);
					pst.setString(2, waid);
					java.sql.ResultSet rs=pst.executeQuery();
					while(rs.next()){
						edtBatch.addItem(rs.getString(1));
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
				
			 
				this.edtBatch.addActionListener(this);
			 
				try {
				
					conn=DBUtil.getConnection();
					String sql="select m_type,m_name,manufacturer from material where m_id=?";
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					pst.setString(1,m_id);
					java.sql.ResultSet rs=pst.executeQuery();
					if(rs.next()){
						BeanMaterial m=new BeanMaterial();
						m.setM_type(rs.getString(1));
						m.setM_name(rs.getString(2));
						m.setManufacturer(rs.getString(3));
					
						edtManufacturer.setText(m.getManufacturer());
						edtMatype.setText(m.getM_type());
						edtManame.setText(m.getM_name());
					}
					
					
				}catch (SQLException e1) {
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
				
				 edtBatch.setSelectedIndex(0);
					batch=this.edtBatch.getSelectedItem().toString();
					 
					 try {
							
							conn=DBUtil.getConnection();
							String sql="select m_count,average_cost from stock where batch=? and waid=?" ;
							
							java.sql.PreparedStatement pst=conn.prepareStatement(sql);
							pst.setString(1,batch);
							pst.setString(2,waid);
							java.sql.ResultSet rs=pst.executeQuery();
						
							while(rs.next()){
								edtMacount.setText(rs.getString(1));
								edtWacost.setText(rs.getString(2));
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
			
			
		}
		
		
		
		else if(e.getSource()==this.edtWaid){
			edtMaid.setSelectedIndex(0);
			edtBatch.setSelectedIndex(0);
			 waid=this.edtWaid.getSelectedItem().toString();
			 Connection conn=null;
			 this.edtMaid.removeActionListener(this);
			 edtMaid.removeAllItems();
			 String[] material=new String[]{}; 
			 try {	
					conn=DBUtil.getConnection();
					String sql="select distinct(m_id) from stock where waid=?" ;
					
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					pst.setString(1,waid);
					java.sql.ResultSet rs=pst.executeQuery();
				
					while(rs.next()){
						
						 edtMaid.addItem(rs.getString(1));
					
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
				
			
			 
			 
			 this.edtMaid.addActionListener(this);
			 edtMaid.setSelectedIndex(0);
			 edtBatch.setSelectedIndex(0);
			 
			 m_id=this.edtMaid.getSelectedItem().toString();
			  conn=null;
				try {
				
					conn=DBUtil.getConnection();
					String sql="select m_type,m_name,manufacturer from material where m_id=?";
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					pst.setString(1,m_id);
					java.sql.ResultSet rs=pst.executeQuery();
					if(rs.next()){
						BeanMaterial m=new BeanMaterial();
						m.setM_type(rs.getString(1));
						m.setM_name(rs.getString(2));
						m.setManufacturer(rs.getString(3));
					
						edtManufacturer.setText(m.getManufacturer());
						edtMatype.setText(m.getM_type());
						edtManame.setText(m.getM_name());
					}
					
					
				}catch (SQLException e1) {
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
			 
			 
				
				
				edtBatch.setSelectedIndex(0);
				 m_id=this.edtMaid.getSelectedItem().toString();
				 
				 this.edtBatch.removeActionListener(this);
				 edtBatch.removeAllItems();
				
				 
				 try {
					
						conn=DBUtil.getConnection();
						String sql="select batch from stock where m_id=? and waid=?" ;
						java.sql.PreparedStatement pst=conn.prepareStatement(sql);
						pst.setString(1,m_id);
						pst.setString(2, waid);
						java.sql.ResultSet rs=pst.executeQuery();
						while(rs.next()){
							edtBatch.addItem(rs.getString(1));
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
					
				 
					this.edtBatch.addActionListener(this);
				
					 edtBatch.setSelectedIndex(0);
						batch=this.edtBatch.getSelectedItem().toString();
						 
						 try {
								
								conn=DBUtil.getConnection();
								String sql="select m_count,average_cost from stock where batch=? and waid=?" ;
								
								java.sql.PreparedStatement pst=conn.prepareStatement(sql);
								pst.setString(1,batch);
								pst.setString(2,waid);
								java.sql.ResultSet rs=pst.executeQuery();
							
								while(rs.next()){
									edtMacount.setText(rs.getString(1));
									edtWacost.setText(rs.getString(2));
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
				
				
		}
		
		
		else if(e.getSource()==this.edtBatch){	
			 
				batch=this.edtBatch.getSelectedItem().toString();
				Connection conn=null;
				 try {
						
						conn=DBUtil.getConnection();
						String sql="select m_count,average_cost from stock where batch=? and waid=?" ;
						
						java.sql.PreparedStatement pst=conn.prepareStatement(sql);
						pst.setString(1,batch);
						pst.setString(2,waid);
						java.sql.ResultSet rs=pst.executeQuery();
					
						while(rs.next()){
							edtMacount.setText(rs.getString(1));
							edtWacost.setText(rs.getString(2));
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
				
			
			
		}
		
		
		
		else if(e.getSource()==this.btnOk){
			if(this.edtInid.getText().equals("")){
				JOptionPane.showMessageDialog(null,  "请填写完整","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(this.edtMacount.getText().equals("")){
				JOptionPane.showMessageDialog(null,  "请填写完整","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			scrapbound=new BeanScrapbound();
			String in_id=this.edtInid.getText();
			
			waid=this.edtWaid.getSelectedItem().toString();
			String wmaname=this.edtManame.getText();
			String workerid=this.edtWorkerid.getSelectedItem().toString();
			int macount=Integer.parseInt(this.edtMacount.getText());
			float macost=Float.parseFloat(this.edtWacost.getText());
			String manufacturer=this.edtManufacturer.getText();
			String matype=this.edtMatype.getText();
			String batch=this.edtBatch.getSelectedItem().toString();
			if(macount<=0){
				JOptionPane.showMessageDialog(null,  "报废物料数量不能小于等于零","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			scrapbound.setSc_id(in_id);
			scrapbound.setM_id(m_id);
			scrapbound.setWaid(waid);
			scrapbound.setWorker_id(workerid);
			scrapbound.setMaterial_count(macount);
			scrapbound.setMaterial_type(matype);
			scrapbound.setBatch(batch);
			BeanStock stock=new BeanStock();
			stock.setAverage_cost(macost);
			stock.setBatch(batch);
			stock.setM_count(macount);
			stock.setM_id(m_id);
			stock.setWaid(waid);
			try {
				
				boolean flag=(new StockManager()).ScrapStock(stock);
				if(flag)
				(new ScrapboundManager()).createScrapbound(scrapbound);
				else
					throw new BusinessException("该物料库存不足");
				this.setVisible(false);
			} catch (BaseException e1) {
				
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	
}

