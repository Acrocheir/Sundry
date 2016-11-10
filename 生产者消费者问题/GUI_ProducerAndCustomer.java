package WaO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI_ProducerAndCustomer extends JFrame {
	private SteamedWarehouse sw = new SteamedWarehouse();
	GUI_Producer gui_producer = new GUI_Producer(sw);
	GUI_Customer gui_customer = new GUI_Customer(sw);
	
	private JButton producer_start = new JButton("��ʼ����ͷ");
	public JButton customer_start = new JButton("��ʼ����ͷ");
	private JLabel jl_chushi = null;
	private ImageIcon icon = null;
	
	public GUI_ProducerAndCustomer() {
		super("��ͷ��ģ�������ߺ�����������1.0");
		this.setSize(800, 600);
		Toolkit tool = Toolkit.getDefaultToolkit(); //��ȡ��Ļ��С
		Dimension dim = tool.getScreenSize(); //��Ż�ȡ��Ļ�Ĵ�С
		int swidth = (int)dim.getWidth(); //��߶�ת��Ϊint��
		int sheight = (int)dim.getHeight();
		this.setLocation((swidth-800)/2,(sheight-600)/2); //����
		
		icon = new ImageIcon("F:\\Eclipse\\������\\Computer_OS\\src\\Img\\Chushi.jpg");
		jl_chushi = new JLabel(icon);
		this.setLayout(new GridLayout(3, 1)); //����
		gui_producer.add(producer_start);
		gui_customer.add(customer_start);
	//	this.customer_start.setEnabled(false);
		this.add(gui_producer);
		this.add(sw);
		this.add(gui_customer);
		
		this.setResizable(false); //�������
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//����ͼ��
		String path="/Img/thumb.jpg";
		try{
			Image img = ImageIO.read(this.getClass().getResource(path));
			this.setIconImage(img);
		}catch(IOException e){
			e.printStackTrace();
		}
		//�����߿�ʼ��������¼�
		producer_start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new Thread(gui_producer).start(); //�������߳�
			}
		});
		//�����߿�ʼ���ѵ��ʱ��
		customer_start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new Thread(gui_customer).start(); //�������߳�
			}
		});
	}
	
	public static void main(String[] args) {
		new GUI_ProducerAndCustomer();
	//	new Thread(new GUI_Producer()).start(); //�������߳�
	//	new Thread(new GUI_Customer()).start(); //�������߳�
	}
}
