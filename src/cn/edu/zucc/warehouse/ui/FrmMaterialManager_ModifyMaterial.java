


package cn.edu.zucc.warehouse.ui;

import java.awt.BorderLayout;
import java.awt.Button;
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
import javax.swing.JTextField;

import cn.edu.zucc.warehouse.control.MaterialManager;
import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.control.WarehouseManager;
import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.model.BeanWarehouse;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.DBUtil;

 public class FrmMaterialManager_ModifyMaterial   extends JDialog implements ActionListener {
	private BeanMaterial material=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	
	
	private JLabel labelManame= new JLabel("物料名称：");
	private JLabel labelMatype= new JLabel("物料类型：");
	private JLabel labelManufacturer= new JLabel("生产厂商：");

	
	

	private JTextField edtManame = new JTextField(20);
	private JTextField edtManufacturer=new JTextField(20);
	private JComboBox cmbUsertype= new JComboBox(new String[] { });
	
	
	public FrmMaterialManager_ModifyMaterial(JDialog f, String s, boolean b,String mid) {
		super(f, s, b);
		
		
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select m_type from mtype ";
			java.sql.Statement st=conn.createStatement();
			java.sql.ResultSet rs=st.executeQuery(sql);
			String a=null;
			while(rs.next()){
				
				cmbUsertype.addItem(rs.getString(1));
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
			String sql="select manufacturer,m_name from material where m_id=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,mid);
			java.sql.ResultSet rs=pst.executeQuery();
			while(rs.next()){
				edtManufacturer.setText(rs.getString(1));
				edtManame.setText(rs.getString(2));
			
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
		material=new BeanMaterial();
		material.setM_id(mid);
	
		workPane.add(labelManame);
		workPane.add(edtManame);
	
		
		
		workPane.add(labelManufacturer);
		workPane.add(edtManufacturer);
		
		workPane.add(labelMatype);
		workPane.add(cmbUsertype);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 180);
		// 屏幕居中显示
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnOk.addActionListener(this);
		this.btnCancel.addActionListener(this);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnCancel) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.btnOk){
			
		
			String maName=this.edtManame.getText();
			String maType=this.cmbUsertype.getSelectedItem().toString();
			String maManufacturer=this.edtManufacturer.getText();
			
			material.setManufacturer(maManufacturer);
			material.setM_name(maName);
			material.setM_type(maType);
			
			try {
				(new MaterialManager()).modifyMaterial(material);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.material=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanMaterial getwarehouse() {
		return material;
	}
	
}
