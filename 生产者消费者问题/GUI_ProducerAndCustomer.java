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
	
	private JButton producer_start = new JButton("开始做馒头");
	public JButton customer_start = new JButton("开始吃馒头");
	private JLabel jl_chushi = null;
	private ImageIcon icon = null;
	
	public GUI_ProducerAndCustomer() {
		super("馒头铺模拟生产者和消费者问题1.0");
		this.setSize(800, 600);
		Toolkit tool = Toolkit.getDefaultToolkit(); //获取屏幕大小
		Dimension dim = tool.getScreenSize(); //存放获取屏幕的大小
		int swidth = (int)dim.getWidth(); //宽高度转换为int型
		int sheight = (int)dim.getHeight();
		this.setLocation((swidth-800)/2,(sheight-600)/2); //居中
		
		icon = new ImageIcon("F:\\Eclipse\\大三上\\Computer_OS\\src\\Img\\Chushi.jpg");
		jl_chushi = new JLabel(icon);
		this.setLayout(new GridLayout(3, 1)); //布局
		gui_producer.add(producer_start);
		gui_customer.add(customer_start);
	//	this.customer_start.setEnabled(false);
		this.add(gui_producer);
		this.add(sw);
		this.add(gui_customer);
		
		this.setResizable(false); //不可最大化
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//设置图标
		String path="/Img/thumb.jpg";
		try{
			Image img = ImageIO.read(this.getClass().getResource(path));
			this.setIconImage(img);
		}catch(IOException e){
			e.printStackTrace();
		}
		//生产者开始生产点击事件
		producer_start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new Thread(gui_producer).start(); //生产者线程
			}
		});
		//消费者开始消费点击时间
		customer_start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new Thread(gui_customer).start(); //消费者线程
			}
		});
	}
	
	public static void main(String[] args) {
		new GUI_ProducerAndCustomer();
	//	new Thread(new GUI_Producer()).start(); //生产者线程
	//	new Thread(new GUI_Customer()).start(); //消费者线程
	}
}
