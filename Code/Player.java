import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.Image.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;


public class Player extends MapObject {
  
  private BlastObject blast;
  
  private int fireDamage;
  private int ultDamage;
  
  private boolean flinching;
  private long flinchTimer;
  private boolean fireCheck, ultCheck;
  private static boolean dead;
  
  private static BufferedImage image = null;
  
  //  private BufferedImage blast;
  // private boolean subLeft, subRight; 
  
  //  private double moveScale;
  
  public Player(/*double ms*/){
    try{
      image = ImageIO.read(getClass().getResourceAsStream ("Resources/idleRight.gif"));
      //moveScale = ms;
    }
    catch(Exception e){
      e.printStackTrace();
    }
    
    health = maxHealth = 50000;
    fireLeft = maxFire = 25;
    ultCost = 15;
    weakCost = 2;
    
    fireDamage = 2;
    ultDamage = 5;
    
    width = 74;
    height= 72;
    
    moveSpeed = 0.3;
    maxSpeed = 3.0;
    stopSpeed = 0.4;
    fallSpeed = 0.5;
    maxFallSpeed = 1;
    jumpStart = -4;
    stopJumpSpeed = 1;
    
    notShooting = true;
    
  }
  
  //get variable methods 
  /* public int getHealth() { return health; }
   public int getMaxHealth() { return maxHealth; }
   public int getFireLeft() { return fireLeft; }
   public int getMaxFire() { return maxFire; }*/
  public static int getW(){ return image.getWidth(); }
  public static int getH(){ return image.getHeight(); }
  public static boolean getDead() { return dead; }
  
  public void setFire(int a){
    fireLeft += a;
  }
  
  public void checkAttack(Enemy en){
    if(blast != null){
      //check weak attack
      if(fireCheck){
        if ( blast.intersects(en)){
          en.hit(fireDamage);
          blast.setHit();
          if(blast.shouldRemove())
            blast = null;
        }
      }
      //check ult attack
      else if(ultCheck){
        if( blast.intersects(en)){
          en.hit(ultDamage);
          blast.setHit();
          if(blast.shouldRemove())
            blast = null;
        }
      }
    }
    //check for enemy collision
    if(this.intersects(en)){
      hit(en.getDamage());
    }
  }
  
    public void checkBossAttack(Boss en){
    if(blast != null){
      //check weak attack
      if(fireCheck){
        if ( blast.intersects(en)){
          en.hit(fireDamage);
          blast.setHit();
          if(blast.shouldRemove())
            blast = null;
        }
      }
      //check ult attack
      else if(ultCheck){
        if( blast.intersects(en)){
          en.hit(ultDamage);
          blast.setHit();
          if(blast.shouldRemove())
            blast = null;
        }
      }
    }
    //check for enemy collision
    if(this.intersects(en)){
      hit(en.getDamage());
    }
  }
  
  public void hit(int damage){
    if(flinching) return;
    health -= damage;
    if(health < 0) { health = 0; }
    if(health == 0) { dead = true; }
    flinching = true;
    flinchTimer = System.nanoTime();
  }
  
  private void getNextPosition() {
    // movement
    if(left) {
      dx -= moveSpeed;
      if(dx < -maxSpeed) {
        dx = -maxSpeed;
      }
    }
    else if(right) {
      dx += moveSpeed;
      if(dx > maxSpeed) {
        dx = maxSpeed;
      }
    }
    else {
      if(dx > 0) {
        dx -= stopSpeed;
        if(dx < 0) {
          dx = 0;
        }
      }
      else if(dx < 0) {
        dx += stopSpeed;
        if(dx > 0) {
          dx = 0;
        }
      }
    }
    // jumping
    //Grid is invetered dont forget
    //top left == 0, using negative to go up
    //pos to go down
    if(jumping && !falling) {
      dy = jumpStart;
      falling = true; 
    }
    //falling
    if(falling) { 
      if(dy > 0) {
        jumping = false;
      }
      if(dy < 0 && !jumping) 
      { dy += stopJumpSpeed; }
      
      if(dy <= maxFallSpeed && !jumping) 
      { dy += maxFallSpeed; }
      
      //keep from falling off screen from bottom 
      if(y >= GamePanel.HEIGHT - image.getHeight() && !jumping)
      {
        dy = 0;
        falling = false;
      }
      //cant leave the top of the screen when jumping
      if ( y < 0 && jumping)
      { jumping = false; }
      
    }
    
  }//end of getNextPosition
  
  /* public void setPos(double x, double y){
   this.x = (x * moveScale) % GamePanel.WIDTH;
   this.y = (y * moveScale) % GamePanel.HEIGHT;
   }*/
  
  public BufferedImage getImage() { return image; } 
  
