import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class Enemy extends MapObject {
  
  private static BufferedImage image = null;
  
 /* protected int health;
  protected int maxHealth;*/
  private static boolean dead;
  private int damage;
  
  private boolean flinching;
  private long flinchTimer;
  
  private double x;
  private double y;
  private double dx;
  private double dy;
  
  public Enemy(){
    
    health = maxHealth = 30;
    damage = 1;
    
    try{
      image = ImageIO.read(getClass().getResourceAsStream ("Resources/enemyLeft.gif"));
    }
    catch(Exception e){ e.printStackTrace(); }
  }
  
  public int getWidth() { return image.getWidth(); }
  public int getHeight() { return image.getHeight(); }
  public int getX() { return (int)x; }
  public int getY() { return (int)y; }
  
  public static boolean isDead() { return dead; }
  
  public int getDamage() { return damage; }
  
  public void hit(int hurt) {
    if(dead || flinching) return;
    health -= hurt;
    if(health < 0) health = 0;
    if(health == 0) dead = true;
    flinching = true;
    flinchTimer = System.nanoTime();
  }
  
  
  public void setVec(double dx, double dy){
    this.dx = dx;
    this.dy = dy; 
  }
  public void regen(){
   if (health < maxHealth-10)
   health +=1;
  }

  public void setPosition(double x, double y){
    this.x = (x /* moveScale*/) % GamePanel.WIDTH;
    this.y = (y /* moveScale*/) % GamePanel.HEIGHT;
  }
  
  public void getNextPosition(){
    x += dx;
    y += dy;  
  }
  
  public void update() {
    
    // update position
    getNextPosition();

    // check flinching
    if(flinching) {
      long elapsed =
        (System.nanoTime() - flinchTimer) / 1000000;
      if(elapsed > 100) {
        flinching = false;
      }
    }
    
    //make game more difficult the weaker the enemy gets
   /* if (health < 30 && health >= 25 && dx > 0)
      dx = 1.5;
    else if(health < 30 && health >= 25 && dx < 0)
      dx = -1.5;*/
    else if (health < 25 && health > 10 && dx > 0)
      dx = 1.5;
    else if (health < 25 && health > 10 && dx < 0)
      dx = -1.5;
    else if (health < 10 && dx > 0)
      dx = 2.5;
    else if (health < 10 && health >=5 && dx < 0)
      dx = -2.5;
    /*else if (health < 5 && dx > 0)
      dx = 3.5;
    else if (health < 5 && dx < 0)
      dx = -3.5;*/
    
    try{
      //have it so that the enemy can not leave the game screen
      //bounce off the wall into the other direction
      if ( x < 0){
        image = ImageIO.read(getClass().getResourceAsStream ("Resources/enemyRight.gif"));
        dx = -dx;
        // g.drawImage(image, ((int)x + GamePanel.WIDTH), (int)y, null);
        
      }        
      if ( x >= GamePanel.WIDTH - image.getWidth()){
        image = ImageIO.read(getClass().getResourceAsStream ("Resources/enemyLeft.gif"));
        dx = -dx;
        // g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
      }   
    }
    catch (Exception e)
    { e.printStackTrace(); }
    
  }
  
  public void draw(Graphics2D g){
      //draw the current image for image
      g.drawImage(
                  image,
                  (int)(x),
                  (int)(y),
                  null
                 );
    
  }
  
  
  
  
  
}