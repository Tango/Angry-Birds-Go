package firstGamePack;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class Competitor extends movingObject {
	Image imgForward = new ImageIcon("res/competitor.png").getImage();
	Image imgForward0 = new ImageIcon("res/competitor0.png").getImage();
	Image imgCrash = new ImageIcon("res/competitor_crash.png").getImage();
	Image imgCrash0 = new ImageIcon("res/competitor_crash_0.png").getImage();
	Road road;
	int speedCollision;
	double accelBreaking, wayDY;
	
	public Competitor(int x, int y, int speed, Road road){
		this.x = x;
		this.y = y;
		this.speed = speed;
		speedCollision = speed;
		this.road = road;
		this.width = 202;    // 202 
		this.height = 29;    // 99
		Random rand = new Random();
		acceleration = rand.nextInt(10);
		stance = Stance.forward;
		who = Is.competitor;
		img = imgForward;

	}
	
	public Rectangle getRect(){
		return new Rectangle(x, y+70, width, height);
	}	
	
	public void go(){
		x = x - road.carObj.speed + speed + acceleration;
	}
	public void goBreaking(){	
		if (speedCollision >= 0){  
			
			x = x - road.carObj.speed + speedCollision + (int)accelBreaking;
			accelBreaking -= 0.25;
			wayDY += (wayDY <=10)? 0.15 : 0;
			y += wayDY; 
		}
	}
//	 
	 void drawParticipant(Graphics g){
			switch(stance){
				case forward:
					if (shift%2 == 1){
						img = imgForward;
						g.drawImage(img, x, y,null);
					} else {
						img = imgForward0;
						g.drawImage(img, x, y,null);
					}
				break;
				case collision:
					if (shift%2 == 1){
						//	img = img;
						g.drawImage(imgCrash, x, y,null);
					} else {
						//	e.img = e.img0;
						g.drawImage(imgCrash0, x, y,null);
					}
				break;
				}
			} 
}
