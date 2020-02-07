import java.awt.Rectangle;

public class Ball
{
	public static final double MAX_SPEED = 8.5;
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;
	private double x;
	private double y;
	private double speed;
	private double angle;
	private Rectangle rect;
	private boolean canMove;
	
	public Ball(int x, int y)
	{
		this.x=x;
		this.y=y;
		speed = 5;
		//speed=8;
		canMove=false;
		int random = (int)(Math.random()*2);
		if(random==0)
		{
			angle=0;
		}
		else
		{
			angle=180;
		}
		//angle=100;
		//System.out.println("Angle of ball: "+angle);
		updateRectangle();
	}
	
	public boolean getMove()
	{
		return canMove;
	}
	
	public void setMove(boolean canMove)
	{
		this.canMove=canMove;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getAngle()
	{
		return angle;
	}
	
	public double getSpeed()
	{
		return speed;
	}
	
	public void setX(double x)
	{
		this.x=x;
	}
	
	public void setY(double y)
	{
		this.y=y;
	}
	
	public void setAngle(double angle)
	{
		this.angle=angle;
	}
	
	public void setSpeed(double speed)
	{
		this.speed=speed;
		if(speed>MAX_SPEED)
		{
			speed=MAX_SPEED;
		}
	}
	
	public void updateRectangle()
	{
		rect = new Rectangle((int)x, (int)y, WIDTH, HEIGHT);
	}	
	
	public Rectangle getRectangle()
	{
		return rect;
	}		
}