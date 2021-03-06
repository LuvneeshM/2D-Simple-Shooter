import javax.swing.*;

public class Game extends JFrame{
    
   //constants for the dimension
  //can be used by the other class
  final static int WINDOW_WIDTH = 340;
  final static int WINDOW_HEIGHT = 260;
  
  // constructor
  public Game(){
    
    GamePanel panel = new GamePanel();
    
    this.setContentPane(panel); //adds a new Panel called panel to the Frame
    
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//will set the default close operation
    this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT+30);//sets the size of the frame
    this.setLocationRelativeTo(null); //sets the frame to center of the screen
    this.setTitle("Mega Man Game"); //sets the title of the frame
        
    setResizable(true); //can not change the size
    setVisible(true); //makes the frame visible
    
    panel.setFocusable(true);//sets the panel to be focusable
  }
  
  public static void main(String[] args)
  {
    //MUST HAVE STUFF
    try {
      UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    new Game();
  }//end of main method 
  
  
}//end of class 
    
