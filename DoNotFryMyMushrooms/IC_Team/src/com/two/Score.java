package com.two;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Score {
	private Font scorefont;
	private int score;
	private int time;
	int x, y;

	public Score(GameStart ga) {
		scorefont = new Font(Font.MONOSPACED, Font.BOLD, 30);
		score = 0;
		x = 75;
		y = 170;
	}
	/*
	 * 绘制分数显示
	 */
	public void paint(Graphics g) {
		g.setFont(scorefont);
		g.setColor(Color.ORANGE);
		g.drawString("得分：" + score, 0, 30);
		score ++;//按时间加分

	}

	/*
	 * 重置分数
	 */
	public void rset() {
		score = 0;
	}

	/*
	 * 按时间加分
	 */

	public void paintover(Graphics g) {
		g.setFont(scorefont);
		g.setColor(Color.RED);
		g.drawString("得分：" + score, x, y);
	}

}
