package com.two;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Tip {
	Font ftip0, ftip1;
	int x, y;

	public Tip() {
		// TODO Auto-generated constructor stub
		ftip0 = new Font(Font.MONOSPACED, Font.BOLD, 30);
		ftip1 = new Font(Font.MONOSPACED, Font.BOLD, 30);
		x = 45;
		y = 200;
	}

	public void paint(Graphics g) {
		g.setFont(ftip0);
		g.setColor(Color.ORANGE);
		g.drawString("按enter键开始", x + 7, y - 15);
		g.setFont(ftip1);
		g.setColor(Color.ORANGE);
		g.drawString("用左右键控制蘑菇移动", x - 47, y + 15);
	}
}
