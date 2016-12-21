package com.two;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Car implements Runnable{
	private BufferedImage imgCar;
	private int x = 20; // С���ĺ�����
	private int y = 380; // С����������
	private GameStart gs;
	public Car(GameStart gs) throws IOException {
		this.gs=gs;
		imgCar = ImageIO.read(this.getClass().getResource("/img/111.png"));
		x = 20;
		y = 380;
	}
	//重置
    public void reset(){
    	x = 20;
		y = 380;
    }
	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}
	//用来检测按键 状态
	@Override
	public void run() {
		while(true){
			try{
			Thread.sleep(5);
			if(gs.directionLeft==true&&x>0)
			    x--;
			if(gs.directionRight==true&&x<gs.getWidth()-imgCar.getWidth())
				x++;
			}catch(Exception e){}
		}
	}

    //判断是否撞上炸弹
	public boolean hitStone(Stone stone) {
		if (x < stone.getX()-stone.getWidth() || x > stone.getX()+stone.getWidth())
			return false;
		else if(y>stone.getY()+stone.getHeight()-5)
			return false;
		else
			return true;
	}

	public void paintCar(Graphics g) {
		g.drawImage(imgCar, x, y, null);
	}
}
