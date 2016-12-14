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
	//��ʼ��
	public JPanel jpSCC = new JPanel(new GridLayout(2,1));
	public JScrollPane jspSCC = null;
	private JTable jtSCC = null;
	private Vector rowData = new Vector();
	private Vector columName = new Vector();
	private String[] str = {"--��ѡ������--"};
	private String str0 = "--��ѡ������--";
	//��ѯ
	private JPanel jpOne = new JPanel();
	private JPanel jpTwo = new JPanel();
	private JLabel jlStr0 = new JLabel("ѡ�β�ѯ");
	private JLabel jlSCC_STU = new JLabel("ѧ�ţ�");
	private JTextField jtfSCC = new JTextField(12);
	private JButton jbtnSCC = new JButton("��ѯ");
	//ɾ��
	private JLabel jlKongGe = new JLabel("          ");
	private JLabel jlShanChu_kehao = new JLabel("�γ̺ţ�");
	private JTextField jtfShanChu_kehao = new JTextField(12);
	private JButton jbtnDelete = new JButton("ɾ��");
	private int a = 0;  //��ʾ����˲�ѯ
	private String strStuId = null;
	//ѡ��
	public JPanel jpXuanKe = new JPanel(new GridLayout(2,1));
	private JPanel jpThree = new JPanel();
	private JPanel jpFour = new JPanel();
	private JLabel jlStr1 = new JLabel("ѧ��ѡ��");
	private JLabel jlXuanKe_xuehao = new JLabel("ѧ�ţ�");
	private JTextField jtfXuanKe_xuehao = new JTextField(12);
	private JLabel jlXuanKe_course = new JLabel("                �γ̣�");
	private JComboBox jcbXuanKe_course = new JComboBox(str);
	private JButton jbtnXuanKe_queren = new JButton("ȷ��");
	private int bInt = 0;//��ʾ��������ӿγ��¼�
	
	public StudentChooseCourse(){
		//��ʼ��
		columName.add("ѧ��");
		columName.add("����");
		columName.add("�γ̺�");
		//columName.add("ѧ�ں�");
		columName.add("�γ�");
		columName.add("ѧ��");
		//��ѯ
		jpOne.add(jlSCC_STU);
		jpOne.add(jtfSCC);
		jpOne.add(jbtnSCC);
		jpTwo.add(jlStr0);
		jpSCC.add(jpTwo);
		jpSCC.add(jpOne);
		//ɾ��
		jpOne.add(jlKongGe);
		jpOne.add(jlShanChu_kehao);
		jpOne.add(jtfShanChu_kehao);
		jtfShanChu_kehao.setEditable(false);
		jpOne.add(jbtnDelete);
		//����
		jpFour.add(jlStr1);
		jpThree.add(jlXuanKe_xuehao);
		jpThree.add(jtfXuanKe_xuehao);
		jpThree.add(jlXuanKe_course);
		jpThree.add(jcbXuanKe_course);
		jpThree.add(jbtnXuanKe_queren);
		jpXuanKe.add(jpFour);
		jpXuanKe.add(jpThree);
		
		jtSCC = new JTable(rowData,columName);
		jtSCC.setPreferredScrollableViewportSize(new Dimension(880, 300)); //���Ĵ�С
		//�������ݾ���
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jtSCC.setDefaultRenderer(Object.class, dtcr);
		jspSCC = new JScrollPane(jtSCC);
		
		//��ʾ�γ�
		try {
			dbconn = linkDB.lindDataBase();
			Statement stmt = dbconn.createStatement();//����SQL�������
		    ResultSet rsCourse = stmt.executeQuery("select distinct course_name from Course");
			while(rsCourse.next()){
				jcbXuanKe_course.addItem(rsCourse.getString("course_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//��ѯ
		jbtnSCC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!jtfSCC.getText().trim().equals("")){
					Serch(jtfSCC.getText().trim());
				}else{
					JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		//ɾ��
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
								rs.next(); //��catch��˵��û�γ���
								if(rs.getString("course_score")==null){
									String strAdd = "delete StudentChooseCourse where student_id='"+strStuId+"' and course_score is null and course_code='"+jtfShanChu_kehao.getText().trim()+"'";
									Object[] options={"ȷ��","ȡ��"};
									int response=JOptionPane.showOptionDialog(null, "ȷ��ɾ���ÿγ̣�","ɾ����ʾ��",
										JOptionPane.YES_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
									if(response==0){
										stmt.executeUpdate(strAdd);
										JOptionPane.showMessageDialog(null, "ɾ���ɹ���","��Ϣ��ʾ",JOptionPane.INFORMATION_MESSAGE);
										dbconn.close();
										Serch(strStuId);
										jtfShanChu_kehao.setEditable(false);
									}	
								}else{
									JOptionPane.showMessageDialog(null, "�˿γ����гɼ�������ɾ����","��Ϣ��ʾ",JOptionPane.ERROR_MESSAGE);
								}
							}catch(Exception e1){
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, "�����ڴογ̻����޿γ̿�ɾ����","������ʾ",JOptionPane.ERROR_MESSAGE);
							    i = false;
							}
						}else{
							JOptionPane.showMessageDialog(null, "��Ҫɾ���Ŀγ̺Ų���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
						}
						if(i==true)
						    a = 0;
						else
							a = 1;
						jtfSCC.setText("");
					}else{
						JOptionPane.showMessageDialog(null, "���Ȳ�ѯ������ѡ�Ŀγ̣�","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		//ȷ��ѡ��
		jbtnXuanKe_queren.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(jtfXuanKe_xuehao.getText().trim().equals("")){
						JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
					else if(jcbXuanKe_course.getSelectedItem()==str0){
						JOptionPane.showMessageDialog(null, "�γ̲���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
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
						    bInt = 1; //��ʾ�����ѡ���¼�
						    Serch(jtfXuanKe_xuehao.getText().trim());
						    JOptionPane.showMessageDialog(null, "ѡ�γɹ���","��Ϣ��ʾ",JOptionPane.INFORMATION_MESSAGE);
						    bInt = 0;
						}else{
							JOptionPane.showMessageDialog(null, "�����ڸ�ѧ�����޷�ִ�д˲�����","������ʾ",JOptionPane.ERROR_MESSAGE);
						}
						dbconn.close();
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
	}
	
	//��ѯ
	public void Serch(String str){
		try {
			dbconn = linkDB.lindDataBase();
			Statement stmt = dbconn.createStatement();//����SQL�������
			ResultSet rs = stmt.executeQuery("select a.student_id, a.student_name, d.course_code, d.course_name, d.course_credit " +
					"from Student a,StudentChooseCourse c,Course d where a.student_id=c.student_id and d.course_code=c.course_code " +
					"and a.student_id='"+str+"' order by a.student_id"); //����ҳ����ݵ�������
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
				JOptionPane.showMessageDialog(null, "û�и�ѧ����ѡ����Ϣ��","��ѯ��ʾ",JOptionPane.INFORMATION_MESSAGE);
			    jtfSCC.setText("");
			}
			else if(b == true&&bInt == 0) {
				jtfShanChu_kehao.setEditable(true);
				JOptionPane.showMessageDialog(null, "�ɹ���ѯ����ѧ����ѡ����Ϣ��","��ѯ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				jtfSCC.setText("");
			}
			dbconn.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
}