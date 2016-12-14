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
	//��ѯ
	public JPanel jpChaXun = new JPanel(new GridLayout(3, 1));
	private JPanel jpOne = new JPanel();
	private JPanel jpTwo = new JPanel();
	private JLabel jL0 = new JLabel("�ɼ���ѯ");
	private JLabel jlXueHao = new JLabel("ѧ�ţ�");
	private JTextField jtfXueHao = new JTextField(12);
	private JButton jbtnChaXun = new JButton("��ѯ");
	
	public StudentScore(){
		//��ʼ��
		columName.add("ѧ��");
		columName.add("����");
		columName.add("�γ�����");
		columName.add("���Գɼ�");
		columName.add("�Ƿ񲹿�");
		columName.add("�����ɼ�");
		
		jtStudentScore = new JTable(rowData,columName);//���ݼӵ�������
		jtStudentScore.setPreferredScrollableViewportSize(new Dimension(880, 350)); //���Ĵ�С
		//�������ݾ���
	    DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
	    dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jtStudentScore.setDefaultRenderer(Object.class, dtcr);
		jspStudentScore = new JScrollPane(jtStudentScore);
		
		try {
			dbconn = linkDB.lindDataBase();
			Statement stmt = dbconn.createStatement();//����SQL�������
		    ResultSet rs = stmt.executeQuery("select a.student_id, a.student_name, b.course_name, c.course_score, " +
		    		"c.course_examagain, c.examagian_score from Student a, Course b, StudentChooseCourse c " +
		    		"where c.student_id=a.student_id and c.course_code=b.course_code order by a.student_id"); //����ҳ����ݵ�������
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
		
		//��ѯ
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
					JOptionPane.showMessageDialog(null, "�γ̺Ų���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
				}
				else{
					try {
						dbconn = linkDB.lindDataBase();
						Statement stmt = dbconn.createStatement();//����SQL�������
					    ResultSet rs = stmt.executeQuery("select a.student_id, a.student_name, b.course_name, c.course_score, " +
					    		"c.course_examagain, c.examagian_score from Student a, Course b, StudentChooseCourse c " +
					    		"where c.student_id=a.student_id and c.course_code=b.course_code and a.student_id='"+jtfXueHao.getText().trim()+"' order by a.student_id"); //����ҳ����ݵ�������
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
					    	JOptionPane.showMessageDialog(null, "�޸�ѧ���ĳɼ���Ϣ��","��Ϣ��ʾ",JOptionPane.INFORMATION_MESSAGE);
					    }else {
					    	JOptionPane.showMessageDialog(null, "��ѯѧ���ɼ��ɹ���","��Ϣ��ʾ",JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
	}
}