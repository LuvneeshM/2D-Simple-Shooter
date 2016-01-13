import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;


public class GamePanel extends JPanel implements Runnable, KeyListener
{
  
  //dimensions
  public static final int WIDTH = 320;
  public static final int HEIGHT = 240;
  public static final int SCALE = 2;
  
  //game thread
  private static Thread thread;
  private boolean running;
  private int FPS = 60;
  private long targetTime = 1000/FPS;
  //image
  private BufferedImage image;
  private Graphics2D g;
  
  //game state manager
  private GameStateManager gsm;
  
  public GamePanel(){
    setPreferredSize(new Dimension(WIDTH *SCALE, HEIGHT *SCALE));
    setFocusable(true);
    requestFocus();
  }
  
  public void addNotify(){
    super.addNotify();
    if (thread == null){
      thread = new Thread (this);
      addKeyListener(this);
      thread.start();

    }
    
  }
  
  public static Thread getThread(){ return thread; }
  
  private void init(){
    
    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    
    g = (Graphics2D) image.getGraphics();
    
    running = true;
    
    gsm = new GameStateManager();
  }
  
  public  void run(){
    init();
    
    long start, elapsed, wait;
    
    //game loop
    while (running){
      
      start = System.nanoTime();
      
      update();
      draw();
      drawToScreen();
      
      elapsed = System.nanoTime() - start;
      
      wait = Math.abs(targetTime - elapsed / 1000000);
      
      try {
        thread.sleep(wait);
      }
      catch(Exception e){
        e.printStackTrace();
      }
      /*if(Player.getDead()){
        running = false;
      }*/
    }
  }
  
  public void update(){
    gsm.update();
  }
  public void draw(){
    gsm.draw(g);
  }
  private void drawToScreen(){
    Graphics g2 = getGraphics();
    g2.drawImage(image,0,0,null);
    g2.dispose();
  }
  
  
  //methods used to move character around
  //methods that are needed due to implementing the abstract class
  public void keyPressed(KeyEvent e){
    gsm.keyPressed(e.getKeyCode());
 
  }
  
  public void keyReleased(KeyEvent e){
    gsm.keyReleased(e.getKeyCode());
  }
  
  public void keyTyped(KeyEvent e){
    
  }
  
}