
package cn.edu.zucc.warehouse.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.zucc.warehouse.control.WorkerManager;
import cn.edu.zucc.warehouse.model.BeanSystemUser;
import cn.edu.zucc.warehouse.model.BeanWarehouse;
import cn.edu.zucc.warehouse.model.BeanWorker;
import cn.edu.zucc.warehouse.util.BaseException;

public class FrmWorkerManager_Modify   extends JDialog implements ActionListener {
	private BeanWorker worker=null;
	
	private JPanel toolBar = new JPanel();
	private JPanel workPane = new JPanel();
	private Button btnOk = new Button("确定");
	private Button btnCancel = new Button("取消");
	private JLabel labelWorkerName = new JLabel("员工名称：");

	
	
	private JTextField edtWorkerName = new JTextField(20);

	
	
	public FrmWorkerManager_Modify (JDialog f, String s, boolean b,String waid) {
		super(f, s, b);
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnOk);
		toolBar.add(btnCancel);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		worker=new BeanWorker();
		worker.setWorker_id(waid);
		workPane.add(labelWorkerName);
		workPane.add(edtWorkerName);

		
		this.getContentPane().add(workPane, BorderLayout.CENTER);
		this.setSize(320, 180);
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
			
		
		String warename=edtWorkerName.getText();
		worker.setWorker_name(warename);

			try {
				(new WorkerManager()).changeWorker(worker);
				this.setVisible(false);
			} catch (BaseException e1) {
				this.worker=null;
				JOptionPane.showMessageDialog(null, e1.getMessage(),"错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	public BeanWorker getwarehouse() {
		return worker;
	}
	
}
