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
	private String[] str = {"--��ѡ������--"};
	private String str0 = "--��ѡ������--";
	private JLabel jlSTR0 = new JLabel("ѧ������");
	private JLabel jlXueHao = new JLabel("ѧ�ţ�");
	private JTextField jtfXueHao = new JTextField(12);
	private JLabel jlXingMIng = new JLabel("       ������");
	private JTextField jtfXingMing = new JTextField(16);
	private JLabel jlXingBie = new JLabel("       �Ա�");
	private JComboBox jcbXingBie = new JComboBox(str);
	private JLabel jlNianLing = new JLabel("       ���䣺");
	private JTextField jtfNianLing = new JTextField(5);
	private JLabel jlMinZu = new JLabel("���壺");
	private JComboBox jcbMinZu = new JComboBox(str);
	private JLabel jlXueYuan = new JLabel("         ѧԺ��");
	private JComboBox jcbXueYuan = new JComboBox(str);
	private JLabel jlZhuanYe = new JLabel("         רҵ��");
	private JComboBox jcbZhuanYe = new JComboBox(str);
	private JLabel jlBanJi = new JLabel("         �༶��");
	private JComboBox jcbBanJi = new JComboBox(str);
	private JLabel jlZZMM = new JLabel("������ò��");
	private JComboBox jcbZZMM = new JComboBox(str);
	private JLabel jlCSRQ = new JLabel("       �������ڣ�");
	private JTextField jtfCSRQ = new JTextField(10);
	private JLabel jlGeShi = new JLabel("��ʽ:xxxx-xx-xx");
	private JLabel jlJTZZ = new JLabel("       ��ͥסַ��");
	private JTextField jtfJTZZ = new JTextField(18);
	public JButton jbtnTianJia = new JButton("����");
	
	public StudentInfo(){
		jcbXingBie.addItem("��");
		jcbXingBie.addItem("Ů");
		
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
			Statement stmt = dbconn.createStatement();//����SQL�������
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
		//ѡ��ѧԺ��ʾרҵ
		jcbXueYuan.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					XueYuan_ZhuanYe(jcbXueYuan, jcbZhuanYe);
				}	
			}
		});
		//ѡ��רҵ��ʾ�༶
		jcbZhuanYe.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED){
					ZhuanYe_BanJi(jcbZhuanYe, jcbBanJi);
				}	
			}
		});
		//����
		jbtnTianJia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TianJia();
			}
			
		});
		
	}
	
	//���ѧԺ��ʾרҵ
	public void XueYuan_ZhuanYe(JComboBox jcb0,JComboBox jcb1){
		try {
			jcb1.removeAllItems();
			jcb1.addItem("--��ѡ������--");
			dbconn = linkDB.lindDataBase();
			Statement stmtMajor = dbconn.createStatement();//����SQL�������
		    ResultSet rsMajor = stmtMajor.executeQuery("select distinct major_name from Major " +
		    		"where academy_code=(select academy_code from Academy where academy_name='"+jcb0.getSelectedItem()+"')"); //����ҳ����ݵ�������
		    while(rsMajor.next()){
		    	jcb1.addItem(rsMajor.getString("major_name"));
		    }
		    dbconn.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	//���רҵ��ʾ�༶
	public void ZhuanYe_BanJi(JComboBox jcb0, JComboBox jcb1){
		try {
			jcb1.removeAllItems();
			jcb1.addItem("--��ѡ������--");
			dbconn = linkDB.lindDataBase();
			Statement stmtMajor = dbconn.createStatement();//����SQL�������
		    ResultSet rsMajor = stmtMajor.executeQuery("select distinct class_name from Class " +
		    		"where major_code=(select major_code from Major where major_name='"+jcb0.getSelectedItem()+"')"); //����ҳ����ݵ�������
		    while(rsMajor.next()){
		    	jcb1.addItem(rsMajor.getString("class_name"));
		    }
		    dbconn.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}	
	}
	
	//����
	public void TianJia(){
		int a1=0,a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,a10=0,a11=0;
		if(jtfXueHao.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "ѧ�Ų���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
		}else{a1=1;}
		if(jtfXingMing.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "��������Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
		}else{a2=1;}
		if(jcbXingBie.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "�Ա���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);    
		}else{a3=1;}
		if(jtfNianLing.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "���䲻��Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
		}else{a4=1;}
		if(jcbMinZu.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "���岻��Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
		}else{a5=1;}
		if(jcbXueYuan.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "ѧԺ����Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
		}else{a6=1;}
		if(jcbZhuanYe.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "רҵ����Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
		}else{a7=1;}
		if(jcbBanJi.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "�༶����Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
		}else{a8=1;}
		if(jcbZZMM.getSelectedItem()==str0){
			JOptionPane.showMessageDialog(null, "������ò����Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
		}else{a9=1;}
		if(jtfCSRQ.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "�������ڲ���Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
		}else{a10=1;}
		if(jtfJTZZ.getText().trim().equals("")){
			JOptionPane.showMessageDialog(null, "��ͥסַ����Ϊ�գ�","������ʾ",JOptionPane.ERROR_MESSAGE);
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
					JOptionPane.showMessageDialog(null, "���Ӹ�ѧ���ɹ���","��Ϣ��ʾ",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "�Ѵ��ڸ�ѧ��ѧ����","������ʾ",JOptionPane.INFORMATION_MESSAGE);
				}
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
	}
}