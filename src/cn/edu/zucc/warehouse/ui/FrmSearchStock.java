

package cn.edu.zucc.warehouse.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.warehouse.control.InboundManager;
import cn.edu.zucc.warehouse.control.MaterialManager;
import cn.edu.zucc.warehouse.control.ScrapboundManager;
import cn.edu.zucc.warehouse.control.StockManager;
import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.model.BeanInbound;
import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.model.BeanStock;
import cn.edu.zucc.warehouse.model.BeanStocknobatch;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.DBUtil;


public class  FrmSearchStock   extends JDialog implements ActionListener {
	
	
	String danjuid="";
	String waid="";
	String maid="";
	String time="";
	String type="";
	
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();

	
	private  JLabel labelWaid= new JLabel("请选择仓库编号：");
	private  JLabel labelMaid= new JLabel("请选择物料编号：");
	
	
	
	
	private JComboBox edtWaid= new JComboBox(new String[] {});

	private JComboBox edtMaid= new JComboBox(new String[] {});
	
	
	private Button btnOK = new Button("确定");
	private Button btnCancel = new Button("取消");
	

	private Object tblTitle[]={"仓库编号","物料编号","物料名称","物料类型","成本","物料数量"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	
	
	
	private void reloadUserTable1(){
		try {
			BeanStocknobatch stock=(new StockManager()).searchstock(waid,maid);
			tblData =new Object[1][9];
			
              int i=0;
				
				tblData[0][0]=waid;
				tblData[0][1]=maid;
				
			
				
				BeanMaterial matreial=(new MaterialManager()).loadMaterial(maid);
				
				
				tblData[0][2]=matreial.getM_name();
				
				tblData[0][3]=matreial.getM_type();
			
				tblData[0][4]=stock.getAverage_cost();
				tblData[0][5]=stock.getM_count();
			
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public  FrmSearchStock  (Frame f, String s, boolean b) {
		super(f, s, b);
		edtMaid.setPreferredSize(new Dimension(120, 25));
		edtWaid.setPreferredSize(new Dimension(120, 25));
		
		Connection conn=null; 
			try {
				conn=DBUtil.getConnection();
				String sql="select waid from warehouse ";
				java.sql.Statement st=conn.createStatement();
				java.sql.ResultSet rs=st.executeQuery(sql);
				while(rs.next()){
					
					edtWaid.addItem(rs.getString(1));
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
			
		
		

		
		
		
		
		workPane.setLayout(new FlowLayout(FlowLayout.LEFT));

		workPane.add(labelWaid);
		workPane.add(edtWaid);

		workPane.add(labelMaid);
		workPane.add(edtMaid);
		
		workPane.add(btnOK);
		workPane.add(btnCancel);
		
		
		this.getContentPane().add(workPane, BorderLayout.NORTH);
		
		//提取现有数据
		
		this.getContentPane().add(new JScrollPane(this.userTable), BorderLayout.CENTER);
		
		// 屏幕居中显示
		this.setSize(950, 600);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();

		this.btnOK.addActionListener(this);
		this.edtWaid.addActionListener(this);
		edtWaid.setSelectedIndex(0);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==this.btnOK){
	
			 waid=edtWaid.getSelectedItem().toString();
			 maid=edtMaid.getSelectedItem().toString();
				this.reloadUserTable1();
			
		}
	
		
else if(e.getSource()==this.edtWaid){
			
			
			
			 waid=this.edtWaid.getSelectedItem().toString();
			 Connection conn=null;
			 
			 this.edtMaid.removeActionListener(this);
			 edtMaid.removeAllItems();
			
			 
			 try {
				
					conn=DBUtil.getConnection();
					String sql="select distinct(m_id) from stock where waid=?" ;
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					pst.setString(1,waid);
					java.sql.ResultSet rs=pst.executeQuery();
					while(rs.next()){
						 edtMaid.addItem(rs.getString(1));
					}
					
					
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				
				}
				finally{
					if(conn!=null)
						try {
							conn.close();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
				
			
			 
			 edtMaid.addActionListener(this);
			 
			 edtMaid.setSelectedIndex(0);
				
				
		}
		
		
		
		
		
		
		
		
		
		
	}
}
