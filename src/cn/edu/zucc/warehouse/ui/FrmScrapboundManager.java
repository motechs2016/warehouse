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
import cn.edu.zucc.warehouse.control.OutboundManager;
import cn.edu.zucc.warehouse.control.ScrapboundManager;
import cn.edu.zucc.warehouse.control.StockManager;
import cn.edu.zucc.warehouse.control.SystemUserManager;

import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.model.BeanScrapbound;
import cn.edu.zucc.warehouse.model.BeanStock;
import cn.edu.zucc.warehouse.util.BaseException;


public class FrmScrapboundManager   extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnAdd = new Button("添加报废");
	private Object tblTitle[]={"报废单号","仓库编号","物料编号","物料名称","物料类型","物料数量","批次","报废时间"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	
	
	
	private void reloadUserTable(){
		try {
			List<BeanScrapbound> scrapbound=(new ScrapboundManager()).loadAllScrapbound();
			tblData =new Object[scrapbound.size()][10];
			for(int i=0;i<scrapbound.size();i++){
				tblData[i][0]=scrapbound.get(i).getSc_id();
				
				tblData[i][1]=scrapbound.get(i).getWaid();
				tblData[i][2]=scrapbound.get(i).getM_id();
				
				String mid=scrapbound.get(i).getM_id();
				
				BeanMaterial matreial=(new MaterialManager()).loadMaterial(mid);
				
				
				tblData[i][3]=matreial.getM_name();
				
				tblData[i][4]=matreial.getM_type();
			
				
				tblData[i][5]=scrapbound.get(i).getMaterial_count();
				tblData[i][6]=scrapbound.get(i).getBatch();
				tblData[i][7]=scrapbound.get(i).getSc_time();
			
			
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public FrmScrapboundManager(Frame f, String s, boolean b) {
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
			FrmScrapboundManager_AddScrapbound dlg=new FrmScrapboundManager_AddScrapbound(this,"添加报废",true);
			dlg.setVisible(true);
			this.reloadUserTable();
		}
	
		
		
		
	}
}
