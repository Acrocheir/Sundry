package expStudent;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class StudentScore {
	private static LinkDB linkDB = new LinkDB();
	private static Connection dbconn;
	public JScrollPane jspStudentScore = null;
	private JTable jtStudentScore = null;
	private Vector rowData = new Vector();
	private Vector columName = new Vector();
	//查询
	public JPanel jpChaXun = new JPanel(new GridLayout(3, 1));
	private JPanel jpOne = new JPanel();
	private JPanel jpTwo = new JPanel();
	private JLabel jL0 = new JLabel("成绩查询");
	private JLabel jlXueHao = new JLabel("学号：");
	private JTextField jtfXueHao = new JTextField(12);
	private JButton jbtnChaXun = new JButton("查询");
	
	public StudentScore(){
		//初始化
		columName.add("学号");
		columName.add("姓名");
		columName.add("课程名称");
		columName.add("考试成绩");
		columName.add("是否补考");
		columName.add("补考成绩");
		
		jtStudentScore = new JTable(rowData,columName);//数据加到表格中
		jtStudentScore.setPreferredScrollableViewportSize(new Dimension(880, 350)); //表的大小
		//表中数据居中
	    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	    dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jtStudentScore.setDefaultRenderer(Object.class, dtcr);
		jspStudentScore = new JScrollPane(jtStudentScore);
		
		try {
			dbconn = linkDB.lindDataBase();
			Statement stmt = dbconn.createStatement();//创建SQL命令对象
		    ResultSet rs = stmt.executeQuery("select a.student_id, a.student_name, b.course_name, c.course_score, " +
		    		"c.course_examagain, c.examagian_score from Student a, Course b, StudentChooseCourse c " +
		    		"where c.student_id=a.student_id and c.course_code=b.course_code order by a.student_id"); //存放找出数据的总行数
		    while(rs.next()){
		    	Vector vNext = new Vector();
		    	vNext.add(rs.getString("student_id"));
		    	vNext.add(rs.getString("student_name"));
		    	vNext.add(rs.getString("course_name"));
		    	vNext.add(rs.getString("course_score"));
		    	vNext.add(rs.getString("course_examagain"));
		    	vNext.add(rs.getString("examagian_score"));
		    	rowData.add(vNext);
		    }
		    dbconn.close();
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//查询
		jpOne.add(jL0);
		jpTwo.add(jlXueHao);
		jpTwo.add(jtfXueHao);
		jpTwo.add(jbtnChaXun);
		jpChaXun.add(jpOne);
		jpChaXun.add(jpTwo);
		jbtnChaXun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtfXueHao.getText().trim().equals("")){
					JOptionPane.showMessageDialog(null, "课程号不能为空！","错误提示",JOptionPane.ERROR_MESSAGE);
				}
				else{
					try {
						dbconn = linkDB.lindDataBase();
						Statement stmt = dbconn.createStatement();//创建SQL命令对象
					    ResultSet rs = stmt.executeQuery("select a.student_id, a.student_name, b.course_name, c.course_score, " +
					    		"c.course_examagain, c.examagian_score from Student a, Course b, StudentChooseCourse c " +
					    		"where c.student_id=a.student_id and c.course_code=b.course_code and a.student_id='"+jtfXueHao.getText().trim()+"' order by a.student_id"); //存放找出数据的总行数
					    Vector data = new Vector();
					    DefaultTableModel dtmStuCourse = new DefaultTableModel();
					    boolean b = false;
					    while(rs.next()){
					    	Vector vNext = new Vector();
					    	vNext.add(rs.getString("student_id"));
					    	vNext.add(rs.getString("student_name"));
					    	vNext.add(rs.getString("course_name"));
					    	vNext.add(rs.getString("course_score"));
					    	vNext.add(rs.getString("course_examagain"));
					    	vNext.add(rs.getString("examagian_score"));
					    	data.add(vNext);
					    	dtmStuCourse.setDataVector(data, columName);
					        jtStudentScore.setModel(dtmStuCourse);
					        b = true;
					    }
					    dbconn.close();
					    if(b==false){
					    	JOptionPane.showMessageDialog(null, "无该学生的成绩信息！","消息提示",JOptionPane.INFORMATION_MESSAGE);
					    }else {
					    	JOptionPane.showMessageDialog(null, "查询学生成绩成功！","消息提示",JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
	}
}
