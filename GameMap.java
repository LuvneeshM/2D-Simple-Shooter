import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

//similar to the background class
//has a moving background for the game
public class GameMap{
  
  private BufferedImage image = null;

  private double x;
  private double y;
  private double dx;
  private double dy;
  
  private double moveScale;
  
  public GameMap(double ms){
    try{
      image = ImageIO.read(getClass().getResourceAsStream("Resources/map.gif"));
      moveScale = ms;
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
  
  public void setPos(double x, double y){
    this.x = (x * moveScale) % GamePanel.WIDTH;
    this.y = (y * moveScale) % GamePanel.HEIGHT;
    
  }
  
  public void setVec(double dx, double dy){
    this.dx = dx;
    this.dy = dy;
  }
  
  public void update(){
    x += dx;
    y += dy;  
  }
  
  public void draw(Graphics2D g){
    /* g.setColor(Color.CYAN);
     g.fillRect(0,0, GamePanel.WIDTH, GamePanel.HEIGHT-50);
     g.setColor(Color.WHITE);*/
    
    g.drawImage(image, (int)x, (int)y, null);
    
    if ( x < 0)
      g.drawImage(image, ((int)x + GamePanel.WIDTH), (int)y, null);
    if ( x > 0)
      g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);    
  }
  
  
  
}