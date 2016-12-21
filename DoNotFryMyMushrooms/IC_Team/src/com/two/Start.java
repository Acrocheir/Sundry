package com.two;

import java.io.IOException;

import javax.swing.SwingUtilities;

public class Start {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {			
			@Override
			public void run() {
				GameWindow win;
				try {
					win = new GameWindow();
					win.setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
			}
		});
		

	}

}
