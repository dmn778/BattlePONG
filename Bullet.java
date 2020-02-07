import java.awt.Rectangle;

public class Bullet
{
	public static final int PADDLE1=1;
	public static final int PADDLE2=2;
	public static final int WIDTH=10;
	public static final int HEIGHT=6;
	private int player;
	private double x;
	private double y;
	private double speed=7;
	private Rectangle rect;
	
	public Bullet(int player, double x, double y)
	{
		this.player=player;
		this.x=x;
		this.y=y;
	}
	
	public void setX(double x)
	{
		this.x=x;
	}
	
	public void setY(double y)
	{
		this.y=y;
	}
	
	public void update()
	{
		if(player==PADDLE1)
		{
			x+=speed;
		}
		else
		{
			x-=speed;
		}
		updateRectangle();
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
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