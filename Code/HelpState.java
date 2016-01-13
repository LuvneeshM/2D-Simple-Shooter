import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class HelpState extends GameState{
  
  public HelpState(GameStateManager gsm){
    
    this.gsm = gsm;    
  //  init();
  }
  
  
  public void init()
  {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    HelpPanel help = new HelpPanel();
    
    frame.add(help);    
    frame.setSize(400, 300);//sets the size of the frame
    frame.pack();
    frame.setVisible(true);
  }
  
  public void update()
  {
    
  }
  
  public void draw(java.awt.Graphics2D g)
  {
    
  }
  
  public void keyPressed(int k)
  {
    
  }
  
  public void keyReleased (int k)
  {
    
  }
  
  
}