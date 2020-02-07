import java.awt.Rectangle;

public class Paddle
{
	public static final int WIDTH = 15;
	public static final int HEIGHT = 70;
	public static final int PLAYING=0;
	public static final int RELOAD=1;
	public static final int DEAD=2;
	public static final int MAX_AMMO=3;
	private boolean upPressed;
	private boolean downPressed;
	private double x;
	private double y;
	private double speed = 6;
	private Rectangle rect;
	private int status;
	private int ammo;
	private BulletArrayList bullets;
	private int identity;
	private long waitTime;
	private long currentTime;
	private int health;
	
	public Paddle(int x, int y, int identity)
	{
		this.x=x;
		this.y=y;
		upPressed=downPressed=false;
		updateRectangle();
		status=PLAYING;
		ammo=MAX_AMMO;
		bullets = new BulletArrayList();
		this.identity=identity;
		health=3;
	}
	
	public BulletArrayList getBullets()
	{
		return bullets;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public void setHealth(int health)
	{
		this.health=health;
		if(health<0)
		{
			health=0;
		}
	}
	
	public int getAmmo()
	{
		return ammo;
	}
	
	public void setAmmo(int ammo)
	{
		this.ammo=ammo;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public void setStatus(int status)
	{
		this.status=status;
	}
	
	public void update()
	{
		bullets.update();
		if((upPressed & downPressed) || status!=PLAYING)
		{
			if(status==RELOAD)
			{
				if(System.currentTimeMillis()>waitTime)
				{
					ammo=MAX_AMMO;
					status=PLAYING;
				}
			}
			else if(status==DEAD)
			{
				if(System.currentTimeMillis()>waitTime)
				{
					health=3;
					status=PLAYING;
				}
			}
			return;
		}
		else if(upPressed && status==PLAYING)
		{
			y-=speed;
			if(y<25)
			{
				y=25;
			}
			updateRectangle();
		}
		else if(downPressed && status==PLAYING)
		{
			y+=speed;
			if(y+HEIGHT-30+10*health>475)
			{
				y=475-(HEIGHT-30+10*health);
			}
			updateRectangle();
		}
	}
	
	public Rectangle getRectangle()
	{
		return rect;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public boolean getDownPressed()
	{
		return downPressed;
	}
	
	public boolean getUpPressed()
	{
		return upPressed;
	}
	
	public void setX(double x)
	{
		this.x=x;
	}
	
	public void setY(double y)
	{
		this.y=y;
	}
	
	public void setDownPressed(boolean downPressed)
	{
		this.downPressed=downPressed;
	}
	
	public void setUpPressed(boolean upPressed)
	{
		this.upPressed=upPressed;
	}
	
	public void updateRectangle()
	{
		rect = new Rectangle((int)x, (int)y, WIDTH, (HEIGHT-30+10*health));
	}
	
	public void shoot()
	{
		if(ammo>0)
		{
			double b1X;
			double b1Y;
			double b2X;
			double b2Y;		
			if(identity==Bullet.PADDLE1)
			{
				b1X=x+WIDTH;
				b1Y=y;
				b2X=x+WIDTH;
				b2Y=y+HEIGHT-Bullet.HEIGHT-30+10*health;
			}
			else
			{
				b1X=x-Bullet.WIDTH;
				b1Y=y;
				b2X=x-Bullet.WIDTH;
				b2Y=y+HEIGHT-Bullet.HEIGHT-30+10*health;
			}
			Bullet b1=new Bullet(identity, b1X, b1Y);
			Bullet b2=new Bullet(identity, b2X, b2Y);
			bullets.getItems().add(b1);
			bullets.getItems().add(b2);
			ammo--;
		}
	}
	public void reload()
	{
		status=RELOAD;
		currentTime=System.currentTimeMillis();
		waitTime=currentTime+1000;
	}	
		
	public void die()
	{
		status=DEAD;
		currentTime=System.currentTimeMillis();
		waitTime=currentTime+4000;
	}		
}