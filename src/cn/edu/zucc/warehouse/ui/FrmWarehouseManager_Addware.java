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
import javax.swing.JTextField;

	import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.control.WarehouseManager;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.model.BeanWarehouse;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.DBUtil;

	public class FrmWarehouseManager_Addware extends JDialog implements ActionListener {
		private BeanWarehouse warehouse=null;
		
		private JPanel toolBar = new JPanel();
		private JPanel workPane = new JPanel();
		private Button btnOk = new Button("确定");
		private Button btnCancel = new Button("取消");
		private JLabel labelUserid = new JLabel("仓库编号：");
		private JLabel labelUsername = new JLabel("仓库名称：");
		private JLabel labelwareman = new JLabel(" 联系人:     ");
		private JLabel labeladdress = new JLabel("地址:        ");
		
		
		private JTextField edtUserid = new JTextField(20);
		private JTextField edtUsername = new JTextField(20);
		private JComboBox edtwareman = new JComboBox(new String[] {});
		private JTextField edtaddress= new JTextField(20);
		public FrmWarehouseManager_Addware(JDialog f, String s, boolean b) {
			super(f, s, b);
			edtwareman.setPreferredSize(new Dimension(220, 25));
			Connection conn=null;

			try {
				conn=DBUtil.getConnection();
				String sql="select worker_name from worker ";
				java.sql.Statement st=conn.createStatement();
				java.sql.ResultSet rs=st.executeQuery(sql);
				while(rs.next()){
					
					edtwareman.addItem(rs.getString(1));
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
			
			workPane.add(labelUserid);
			workPane.add(edtUserid);
			
			workPane.add(labelUsername);
			workPane.add(edtUsername);
			
			workPane.add(labelwareman);
			workPane.add(edtwareman);
			
			workPane.add(labeladdress);
			workPane.add(edtaddress);
			
			this.getContentPane().add(workPane, BorderLayout.CENTER);
			this.setSize(320, 250);
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
				
				String waid=(this.edtUserid.getText());
				String waname=this.edtUsername.getText();
				String wareman=this.edtwareman.getSelectedItem().toString();
				String address=this.edtaddress.getText();
				warehouse=new BeanWarehouse();
				warehouse.setAddress(address);
				warehouse.setWaid(waid);
				warehouse.setWaname(waname);
				warehouse.setWarman(wareman);
				
				try {
					(new WarehouseManager()).createUser(warehouse);
					this.setVisible(false);
				} catch (BaseException e1) {
					this.warehouse=null;
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
		public BeanWarehouse getWarehouse() {
			return warehouse;
		}
		
	}
