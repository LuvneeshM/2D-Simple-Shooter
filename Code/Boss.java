import java.awt.Graphics;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Boss extends MapObject{
  
  private static BufferedImage image = null;
  private BlastObject blast;
  
  /* protected int health;
   protected int maxHealth;*/
  private boolean dead;
  private int damage;
  
  private int ultDamage;
  
  private boolean ultCheck, fireCheck;
  
  private boolean flinching;
  private long flinchTimer;
  
  private double x;
  private double y;
  private double dx;
  private double dy;
  private double tempdx;
  
  public Boss(){
    
    health = maxHealth = 30;
    damage = 1;
    ultDamage = 2;
    
    fireLeft = maxFire = 25;
    ultCost = 15;
    
    try{
      image = ImageIO.read(getClass().getResourceAsStream ("Resources/BossLeft.gif"));
    }
    catch(Exception e){ e.printStackTrace(); }
  }
  
  public int getWidth() { return image.getWidth(); }
  public int getHeight() { return image.getHeight(); }
  public int getX() { return (int)x; }
  public int getY() { return (int)y; }
  public BufferedImage getImage() { return image; }
  public boolean isDead() { return dead; }
  public int getDamage() { return damage; }
  
  public void hit(int hurt) {
    if(dead || flinching) return;
    health -= hurt;
    if(health < 0) health = 0;
    if(health == 0) dead = true;
    flinching = true;
    flinchTimer = System.nanoTime();
  }
  
  public void setFire(int a){
    fireLeft += a;
  }
  
  public void setVec(double dx, double dy){
    this.dx = dx;
    this.dy = dy; 
  }
  
  public void setPosition(double x, double y){
    this.x = (x /* moveScale*/) % GamePanel.WIDTH;
    this.y = (y /* moveScale*/) % GamePanel.HEIGHT;
  }
  
  public void getNextPosition(){
    x += dx;
    y += dy;  
  }
  
  public void checkBossAttack(Player en){
    if(blast != null){
      //check ult attack
      if(ultCheck){
        if( blast.intersects(en)){
          en.hit(ultDamage);
          blast.setHit();
          if(blast.shouldRemove())
            blast = null;
        }
      }
    }
  }
  
  
  public void update() {
    
    // update position
    getNextPosition();
    
    // check flinching
    if(flinching) {
      long elapsed =
        (System.nanoTime() - flinchTimer) / 1000000;
      if(elapsed > 400) {
        flinching = false;
      }
    }
    
    //make game more difficult the weaker the enemy gets
    if (health < 30 && health >= 25 && dx > 0)
      dx = 0.5;
    else if(health < 30 && health >= 25 && dx < 0)
      dx = -0.5;
    else if (health < 25 && health > 10 && dx > 0)
      dx = 1.0;
    else if (health < 25 && health > 10 && dx < 0)
      dx = -1.0;
    else if (health < 10 && dx > 0)
      dx = 2.5;
    else if (health < 10 && health >=5 && dx < 0)
      dx = -2.5;
    
    if (blast == null && (ult || fire) && fireLeft >=ultCost ){
      ultCheck = true;
      
      if (dx > 0)
      {
        blast = new BlastObject("Resources/BossAttackR.gif",1);
        blast.setPosition(x ,y + image.getHeight()/10);
        fireLeft -= ultCost;
      }
      if (dx < 0 )
      {
        blast = new BlastObject("Resources/BossAttackL.gif",1);
        blast.setPosition(image.getWidth(),y + image.getHeight()/10);
        fireLeft -= ultCost;
      }
      
    }
    
   /* if (ult && fireLeft >=ultCost){
      tempdx = dx;
      dx = 0;
      fireLeft -= ultCost;
      
      // g.drawImage(image, ((int)x - GamePanel.WIDTH), (int)y, null);
    }*/
    
    try{
      //have it so that the enemy can not leave the game screen
      //bounce off the wall into the other direction
      if ( x < 0){
        image = ImageIO.read(getClass().getResourceAsStream ("Resources/BossRight.gif"));
        dx = -dx;
        
      }        
      if ( x >= GamePanel.WIDTH - image.getWidth()){
        image = ImageIO.read(getClass().getResourceAsStream ("Resources/BossLeft.gif"));
        dx = -dx;
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
    try{
      //draw the ult attack
      /*if (ult && fireLeft >=ultCost){
        tempdx = dx;
        dx = 0;
        fireLeft -= ultCost;
        
       // g.drawImage(image, ((int)x - GamePanel.WIDTH), (int)y, null);
      }*/
      
      //draw the blast
      //check to see if it goes of screen to remove
      if (blast != null){
        blast.draw(g);
        blast.checkOnScreen();
        if (blast.shouldRemove()){
          blast = null;
          ultCheck = false;
        //  dx = tempdx;
        }
      }
    }
    catch(Exception e)
    { e.printStackTrace(); }
  }
  
  
}
