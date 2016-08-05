



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

import cn.edu.zucc.warehouse.control.BackboundManager;
import cn.edu.zucc.warehouse.control.InboundManager;
import cn.edu.zucc.warehouse.control.MaterialManager;
import cn.edu.zucc.warehouse.control.MoveboundManager;
import cn.edu.zucc.warehouse.control.OutboundManager;
import cn.edu.zucc.warehouse.control.ScrapboundManager;
import cn.edu.zucc.warehouse.control.StockManager;
import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.model.BeanBackbound;
import cn.edu.zucc.warehouse.model.BeanInbound;
import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.model.BeanMovebound;
import cn.edu.zucc.warehouse.model.BeanOutbound;
import cn.edu.zucc.warehouse.model.BeanScrapbound;
import cn.edu.zucc.warehouse.model.BeanStocknobatch;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.DBUtil;


 public class FrmStaticStock   extends JDialog implements ActionListener {
	
	
	String waid="";
	String maid="";
	String starttime="";
	String endtime="";

	
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();

	
	private  JLabel labelWaid= new JLabel("请选择仓库编号：");
	private  JLabel labelTime= new JLabel("请选择单据的时间段：");
	private  JLabel labelTime1= new JLabel("—");
	
	

	private JComboBox edtWaid= new JComboBox(new String[] {});
	private JTextField edtStartTime= new JTextField(10);
	private JTextField edtEndTime= new JTextField(10);
	
	
	private Button btnOK = new Button("确定");
	private Button btnCancel = new Button("取消");
	

	private Object tblTitle[]={"物料编号","物料名称","平均成本","库存数","入库数","出库数","移入数","移出数","报废数","退库数","平衡标志"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	
	
	
	private void reloadUserTableIn() throws BaseException{
		
		int n=0;
		 n=(new StockManager()).StaticStock(waid);
			tblData =new Object[n][11];
			Connection conn=null;
			
			try {
				 conn=null;
				int i=0;
					 
					conn=DBUtil.getConnection();
					String sql="select distinct(m_id) from stock where waid=?";
					java.sql.PreparedStatement pst=conn.prepareStatement(sql);
					pst.setString(1,waid);
					java.sql.ResultSet rs=pst.executeQuery();
					while(rs.next()){
							maid=rs.getString(1);
						tblData[i][0]=maid;
						
						BeanMaterial matreial=(new MaterialManager()).loadMaterial(maid);
						tblData[i][1]=matreial.getM_name();
						BeanStocknobatch stocknobatch=(new StockManager()).searchstock(waid,maid);
						tblData[i][2]=stocknobatch.getAverage_cost();
						
						
						int StaticInbound=(new StockManager()).StaticInbound(waid,maid,starttime,endtime);
						tblData[i][4]=StaticInbound;
						
						int StaticMovein=(new StockManager()).StaticMovein(waid,maid,starttime,endtime);
						tblData[i][6]=StaticMovein; 
						
						int StaticMoveout=(new StockManager()).StaticMoveout(waid,maid,starttime,endtime);
						tblData[i][7]=StaticMoveout; 
						int StaticScrap=(new StockManager()).StaticScrap(waid,maid,starttime,endtime);
						tblData[i][8]=StaticScrap; 
						int staticBack1=(new StockManager()).StaticBack(waid,maid,starttime,endtime);
						tblData[i][9]=staticBack1; 
						int StaticOutbound=(new StockManager()).StaticOutbound(waid,maid,starttime,endtime);
						tblData[i][5]=StaticOutbound+staticBack1;
						int balancecount=StaticInbound-StaticOutbound+StaticMovein-StaticMoveout-StaticScrap;
						tblData[i][3]=balancecount;
						if(balancecount==0)
						 tblData[i][10]="平衡"; 
						else if(balancecount<0)
							 tblData[i][10]="出大于入"; 
						else 
							 tblData[i][10]="入大于出"; 
						i++;
						//"物料编号","移出数","报废数","退库数","平衡标志"

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
		
				
			
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		
	}
	
	
	
	
	
	public FrmStaticStock (Frame f, String s, boolean b) {
		super(f, s, b);
	
		
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
			
		

		
		
		edtWaid.setPreferredSize(new Dimension(120, 25));
		
		
		workPane.setLayout(new FlowLayout(FlowLayout.LEFT));

		workPane.add(labelWaid);
		workPane.add(edtWaid);
		workPane.add(labelTime);
		workPane.add(edtStartTime);
	
		workPane.add(labelTime1);
		workPane.add(edtEndTime);
		
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
			 starttime=edtStartTime.getText();
			 endtime=edtEndTime.getText();
			 if(starttime.equals(""))
				 starttime="1900-1-1";
			 if(endtime.equals(""))
				 endtime=new java.sql.Date(System.currentTimeMillis()).toString();
				try {
					this.reloadUserTableIn();
				} catch (BaseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
		}
	
		
		
		
	}
}
