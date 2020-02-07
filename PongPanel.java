import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;

public class PongPanel extends JPanel implements KeyListener, Runnable
{
	private BufferedImage buffer;
	private PongGame game;
	private int updatesPerSecond = 30;
	private long startTime = 0;
	private long updateCount = 0;	
	private int fps;
	
	public PongPanel()
	{
		setSize(650, 525);
		reset();
		addKeyListener(this);
		game=new PongGame();
		try
		{

			buffer = new BufferedImage(650, 525, BufferedImage.TYPE_4BYTE_ABGR);
		}
		catch(Exception e)
		{
			System.out.println("Error loading images: ");
			e.printStackTrace();
		}

	}
	

	
	public void paint(Graphics g)
	{

		Graphics bg = buffer.getGraphics();
		bg.setColor(Color.WHITE);
		bg.fillRect(0,0,getWidth(),getHeight()); //background
		bg.setColor(Color.BLACK);
		bg.fillRect(0,25,getWidth(),getHeight()-75);
		bg.setColor(Color.WHITE);
		for(int x=0; x<13; x++)
		{
			bg.fillRect(320, 28+x*35, 5, 25);
		}
		Font f1 = new Font("Times New Roman", Font.BOLD, 48);
		bg.setFont(f1);
		bg.drawString(""+game.getPlayerOneScore(), 225, 75);
		bg.drawString(""+game.getPlayerTwoScore(), 400, 75);
		bg.setColor(Color.PINK);
		bg.fillRect((int)game.getBall().getX(), (int)game.getBall().getY(), 10, 10);
		if(game.getPaddle1().getStatus()==Paddle.PLAYING)
		{
			bg.setColor(Color.WHITE);
		}
		else if(game.getPaddle1().getStatus()==Paddle.RELOAD)
		{
			bg.setColor(Color.YELLOW);
		}
		else if(game.getPaddle1().getStatus()==Paddle.DEAD)
		{
			bg.setColor(Color.RED);
		}		
		bg.fillRect((int)game.getPaddle1().getX(), (int)game.getPaddle1().getY(), Paddle.WIDTH, Paddle.HEIGHT-30+10*game.getPaddle1().getHealth());
		if(game.getPaddle2().getStatus()==Paddle.PLAYING)
		{
			bg.setColor(Color.WHITE);
		}
		else if(game.getPaddle2().getStatus()==Paddle.RELOAD)
		{
			bg.setColor(Color.YELLOW);
		}
		else if(game.getPaddle2().getStatus()==Paddle.DEAD)
		{
			bg.setColor(Color.RED);
		}		
		bg.fillRect((int)game.getPaddle2().getX(), (int)game.getPaddle2().getY(), Paddle.WIDTH, Paddle.HEIGHT-30+10*game.getPaddle2().getHealth());
		bg.setColor(Color.WHITE);
		for(int x=0; x<game.getPaddle1().getBullets().getItems().size(); x++)
		{
			bg.fillOval((int)game.getPaddle1().getBullets().getItems().get(x).getX(), (int)game.getPaddle1().getBullets().getItems().get(x).getY(), Bullet.WIDTH, Bullet.HEIGHT);
		}
		for(int x=0; x<game.getPaddle2().getBullets().getItems().size(); x++)
		{
			bg.fillOval((int)game.getPaddle2().getBullets().getItems().get(x).getX(), (int)game.getPaddle2().getBullets().getItems().get(x).getY(), Bullet.WIDTH, Bullet.HEIGHT);
		}		
		Font f2 = new Font("Times New Roman", Font.BOLD, 20);
		bg.setFont(f2);
		if(game.status()==PongGame.PLAYER_1_WINS)
		{
			bg.drawString("PLAYER 1 WON", 90, 125);
			bg.drawString("PRESS N FOR A NEW GAME", 40, 175);
		}
		else if(game.status()==PongGame.PLAYER_2_WINS)
		{
			bg.drawString("PLAYER 2 WON", 420, 125);
			bg.drawString("PRESS N FOR A NEW GAME", 370, 175);			
		}
		bg.setColor(Color.BLACK);
		Font f3 = new Font("Times New Roman", Font.BOLD, 18);
		bg.setFont(f3);
		bg.drawString("Ammo : "+game.getPaddle1().getAmmo()+"/3", 15, 495);
		bg.drawString("Health :",15, 515);
		bg.drawString(game.getPaddle2().getAmmo()+"/3 : Ammo", 545, 495);
		bg.drawString(": Health",573, 515);
		if(game.getPaddle1().getHealth()==3)
		{
			bg.setColor(Color.GREEN);
			bg.fillRect(85, 503, 120, 15);
		}
		else if(game.getPaddle1().getHealth()==2)
		{
			bg.setColor(Color.YELLOW);
			bg.fillRect(85, 503, 80, 15);
		}
		else if(game.getPaddle1().getHealth()==1)
		{
			bg.setColor(Color.RED);
			bg.fillRect(85, 503, 40, 15);
		}
		if(game.getPaddle2().getHealth()==3)
		{
			bg.setColor(Color.GREEN);
			bg.fillRect(445, 503, 120, 15);
		}
		else if(game.getPaddle2().getHealth()==2)
		{
			bg.setColor(Color.YELLOW);
			bg.fillRect(485, 503, 80, 15);
		}
		else if(game.getPaddle2().getHealth()==1)
		{
			bg.setColor(Color.RED);
			bg.fillRect(525, 503, 40, 15);
		}			
		g.drawImage(buffer,0,0,null);
	}
	
