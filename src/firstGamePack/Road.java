package firstGamePack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Road extends JPanel implements ActionListener, Runnable{
	private final int finishDistance = 3650;  
	private final double timeWin = 60; 
	private double countdown = timeWin;     // timer
	double counter = 0;                     // time counter
	
	Timer mainTimer = new Timer(20, this);
	
	Image img = new ImageIcon("res/road3.png").getImage();
	Image imgLand = new ImageIcon("res/landscape.png").getImage();
	Image imgMount = new ImageIcon("res/mountain.png").getImage();
		
	Car carObj = new Car ();
	Cloud cloudObj = new Cloud();
	
	Thread competitorGenerator = new Thread(this);
	List<Competitor> ListOfCompetitors = new ArrayList<Competitor>();
	List<movingObject> AllParticipantsList = new ArrayList<movingObject>();
	
	//
//================================================================================================================		
	public Road() {
		mainTimer.start();
		competitorGenerator.start();
		addKeyListener(new RoadKeyAdapter());
		setFocusable(true);
	} 
	
//=====================================================================================================================	
	public void meterPanel(){
 		movingObject.shift += 0.5;
		counter += 0.02;
		countdown -= (countdown > 0)? 0.02 : 0;  
	}
	
//=====================================================================================================================	
	public void ñollission() { 
		Iterator<Competitor> i = ListOfCompetitors.iterator();
		while (i.hasNext()){ 
			Competitor tempCompetitor = i.next();
			if (carObj.getRect().intersects(tempCompetitor.getRect())){
				tempCompetitor.stance = Stance.collision; 				
				
				tempCompetitor.accelBreaking = ((carObj.speed-tempCompetitor.speed)/6)*5;
				carObj.speed -= (carObj.speed > 0)? ((carObj.speed)*2/10): 0;
				carObj.acceleration = 0;  
			} 
		}
	}
	
//=====================================================================================================================	
	private class RoadKeyAdapter extends KeyAdapter{
		
		public void keyPressed(KeyEvent e){
			carObj.keyPressed(e);
		}
		
		public void keyReleased(KeyEvent e){
			carObj.keyReleased(e);
		}
	}
	
//=====================================================================================================================	
	private void drawLandscape(Graphics g){
		g = (Graphics2D) g;
		g.drawImage( imgMount,0, 0, null);                              // draw background mountain


		g.drawImage( imgLand,carObj.layerX3, 0, null);   // draw moving landscape between road and mountain
		g.drawImage( imgLand,carObj.layerX4, 0, null);	 // draw moving next landscape between road and mountain

		g.drawImage( img,carObj.layerX1, 0, null);    // draw moving road 
		g.drawImage( img,carObj.layerX2, 0, null);    // draw next moving road
		
	}
	
//=====================================================================================================================	 
	 	 
	 private void drawMeterPanel(Graphics g){
		 g.setColor(Color.DARK_GRAY);
			Font fontStyle = new Font("Arial", Font.BOLD,15);
			g.setFont(fontStyle);
			g.drawString("Speed "+(int)carObj.km_hSpeed+" km/h",30,30);
			g.drawString("Distance "+(int)carObj.distance+" meters",30,55);
			g.drawString("Time left "+ (int)countdown + " s",30,70);
			g.drawString("Time "+ (int)counter + " s",30,85); 
			g.drawString("      "+ carObj.speed,30,100);
	 }
	 
//=====================================================================================================================	 
	public void paint (Graphics g){
	   	g = (Graphics2D) g;
		 
		drawLandscape(g);      
		
		sortedParticipantsList();
		Iterator<Competitor> i = ListOfCompetitors.iterator();  
		while(i.hasNext()){
				
					Competitor temp = i.next();
					temp.drawParticipant(g);		
		}
		carObj.drawParticipant(g);
		cloudObj.drawParticipant(g);
		
		drawMeterPanel(g);
		
	}
	
//=====================================================================================================================
	private void sortedParticipantsList(){
		AllParticipantsList.add(carObj);
		Iterator<Competitor> i = ListOfCompetitors.iterator();  
		while(i.hasNext()){
			Competitor temp = i.next();
			AllParticipantsList.add(temp);
			
			if (temp.x >= 2000 || temp.x <= -2000 || temp.y >=605)
				i.remove();  //  delete a competitor which is going out of the play field 
			
		}
		Collections.sort(AllParticipantsList);
	}

//=====================================================================================================================
	private void moveAllCompetitors(){
	Iterator<Competitor> i = ListOfCompetitors.iterator();  
			while(i.hasNext()){
			Competitor temp  = i.next();
			
			if (temp.x >= 2000 || temp.x <= -2000 || temp.y >=605)
					i.remove();  //  delete a competitor which is going out of the play field 

			if (temp.stance == Stance.collision) temp.goBreaking();  // move competitor
			else temp.go();
			
		}
	}
	
//=====================================================================================================================	
	@Override
	public void actionPerformed(ActionEvent e) {

		meterPanel();
		cloudObj.go();
		carObj.go(); // move player 
		moveAllCompetitors();

		repaint();
		ñollission();	
		checkWin();
	}
	
//=====================================================================================================================
	private void checkWin(){
		  if (carObj.distance >= finishDistance && counter <= timeWin){
			  JOptionPane.showMessageDialog(null, "CONGRADULATION!\nYou're WINNER!!!"
					  +"\nyou've driven "+(int)carObj.distance+" meters in "+(int)counter+" seconds");
			  System.exit(0);
		  } else if (carObj.distance <= finishDistance && counter > timeWin){
			  JOptionPane.showMessageDialog(null, "You lose the race!"
					  +"\nyou've driven "+(int)carObj.distance+" meters in "+(int)counter+" seconds\nWINNER's time: "+finishDistance+" meters in "+(int)timeWin+" seconds");
			  System.exit(0);
		  }	  
	  }
	  
//=====================================================================================================================
	@Override
	public void run() {
	  while (true){
		Random rand = new Random();
		
		  try {
		  Thread.sleep(500+rand.nextInt(2000)); 
 
			ListOfCompetitors.add(new Competitor(1101,      
				240+rand.nextInt(185),                      
				70+rand.nextInt(50),                        
				this));
		  } catch (InterruptedException e) {
					e.printStackTrace();
		  	}
	  }
	}
}
