package cn.edu.zucc.warehouse.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.DBUtil;
import java.sql.Connection;

import cn.edu.zucc.warehouse.control.MaterialManager;
import cn.edu.zucc.warehouse.control.SystemUserManager;

public class FrmMain extends JFrame implements ActionListener {
	String typeid=null;
	
	private JMenuBar menubar=new JMenuBar(); 
    private JMenu menu_Manager=new JMenu("系统管理");
    private JMenu menu_lend=new JMenu("仓库管理");
    private JMenu menu_Material=new JMenu("物料管理");
    private JMenu menu_Stock=new JMenu("库操作管理");
    private JMenu menu_Statistics=new JMenu("盘点报表");
    private JMenu menu_search=new JMenu("查询");
    private JMenu menu_Worker=new JMenu("员工管理");
    
    private JMenuItem  menuItem_UserManager=new JMenuItem("用户管理");
 

    
    
 
    private JMenuItem  menuItem_Warehouse=new JMenuItem("仓库基本信息管理");

    
    private JMenuItem  menuItem_material=new JMenuItem("物料基本信息管理");
    private JMenuItem  menuItem_material1=new JMenuItem("入库管理");
    private JMenuItem  menuItem_material2=new JMenuItem("出库管理");
    private JMenuItem  menuItem_material3=new JMenuItem("移库管理");
    private JMenuItem  menuItem_material4=new JMenuItem("报废管理");
    private JMenuItem  menuItem_material5=new JMenuItem("退库管理");
  
    
    private JMenuItem  menuItem_BookLendSearch=new JMenuItem("库存盘点");


    
    private JMenuItem  menuItem_search1=new JMenuItem("单据查询");
    private JMenuItem  menuItem_search2=new JMenuItem("库存查询");
    
    private JMenuItem  menuItem_Worker=new JMenuItem("员工管理");
    
	private Object tblTypeTitle[]={"仓库名称","仓库编号","地址","联系人","创建时间"};
	private Object tblTypeData[][];
	DefaultTableModel tabTypeModel=new DefaultTableModel();
	private JTable dataTableType=new JTable(tabTypeModel);
	
	private Object tblItemTitle[]={"物料编号","仓库编号","物料名称","物料类型","生产厂商","平均成本","物料数量","批次"};
	Object tblItemData[][];
	DefaultTableModel tabItemModel=new DefaultTableModel();
	JTable dataTableItem=new JTable(tabItemModel);
	

