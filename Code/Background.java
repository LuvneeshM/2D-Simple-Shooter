
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;


public class Background {
  
  private BufferedImage image = null;
  
  private double x;
  private double y;
  private double dx;
  private double dy;
  
  private int timer;
  private String mapLoc;
  
  private double moveScale;
  
  public Background(String s, double ms){
         
    moveScale = ms;
    mapLoc = s;
    timer = 0;
    
    try{
      image = ImageIO.read(getClass().getResourceAsStream(s));
    }
    catch (Exception e){
      e.printStackTrace();
    }
  }
  
  public void time(){
    if (mapLoc.equals("Resources/map.gif"))
    {
      timer++;
    }
  }
  
  public int getTime(){
    return timer;
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
  
  public void draw(Graphics2D g)
  {
      g.drawImage(image, (int)x, (int)y, null);
      
      if ( x < 0)
          g.drawImage(image, ((int)x + GamePanel.WIDTH), (int)y, null);
      if ( x > 0)
          g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y, null);

      g.setColor(Color.RED);
      g.drawString("Time: " + timer, 100,10);
  }  
  
  
  
  
}