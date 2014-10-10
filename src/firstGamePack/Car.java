package firstGamePack;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
//
public class Car extends movingObject{
	
	Image imgForward = new ImageIcon("res/birdmobile_1.png").getImage();
	Image imgForward0 = new ImageIcon("res/birdmobile_0.png").getImage();
	Image imgUp = new ImageIcon("res/birdmobile_up.png").getImage();
	Image imgDn = new ImageIcon("res/birdmobile_down.png").getImage();
	Image imgCrash = new ImageIcon("res/birdmobile_crash2.png").getImage();
	Image imgSpeed = new ImageIcon("res/birdmobile_speed.png").getImage();
	Image imgSpeed0 = new ImageIcon("res/birdmobile_speed0.png").getImage();
	Image imgBreake = new ImageIcon("res/birdmobile_brake.png").getImage();
	public double distance = 0;

	int dy = 0;
	int layerX1 = 0;
	int layerX2 = 1100;
	int layerX3 = 0;
	int layerX4 = 1100;
	double km_hSpeed;
	
	Car(){
		width = 183;
		height = 60;
		speed = 0;        // speed of the main layer, which is road.png
		speedL = 0;       // speed of the second layer, which is landscape.png 
		maxSpeed = 125;   // 250 km/h <=> 695 m/c
		acceleration = 0;
		topY = 240;
		bottomY = 435;
		x = 70;
		y = 400;
		img = imgForward;
		stance = Stance.nomove;
		who = Is.player;
	}
	
	public Rectangle getRect(){
		return new Rectangle(x+10, y+65, width, height);
	}	

//==================================================================
	 void drawParticipant (Graphics g){
		 g.drawImage(img,x,y, null);  // draw player
		 switch(stance){
		 	case forward:
				 if (shift%2 == 1){
					 img = imgForward; 
					 g.drawImage(img, x, y, null);  // draw player
				 }
				 else {
					 img = imgForward0;
					 g.drawImage(img, x, y, null);  // draw player
				 }
				 break;
		 	case up:
		 		img = imgUp;
		 		g.drawImage(img,x,y, null);  // draw player
		 		break;
		 	case down:
		 		img = imgDn;
		 		g.drawImage(img,x,y, null);  // draw player
		 		break;
		 	case acceleration:
				 if (shift%2 == 1){
				 img = imgSpeed; 
				 g.drawImage(img,x,y, null);  // draw player
				 }
				 else {
					 img = imgSpeed0;
					 g.drawImage(img,x,y, null);  // draw player
				 }	
		 		break;
		 	case breaking:
		 		img = imgBreake;
		 		g.drawImage(img,x,y, null);  // draw player
		 		break;
		 }
		 
	 }
	
//==================================================================
	


	public void go(){
		distance = distance + (double)speed/90;
		speed = speed + acceleration;
		speedL = speed/10;
		km_hSpeed = speed * 2;
		y -= dy;
		if (speed <= 0) speed = 0;
		
		if (speed >= maxSpeed) speed = maxSpeed;
 
		if (layerX2 - speed <= 0){
			layerX1 = 0;
			layerX2 = 1100;
			} else {
			layerX1 = layerX1 - speed; 
			layerX2 = layerX2 - speed;
		}
		
		if (speed <= 10 && speed > 0) speedL = 1;
		if (speedL <= 0) speedL = 0;
		
			if (layerX4 - speedL <= 0){
				layerX3 = 0;
				layerX4 = 1100;
				} else {
					layerX3 = layerX3 - speedL; 
					layerX4 = layerX4 - speedL;
				}
		if (y <= topY) y = topY;
		if (y >= bottomY) y = bottomY;
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT){
			acceleration = 1;
			stance = Stance.acceleration;
		}
		if (key == KeyEvent.VK_LEFT){
			acceleration = -1;
			stance = Stance.breaking;
		}
		if (key == KeyEvent.VK_DOWN){
			stance = Stance.down;
			dy -=6;
		}
		if (key == KeyEvent.VK_UP ){
			stance = Stance.up;
			dy +=6;
		}
	}
	public void keyReleased(KeyEvent e){
	//	
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT){
			acceleration = 0;
			stance = Stance.forward;
			img = imgForward;
		}
		if (speed == 0){
			stance = Stance.nomove;
			img = imgForward;
		} 
		
		if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN){
			dy = 0;
			img = imgForward;
			stance = Stance.forward;
		}
	}
	
}
