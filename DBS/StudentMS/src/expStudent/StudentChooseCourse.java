package expStudent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.omg.CORBA.PRIVATE_MEMBER;

public class StudentChooseCourse {
	private static LinkDB linkDB = new LinkDB();
	private static Connection dbconn;
	//初始化
	public JPanel jpSCC = new JPanel(new GridLayout(2,1));
	public JScrollPane jspSCC = null;
	private JTable jtSCC = null;
	private Vector rowData = new Vector();
	private Vector columName = new Vector();
	private String[] str = {"--请选择内容--"};
	private String str0 = "--请选择内容--";
	//查询
	private JPanel jpOne = new JPanel();
	private JPanel jpTwo = new JPanel();
	private JLabel jlStr0 = new JLabel("选课查询");
	private JLabel jlSCC_STU = new JLabel("学号：");
	private JTextField jtfSCC = new JTextField(12);
	private JButton jbtnSCC = new JButton("查询");
	//删除
	private JLabel jlKongGe = new JLabel("          ");
	private JLabel jlShanChu_kehao = new JLabel("课程号：");
	private JTextField jtfShanChu_kehao = new JTextField(12);
	private JButton jbtnDelete = new JButton("删除");
	private int a = 0;  //表示点击了查询
	private String strStuId = null;
	//选课
	public JPanel jpXuanKe = new JPanel(new GridLayout(2,1));
	private JPanel jpThree = new JPanel();
	private JPanel jpFour = new JPanel();
	private JLabel jlStr1 = new JLabel("学生选课");
	private JLabel jlXuanKe_xuehao = new JLabel("学号：");
	private JTextField jtfXuanKe_xuehao = new JTextField(12);
	private JLabel jlXuanKe_course = new JLabel("                课程：");
	private JComboBox jcbXuanKe_course = new JComboBox(str);
	private JButton jbtnXuanKe_queren = new JButton("确认");
	private int bInt = 0;//表示点击了添加课程事件
	
