import java.awt.*;
import java.awt.event.*;

public class LevelState extends GameState{
  
  private GameMap map;
  private Background bg;
  
  private Player pl;
  private Enemy en;
  private Boss b;
  
  private ScreenInfo info;
  
  private Font font;
  
  public LevelState(GameStateManager gsm){
    this.gsm = gsm;    
    init();
  }
  
  public void init(){
    
    bg = new Background("Resources/map.gif", 0.1);
    
    en = new Enemy();
    en.setPosition(200, GamePanel.HEIGHT - 72 -1);
    en.setVec(-.5,0);
    
    pl = new Player();
    pl.setPosition(74,GamePanel.HEIGHT - 72 -1);
    
    b = new Boss();
    b.setPosition(100, GamePanel.HEIGHT - 150 -1);
    b.setVec(-1.0, 0);
    
    info = new ScreenInfo(pl, en, b);
  }
  public void update(){
    //  blast.update();
    
    bg.time();
    if(bg.getTime()%25 == 0 && pl.getFireLeft() < pl.getMaxFire())
    {
      pl.setFire(1);
      if(!en.isDead() && bg.getTime()%100 == 0)
       en.regen();
      if(en.isDead() && b.getFireLeft() < b.getMaxFire())
        b.setFire(5);
    }
    
    pl.update();
    
    if(!en.isDead()){
      //attack enemies
      pl.checkAttack(en);
      if (!en.isDead())
        en.update();
    }
    if(en.isDead()){
      if (!b.isDead()){
        pl.checkBossAttack(b);
        b.checkBossAttack(pl);
        b.update();
      }
    }
    
  }
  public void draw(Graphics2D g){
    //draw everything while player is alive and enemy alive
    if(!pl.getDead())
    {
      if(!en.isDead())
      {
        //draw new background 
        bg.draw(g);
        
        //draw the enemy
        //draw while alive
        en.draw(g);
        
        //draw the player while alive
        pl.draw(g);
        
        //draw info
        info.draw(g);
      }
      //enemy dies
      //boss comes in
      if (en.isDead()){
        //draw new background 
        bg.draw(g);
        
        //draw the enemy
        //draw while alive
        b.draw(g);
        
        //draw the player while alive
        pl.draw(g);
        
        //draw info
        info.draw(g);    
      }
      if (b.isDead())
      {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        g.setColor(Color.BLACK);
        font = new Font ("Comic San MS", Font.PLAIN, 20);
        g.setFont(font);
        
        g.drawString("PLAYER WINS", 100, 100);
      }
    }
      if(pl.getDead() && !b.isDead()){
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        
        g.setColor(Color.BLACK);
        font = new Font ("Comic San MS", Font.PLAIN, 20);
        g.setFont(font);
        
        g.drawString("PLAYER LOSES", 100, 100);
      }
    }
  
  public void keyPressed(int k){
    if( k == KeyEvent.VK_LEFT)
      pl.setLeft(true);
    else if( k == KeyEvent.VK_RIGHT)
      pl.setRight(true);
    else if( k == KeyEvent.VK_UP)
      pl.setJumping(true);
    else  if( k == KeyEvent.VK_E)
    {
      pl.setFire(true);
      pl.setNotShooting(false);
    }
    else if ( k == KeyEvent.VK_R){
      pl.setUlt(true);
      pl.setNotShooting(false);
    }

  }
  
  public void keyReleased(int k){
    if( k == KeyEvent.VK_LEFT)
      pl.setLeft(false);
    else if( k == KeyEvent.VK_RIGHT)
      pl.setRight(false);
    else if( k == KeyEvent.VK_UP)
      pl.setJumping(false);
    else if( k == KeyEvent.VK_E)
    {     
      pl.setNotShooting(true);
      pl.setFire(false);
    }
    else if ( k == KeyEvent.VK_R)
    {  
      pl.setNotShooting(true);
      pl.setUlt(false);
    }

    
  }
  
  
  
}