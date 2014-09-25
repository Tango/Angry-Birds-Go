package firstGamePack;

import javax.swing.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class Main {

	public static void main(String[] args) {
		JFrame f = new JFrame("My first Game");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setBounds(0, 0, 1100, 600);   //f.setSize(600,600);
		f.add(new Road());
		f.setVisible(true);  
	}	
}