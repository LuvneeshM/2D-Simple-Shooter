import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ScreenInfo {
  
  private Player player;
  private Enemy en;
  private Boss b;
  private BufferedImage playerInfo, enemyInfo, bInfo;
  private Font font;
  
  public ScreenInfo (Player p, Enemy enemy, Boss bo){
    
    player = p;
    en = enemy;
    b = bo;
    
    try{
      playerInfo = ImageIO.read(getClass().getResourceAsStream("Resources/Info/hud.gif"));
      enemyInfo = ImageIO.read(getClass().getResourceAsStream("Resources/Info/enemyInfo.gif"));
      bInfo = ImageIO.read(getClass().getResourceAsStream("Resources/Info/enemyInfo.gif"));
      font = new Font ("Comic San MS", Font.PLAIN, 14);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    
    
  }
  
  
  public void draw(Graphics2D g){
    
    g.setColor(Color.BLACK);
    g.setFont(font);
    
    g.drawImage(playerInfo, 0, 15, null);
    g.drawString("Player: ", 0 ,12);
    g.drawString(player.getHealth() + "/" + player.getMaxHealth(), 20,30);
    g.drawString(player.getFireLeft() + "/" + player.getMaxFire(), 18,50);
    
    
    g.drawString("Land Enemy: ", GamePanel.WIDTH-enemyInfo.getWidth(), 12);
    if(!en.isDead()){
      g.drawImage(enemyInfo, GamePanel.WIDTH-enemyInfo.getWidth(), 15, null);
      g.drawString(en.getHealth() + "/" + en.getMaxHealth(), GamePanel.WIDTH-enemyInfo.getWidth()+20, 30);
      g.drawString(en.getFireLeft() + "/" + en.getMaxFire(), GamePanel.WIDTH-enemyInfo.getWidth()+18, 50);
    }
    else{
      g.drawImage(bInfo, GamePanel.WIDTH-enemyInfo.getWidth(), 15, null);
      g.drawString(b.getHealth() + "/" + b.getMaxHealth(), GamePanel.WIDTH-bInfo.getWidth()+20, 30);
      g.drawString(b.getFireLeft() + "/" + b.getMaxFire(), GamePanel.WIDTH-bInfo.getWidth()+18, 50);
    }
    
  }
  
  
  
  
  
  
  
  
  
  
  
}