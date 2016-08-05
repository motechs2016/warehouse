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

import cn.edu.zucc.warehouse.control.BackboundManager;
import cn.edu.zucc.warehouse.control.OutboundManager;
import cn.edu.zucc.warehouse.control.StockManager;
import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.model.BeanBackbound;
import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.model.BeanStock;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.BusinessException;
import cn.edu.zucc.warehouse.util.DBUtil;

public class FrmBackboundlManager_AddBackbound   extends JDialog implements ActionListener {
	private BeanBackbound backbound=null;
	private BeanMaterial m=null;
	String m_id=null;
	String waid=null;
	String batch=null;
	String out_id=null;
	int macount2=0;
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelBackid = new JLabel("退库单号：");
	private JLabel labelOutid = new JLabel("出库单号：");
	private JLabel labelMaid = new JLabel("物料编号：");
	private JLabel labelWaid = new JLabel("仓库编号：");
	private JLabel labelWorkerid = new JLabel("工人编号：");
	private JLabel labelManame= new JLabel("物料名称：");
	private JLabel labelMatype= new JLabel("物料类型：");
	private JLabel labelMacount = new JLabel("物料数量：");
	private JLabel labelMacost = new JLabel("物料单价：");
	private JLabel labelManufacturer= new JLabel("生产厂商：");
	private JLabel labelBatch= new JLabel("物料批次：");
	
	private JTextField edtBackid = new JTextField(20);
	private JComboBox edtOutid =new JComboBox(new String[] {});
	private JTextField edtMaid = new JTextField(20);
	private JTextField edtWaid = new JTextField(20);
	private JTextField edtManame = new JTextField(20);
	private JTextField edtWorkerid =new JTextField(20);
	private JTextField edtMacount=new JTextField(20);
	private JTextField edtMacost=new JTextField(20);
	private JTextField edtManufacturer=new JTextField(20);
	private JTextField edtMatype= new JTextField(20);
	private JTextField edtBatch=new JTextField(20);
	
	
	public FrmBackboundlManager_AddBackbound (JDialog f, String s, boolean b) {
		super(f, s, b);
	
		edtOutid.setPreferredSize(new Dimension(220, 25));
		Connection conn=null;

		
		try {
			conn=DBUtil.getConnection();
			String sql="select out_id from outbound ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				out_id=rs.getString(1);
				edtOutid.addItem(out_id);
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
		
		
		
		edtOutid.setSelectedIndex(0);
		out_id=this.edtOutid.getSelectedItem().toString();
		
		try {
			conn=DBUtil.getConnection();
			String sql="select waid,m_id,worker_id,batch,material_count,average_cost from outbound where out_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,out_id);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				waid=rs.getString(1);
				edtWaid.setText(waid);
				m_id=rs.getString(2);
				edtMaid.setText(m_id);
				edtWorkerid.setText(rs.getString(3));
				batch=rs.getString(4);
				edtBatch.setText(batch);
				macount2=rs.getInt(5);
				edtMacount.setText(Integer.toString(macount2));
				edtMacost.setText(rs.getString(6));
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
	
				
				
				 
				 
				
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		workPane.add(labelBackid);
		workPane.add(edtBackid);
		
		workPane.add(labelOutid);
		workPane.add(edtOutid);
		
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
		
		workPane.add(labelMacost);
		workPane.add(edtMacost);
		
		workPane.add(labelMacount);
		workPane.add(edtMacount);
		
		
		
		
		edtManufacturer.setEditable(false);
		edtMatype.setEditable(false);
		edtManame.setEditable(false);
		edtMaid.setEditable(false);
		edtWaid.setEditable(false);
		edtWorkerid.setEditable(false);
		edtBatch.setEditable(false);
		edtMacost.setEditable(false);
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
		
		this.edtOutid.addActionListener(this);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		
		
	
		
		
		else if(e.getSource()==this.edtOutid){	
			
				
			out_id=this.edtOutid.getSelectedItem().toString();
			Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql="select waid,m_id,worker_id,batch,material_count,average_cost from outbound where out_id=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,out_id);
				java.sql.ResultSet rs=pst.executeQuery();
				while(rs.next()){
					waid=rs.getString(1);
					edtWaid.setText(waid);
					m_id=rs.getString(2);
					edtMaid.setText(m_id);
					edtWorkerid.setText(rs.getString(3));
					batch=rs.getString(4);
					edtBatch.setText(batch);
					macount2=rs.getInt(5);
					edtMacount.setText(Integer.toString(macount2));
					edtMacost.setText(rs.getString(6));
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
		
			
		}
		
		else if(e.getSource()==this.btnOk){
			
			
			backbound=new BeanBackbound();
			String back_id=this.edtBackid.getText();
			String out_id=this.edtOutid.getSelectedItem().toString();
			 waid=this.edtWaid.getText();
			String wmaname=this.edtManame.getText();
			String workerid=this.edtWorkerid.getText();
			int macount1=Integer.parseInt(this.edtMacount.getText());
			float macost=Float.parseFloat(this.edtMacost.getText());
			String manufacturer=this.edtManufacturer.getText();
			String matype=this.edtMatype.getText();
			String batch=this.edtBatch.getText();
			
			if(macount1<=0){
				JOptionPane.showMessageDialog(null,  "报废物料数量不能小于等于零","提示",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			
			backbound.setBack_id(back_id);
			backbound.setM_id(m_id);
			backbound.setWaid(waid);
			backbound.setWorker_id(workerid);
			backbound.setMaterial_count(macount1);
			backbound.setAverage_cost(macost);
			backbound.setMaterial_type(matype);
			backbound.setOut_id(out_id);
			backbound.setBatch(batch);
			BeanStock stock=new BeanStock();
			stock.setAverage_cost(macost);
			stock.setBatch(batch);
			stock.setM_count(macount1);
			stock.setM_id(m_id);
			stock.setWaid(waid);
			if(back_id.equals(""))
				{
				JOptionPane.showMessageDialog(null,  "请填写退库单号","提示",JOptionPane.ERROR_MESSAGE);
				return;
				
				}
			if(macount1>macount2)
			{
			JOptionPane.showMessageDialog(null,  "退库数量超过出库数量，无法退库","提示",JOptionPane.ERROR_MESSAGE);
			return;
			
			}
			if(macount1<0)
			{
			JOptionPane.showMessageDialog(null,  "退库数量不能为负数，无法退库","提示",JOptionPane.ERROR_MESSAGE);
			return;
			
			}
			
			
			try {
				(new BackboundManager()).createBackbound(backbound);
				 (new StockManager()).backStock(stock);
				 (new OutboundManager()).BackOutbound(backbound);
				this.setVisible(false);
			} catch (BaseException e1) {
				
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	
}

