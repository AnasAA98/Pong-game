package pong;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle {
	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	int player1; // current score of player 1
	int player2;//current score of player 2
	
	Score(int GAME_WIDTH,int GAME_HEIGHT){
	Score.GAME_WIDTH=GAME_WIDTH;
	Score.GAME_HEIGHT=GAME_HEIGHT;
	}
	public void draw(Graphics g) {
	    // draw a dotted line
	    g.setColor(Color.white);
	    Graphics2D g2d = (Graphics2D) g;
	    g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{10}, 0));
	    g2d.setFont(new Font("Consolas", Font.BOLD , 50));
	    g2d.drawLine(GAME_WIDTH / 2, 0, GAME_WIDTH / 2, GAME_HEIGHT);
	    // Draw scores for Player 1
	    String player1Score = String.format("Player 1: %02d", player1);
	    g.drawString(player1Score, (GAME_WIDTH / 4) - 150, 50);
	    // Draw scores for Player 2
	    String player2Score = String.format("Player 2: %02d", player2);
	    g.drawString(player2Score, (GAME_WIDTH / 2) + 20, 50);	
	     /* to draw the scores only:
	    g.drawString("Player 1: "+String.valueOf(player1/10)+String.valueOf(player1%10), (GAME_WIDTH/2)-85, 50);
        g.drawString("Player 2: "+String.valueOf(player1/10)+String.valueOf(player1%10), (GAME_WIDTH/2)+20, 50);
	*/
	}
}