package com.two;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;

public class Stone {
	private int x = 20;
	private int y = -60; // ʯͷ������
	private BufferedImage imgStone; // ʯͷ
	private GameStart gameStart;
	private int speed;
	private long Starttime;

	public Stone(GameStart gameStart) {
		this.gameStart = gameStart;
		Starttime = gameStart.nowtime;
		randomY(); // ������y
		try {
			imgStone = ImageIO.read(this.getClass().getResource("/img/boomok.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		speed = 1;
	}

	public void reset() {
		randomY();
		speed = 1;
	}

	public int getHeight() {
		return imgStone.getHeight();
	}

	public int getWidth() {
		return imgStone.getWidth();
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

	/**
	 * ��ȡ�����
	 */
	private void randomY() {
		Random random = new Random();
		y = (-1) * (random.nextInt(700));
		y=0-(int)(Math.random()*440+60);
		
//		y = (-1) * (random.nextInt(25)*random.nextInt(20));
	}

	/**
	 * ����ʯͷ
	 */
	public void paintStone(Graphics g) {
		g.drawImage(imgStone, x, y, null);
		// if (y > gameStart.panelHeight() - this.getHeight()) {
		if (y >= 380) {
			// y = -30;
			randomY(); // �ٴλ�����y
		}
		if (gameStart.nowtime - Starttime >= 10000 && speed <= 5) {
			speed++;
			Starttime = gameStart.nowtime;
		}
		y += speed;
	}

}
