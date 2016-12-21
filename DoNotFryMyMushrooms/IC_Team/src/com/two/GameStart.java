package com.two;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameStart extends JPanel implements Runnable {

	private Background background = new Background();
	private Car car = new Car(this);
	private Stones stones0 = new Stones(this);
	private GameStatus gameStatus = new GameStatus();
	private Score score = new Score(this);
	private BufferedImage canvas;
	private Tip tip = new Tip();
	// 游戏状态
	public int status;
	public static final int READY = 0;
	public static final int PLAYING = 1;
	public static final int GAMEOVER = 2;
	public static final int WAIT = 3;
	long starttime, nowtime;
	// private Music music;

	// 判断左右
	boolean directionLeft = false; // 方向，true表示按下，false表示没按下
	boolean directionRight = false;

	public GameStart() throws IOException {
		// 键盘事件
		// music=new Music("/music/bg.wav");
		new Music("/music/bg.wav").start();
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					directionLeft = false;
					break;
				case KeyEvent.VK_RIGHT:
					directionRight = false;
					break;
				default:
					break;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				switch (e.getKeyCode()) {
				case KeyEvent.VK_SPACE:
					if (status == PLAYING)
						status = WAIT;
					break;
				case KeyEvent.VK_ENTER:
					if (status == READY) {
						status = PLAYING;
						starttime = System.currentTimeMillis();
					}
					if (status == GAMEOVER) {
						status = READY;
					}
					if (status == WAIT) {
						status = PLAYING;
					}
					break;
				case KeyEvent.VK_LEFT:
					directionLeft = true;
					break;
				case KeyEvent.VK_RIGHT:
					directionRight = true;
					break;
				default:
					break;
				}
			}
		});
	}

	// 面板初定义完毕后执行
	@Override
	public void doLayout() {
		status = READY;
		canvas = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_ARGB);
		new Thread(this).start();
		new Thread(car).start();

	}

	public int panelHeight() {
		return background.groundHeight();
	}

	@Override
	public void run() {
		try {
			while (true) {
				Thread.sleep(1000 / 60);
				this.gamePaint();
				this.repaint();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	// 重绘方法

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(canvas, 0, 0, null);
	}

	private void gamePaint() {

		// 重绘panel
		// this.repaint();
		Graphics g = canvas.getGraphics();
		background.paintground(g);
		if (status == READY) {
			gameStatus.paintstart(g);
			tip.paint(g);
			score.rset();
		}
		if (status == GAMEOVER) {
			gameStatus.paintfinish(g);
			score.paintover(g);
		}
		if (status == PLAYING) {

			nowtime = System.currentTimeMillis();
			stones0.paint(g);
			// 判断 蘑菇是否撞上炸弹
			if (car.hitStone(stones0.stone0))
				status = GAMEOVER;
			if (car.hitStone(stones0.stone1))
				status = GAMEOVER;
			if (car.hitStone(stones0.stone2))
				status = GAMEOVER;
			if (car.hitStone(stones0.stone3))
				status = GAMEOVER;
			if (car.hitStone(stones0.stone4))
				status = GAMEOVER;
			if (status == GAMEOVER) {
				new Music("/music/dath.wav").start();
				car.reset();
				stones0.reset();
			}
			car.paintCar(g);
			score.paint(g);

		}
		if (status == WAIT)
			gameStatus.paintwait(g);

	}
}
