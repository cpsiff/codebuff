// Carter Sifferman
// 25 February 2016

/*TODO
-Make the bubbles dissapear when you resize the window to too small
-Make the bubbles bounce off each other correctly
*/

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class NeatGraphics extends Applet implements MouseListener {

	boolean run;
	CircleSquad squad;
	int xClick;
	int yClick;
	int xMouse;
	int yMouse;
	int winW;
	int winH;
	Image virtualMem;
	Graphics gBuffer;

	// Just initializes some stuff
	public void init() {
		winW = getSize().width;
		winH = getSize().height;

		run = false;
		squad = new CircleSquad();
		addMouseListener(this);
		virtualMem = createImage(winW, winH);
		gBuffer = virtualMem.getGraphics();
	}

	public void paint(Graphics g) {
		// winW and winH store the width and height of the window, so that the balls bounce off the
		// window no matter what size
		squad.draw(gBuffer);
		g.drawImage(virtualMem, 0, 0, this);
		delay(10);
		// erase
		gBuffer.setColor(new Color(255, 255, 255));
		gBuffer.fillRect(0, 0, winW, winH);
		squad.move();
		squad.checkCollision(winW, winH);

		repaint();
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	// This listens for mouse clicks and adds a circle object to the circleSquad upon a click
	public void mouseClicked(MouseEvent e) {
		xClick = e.getX();
		yClick = e.getY();
		squad.add(xClick, yClick);
		// idk what this is for but I'm afraid of touching it
		run = true;
		// for debuging purposes
		System.out.println("Clicked");
	}

	// This is used above in paint
	public static void delay(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
		}
	}

	public void update(Graphics g) {
		paint(g);
	}
}