  public void update() {
    
    // update position
    getNextPosition();
    
    setPosition(x, y);
    
    x += dx;
    y += dy;  
    
    // check done flinching
    if(flinching) {
      long elapsed =
        (System.nanoTime() - flinchTimer) / 1000000;
      if(!Enemy.isDead()){
        if(elapsed > 1000) {
          flinching = false;
        }
      }
      else {
        if(elapsed > 2500)
          flinching = false;
      }
    }
    
    
    //draw the ULT 
    if (blast == null && ult && fireLeft >=ultCost ){
      ultCheck = true;
      fireCheck = false;
      
      if (MapObject.getSubLeft())
      {
        blast = new BlastObject("Resources/blastLeft.gif",1);
        blast.setPosition(x - image.getWidth()/2 -5,y + image.getHeight()/10);
      }
      else if (MapObject.getSubRight())
      {
        blast = new BlastObject("Resources/blastRight.gif",1);
        blast.setPosition(image.getWidth() + x,y + image.getHeight()/10);
      }
      
    }
    //draw the WEAK ATTACK 
    if (blast == null && fire && fireLeft >= weakCost){
      fireLeft -= weakCost;
      fireCheck = true;
      ultCheck = false;
      
      if (MapObject.getSubLeft())
      {
        blast = new BlastObject("Resources/weak attack.gif",1);
        blast.setPosition(x - image.getWidth()/2 -5,y + image.getHeight()/4);
      }
      else if (MapObject.getSubRight())
      {
        blast = new BlastObject("Resources/weak attack.gif",1);
        blast.setPosition(image.getWidth() + x,y + image.getHeight()/4);
      }
    }
    
    
    
    /*if ( x + image.getWidth() == GamePanel.WIDTH) { x = 0;}
     if ( x == 0) { x = GamePanel.WIDTH; }*/
    
  }
  
  public void draw(Graphics2D g) {
    try{
      //draw the current image for image
      g.drawImage(
                  image,
                  (int)(x),
                  (int)(y),
                  null
                 );
      // Moving the player around
      // player is being drawn
      //draw the player moving, not shooting
      if( notShooting){
        if(right) {
          /*  subRight = true;
           subLeft = false;*/
          image = ImageIO.read(getClass().getResourceAsStream("Resources/right.gif"));
          g.drawImage(
                      image,
                      (int)(x),
                      (int)(y),
                      null
                     );
        }
        else if(left) {
          /*subRight = false;
           subLeft = true;*/
          image = ImageIO.read(getClass().getResourceAsStream("Resources/left.gif"));
          g.drawImage(
                      image,
                      (int)(x),
                      (int)(y),
                      null
                     );
        }
        else {
          g.drawImage(
                      image,
                      (int)(x),
                      (int)(y),
                      null
                     );
          
        }        
      }
      
      //draw the player shooting, mot moving     
      //cannot move while attacking, except in air
      //with exception of clicking a move after to move very slowly
      if(fire && !(jumping || falling) && fireLeft >= weakCost) {
        left = false;
        right = false;
        dx = 0;
        if (subLeft){
          image = ImageIO.read(getClass().getResourceAsStream("Resources/weakAttackLeft.gif"));
        }
        else if (subRight){
          image = ImageIO.read(getClass().getResourceAsStream("Resources/weakAttackRight.gif"));
        }
        g.drawImage(image, ((int)x - GamePanel.WIDTH), (int)y, null);
      }
      if (ult && !(jumping || falling) && fireLeft >=ultCost){
        left = false;
        right = false;
        dx = 0;
        if (subLeft){
          image = ImageIO.read(getClass().getResourceAsStream("Resources/ult left.gif"));
          fireLeft -= ultCost;
        }
        else if (subRight){
          image = ImageIO.read(getClass().getResourceAsStream("Resources/ult right.gif"));
          fireLeft -= ultCost;
        }
        g.drawImage(image, ((int)x - GamePanel.WIDTH), (int)y, null);
      }
      
      //draw the blast
      //check to see if it goes of screen to remove
      if (blast != null){
        blast.draw(g);
        blast.checkOnScreen();
        if (blast.shouldRemove()){
          blast = null;
          fireCheck = false;
          ultCheck = false;
        }
      }
      
      //check to see if megaman goes off screen
      if ( x < -10){
        x=-10;
        // g.drawImage(image, ((int)x + GamePanel.WIDTH), (int)y, null);
        
      }        
      if ( x >= GamePanel.WIDTH - image.getWidth()/2){
        x = GamePanel.WIDTH - image.getWidth()/2;
        // g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);
      }
      if(Enemy.isDead()){
        if ( left && x < -3)
        {
          x = GamePanel.WIDTH - image.getWidth()/2;
          //g.drawImage(image, ((int)x - GamePanel.WIDTH), (int)y, null);
        }
        if ( right && x >= GamePanel.WIDTH - image.getWidth())
        {
          x = ( x % GamePanel.WIDTH) - GamePanel.WIDTH;
          //g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);  
        }  
      }
      // draw player
      if(flinching) {
        //see how long since start flinching
        //want in miliseconds
        g.drawString("PLAYER INJURED", GamePanel.WIDTH/2, 100);
        long elapsed =(System.nanoTime() - flinchTimer) / 1000000;
        if(elapsed / 100 % 2 == 0) {
          return;
        }
      }
      
    }//try loop
    catch(Exception e)
    { e.printStackTrace(); }
    
  }//end of the draw method
  
  
  
  
  
  
  
  
  
  
}






