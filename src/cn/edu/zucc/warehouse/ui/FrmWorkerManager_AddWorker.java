




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

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import cn.edu.zucc.warehouse.control.SystemUserManager;
import cn.edu.zucc.warehouse.control.WarehouseManager;
import cn.edu.zucc.warehouse.control.WorkerManager;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.model.BeanWarehouse;
import cn.edu.zucc.warehouse.model.BeanWorker;
import cn.edu.zucc.warehouse.util.BaseException;

public class  FrmWorkerManager_AddWorker extends JDialog implements ActionListener {
	private BeanWorker worker=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelWorkerid = new JLabel("员工编号：");
	private JLabel labelWorkername = new JLabel("员工名称：");

	
	
	private JTextField edtWorkerid = new JTextField(20);
	private JTextField edtWorkername = new JTextField(20);

		
	public FrmWorkerManager_AddWorker(JDialog f, String s, boolean b) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		
		workPane.add(labelWorkerid);
		workPane.add(edtWorkerid);
		
		workPane.add(labelWorkername);
		workPane.add(edtWorkername);
		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 250);
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
			
			String waid=(this.edtWorkerid.getText());
			String waname=this.edtWorkername.getText();
			
			worker=new BeanWorker();
		
			worker.setWorker_id(waid);
			worker.setWorker_name(waname);
			
			try {
				(new WorkerManager()).createUser(worker);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.worker=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanWorker getWorker() {
		return worker;
	}
	
}
