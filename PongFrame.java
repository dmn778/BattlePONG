import java.awt.*;
import javax.swing.*;

public class PongFrame extends JFrame
{
	public PongFrame()
	{
		//creates the frame with the given title
		super("Pong");
		
		//makes the x kill the program
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//makes the window notable to be resized by the user
		setResizable(false);
		
		//creates the window (still invisible, for measurement)
		pack();
		
		//creates the MS Panel
		PongPanel p = new PongPanel();
		
		//gets the frames insets
		Insets in = getInsets();
		
		//calculates the size needed for the frame and its panel
		int frameWidth = p.getWidth() + in.left + in.right;
		int frameHeight = p.getHeight() + in.top + in.bottom;
		
		//sets the desired size for the window
		setPreferredSize(new Dimension(frameWidth, frameHeight));
		
		//turns off the layout options
		setLayout(null);
		
		//add the panel to the frame
		add(p);
		
		//adjust the size of the frame to the preferredSize
		pack();
		
		//makes the frame visible
		setVisible(true);
	}
}