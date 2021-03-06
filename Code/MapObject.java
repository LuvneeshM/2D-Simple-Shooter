//super class for all objects
//players, enemies, projectiles

import java.awt.Rectangle;

public abstract class MapObject{
  
  //postion and vector
  protected static double x;
  protected static double y;
  protected double dx;
  protected double dy;
  
  //dimensions
  protected int width;
  protected int height;
  
  //collisions box with enemies
  protected int cWidth;
  protected int cHeight;
  
  //collision 
  protected int currRow;
  protected int currCol;
  protected double xdest;
  protected double ydest;
  protected double xtemp;
  protected double ytemp;
  protected boolean topLeft;
  protected boolean topRoght;
  protected boolean bottonLeft;
  protected boolean bottonRight;
  
  //animations
  protected int currentAction;
  protected int previousAction;
  protected boolean notShooting;
  
  //movement
  protected boolean left, right;
  protected boolean jumping, falling;
 
  //attacking
  protected static boolean fire, ult;
  protected boolean shot;
  
  //movement attributes, physics stuff
  protected double moveSpeed;
  protected double maxSpeed; 
  protected double stopSpeed;
  protected double fallSpeed;
  protected double maxFallSpeed;
  protected double jumpStart;
  protected double stopJumpSpeed;
  protected static boolean subLeft, subRight; 
  
  //health for players and enemies
  //blaster can ignore this 
  protected int health;
  protected int maxHealth;
  
  //cost to use and the amount of each attacks
  //weak and ult attack will cost different amounts
  protected int maxFire;
  protected int fireLeft;
  protected int ultCost;
  protected int weakCost;
  
  
  //constructor
  public MapObject(){
    
  }
  
  //rectangle collision
  public boolean intersects(MapObject o){
    Rectangle r1 = getRectangle();
    Rectangle r2 = o.getRectangle();
    //tell if the two have collided
    //works well with bullets and other porjectiles
    return r1.intersects(r2);
  }
  
  public Rectangle getRectangle(){
    return new Rectangle(getX(), getY(), getWidth()-30, getHeight()-30);
  }
  
  public int getX() {return (int)x;}
  public int getY() {return (int)y;}
  public int getWidth() {return width;}
  public int getHeight() {return height;}
  public int getHealth() {return health;}
  public int getMaxHealth() {return maxHealth;}
  public int getFireLeft() {return fireLeft; }
  public int getMaxFire() {return maxFire; }
  
  public static boolean getSubLeft() {return subLeft; }
  public static boolean getSubRight() {return subRight; }
  
  
  public void setPosition(double x, double y){
    this.x = x;
    this.y = y;  
  }
  
  public void setVector(double dx, double dy){
    this.dx = dx;
    this.dy = dy;
  }
  
  //movement
  //check left
  public void setLeft(boolean b) {
    subRight = false;
    subLeft = true;
    left = b;
  }
  //check right
  public void setRight(boolean b) {
    subRight = true;
    subLeft = false;
    right = b;
  }
  //movements for jumping
  public void setJumping (boolean b) {jumping = b;}
  //shooting
  public void setFire(boolean b) { fire = b;}
  public void setUlt(boolean b) { ult = b; }
  //so that you wont see left or right moving image when shooting
  public void setNotShooting(boolean b) { notShooting = b;}
  //for shooting
  public void setShot(boolean b) { shot = b; }
  
  public boolean getRight() { return right; }
  
  public boolean notOnScreen(){
    return x + width < 0 || x - width > GamePanel.WIDTH || y +height <0 || y - height >GamePanel.HEIGHT;
  }
  

  
  
  
}



