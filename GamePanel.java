 package pong;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
	
	static final int GAME_WIDTH = 1000; //width and height won't ever be modified in the program again
	static final int GAME_HEIGHT = (int)(GAME_WIDTH* (5.0/9.0)); // dimensions of a pong table
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT); 
	static final int BALL_DIAMETER = 20 ;
	static final int PADDLE_WIDTH = 25 ;
	static final int PADDLE_HEIGHT = 100 ;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle1;
	Paddle paddle2;
	Ball ball;
	Score score;
	
	GamePanel(){
		newPaddles();
		newBall();
		score = new Score(GAME_WIDTH,GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new AL()); // Al: action listener
		this.setPreferredSize(SCREEN_SIZE);
		gameThread = new Thread(this);
		gameThread.start();

	}
	public void newBall() {
		random = new Random();
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER); 
		}
	public void newPaddles() {
		paddle1=new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
		paddle2=new Paddle((GAME_WIDTH-PADDLE_WIDTH),(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);

	}
	public void paint(Graphics g) {
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	public void draw(Graphics g) {
		paddle1.draw(g);
		paddle2.draw(g);
		ball.draw(g);
		score.draw(g);
	}
	public void move() {
		paddle1.move();
		paddle2.move();
		ball.move();


	}
	public void checkCollision() { 
		//method used to stop paddles from going off window limit
		if(paddle1.y<=0) //checks upper border
			paddle1.y=0;
		
		if(paddle1.y>=(GAME_HEIGHT-PADDLE_HEIGHT)) //checks lower border
			paddle1.y=(GAME_HEIGHT-PADDLE_HEIGHT);
		
		if(paddle2.y<=0)
			paddle2.y=0;
		
		if(paddle2.y>=(GAME_HEIGHT-PADDLE_HEIGHT)) 
			paddle2.y=(GAME_HEIGHT-PADDLE_HEIGHT);
	// will make the ball bounce off top and bottom window edges+ paddles.
		if(ball.y <= 0) {  // checks the uppper border
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= GAME_HEIGHT - BALL_DIAMETER) { //lower border
		ball.setYDirection(-ball.yVelocity);
		}
		//paddles:
		if(ball.intersects(paddle1)) {
			ball.xVelocity=Math.abs(ball.xVelocity) ;
			ball.xVelocity++;//(optional)increase speed of the ball after each intersection with the paddles
			if(ball.yVelocity>0)
				ball.yVelocity++;
			else
				ball.yVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);

		}
		if(ball.intersects(paddle2)) {
			ball.xVelocity=Math.abs(ball.xVelocity) ;
			ball.xVelocity++;//(optional)increase speed of the ball after each intersection with the paddles
			if(ball.yVelocity>0)
				ball.yVelocity++;
			else
				ball.yVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
			// give a player 1 point and creates new paddles and ball
		if(ball.x<=0) {
			score.player2++;
			newPaddles();
			newBall();
			
		}
	
		if(ball.x>=GAME_WIDTH- BALL_DIAMETER) {
			score.player1++;
			newPaddles();
			newBall();
		}
	}
	
	
	public void run() {
		// game loop
		 long lastTime = System.nanoTime();
		 double amountOfTicks=60.0;
		 double ns=1000000000 / amountOfTicks;
		 double delta = 0;
		 while(true) {
			 long now =System.nanoTime();
			 delta += (now-lastTime)/ns;
			 lastTime=now;
			 if(delta >= 1) {
				 move();
				 checkCollision();
				 repaint();
				 delta--;
			 }
		 }
	}
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
		paddle1.keyPressed(e);
		paddle2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			paddle1.KeyReleased(e);
			paddle2.KeyReleased(e);
			
		}
	}
}