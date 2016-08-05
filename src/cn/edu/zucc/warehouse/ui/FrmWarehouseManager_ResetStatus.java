package cn.edu.zucc.warehouse.ui;

	import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

	import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

	import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.control.WarehouseManager;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.model.BeanWarehouse;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.DBUtil;

	public class FrmWarehouseManager_ResetStatus  extends JDialog implements ActionListener {
		private BeanWarehouse ware=null;
		
		private JPanel toolBar = new JPanel();
		private JPanel workPane = new JPanel();
		private Button btnOk = new Button("确定");
		private Button btnCancel = new Button("取消");
		private JLabel labelWarename = new JLabel("仓库名称：");
		private JLabel labelAddress = new JLabel("地址：");
		private JLabel labelWareman = new JLabel("联系人：");
		
		
		private JTextField edtWarename = new JTextField(20);
		private JTextField edtAddress = new JTextField(20);
		private JComboBox edtWareman = new JComboBox(new String[] {});
		
		
		public FrmWarehouseManager_ResetStatus(JDialog f, String s, boolean b,String waid) {
			super(f, s, b);
			
			
			Connection conn=null;
			
			try {
				conn=DBUtil.getConnection();
				String sql="select address,waname,warman from warehouse where waid=?";
				java.sql.PreparedStatement pst=conn.prepareStatement(sql);
				pst.setString(1,waid);
				java.sql.ResultSet rs=pst.executeQuery();
				while(rs.next()){
					edtAddress.setText(rs.getString(1));
					edtWarename.setText(rs.getString(2));
					
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
				String sql="select worker_name from worker ";
				java.sql.Statement st=conn.createStatement();
				java.sql.ResultSet rs=st.executeQuery(sql);
				while(rs.next()){
					
					edtWareman.addItem(rs.getString(1));
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
			ware=new BeanWarehouse();
			ware.setWaid(waid);
		
			workPane.add(labelWarename);
			workPane.add(edtWarename);
			workPane.add(labelAddress);
			workPane.add(edtAddress);
			workPane.add(labelWareman);
			workPane.add(edtWareman);
			
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
				
			
			String address=edtAddress.getText();
			String warename=edtWarename.getText();
			String warman=edtWareman.getSelectedItem().toString();
				
			
				ware.setWaname(warename);
				ware.setAddress(address);
				ware.setWarman(warman);
				try {
					(new WarehouseManager()).changeWareStatus(ware);
					this.setVisible(false);
				} catch (BaseException e1) {
					this.ware=null;
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
		public BeanWarehouse getwarehouse() {
			return ware;
		}
		
	}
