package cn.edu.zucc.warehouse.ui;


	import java.awt.BorderLayout;
	import java.awt.Button;
	import java.awt.FlowLayout;
	import java.awt.Frame;
	import java.awt.Toolkit;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.WindowAdapter;
	import java.awt.event.WindowEvent;
	import java.util.List;

	import javax.swing.JDialog;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.table.DefaultTableModel;

	import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.control.WarehouseManager;
	import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.model.BeanWarehouse;
import cn.edu.zucc.warehouse.util.BaseException;


	public class FrmWarehouseManager  extends JDialog implements ActionListener {
		private JPanel toolBar = new JPanel();
		private Button btnAdd = new Button("添加仓库");
		private Button btnDelete = new Button("删除仓库");
		private Button btnResetStatus = new Button("修改仓库信息");
		private Object tblTitle[]={"仓库编号","仓库名称","地址","联系人","创建时间"};
		private Object tblData[][];
		DefaultTableModel tablmod=new DefaultTableModel();
		private JTable userTable=new JTable(tablmod);
		private void reloadWarehouseTable(){
			try {
				List<BeanWarehouse> warehouse=(new WarehouseManager()).loadAllWarehouse(false);
				tblData =new Object[warehouse.size()][5];
				for(int i=0;i<warehouse.size();i++){
					tblData[i][0]=warehouse.get(i).getWaid();
					tblData[i][1]=warehouse.get(i).getWaname();
					tblData[i][2]=warehouse.get(i).getAddress();
					tblData[i][3]=warehouse.get(i).getWarman();
					tblData[i][4]=warehouse.get(i).getW_time();
				}
				tablmod.setDataVector(tblData,tblTitle);
				this.userTable.validate();
				this.userTable.repaint();
			} catch (BaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public FrmWarehouseManager(Frame f, String s, boolean b) {
			super(f, s, b);
			toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
			toolBar.add(btnAdd);
			toolBar.add(this.btnDelete);
			
			toolBar.add(this.btnResetStatus);
			
			this.getContentPane().add(toolBar, BorderLayout.NORTH);
			//提取现有数据
			this.reloadWarehouseTable();
			this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
			
			// 屏幕居中显示
			this.setSize(800, 600);
			double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
			double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
			this.setLocation((int) (width - this.getWidth()) / 2,
					(int) (height - this.getHeight()) / 2);

			this.validate();

			this.btnAdd.addActionListener(this);

			this.btnDelete.addActionListener(this);
			this.btnResetStatus.addActionListener(this);
			this.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					//System.exit(0);
				}
			});
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==this.btnAdd){
				FrmWarehouseManager_Addware dlg=new FrmWarehouseManager_Addware(this,"添加仓库",true);
				dlg.setVisible(true);
				if(dlg.getWarehouse()!=null){//刷新表格
					this.reloadWarehouseTable();
				}
			}
		
			else if(e.getSource()==this.btnDelete){
				int i=this.userTable.getSelectedRow();
				if(i<0) {
					JOptionPane.showMessageDialog(null,  "请选择仓库编号","提示",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(JOptionPane.showConfirmDialog(this,"确定删除仓库吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					String waid=this.tblData[i][0].toString();
					try {
						(new WarehouseManager()).deleteWarehouse(waid);
						this.reloadWarehouseTable();
					} catch (BaseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
					}
					
				}
				
			}
			
			else if(e.getSource()==this.btnResetStatus){
				
				int i=this.userTable.getSelectedRow();
				String waid=this.tblData[i][0].toString();
				FrmWarehouseManager_ResetStatus dlg=new FrmWarehouseManager_ResetStatus(this,"修改仓库信息",true,waid);
				dlg.setVisible(true);
				if(dlg.getwarehouse()!=null){//刷新表格
					this.reloadWarehouseTable();
				}
				
				
			}
		}
	}
