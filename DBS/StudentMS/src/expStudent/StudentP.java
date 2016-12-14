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

public class StudentP {
	private LinkDB linkDB = new LinkDB();
	private Connection dbconn;
	
	public JPanel jpStuP = new JPanel(new GridLayout(3,1));
	public JScrollPane jspStu_P = null;
	private JTable jtP = null;
	private JPanel jpOne = new JPanel();
	private JPanel jpTwo = new JPanel();
	private JLabel jlStr0 = new JLabel("�ͷ���ѯ");
	private JLabel jlStudentId = new JLabel("ѧ��ѧ�ţ�");
	private JTextField jtfStudentId = new JTextField(15);
	private JButton jbtnChaxun = new JButton("��ѯ");
	private Vector columnName_P = new Vector();
	
	public StudentP(){
		jpOne.add(jlStr0);
		jpTwo.add(jlStudentId);
		jpTwo.add(jtfStudentId);
		jpTwo.add(jbtnChaxun);
		jpStuP.add(jpOne);
		jpStuP.add(jpTwo);
		columnName_P.add("�ͷ����");
		columnName_P.add("ѧ��");
		columnName_P.add("����");
		columnName_P.add("ѧԺ");
		columnName_P.add("רҵ");
		columnName_P.add("�༶");
		columnName_P.add("�ͷ�ʱ��");
		columnName_P.add("�ͷ�����");
		
		jtP = new JTable(null,columnName_P);
		jtP.setPreferredScrollableViewportSize(new Dimension(880, 350)); //���Ĵ�С
		//�������ݾ���
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		jtP.setDefaultRenderer(Object.class, dtcr);
		jspStu_P = new JScrollPane(jtP);
		
		//��ѯ
		jbtnChaxun.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jtfStudentId.getText().trim().equals(""))
					JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
				else{
					try{
						dbconn = linkDB.lindDataBase();
						Statement stmt = dbconn.createStatement();
						ResultSet rsR = stmt.executeQuery("select a.punishment_code, a.student_id, b.student_name, c.academy_name, d.major_name,e.class_name, a.punishment_time, a.punishment_note " +
				    		"from Punishment a, Student b, Academy c, Major d, Class e " +
				    		"where a.student_id=b.student_id and c.academy_code=d.academy_code and b.major_code=d.major_code " +
				    		"and b.class_code=e.class_code and a.student_id='"+jtfStudentId.getText().trim()+"'");
						Vector dataP = new Vector();
						DefaultTableModel dtmStuP = new DefaultTableModel();
						boolean b = false;
						while(rsR.next()){
							Vector vNext = new Vector();
							vNext.add(rsR.getString("punishment_code"));
							vNext.add(rsR.getString("student_id"));
							vNext.add(rsR.getString("student_name"));
							vNext.add(rsR.getString("academy_name"));
							vNext.add(rsR.getString("major_name"));
							vNext.add(rsR.getString("class_name"));
							vNext.add(rsR.getString("punishment_time"));
							vNext.add(rsR.getString("punishment_note"));
							dataP.add(vNext);
							dtmStuP.setDataVector(dataP, columnName_P);
							jtP.setModel(dtmStuP);
							b = true;
						}
						dbconn.close();
						if(b==true)
							JOptionPane.showMessageDialog(null, "�ͷ���ѯ�ɹ���","��ѯ��ʾ",JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null, "��ѧ�����κγͷ���","��ѯ��ʾ",JOptionPane.INFORMATION_MESSAGE);
					}catch(Exception e2){
						e2.printStackTrace();
						JOptionPane.showMessageDialog(null, "�˲�ѯ��Ч��","������ʾ",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
	}
}