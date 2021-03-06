import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelpPanel extends JPanel{
  
  private JLabel intro, info, info2, info3, info4, info5, info6, info6C, info7, info7C, info8, info8C;
  
  public HelpPanel(){
    
    intro = new JLabel ("Instructions for the game:");
    info = new JLabel ("Use the left and right arrows to move.");
    info2 = new JLabel ( "To jump use the up arrow.");
    info3 = new JLabel ( "To shoot use either E or R.");
    info4 = new JLabel ("E is the weak attack.");
    info5 = new JLabel ("R is the strong attack.");
    info6 = new JLabel ("There will be a small lag time");
    info6C = new JLabel ("when starting the game.");
    info7 = new JLabel("After defeating the first enemy, you will have to face the boss."); 
    info7C = new JLabel("Watch out for the boss's attacks.");
    info8 = new JLabel("It is possible to control when the boss attacks.");
    info8C = new JLabel("So time your attack carefully.");
    
    this.add(intro);
    this.add(info);  
    this.add(info2);  
    this.add(info3);  
    this.add(info4);  
    this.add(info5);  
    this.add(info6);  
    this.add(info6C);  
    this.add(info7);
    this.add(info7C);
    this.add(info8);
    this.add(info8C);
    
    this.setPreferredSize(new Dimension(400, 300));
    setBackground (Color.yellow);
    
  }
  
}