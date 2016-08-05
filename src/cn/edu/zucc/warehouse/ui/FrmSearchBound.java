
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
import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.model.BeanBackbound;
import cn.edu.zucc.warehouse.model.BeanInbound;
import cn.edu.zucc.warehouse.model.BeanMaterial;
import cn.edu.zucc.warehouse.model.BeanMovebound;
import cn.edu.zucc.warehouse.model.BeanOutbound;
import cn.edu.zucc.warehouse.model.BeanScrapbound;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.util.BaseException;
import cn.edu.zucc.warehouse.util.DBUtil;


public class FrmSearchBound  extends JDialog implements ActionListener {
	
	
	String danjuid="";
	String waid="";
	
	String time="";
	String type="";
	
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();

	private  JLabel labelID= new JLabel("请输入单据号：");
	private  JLabel labelWaid= new JLabel("请选择仓库编号：");
	private  JLabel labelTime= new JLabel("请选择单据时间：");
	private  JLabel labelType= new JLabel("请选择单据类型：");
	
	
	private JTextField edtID= new JTextField(10);
	private JComboBox edtWaid= new JComboBox(new String[] {});
	private JTextField edtTime= new JTextField(10);
	private JComboBox edtType= new JComboBox(new String[] {"入库","出库","移库","报废","退库"});
	
	
	private Button btnOK = new Button("确定");
	private Button btnCancel = new Button("取消");
	

	private Object tblTitle[]={"单号","仓库编号","物料编号","物料名称","物料类型","单价","物料数量","批次","时间"};
	private Object tblData[][];
	DefaultTableModel tablmod=new DefaultTableModel();
	private JTable userTable=new JTable(tablmod);
	
	
	
