package firstGamePack;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Cloud extends movingObject {
	Image img = new ImageIcon("res/cloud.png").getImage(); 
	int cloudX2;
	int cloudX1;
	double dx;
	Stance stance;  //  condition
	
	Cloud(){
		
		x = 1100;
		y = 50;
		cloudX2 = 1600;
		cloudX1 = 500;
		speed = 1;
		topY = - 30;
		bottomY = 70;
		//
	}
	
	public void go(){			
			if (cloudX2 - speed <= 0){
				cloudX1 = 0;
				cloudX2 = 1100;
				} else {
					cloudX1 = cloudX1 - speed;
					cloudX2 = cloudX2 - speed;
				}
	}
	
	void drawParticipant(Graphics g){
		g.drawImage(img, cloudX1, y, null);  // draw cloud
		g.drawImage(img, cloudX2, y, null);  // draw next cloud
	}
}
