package cn.edu.zucc.warehouse.ui;





	import java.awt.BorderLayout;
	import java.awt.Button;
	import java.awt.FlowLayout;
	import java.awt.Toolkit;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	import javax.swing.JComboBox;
	import javax.swing.JDialog;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JTextField;

	import cn.edu.zucc.warehouse.control.SystemUserManager;
	import cn.edu.zucc.warehouse.model.BeanSystemUser;
	import cn.edu.zucc.warehouse.util.BaseException;

	public class FrmUserManager_ResetStatus extends JDialog implements ActionListener {
		private BeanSystemUser user=null;
		
		private JPanel toolBar = new JPanel();
		private JPanel workPane = new JPanel();
		private Button btnOk = new Button("确定");
		private Button btnCancel = new Button("取消");
		private JLabel labelUsertype = new JLabel("类别：");
		
		private JComboBox cmbUserStatus= new JComboBox(new String[] { "正常", "锁定"});
		public FrmUserManager_ResetStatus(JDialog f, String s, boolean b,String userid) {
			super(f, s, b);
			user=new BeanSystemUser();
			user.setUser_id(userid);
			toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
			toolBar.add(btnOk);
			toolBar.add(btnCancel);
			this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		
			workPane.add(labelUsertype);
			workPane.add(cmbUserStatus);
			this.getContentPane().add(workPane, BorderLayout.CENTER);
			this.setSize(300, 150);
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
				if(this.cmbUserStatus.getSelectedIndex()<0){
					JOptionPane.showMessageDialog(null,  "请设置账号状态","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				
			
				String userstatus=this.cmbUserStatus.getSelectedItem().toString();
				
			
				user.setStatus(userstatus);
				try {
					(new SystemUserManager()).changeUserStatus(user);
					this.setVisible(false);
				} catch (BaseException e1) {
					this.user=null;
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
			}
			
		}
		public BeanSystemUser getUser() {
			return user;
		}
		
	}
