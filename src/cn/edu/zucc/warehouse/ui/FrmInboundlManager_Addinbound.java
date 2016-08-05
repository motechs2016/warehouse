


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

import cn.edu.zucc.warehouse.control.InboundManager;
import cn.edu.zucc.warehouse.control.StockManager;
import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.model.BeanInbound;
import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.model.BeanStock;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.DBUtil;

public class FrmInboundlManager_Addinbound  extends JDialog implements ActionListener {
	private BeanInbound inbound=null;
	private BeanMaterial m=null;
	String m_id=null;
	
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("ȷ��");
	private Button btnCancel = new Button("ȡ��");
	private JLabel labelInid = new JLabel("��ⵥ�ţ�");
	private JLabel labelMaid = new JLabel("���ϱ�ţ�");
	private JLabel labelWaid = new JLabel("�ֿ��ţ�");
	private JLabel labelWorkerid = new JLabel("���˱�ţ�");
	private JLabel labelManame= new JLabel("�������ƣ�");
	private JLabel labelMatype= new JLabel("�������ͣ�");
	private JLabel labelMacount = new JLabel("����������");
	private JLabel labelWacost = new JLabel("���ϵ��ۣ�");
	private JLabel labelManufacturer= new JLabel("�������̣�");
	private JLabel labelBatch= new JLabel("�������Σ�");
	
	private JTextField edtInid = new JTextField(20);
	private JComboBox edtMaid = new JComboBox(new String[] {});
	private JComboBox edtWaid = new JComboBox(new String[] {});
	private JTextField edtManame = new JTextField(20);
	private JComboBox edtWorkerid =new JComboBox(new String[] {});
	private JTextField edtMacount=new JTextField(20);
	private JTextField edtWacost=new JTextField(20);
	private JTextField edtManufacturer=new JTextField(20);
	private JTextField edtMatype= new JTextField(20);
	private JTextField edtBatch= new JTextField(20);
	
	
	public FrmInboundlManager_Addinbound(JDialog f, String s, boolean b) {
		super(f, s, b);
		edtMaid.setPreferredSize(new Dimension(220, 25));
		edtWaid.setPreferredSize(new Dimension(220, 25));
		edtWorkerid.setPreferredSize(new Dimension(220, 25));
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select m_id from material ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				
				edtMaid.addItem(rs.getString(1));
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
		
		String m_id=this.edtMaid.getSelectedItem().toString();
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
			
			
		}catch (SQLException e) {
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
		
		
		
		
		
		 conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select waid from warehouse ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			while(rs.next()){
				
				edtWaid.addItem(rs.getString(1));
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
		
		 conn=null;
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
	
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(330, 480);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		this.edtMaid.addActionListener(this);
		
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		
		
		else if(e.getSource()==this.edtMaid){
			System.out.println("11");
			 m_id=this.edtMaid.getSelectedItem().toString();
			 Connection conn=null;
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
			if(this.edtInid.getText().equals("")){
				JOptionPane.showMessageDialog(null,  "����д����","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(this.edtMacount.getText().equals("")){
				JOptionPane.showMessageDialog(null,  "����д����","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(this.edtWacost.getText().equals("")){
				JOptionPane.showMessageDialog(null,  "����д����","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(this.edtBatch.getText().equals("")){
				JOptionPane.showMessageDialog(null,  "����д����","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			
			
			inbound=new BeanInbound();
			String in_id=this.edtInid.getText();
			String maid=this.edtMaid.getSelectedItem().toString();
			String waid=this.edtWaid.getSelectedItem().toString();
			String wmaname=this.edtManame.getText();
			String workerid=this.edtWorkerid.getSelectedItem().toString();
			int macount=Integer.parseInt(this.edtMacount.getText());
			float macost=Float.parseFloat(this.edtWacost.getText());
			String manufacturer=this.edtManufacturer.getText();
			String matype=this.edtMatype.getText();
			String batch=this.edtBatch.getText();
			if(macount<=0){
				JOptionPane.showMessageDialog(null,  "���������������С�ڵ�����","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(macost<=0){
				JOptionPane.showMessageDialog(null,  "������ϵ��۲���С�ڵ�����","��ʾ",JOptionPane.ERROR_MESSAGE);
				return;
			}
			Connection conn=null;
			 try {
				
					conn=DBUtil.getConnection();
					String sql="select * from inbound where batch=?" ;
					
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					pst.setString(1,batch);
					java.sql.ResultSet rs=pst.executeQuery();
				
					if(rs.next())
					{
						JOptionPane.showMessageDialog(null,  "�������Ѿ�����","��ʾ",JOptionPane.ERROR_MESSAGE);
						return;
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
			inbound.setIn_id(in_id);
			inbound.setM_id(maid);
			inbound.setWaid(waid);
			inbound.setWorker_id(workerid);
			inbound.setMaterial_count(macount);
			inbound.setAverage_cost(macost);
			inbound.setMaterial_type(matype);
			inbound.setBatch(batch);
			BeanStock stock=new BeanStock();
			stock.setAverage_cost(macost);
			stock.setBatch(batch);
			stock.setM_count(macount);
			stock.setM_id(maid);
			stock.setWaid(waid);
			stock.setStock_id(in_id);
			try {
				(new InboundManager()).createInbound(inbound);
				(new StockManager()).createStock(stock);
				this.setVisible(false);
			} catch (BaseException e1) {
				
				JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	
}