	public StudentChooseCourse(){
		//初始化
		columName.add("学号");
		columName.add("姓名");
		columName.add("课程号");
		//columName.add("学期号");
		columName.add("课程");
		columName.add("学分");
		//查询
		jpOne.add(jlSCC_STU);
		jpOne.add(jtfSCC);
		jpOne.add(jbtnSCC);
		jpTwo.add(jlStr0);
		jpSCC.add(jpTwo);
		jpSCC.add(jpOne);
		//删除
		jpOne.add(jlKongGe);
		jpOne.add(jlShanChu_kehao);
		jpOne.add(jtfShanChu_kehao);
		jtfShanChu_kehao.setEditable(false);
		jpOne.add(jbtnDelete);
		//添加
		jpFour.add(jlStr1);
		jpThree.add(jlXuanKe_xuehao);
		jpThree.add(jtfXuanKe_xuehao);
		jpThree.add(jlXuanKe_course);
		jpThree.add(jcbXuanKe_course);
		jpThree.add(jbtnXuanKe_queren);
		jpXuanKe.add(jpFour);
		jpXuanKe.add(jpThree);
		
		jtSCC = new JTable(rowData,columName);
		jtSCC.setPreferredScrollableViewportSize(new Dimension(880, 300)); //表的大小
		//表中数据居中
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jtSCC.setDefaultRenderer(Object.class, dtcr);
		jspSCC = new JScrollPane(jtSCC);
		
		//显示课程
		try {
			dbconn = linkDB.lindDataBase();
			Statement stmt = dbconn.createStatement();//创建SQL命令对象
		    ResultSet rsCourse = stmt.executeQuery("select distinct course_name from Course");
			while(rsCourse.next()){
				jcbXuanKe_course.addItem(rsCourse.getString("course_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//查询
		jbtnSCC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!jtfSCC.getText().trim().equals("")){
					Serch(jtfSCC.getText().trim());
				}else{
					JOptionPane.showMessageDialog(null, "学号不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//删除
		jbtnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean i = true;
					if(a == 1){
						if(!jtfShanChu_kehao.getText().trim().equals("")){
							try{
								dbconn = linkDB.lindDataBase();
								Statement stmt = dbconn.createStatement();
								ResultSet rs = stmt.executeQuery("select course_score from StudentChooseCourse " +
										"where course_code='"+jtfShanChu_kehao.getText().trim()+"' and student_id='"+strStuId+"'");
								rs.next(); //若catch了说明没课程了
								if(rs.getString("course_score")==null){
									String strAdd = "delete StudentChooseCourse where student_id='"+strStuId+"' and course_score is null and course_code='"+jtfShanChu_kehao.getText().trim()+"'";
									Object[] options={"确认","取消"};
									int response=JOptionPane.showOptionDialog(null, "确认删除该课程？","删除提示！",
										JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
									if(response==0){
										stmt.executeUpdate(strAdd);
										JOptionPane.showMessageDialog(null, "删除成功！","消息提示",JOptionPane.INFORMATION_MESSAGE);
										dbconn.close();
										Serch(strStuId);
										jtfShanChu_kehao.setEditable(false);
									}	
								}else{
									JOptionPane.showMessageDialog(null, "此课程已有成绩，不可删除！","消息提示",JOptionPane.ERROR_MESSAGE);
								}
							}catch(Exception e1){
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, "不存在次课程或已无课程可删除！","错误提示",JOptionPane.ERROR_MESSAGE);
							    i = false;
							}
						}else{
							JOptionPane.showMessageDialog(null, "所要删除的课程号不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
						}
						if(i==true)
						    a = 0;
						else
							a = 1;
						jtfSCC.setText("");
					}else{
						JOptionPane.showMessageDialog(null, "请先查询出你所选的课程！","错误提示",JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		//确认选课
		jbtnXuanKe_queren.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(jtfXuanKe_xuehao.getText().trim().equals("")){
						JOptionPane.showMessageDialog(null, "学号不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
					}
					else if(jcbXuanKe_course.getSelectedItem()==str0){
						JOptionPane.showMessageDialog(null, "课程不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
					}
					else if(!jtfXuanKe_xuehao.getText().trim().equals("")&&jtfXuanKe_xuehao.getText().trim()!=str0){
						dbconn = linkDB.lindDataBase();
						Statement stmt = dbconn.createStatement();
						ResultSet rs = stmt.executeQuery("select student_id from Student where student_id='"+jtfXuanKe_xuehao.getText().trim()+"'");
						boolean b = false;
						if(rs.next()){b = true;}
						if(b == true){
						    String strAdd = "insert into StudentChooseCourse(student_id,course_code) " +
								"select Student.student_id, Course.course_code from Student, Course " +
								"where Student.student_id='"+jtfXuanKe_xuehao.getText().trim()+"' and Course.course_name='"+jcbXuanKe_course.getSelectedItem()+"'";
						    stmt.executeUpdate(strAdd);
						    bInt = 1; //表示点击了选课事件
						    Serch(jtfXuanKe_xuehao.getText().trim());
						    JOptionPane.showMessageDialog(null, "选课成功！","消息提示",JOptionPane.INFORMATION_MESSAGE);
						    bInt = 0;
						}else{
							JOptionPane.showMessageDialog(null, "不存在该学生，无法执行此操作！","错误提示",JOptionPane.ERROR_MESSAGE);
						}
						dbconn.close();
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
	}
	
	//查询
	public void Serch(String str){
		try {
			dbconn = linkDB.lindDataBase();
			Statement stmt = dbconn.createStatement();//创建SQL命令对象
			ResultSet rs = stmt.executeQuery("select a.student_id, a.student_name, d.course_code, d.course_name, d.course_credit " +
					"from Student a,StudentChooseCourse c,Course d where a.student_id=c.student_id and d.course_code=c.course_code " +
					"and a.student_id='"+str+"' order by a.student_id"); //存放找出数据的总行数
			DefaultTableModel dtmSCC = new DefaultTableModel();
			Vector data = new Vector();
			boolean b = false;
			while(rs.next()){
				Vector vNext = new Vector();
				vNext.add(rs.getString("student_id"));
				strStuId = rs.getString("student_id");
				vNext.add(rs.getString("student_name"));
				vNext.add(rs.getString("course_code"));
				vNext.add(rs.getString("course_name"));
				vNext.add(rs.getString("course_credit"));
				data.add(vNext);
				dtmSCC.setDataVector(data, columName);
				jtSCC.setModel(dtmSCC);
				b = true;
				a = 1;
			}
			if(b == false&&bInt == 0) { 
				JOptionPane.showMessageDialog(null, "没有该学生的选课信息！","查询提示",JOptionPane.INFORMATION_MESSAGE);
			    jtfSCC.setText("");
			}
			else if(b == true&&bInt == 0) {
				jtfShanChu_kehao.setEditable(true);
				JOptionPane.showMessageDialog(null, "成功查询到该学生的选课信息！","查询提示",JOptionPane.INFORMATION_MESSAGE);
				jtfSCC.setText("");
			}
			dbconn.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}
