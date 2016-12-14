package expStudent;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.omg.CORBA.PUBLIC_MEMBER;

public class StudentInfo {
	private static LinkDB linkDB = new LinkDB();
	private static Connection dbconn;
	private Student s = new Student();
	private  boolean isTianJia = false;
	
	public JPanel jpStuInsert = new JPanel(new GridLayout(5,1));
	private JPanel jpOne = new JPanel();
	private JPanel jpTwo = new JPanel();
	private JPanel jpThree = new JPanel();
	private JPanel jpFour = new JPanel();
	private JPanel jpFive = new JPanel();
	private String[] str = {"--请选择内容--"};
	private String str0 = "--请选择内容--";
	private JLabel jlSTR0 = new JLabel("学生添加");
	private JLabel jlXueHao = new JLabel("学号：");
	private JTextField jtfXueHao = new JTextField(12);
	private JLabel jlXingMIng = new JLabel("       姓名：");
	private JTextField jtfXingMing = new JTextField(16);
	private JLabel jlXingBie = new JLabel("       性别：");
	private JComboBox jcbXingBie = new JComboBox(str);
	private JLabel jlNianLing = new JLabel("       年龄：");
	private JTextField jtfNianLing = new JTextField(5);
	private JLabel jlMinZu = new JLabel("民族：");
	private JComboBox jcbMinZu = new JComboBox(str);
	private JLabel jlXueYuan = new JLabel("         学院：");
	private JComboBox jcbXueYuan = new JComboBox(str);
	private JLabel jlZhuanYe = new JLabel("         专业：");
	private JComboBox jcbZhuanYe = new JComboBox(str);
	private JLabel jlBanJi = new JLabel("         班级：");
	private JComboBox jcbBanJi = new JComboBox(str);
	private JLabel jlZZMM = new JLabel("政治面貌：");
	private JComboBox jcbZZMM = new JComboBox(str);
	private JLabel jlCSRQ = new JLabel("       出生日期：");
	private JTextField jtfCSRQ = new JTextField(10);
	private JLabel jlGeShi = new JLabel("格式:xxxx-xx-xx");
	private JLabel jlJTZZ = new JLabel("       家庭住址：");
	private JTextField jtfJTZZ = new JTextField(18);
	public JButton jbtnTianJia = new JButton("添加");
	
