package com.two;

import java.awt.Graphics;

public class Stones {
	public Stone stone0,stone1,stone2,stone3,stone4;
	
    	
	public Stones(GameStart panel){
		stone0 = new Stone(panel);
		stone1 = new Stone(panel);
		stone2 = new Stone(panel);
		stone3 = new Stone(panel);
		stone4 = new Stone(panel);
		stone1.setX(stone0.getX()+60);
		stone2.setX(stone0.getX()+120);
		stone3.setX(stone0.getX()+180);
		stone4.setX(stone0.getX()+240);
		//a2.setY(a1.getY()+30);
	}
	public void reset(){
		stone0.reset();
		stone1.reset();
		stone2.reset();
		stone3.reset();
		stone4.reset();
	}
	public void paint(Graphics g){
		stone0.paintStone(g);
		stone1.paintStone(g);
		stone2.paintStone(g);
		stone3.paintStone(g);
		stone4.paintStone(g);
	}

}