	public void reset()
	{
		game=new PongGame();
	}
	
	public void update()
	{
		game.update();
	}
	
	public void run()
	{
		//calculates how many miliseconds to wait for the next update
		int waitToUpdate = (1000/ updatesPerSecond);
		long startTime = System.nanoTime();
		while(true)
		{
			//is true if you update
			boolean shouldRepaint = false;
				
			//finds the current time
			long currentTime = System.nanoTime();
			
			//finds out how many updates are needed
			long updatesNeeded = (((currentTime-startTime)/1000000))/ waitToUpdate;
				
			for(long x = updateCount; x< updatesNeeded; x++)
			{
				update();
				shouldRepaint=true;
				updateCount++;
			}
			if(shouldRepaint)
				repaint();
			try
			{
				Thread.sleep(5);
			}
			catch(Exception e)
			{
				System.out.println("Error with Thread: ");
				e.printStackTrace();
			}				
		}
	}
	
	//occurs when any key goes down
	public void keyPressed(KeyEvent e)
	{
		char key = e.getKeyChar();
		if(key=='w')
		{
			game.getPaddle1().setUpPressed(true);
		}	
		if(key=='s')
		{
			game.getPaddle1().setDownPressed(true);
		}
		if(key=='i')
		{
			game.getPaddle2().setUpPressed(true);
		}
		if(key=='k')
		{
			game.getPaddle2().setDownPressed(true);
		}
		if((key=='d' && game.status()==PongGame.PLAYING) && game.getPaddle1().getStatus()==Paddle.PLAYING)
		{
			game.getPaddle1().shoot();
		}
		if((key=='j' && game.status()==PongGame.PLAYING) && game.getPaddle2().getStatus()==Paddle.PLAYING)
		{
			game.getPaddle2().shoot();
		}
		if((key=='a' && game.status()==PongGame.PLAYING) && (game.getPaddle1().getAmmo()<3 && game.getPaddle1().getStatus()==Paddle.PLAYING))
		{
			game.getPaddle1().reload();
		}
		if((key=='l' && game.status()==PongGame.PLAYING)&& (game.getPaddle2().getAmmo()<3 &&game.getPaddle2().getStatus()==Paddle.PLAYING))
		{
			game.getPaddle2().reload();
		}		
	}
	
	//occurs when any key goes up
	public void keyReleased(KeyEvent e)
	{
		char key = e.getKeyChar();
		if(key=='w')
		{
			game.getPaddle1().setUpPressed(false);
		}	
		if(key=='s')
		{
			game.getPaddle1().setDownPressed(false);
		}
		if(key=='i')
		{
			game.getPaddle2().setUpPressed(false);
		}
		if(key=='k')
		{
			game.getPaddle2().setDownPressed(false);
		}		
	}
	
	//occurs when a character would appear on the screen
	public void keyTyped(KeyEvent e) 
	{
		char key = e.getKeyChar();
		if(key=='n' && game.status()!=PongGame.PLAYING)
		{
			reset();
		}				
	}
	
	public void addNotify() //only for key listeners
	{
		super.addNotify();
		requestFocus();
		Thread pongThread = new Thread(this);
		pongThread.start();
	}
	
}