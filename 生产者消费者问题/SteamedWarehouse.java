package WaO;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SteamedWarehouse extends JPanel {
	int index = 0;
	int num;
	public void setNum(int num){
		this.num = num;
	}
	public int getNum(){
		return this.num;
	}
	Steamed[] sted = new Steamed[5];  //馒头仓库容量
	
	JLabel jl_amount01 = null;
	JLabel jl_amount02= null;
	JLabel jl_amount03 = null;
	JLabel jl_amount04 = null;
	JLabel jl_amount05 = null;
	ImageIcon icon = null;
	public SteamedWarehouse(){
		icon = new ImageIcon("F:\\Eclipse\\大三上\\Computer_OS\\src\\Img\\Mantou.jpg");
		jl_amount01 = new JLabel(icon);
		jl_amount02 = new JLabel(icon);
		jl_amount03 = new JLabel(icon);
		jl_amount04 = new JLabel(icon);
		jl_amount05 = new JLabel(icon);
		jl_amount01.setEnabled(false);
		jl_amount02.setEnabled(false);
		jl_amount03.setEnabled(false);
		jl_amount04.setEnabled(false);
		jl_amount05.setEnabled(false);
		
		this.add(jl_amount01);
		this.add(jl_amount02);
		this.add(jl_amount03);
		this.add(jl_amount04);
		this.add(jl_amount05);
	}
	
	public synchronized void push(Steamed sd){
		while(getNum() == sted.length){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		notifyAll();
		sted[index] = sd;
		index++;
		setNum(++num);
		System.out.println(getNum());
		switch (getNum()) {
		    case 1:
		    	jl_amount01.setEnabled(true);
			    break;
		    case 2:
		    	jl_amount01.setEnabled(true);
				jl_amount02.setEnabled(true);
		    	break;
		    case 3:
		    	jl_amount01.setEnabled(true);
				jl_amount02.setEnabled(true);
				jl_amount03.setEnabled(true);
		    	break;
		    case 4:
		    	jl_amount01.setEnabled(true);
				jl_amount02.setEnabled(true);
				jl_amount03.setEnabled(true);
				jl_amount04.setEnabled(true);
		    	break;
		    case 5:
		    	jl_amount01.setEnabled(true);
				jl_amount02.setEnabled(true);
				jl_amount03.setEnabled(true);
				jl_amount04.setEnabled(true);
				jl_amount05.setEnabled(true);
		    	break;
		    default:
			    break;
		}
	}
	
	public synchronized Steamed pop(){
		while(getNum() == 0){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		notifyAll();
		index--;
		setNum(--num);
		System.out.println(getNum());
		switch (getNum()) {
		    case 0:
		    	jl_amount01.setEnabled(false);
	        	jl_amount02.setEnabled(false);
				jl_amount03.setEnabled(false);
				jl_amount04.setEnabled(false);
				jl_amount05.setEnabled(false);
		    	break;
	        case 1:
	        	jl_amount01.setEnabled(true);
	        	jl_amount02.setEnabled(false);
				jl_amount03.setEnabled(false);
				jl_amount04.setEnabled(false);
				jl_amount05.setEnabled(false);
	    	    break;
	        case 2:
	        	jl_amount01.setEnabled(true);
	        	jl_amount02.setEnabled(true);
				jl_amount03.setEnabled(false);
				jl_amount04.setEnabled(false);
				jl_amount05.setEnabled(false);
	        	break;
	        case 3:
	        	jl_amount01.setEnabled(true);
	        	jl_amount02.setEnabled(true);
				jl_amount03.setEnabled(true);
				jl_amount04.setEnabled(false);
				jl_amount05.setEnabled(false);
	        	break;
	        case 4:
	        	jl_amount01.setEnabled(true);
	        	jl_amount02.setEnabled(true);
				jl_amount03.setEnabled(true);
				jl_amount04.setEnabled(true);
				jl_amount05.setEnabled(false);
	        	break;
	        case 5:
	        	jl_amount01.setEnabled(true);
	        	jl_amount02.setEnabled(true);
				jl_amount03.setEnabled(true);
				jl_amount04.setEnabled(true);
				jl_amount05.setEnabled(true);
	        	break;
	        default:
	        	break;
		}
		return sted[index];
	}
}