	private FrmLogin dlgLogin=null;
	private JPanel statusBar = new JPanel();
	void reloadTypeTable(){
	 	Connection conn=null;
			try {
				conn=DBUtil.getConnection();
				String sql = "select waname,waid,address,warman,w_time,flag from warehouse";
				PreparedStatement pst=conn.prepareStatement(sql);
				
				ResultSet rs = pst.executeQuery();
				int n=0;
				
				while(rs.next()){
					n++;
				}
				pst.close();
				rs.close();
				tblTypeData =new Object[n][5];
				 sql = "select waname,waid,address,warman,w_time,flag from warehouse";
				 pst=conn.prepareStatement(sql);
				
				 rs = pst.executeQuery();
				
				int i=0;
				while(rs.next()){
				tblTypeData[i][0]=rs.getString("waname");
				tblTypeData[i][1]=rs.getString("waid");
				
				tblTypeData[i][2]=rs.getString("address");
				tblTypeData[i][3]=rs.getString("warman");
				tblTypeData[i][4]=rs.getString("w_time");
			
				i++;
				}
			} catch (SQLException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
			finally
			{
				if(conn != null)
				{
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			tabTypeModel.setDataVector(tblTypeData,tblTypeTitle);
			this.dataTableType.validate();
			this.dataTableType.repaint();

		}
	 void reloadTypeItemTabel(String Typeid){
		 Connection conn=null; 
		 try {
			 conn=DBUtil.getConnection();
			String sql = "select m_id,average_cost,m_count,batch from stock where waid=?   ";
			
			PreparedStatement pst=conn.prepareStatement(sql);
			
			pst.setString(1,Typeid);
			ResultSet rs = pst.executeQuery();
			int n=0;
			while(rs.next()){
				
				
				n++;
				
			}
			rs.close();
			
			 rs = pst.executeQuery();
			int i=0;
			tblItemData =new Object[n][9];
			while(rs.next()){
			
			String mid=rs.getString("m_id");
			tblItemData[i][0]=mid;
			tblItemData[i][1]=Typeid;
			
			
				BeanMaterial matreial=null;
				try {
					matreial = (new MaterialManager()).loadMaterial(mid);
				} catch (BaseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			tblItemData[i][2]=matreial.getM_name();
			tblItemData[i][3]=matreial.getM_type();
		
			tblItemData[i][4]=matreial.getManufacturer();
			tblItemData[i][5]=rs.getInt("average_cost");
			tblItemData[i][6]=rs.getInt("m_count");
			tblItemData[i][7]=rs.getString("batch");
			i++;
			}
			
			
		 }catch (SQLException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	
		
		tabItemModel.setDataVector(tblItemData,tblItemTitle);
		this.dataTableItem.validate();
		this.dataTableItem.repaint();
	}
	
	
	public FrmMain(){
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setTitle("库存管理系统");
		dlgLogin=new FrmLogin(this,"登陆",true);
		dlgLogin.setVisible(true);
	    //菜单
	    if("管理员".equals(SystemUserManager.currentUser.getUser_type())){
	    	menu_Manager.add(menuItem_UserManager);
	    	menuItem_UserManager.addActionListener(this);
	    	menubar.add(menu_Manager);
	    }
	    menu_lend.add(this.menuItem_Warehouse);
	    menuItem_Warehouse.addActionListener(this);
	    

	    

	    menu_Material.add(this.menuItem_material);
	    menuItem_material.addActionListener(this);
	    
	    
	    menu_Stock.add(this.menuItem_material1);
	    menuItem_material1.addActionListener(this);
	    
	    menu_Stock.add(this.menuItem_material2);
	    menuItem_material2.addActionListener(this);
	    menu_Stock.add(this.menuItem_material3);
	    menuItem_material3.addActionListener(this);
	    menu_Stock.add(this.menuItem_material4);
	    menuItem_material4.addActionListener(this);
	    menu_Stock.add(this.menuItem_material5);
	    menuItem_material5.addActionListener(this);

	    
	    menubar.add(menu_lend);
	    
	    
	    
	    menu_search.add(this.menuItem_search1);
	    menuItem_search1.addActionListener(this);

	    menu_search.add(this.menuItem_search2);
	    menuItem_search2.addActionListener(this);
	   
	    
	    menu_Statistics.add(this.menuItem_BookLendSearch);
	    menuItem_BookLendSearch.addActionListener(this);


	    menu_Worker.add(this.menuItem_Worker);
	    menuItem_Worker.addActionListener(this);
	    
	    menubar.add(this.menu_Material);
	    menubar.add(this.menu_Stock);
	    menubar.add(this.menu_search);
	    menubar.add(this.menu_Statistics);
	    menubar.add(this.menu_Worker);
	    
	    this.setJMenuBar(menubar);
	    //状态栏
	    statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    JLabel label=new JLabel("您好!"+SystemUserManager.currentUser.getUser_name());
	    statusBar.add(label);
	    
	    
	    this.getContentPane().add(new JScrollPane(this.dataTableType), BorderLayout.WEST);
	    
	    this.dataTableType.addMouseListener(new MouseAdapter (){

			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmMain.this.dataTableType.getSelectedRow();
				if(i<0) {
					return;
				}
				 typeid=FrmMain.this.tblTypeData[i][1].toString();
				FrmMain.this.reloadTypeItemTabel(typeid);
			}
	    	
	    });
	    
	    this.reloadTypeTable();
	    
	    this.getContentPane().add(new JScrollPane(this.dataTableItem), BorderLayout.CENTER);
	    
	    this.getContentPane().add(statusBar,BorderLayout.SOUTH);
	    this.addWindowListener(new WindowAdapter(){   
	    	public void windowClosing(WindowEvent e){ 
	    		System.exit(0);
             }
        });
	    
	    this.setVisible(true);
	    
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.menuItem_UserManager){
			FrmUserManager dlg=new FrmUserManager(this,"用户管理",true);
			dlg.setVisible(true);
		}
		
		else if(e.getSource()==this.menuItem_Warehouse){
			FrmWarehouseManager dlg=new FrmWarehouseManager(this,"仓库基本信息管理",true);
			dlg.setVisible(true);
			this.reloadTypeTable();
		
		}
		else if(e.getSource()==this.menuItem_material){
			FrmMaterialManager dlg=new FrmMaterialManager(this,"物料基本信息管理",true);
			dlg.setVisible(true);
			this.reloadTypeTable();
		
		}
		else if(e.getSource()==this.menuItem_material1){
			FrmInboundManager dlg=new FrmInboundManager(this,"入库管理",true);
			dlg.setVisible(true);
			this.reloadTypeTable();
			this.reloadTypeItemTabel( typeid);
		
		}
		
		else if(e.getSource()==this.menuItem_material2){
			FrmOutboundManager dlg=new FrmOutboundManager(this,"出库管理",true);
			dlg.setVisible(true);
			reloadTypeItemTabel( typeid);
		
		}
		
		else if(e.getSource()==this.menuItem_material3){
			FrmMoveboundManager dlg=new FrmMoveboundManager(this,"移库管理",true);
			dlg.setVisible(true);
			reloadTypeItemTabel( typeid);
		
		}
		else if(e.getSource()==this.menuItem_material4){
			FrmScrapboundManager dlg=new FrmScrapboundManager(this,"报废管理",true);
			dlg.setVisible(true);
			reloadTypeItemTabel( typeid);
		
		}
		
		
		else if(e.getSource()==this.menuItem_material5){
			FrmBackboundManager dlg=new FrmBackboundManager(this,"退库管理",true);
			dlg.setVisible(true);
			reloadTypeItemTabel( typeid);
		
		}
		
		else if(e.getSource()==this.menuItem_search1){
			FrmSearchBound dlg=new FrmSearchBound(this,"单据查询",true);
			dlg.setVisible(true);
			
		
		}
		else if(e.getSource()==this.menuItem_search2){
			FrmSearchStock dlg=new FrmSearchStock(this,"库存查询",true);
			dlg.setVisible(true);
			
		
		}
		else if(e.getSource()==this.menuItem_BookLendSearch){
			FrmStaticStock dlg=new FrmStaticStock(this,"库存盘点",true);
			dlg.setVisible(true);
			
		
		}
		
		else if(e.getSource()==this.menuItem_Worker){
			FrmWorkerManager dlg=new FrmWorkerManager(this,"员工管理",true);
			dlg.setVisible(true);
			
		
		}
		
	}
}
