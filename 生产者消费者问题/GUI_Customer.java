package WaO;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import WaO.QWER.WareHouse;

/**
 * �������߳�
 */
public class GUI_Customer extends JPanel implements Runnable{
	private SteamedWarehouse sw =null;
	private GUI_ProducerAndCustomer pc;
	private JProgressBar jp_customerBar = new JProgressBar();
	private JLabel jl_mantou = null;
	private JLabel jl_chimantou = null;
	private ImageIcon icon = null;
	
	public GUI_Customer(SteamedWarehouse sw){
		super();
		this.sw = sw;
		
		icon = new ImageIcon("F:\\Eclipse\\������\\Computer_OS\\src\\Img\\Chimantou.jpg"); //��ʦͼƬ
		jl_chimantou = new JLabel(icon);
		jl_mantou = new JLabel(sw.icon);
		jl_mantou.setEnabled(false);
		this.add(jl_chimantou);
		this.add(jp_customerBar);
		this.add(jl_mantou);
		jp_customerBar.setStringPainted(true);  //��ʾ����
		jp_customerBar.setForeground(Color.RED);
		
	}
 
	public void run() {
		for(int i=1;;i++){
			jl_mantou.setEnabled(false);
			Steamed sd = sw.pop();
		//	System.out.println("�����ˣ�"+sd);
			for (int j = 0; j < 101; j++) {
				try {
					Thread.sleep(10); // (0.1s+˯��ʱ��)�����߳�����
				} catch (InterruptedException e) {
			}
				jp_customerBar.setValue(j); // ��������ֵ
				if(j==100)
					jl_mantou.setEnabled(true);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
