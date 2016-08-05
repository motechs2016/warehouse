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

import cn.edu.zucc.warehouse.control.MaterialManager;
import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.util.BaseException;


public class FrmMaterialManager  extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加物料信息");
	private Button btnDelete = new Button("删除物料信息");
	private Button btnModify = new Button("修改物料信息");
	private Button btnAddtype = new Button("添加物料类型");

	private Object tblTitle[]={"物料编号","物料名称","物料类型","生产产商"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	private void reloadUserTable(){
		try {
			List<BeanMaterial> material=(new MaterialManager()).loadAllMaterial();
			
			tblData =new Object[material.size()][10];
			for(int i=0;i<material.size();i++){
				
				tblData[i][0]=material.get(i).getM_id();
				tblData[i][1]=material.get(i).getM_name();
				tblData[i][2]=material.get(i).getM_type();
				tblData[i][3]=material.get(i).getManufacturer();
				
			
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmMaterialManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		
		toolBar.add(this.btnDelete);
		toolBar.add(this.btnModify);
		toolBar.add(this.btnAddtype);
		
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadUserTable();
		this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(1000, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
	
		this.btnDelete.addActionListener(this);
		this.btnModify.addActionListener(this);
		this.btnAddtype.addActionListener(this);
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
			FrmMaterialManager_AddMaterial dlg=new FrmMaterialManager_AddMaterial(this,"添加物料信息",true);
			dlg.setVisible(true);
			
				this.reloadUserTable();
		
		}
	
		else if(e.getSource()==this.btnDelete){
			int i=this.userTable.getSelectedRow();
			if(JOptionPane.showConfirmDialog(this,"确定删除该物料信息吗？","确认",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				String mid=this.tblData[i][0].toString();
				try {
					(new MaterialManager()).deleteMaterial(mid);
					this.reloadUserTable();
				} catch (BaseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			
		}
		
		
		
		else if(e.getSource()==this.btnModify){
			int i=this.userTable.getSelectedRow();
			String mid=this.tblData[i][0].toString();
			FrmMaterialManager_ModifyMaterial dlg=new FrmMaterialManager_ModifyMaterial(this,"修改仓库信息",true,mid);
			dlg.setVisible(true);
			if(dlg.getwarehouse()!=null){//刷新表格
				this.reloadUserTable();
			}
			
		}
		
		
		
		else if(e.getSource()==this.btnAddtype){
			int i=this.userTable.getSelectedRow();
			
			FrmMaterialManager_AddMaterialType dlg=new FrmMaterialManager_AddMaterialType(this,"添加物料类型",true);
			dlg.setVisible(true);
			
			
		}
		
		
		
		
		
		
		
	}
}
