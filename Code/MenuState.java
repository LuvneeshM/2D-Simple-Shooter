import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MenuState extends GameState {
  
  private Background bg;
  
  private int currentChoice = 0;
  private String[] options = {
    "Start",
    "Help",
    "Quit"
  };
  
  private Color titleColor;
  private Font titleFont;
  
  private Font font;
  
  public MenuState(GameStateManager gsm){
    
    this.gsm = gsm;
    
    try{
      bg = new Background("Resources/Backgrounds/menubg.gif", 1);
      bg.setVec(-0.1,0);
      
      titleColor = new Color (150,150,150);
      titleFont = new Font("Times New Roman", Font.PLAIN, 28);
      font = new Font ("Arial", Font.PLAIN, 12);
      
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  
  //methods that are needed due to implementing the abstract class
  public void update(){
    bg.update();
  }
  
  public void draw(Graphics2D g)
  {
    //draw background
    bg.draw(g);
    //draw title
    g.setColor(titleColor);
    g.setFont(titleFont);
    g.drawString(" The Game", 100,100);
    
    //draw menu options
    g.setFont(font);
    for (int i = 0; i < options.length; i++){
      if ( i == currentChoice){
        g.setColor(Color.BLACK);
      }
      else 
        g.setColor(Color.RED);
      g.drawString(options[i], 145, 140 + i *15);
      
    }
 
  }
    
  public void init(){  }

  private void select(){
    if (currentChoice == 0){
      gsm.setState(GameStateManager.LEVELSTATE);
    }
    if (currentChoice == 1){
      HelpState h = new HelpState(gsm);
      h.init();
    }
    if (currentChoice == 2){
      System.exit(0);
    }
  }
  
  public void keyPressed(int e){
    if (e == KeyEvent.VK_ENTER){
      select();
    }
    if (e == KeyEvent.VK_UP){
      currentChoice--;
      if (currentChoice == -1){
        currentChoice = options.length - 1;
      }
    }
    if (e == KeyEvent.VK_DOWN){
      currentChoice++;
      if (currentChoice == options.length){
        currentChoice = 0;
      }
    }
     
     
     /*if (e.getKeyCode() == KeyEvent.VK_ENTER){
     select();
     }
     if (e.getKeyCode() == KeyEvent.VK_UP){
     currentChoice--;
     if (currentChoice == -1){
     currentChoice = options.length - 1;
     }
     }
     if (e.getKeyCode() == KeyEvent.VK_DOWN){
     currentChoice++;
     if (currentChoice == options.length){
     currentChoice = 0;
     }
     }
     */
  }
  
  public void keyReleased(int e){
    
  }

  
  
  
}