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

// An object that stores an array of circle objects for easy adding to / removing from the array
class CircleSquad {
  private ArrayList<Circle> circles;
  private int winW;
  private int winH;

  public CircleSquad() {
    circles = new ArrayList<Circle>();
  }

  public void add(int xParam, int yParam) {
    circles.add(new Circle(xParam, yParam));
  }

  public void draw(Graphics g) {
    for (Circle circle : circles) {
      circle.draw(g);
    }
  }

  public void move() {
    for (Circle circle : circles) {
      circle.move();
    }
  }

  // Where the magic happens
  public void checkCollision(int winWParam, int winHParam) {
    winW = winWParam;
    winH = winHParam;
    for (Circle circle : circles) {
      circle.checkWallCollision(winW, winH);
    }
    for (int k = circles.size() - 1; k > 0; k--) {
      for (int i = 0; i < circles.size() - 1; i++) {
        if (k != i) {
          circles.get(i).checkBallCollision(circles.get(k));
        }
      }
    }
  }
}

class Circle {
  private int x;
  private int y;
  private int xVel;
  private int yVel;
  private int diam;
  private int rad;
  private int winW;
  private int winH;
  private int red;
  private int green;
  private int blue;

  public Circle(int xParam, int yParam) {
    diam = (int) (50 * Math.random()) + 25;
    rad = diam / 2;
    x = xParam - rad;
    y = yParam - rad;
    red = (int) (255 * Math.random());
    green = (int) (255 * Math.random());
    blue = (int) (255 * Math.random());
    int i = 1;
    // Makes sure the velocity is not zero in the x direction
    // Ideally this would allow x or y velocity to be zero but not both
    // at the moment it makes sure neither of them is zero, so the balls never travel in a
    // horizontal or vertical line
    while (i == 1) {
      xVel = (int) (10 * Math.random()) - 5;

      if (xVel != 0) {
        i = 0;
      }
    }
    i = 1;
    // Makes sure the velocity is not zero in the y direction
    while (i == 1) {
      yVel = (int) (10 * Math.random()) - 5;

      if (yVel != 0) {
        i = 0;
      }
    }
  }

  // actually draws the balls
  public void draw(Graphics g) {
    g.setColor(new Color(red, green, blue));
    g.drawOval(x, y, diam, diam);
  }

  public void move() {
    this.x += xVel;
    this.y += yVel;
  }

  // simply reverses the velocity in either the x or y direction depending on which wall it collides
  // with
  // takes into account the window size, works if window is rescaled
  public void checkWallCollision(int winWParam, int winHParam) {
    winW = winWParam;
    winH = winHParam;
    if (x + diam > winW || x < 0) {
      xVel *= -1;
    } else if (y + diam > winH || y < 0) {
      yVel *= -1;
    }
  }

  // Receives two circle objects and checks for collision
  public void checkBallCollision(Circle circle2) {
    // Check if their radi overlap
    int a = this.x - circle2.x;
    int b = this.y - circle2.y;
    double distance = Math.sqrt(a * a + b * b);
    // If they overlap do all these calculations I found online to see what direction they keep
    // traveling in
    // This does take into account the mass of the balls. A larger ball will be less affected by a
    // smaller ball hitting it.
    if (distance <= this.rad + circle2.rad) {
      int newXVel1 =
          (this.xVel * (this.rad - circle2.rad) + (2 * circle2.rad * circle2.xVel))
              / (this.rad + circle2.rad);
      int newYVel1 =
          (this.yVel * (this.rad - circle2.rad) + (2 * circle2.rad * circle2.xVel))
              / (this.rad + circle2.rad);
      int newXVel2 =
          (circle2.xVel * (circle2.rad - this.rad) + (2 * this.rad * this.xVel))
              / (this.rad + circle2.rad);
      int newYVel2 =
          (circle2.yVel * (circle2.rad - this.rad) + (2 * this.rad * this.yVel))
              / (this.rad + circle2.rad);
      this.xVel = newXVel1;
      this.yVel = newYVel1;
      circle2.xVel = newXVel2;
      circle2.yVel = newYVel2;

      this.x += xVel;
      circle2.x += xVel;
      this.y += yVel;
      circle2.y += yVel;
    }
  }

  public void set(int xParam, int yParam) {
    this.x = xParam;
    this.y = yParam;
  }
}
