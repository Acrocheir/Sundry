package WaO;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import WaO.QWER.WareHouse;

/**
 * 生产者线程
 */
public class GUI_Producer extends JPanel implements Runnable {
	private SteamedWarehouse sw=null;
	private JProgressBar jp_producer = new JProgressBar(); //生产者进度条
	private JLabel jl_chushi = null;
	private JLabel jl_mantou = null;
	private ImageIcon icon = null;
	
	public GUI_Producer(SteamedWarehouse sw){
		this.sw = sw;
		
		icon = new ImageIcon("F:\\Eclipse\\大三上\\Computer_OS\\src\\Img\\Chushi.jpg"); //厨师图片
		jl_chushi = new JLabel(icon);
		jl_mantou = new JLabel(sw.icon);
		jl_mantou.setEnabled(false);
		this.add(jl_chushi);
		this.add(jp_producer);
		this.add(jl_mantou);
	    jp_producer.setStringPainted(true);  //进度显示数字
	    jp_producer.setForeground(Color.GREEN);    
	}

	public void run() {
		for(int i=1;;i++){
			Steamed sd = new Steamed(i);
		//	if(sw.getNum()==5){
		//	}else {

			for (int j = 0; j < 101; j++) {
				try {
					Thread.sleep(50); // (0.1s+睡眠时间)根据线程数定
				} catch (InterruptedException e) {}
				jp_producer.setValue(j); // 进度条赋值
				if(j==100)
					{jl_mantou.setEnabled(true);}
				else 
					jl_mantou.setEnabled(false);
			}	
			sw.push(sd);
		//	}
		}
	}
}