	public StudentInfo(){
		jcbXingBie.addItem("男");
		jcbXingBie.addItem("女");
		
		jpOne.add(jlSTR0);
		jpTwo.add(jlXueHao);
		jpTwo.add(jtfXueHao);
		jpTwo.add(jlXingMIng);
		jpTwo.add(jtfXingMing);
		jpTwo.add(jlXingBie);
		jpTwo.add(jcbXingBie);
		jpTwo.add(jlNianLing);
		jpTwo.add(jtfNianLing);
		jpThree.add(jlMinZu);
		jpThree.add(jcbMinZu);
		jpThree.add(jlXueYuan);
		jpThree.add(jcbXueYuan);
		jpThree.add(jlZhuanYe);
		jpThree.add(jcbZhuanYe);
		jpThree.add(jlBanJi);
		jpThree.add(jcbBanJi);
		jpFour.add(jlZZMM);
		jpFour.add(jcbZZMM);
		jpFour.add(jlCSRQ);
		jpFour.add(jtfCSRQ);
		jpFour.add(jlGeShi);
		jpFour.add(jlJTZZ);
		jpFour.add(jtfJTZZ);
		jpFive.add(jbtnTianJia);
		
		jpStuInsert.add(jpOne);
		jpStuInsert.add(jpTwo);
		jpStuInsert.add(jpThree);
		jpStuInsert.add(jpFour);
		jpStuInsert.add(jpFive);
		
		try {
			dbconn = linkDB.lindDataBase();
			Statement stmt = dbconn.createStatement();//创建SQL命令对象
		    ResultSet rsMinZu = stmt.executeQuery("select * from Nation");
		    while(rsMinZu.next()){
			    jcbMinZu.addItem(rsMinZu.getString("nation_name"));
			}
		    ResultSet rsXueYuan = stmt.executeQuery("select * from Academy");
		    while(rsXueYuan.next()){
			    jcbXueYuan.addItem(rsXueYuan.getString("academy_name"));
			}
		    ResultSet rsZZMM = stmt.executeQuery("select * from PoliticsStatus");
		    while(rsZZMM.next()){
			    jcbZZMM.addItem(rsZZMM.getString("ps_name"));
			}
		    dbconn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//选择学院显示专业
		jcbXueYuan.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					XueYuan_ZhuanYe(jcbXueYuan, jcbZhuanYe);
				}	
			}
		});
		//选择专业显示班级
		jcbZhuanYe.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					ZhuanYe_BanJi(jcbZhuanYe, jcbBanJi);
				}	
			}
		});
		//添加
		jbtnTianJia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TianJia();
			}
			
		});
		
	}
	
	//点击学院显示专业
	public void XueYuan_ZhuanYe(JComboBox jcb0,JComboBox jcb1){
		try {
			jcb1.removeAllItems();
			jcb1.addItem("--请选择内容--");
			dbconn = linkDB.lindDataBase();
			Statement stmtMajor = dbconn.createStatement();//创建SQL命令对象
		    ResultSet rsMajor = stmtMajor.executeQuery("select distinct major_name from Major " +
		    		"where academy_code=(select academy_code from Academy where academy_name='"+jcb0.getSelectedItem()+"')"); //存放找出数据的总行数
		    while(rsMajor.next()){
		    	jcb1.addItem(rsMajor.getString("major_name"));
		    }
		    dbconn.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	//点击专业显示班级
	public void ZhuanYe_BanJi(JComboBox jcb0, JComboBox jcb1){
		try {
			jcb1.removeAllItems();
			jcb1.addItem("--请选择内容--");
			dbconn = linkDB.lindDataBase();
			Statement stmtMajor = dbconn.createStatement();//创建SQL命令对象
		    ResultSet rsMajor = stmtMajor.executeQuery("select distinct class_name from Class " +
		    		"where major_code=(select major_code from Major where major_name='"+jcb0.getSelectedItem()+"')"); //存放找出数据的总行数
		    while(rsMajor.next()){
		    	jcb1.addItem(rsMajor.getString("class_name"));
		    }
		    dbconn.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}	
	}
	
	//添加
	public void TianJia(){
		int a1=0,a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,a10=0,a11=0;
		if(jtfXueHao.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "学号不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
		}else{a1=1;}
		if(jtfXingMing.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "姓名不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
		}else{a2=1;}
		if(jcbXingBie.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "性别不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);    
		}else{a3=1;}
		if(jtfNianLing.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "年龄不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
		}else{a4=1;}
		if(jcbMinZu.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "民族不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
		}else{a5=1;}
		if(jcbXueYuan.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "学院不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
		}else{a6=1;}
		if(jcbZhuanYe.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "专业不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
		}else{a7=1;}
		if(jcbBanJi.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "班级不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
		}else{a8=1;}
		if(jcbZZMM.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "政治面貌不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
		}else{a9=1;}
		if(jtfCSRQ.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "出生日期不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
		}else{a10=1;}
		if(jtfJTZZ.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "家庭住址不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
		}else{a11=1;}
		if(a1==1&&a2==1&&a3==1&&a4==1&&a5==1&&a6==1&&a7==1&&a8==1&&a9==1&&a10==1&&a11==1){
			try{
				dbconn = linkDB.lindDataBase();
				Statement stmt = dbconn.createStatement();
				ResultSet rs = stmt.executeQuery("select * from Student where student_id='"+jtfXueHao.getText().trim()+"'");
				boolean x = false;
				while(rs.next()){
					x=true;
				}
				if(x==false){
					ResultSet rsOk = stmt.executeQuery("select distinct a.academy_code, b.major_code, c.class_code, d.nation_code, e.ps_id " +
							"from Academy a, Major b, Class c, Nation d, PoliticsStatus e where a.academy_name='"+jcbXueYuan.getSelectedItem()+"' " +
									"and b.major_name='"+jcbZhuanYe.getSelectedItem()+"' and c.class_name='"+jcbBanJi.getSelectedItem()+"' " +
											"and d.nation_name='"+jcbMinZu.getSelectedItem()+"' and e.ps_name='"+jcbZZMM.getSelectedItem()+"' ");
				    rsOk.next();
					String strAdd = "insert Student values('"+jtfXueHao.getText().trim()+"','"+rsOk.getString("nation_code")+"'," +
							"'"+rsOk.getString("major_code")+"','"+rsOk.getString("class_code")+"','"+rsOk.getString("ps_id")+"'," +
									"'"+jtfXingMing.getText().trim()+"','"+jcbXingBie.getSelectedItem()+"',"+jtfNianLing.getText().trim()+"," +
											"'"+jtfCSRQ.getText().trim()+"','"+jtfJTZZ.getText().trim()+"','')";
					stmt.executeUpdate(strAdd);
					dbconn.close();
					s.StuLoading();
					JOptionPane.showMessageDialog(null, "添加改学生成功！","消息提示",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "已存在该学号学生！","错误提示",JOptionPane.INFORMATION_MESSAGE);
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
	}
}
