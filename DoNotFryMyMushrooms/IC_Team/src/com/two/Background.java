package com.two;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Background {
	private BufferedImage background;
	
	public Background() {
		try{
		background = ImageIO.read(this.getClass().getResource("/img/beijing.png"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int groundHeight(){
		return background.getHeight();
	}
	public void paintground(Graphics g){
		g.drawImage(background, 0, 0, null);
	}
	

}
