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
		private Button btnAdd = new Button("��Ӳֿ�");
		private Button btnDelete = new Button("ɾ���ֿ�");
		private Button btnResetStatus = new Button("�޸Ĳֿ���Ϣ");
		private Object tblTitle[]={"�ֿ���","�ֿ�����","��ַ","��ϵ��","����ʱ��"};
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
			//��ȡ��������
			this.reloadWarehouseTable();
			this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
			
			// ��Ļ������ʾ
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
				FrmWarehouseManager_Addware dlg=new FrmWarehouseManager_Addware(this,"��Ӳֿ�",true);
				dlg.setVisible(true);
				if(dlg.getWarehouse()!=null){//ˢ�±��
					this.reloadWarehouseTable();
				}
			}
		
			else if(e.getSource()==this.btnDelete){
				int i=this.userTable.getSelectedRow();
				if(i<0) {
					JOptionPane.showMessageDialog(null,  "��ѡ��ֿ���","��ʾ",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(JOptionPane.showConfirmDialog(this,"ȷ��ɾ���ֿ���","ȷ��",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
					String waid=this.tblData[i][0].toString();
					try {
						(new WarehouseManager()).deleteWarehouse(waid);
						this.reloadWarehouseTable();
					} catch (BaseException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage(),"����",JOptionPane.ERROR_MESSAGE);
					}
					
				}
				
			}
			
			else if(e.getSource()==this.btnResetStatus){
				
				int i=this.userTable.getSelectedRow();
				String waid=this.tblData[i][0].toString();
				FrmWarehouseManager_ResetStatus dlg=new FrmWarehouseManager_ResetStatus(this,"�޸Ĳֿ���Ϣ",true,waid);
				dlg.setVisible(true);
				if(dlg.getwarehouse()!=null){//ˢ�±��
					this.reloadWarehouseTable();
				}
				
				
			}
		}
	}
