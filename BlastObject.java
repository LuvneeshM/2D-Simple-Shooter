import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class BlastObject extends MapObject {
  
  private BufferedImage image = null;

  private double x;
  private double y;
  private double dx;
  private double dy;
  
  private boolean hit;
  private boolean remove;
  
  private double moveScale;
  
  public BlastObject(String s, double ms){
    
    remove = false;
    
    moveSpeed = 3.8;
    if (subRight)
      dx = moveSpeed;
    else 
      dx = -moveSpeed;
    
    width = 74;
    height= 72;
    
    try{
      image = ImageIO.read(getClass().getResourceAsStream(s));
      moveScale = ms;
    }
    catch(Exception e){
      e.printStackTrace();
    }
    
  }
  
  //get Methods
  public boolean shouldRemove() {return remove; }
  public int getWidth() {return image.getWidth(); }
  public int getHeight() { return image.getHeight(); }
  public int getX() { return (int)x; }
  public int getY() { return (int)y; }
  
  //set Methods
  public void setHit(){
    hit = true;
    remove = hit;
    dx = 0;
  }
  public void setPosition(double x, double y){
    this.x = (x * moveScale) % GamePanel.WIDTH;
    this.y = (y * moveScale) % GamePanel.HEIGHT;
  }
  public void setVec(double dx, double dy){
    this.dx = dx;
    this.dy = dy;
  }
  
  public void update(){
    
   //setPosition(x, y);
    
    x += dx;
    y += dy;  
  }
  
  public void draw(Graphics2D g){
    
      g.drawImage(image, (int)x, (int)y, null);
    
    
    update();
    
    /*if ( x < 0)
      g.drawImage(image, ((int)x + GamePanel.WIDTH), (int)y, null);
    if ( x > 0)
      g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);    */
  }
  
  
  public void checkOnScreen(){
    if( x >= GamePanel.WIDTH || x <= -20)
      remove = true;

  }
  
  
  
  
  
  
  
  
  
}