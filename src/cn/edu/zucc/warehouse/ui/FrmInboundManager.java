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

import cn.edu.zucc.warehouse.control.InboundManager;
import cn.edu.zucc.warehouse.control.MaterialManager;
import cn.edu.zucc.warehouse.control.StockManager;
import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.model.BeanInbound;
import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.model.BeanStock;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.util.BaseException;


public class FrmInboundManager  extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加入库");
	private Object tblTitle[]={"入库单号","仓库编号","物料编号","物料名称","物料类型","单价","物料数量","批次","入库时间"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	private void reloadUserTable(){
		try {
			List<BeanInbound> inbound=(new InboundManager()).loadAllinbound();
			tblData =new Object[inbound.size()][10];
			for(int i=0;i<inbound.size();i++){
				tblData[i][0]=inbound.get(i).getIn_id();
				
				tblData[i][1]=inbound.get(i).getWaid();
				tblData[i][2]=inbound.get(i).getM_id();
				
				String mid=inbound.get(i).getM_id();
				
				BeanMaterial matreial=(new MaterialManager()).loadMaterial(mid);
				
				
				tblData[i][3]=matreial.getM_name();
				
				tblData[i][4]=inbound.get(i).getMaterial_type();
			
				tblData[i][5]=inbound.get(i).getAverage_cost();
				tblData[i][6]=inbound.get(i).getMaterial_count();
				tblData[i][7]=inbound.get(i).getBatch();
				tblData[i][8]=inbound.get(i).getIn_time();
			
			
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmInboundManager(Frame f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		toolBar.add(btnAdd);
		
	
		
	
		
		this.getContentPane().add(toolBar, BorderLayout.NORTH);
		//提取现有数据
		this.reloadUserTable();
		this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(1000, 400);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnAdd.addActionListener(this);
	

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
			FrmInboundlManager_Addinbound dlg=new FrmInboundlManager_Addinbound(this,"添加入库",true);
			dlg.setVisible(true);
			this.reloadUserTable();
		}
	
		
		
		
	}
}