	private void reloadUserTableIn(){
		try {
			List<BeanInbound> inbound=null;
			if(!danjuid.equals("")&&!waid.equals("")&&!time.equals(""))
			        inbound=(new InboundManager()).searchbound(danjuid,waid,time);
			else if(!danjuid.equals("")&&!waid.equals("")&&time.equals(""))
				inbound=(new InboundManager()).searchbound1(danjuid,waid,time);
			else if(danjuid.equals("")&&!waid.equals("")&&time.equals(""))
				inbound=(new InboundManager()).searchbound2(danjuid,waid,time);
			else if(danjuid.equals("")&&!waid.equals("")&&!time.equals(""))
				inbound=(new InboundManager()).searchbound3(danjuid,waid,time);
			
			tblData =new Object[inbound.size()][9];
			for(int i=0;i<inbound.size();i++){
                tblData[i][0]=inbound.get(i).getIn_id();
				
				tblData[i][1]=inbound.get(i).getWaid();
				tblData[i][2]=inbound.get(i).getM_id();
				
				String mid=inbound.get(i).getM_id();
				
				BeanMaterial matreial=(new MaterialManager()).loadMaterial(mid);
				
				
				tblData[i][3]=matreial.getM_name();
				
				tblData[i][4]=matreial.getM_type();
			
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
	
	
	private void reloadUserTableOut(){
		try {
			List<BeanOutbound> outbound=null;
			
			if(!danjuid.equals("")&&!waid.equals("")&&!time.equals(""))
				outbound=(new OutboundManager()).searchbound(danjuid,waid,time);
		else if(!danjuid.equals("")&&!waid.equals("")&&time.equals(""))
			outbound=(new OutboundManager()).searchbound1(danjuid,waid,time);
		else if(danjuid.equals("")&&!waid.equals("")&&time.equals(""))
			outbound=(new OutboundManager()).searchbound2(danjuid,waid,time);
		else if(danjuid.equals("")&&!waid.equals("")&&!time.equals(""))
			outbound=(new OutboundManager()).searchbound3(danjuid,waid,time);
			
			tblData =new Object[outbound.size()][9];
			for(int i=0;i<outbound.size();i++){
                tblData[i][0]=outbound.get(i).getOut_id();
				
				tblData[i][1]=outbound.get(i).getWaid();
				tblData[i][2]=outbound.get(i).getM_id();
				
				String mid=outbound.get(i).getM_id();
				
				BeanMaterial matreial=(new MaterialManager()).loadMaterial(mid);
				
				
				tblData[i][3]=matreial.getM_name();
				
				tblData[i][4]=matreial.getM_type();
			
				tblData[i][5]=outbound.get(i).getAverage_cost();
				tblData[i][6]=outbound.get(i).getMaterial_count();
				tblData[i][7]=outbound.get(i).getBatch();
				tblData[i][8]=outbound.get(i).getOut_time();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void reloadUserTableMove(){
		try {
			List<BeanMovebound> movebound=null;
			
			
			if(!danjuid.equals("")&&!waid.equals("")&&!time.equals(""))
				movebound=(new MoveboundManager()).searchbound(danjuid,waid,time);
		else if(!danjuid.equals("")&&!waid.equals("")&&time.equals(""))
			movebound=(new MoveboundManager()).searchbound1(danjuid,waid,time);
		else if(danjuid.equals("")&&!waid.equals("")&&time.equals(""))
			movebound=(new MoveboundManager()).searchbound2(danjuid,waid,time);
		else if(danjuid.equals("")&&!waid.equals("")&&!time.equals(""))
			movebound=(new MoveboundManager()).searchbound3(danjuid,waid,time);
			
			
			
			tblData =new Object[movebound.size()][9];
			for(int i=0;i<movebound.size();i++){
                tblData[i][0]=movebound.get(i).getMove_id();
				
				tblData[i][1]=movebound.get(i).getWaid();
				tblData[i][2]=movebound.get(i).getM_id();
				
				String mid=movebound.get(i).getM_id();
				
				BeanMaterial matreial=(new MaterialManager()).loadMaterial(mid);
				
				
				tblData[i][3]=matreial.getM_name();
				
				tblData[i][4]=matreial.getM_type();
			
				tblData[i][5]=movebound.get(i).getAverage_cost();
				tblData[i][6]=movebound.get(i).getMaterial_count();
				tblData[i][7]=movebound.get(i).getBatch();
				tblData[i][8]=movebound.get(i).getMove_time();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void reloadUserTableScrap(){
		try {
			List<BeanScrapbound> inbound=null;
			
			if(!danjuid.equals("")&&!waid.equals("")&&!time.equals(""))
				inbound=(new ScrapboundManager()).searchbound(danjuid,waid,time);
		else if(!danjuid.equals("")&&!waid.equals("")&&time.equals(""))
			inbound=(new ScrapboundManager()).searchbound1(danjuid,waid,time);
		else if(danjuid.equals("")&&!waid.equals("")&&time.equals(""))
			inbound=(new ScrapboundManager()).searchbound2(danjuid,waid,time);
		else if(danjuid.equals("")&&!waid.equals("")&&!time.equals(""))
			inbound=(new ScrapboundManager()).searchbound3(danjuid,waid,time);
			
			
			
			
			tblData =new Object[inbound.size()][9];
			for(int i=0;i<inbound.size();i++){
                tblData[i][0]=inbound.get(i).getSc_id();
				
				tblData[i][1]=inbound.get(i).getWaid();
				tblData[i][2]=inbound.get(i).getM_id();
				
				String mid=inbound.get(i).getM_id();
				
				BeanMaterial matreial=(new MaterialManager()).loadMaterial(mid);
				
				
				tblData[i][3]=matreial.getM_name();
				
				tblData[i][4]=matreial.getM_type();
			
				
				tblData[i][6]=inbound.get(i).getMaterial_count();
				tblData[i][7]=inbound.get(i).getBatch();
				tblData[i][8]=inbound.get(i).getSc_time();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void reloadUserTableBack(){
		try {
			List<BeanBackbound> backbound=null;
			

			if(!danjuid.equals("")&&!waid.equals("")&&!time.equals(""))
				backbound=(new BackboundManager()).searchbound(danjuid,waid,time);
		else if(!danjuid.equals("")&&!waid.equals("")&&time.equals(""))
			backbound=(new BackboundManager()).searchbound1(danjuid,waid,time);
		else if(danjuid.equals("")&&!waid.equals("")&&time.equals(""))
			backbound=(new BackboundManager()).searchbound2(danjuid,waid,time);
		else if(danjuid.equals("")&&!waid.equals("")&&!time.equals(""))
			backbound=(new BackboundManager()).searchbound3(danjuid,waid,time);
			
			
			tblData =new Object[backbound.size()][9];
			for(int i=0;i<backbound.size();i++){
                tblData[i][0]=backbound.get(i).getBack_id();
				
				tblData[i][1]=backbound.get(i).getWaid();
				tblData[i][2]=backbound.get(i).getM_id();
				
				String mid=backbound.get(i).getM_id();
				
				BeanMaterial matreial=(new MaterialManager()).loadMaterial(mid);
				
				
				tblData[i][3]=matreial.getM_name();
				
				tblData[i][4]=matreial.getM_type();
			
				tblData[i][5]=backbound.get(i).getAverage_cost();
				tblData[i][6]=backbound.get(i).getMaterial_count();
				tblData[i][7]=backbound.get(i).getBatch();
				tblData[i][8]=backbound.get(i).getBack_time();
			}
			tablmod.setDataVector(tblData,tblTitle);
			this.userTable.validate();
			this.userTable.repaint();
		} catch (BaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public FrmSearchBound (Frame f, String s, boolean b) {
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
		workPane.add(labelID);
		workPane.add(edtID);
		workPane.add(labelWaid);
		workPane.add(edtWaid);
		workPane.add(labelTime);
		workPane.add(edtTime);
		workPane.add(labelType);
		workPane.add(edtType);
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
	
			 danjuid=edtID.getText();
			 waid=edtWaid.getSelectedItem().toString();
			
		
		     time=edtTime.getText();
			
		
			String type=edtType.getSelectedItem().toString();
			if(type.equalsIgnoreCase("入库"))
				this.reloadUserTableIn();
			else if(type.equalsIgnoreCase("出库"))
				this.reloadUserTableOut();
			else if(type.equalsIgnoreCase("移库"))
				this.reloadUserTableMove();
			else if(type.equalsIgnoreCase("报废"))
				this.reloadUserTableScrap();
			else if(type.equalsIgnoreCase("退库"))
				this.reloadUserTableBack();
			
		}
	
		
		
		
	}
}
