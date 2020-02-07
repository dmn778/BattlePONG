import java.awt.Rectangle;

public class PongGame
{
	public static final int PLAYING=0;
	public static final int PLAYER_1_WINS=1;
	public static final int PLAYER_2_WINS=2;
	private Paddle p1;
	private Paddle p2;
	private Ball ball;
	private int status;
	private double speedAdd;
	private int playerOneScore;
	private int playerTwoScore;
	private boolean print=false;
	private long waitBall;
	private long currentTime;
	
	public PongGame()
	{
		status=PLAYING;
		speedAdd=0;
		resetBall();
		playerOneScore=playerTwoScore=0;
		p1=new Paddle(13, 213, Bullet.PADDLE1);
	//	p1=new Paddle(13, 394);
		p2=new Paddle(618, 213, Bullet.PADDLE2);
		currentTime=System.currentTimeMillis();
		waitBall=currentTime+2000;
	}
	
	public void update()
	{
		if(status==PLAYING)
		{
			p1.update();
			p2.update();
			if(ball.getMove())
			{
				moveBall(ball.getSpeed());
				speedAdd+=0.0075;
			}
			for(int x=0; x<p1.getBullets().getItems().size(); x++)
			{
				if(p1.getBullets().getItems().get(x).getRectangle().intersects(p2.getRectangle()) && p1.getStatus()!=Paddle.DEAD)
				{
					p2.setHealth(p2.getHealth()-1);
					p1.getBullets().getItems().remove(x);
					p2.updateRectangle();
					if(p2.getHealth()==0)
					{
						p2.die();
					}
					x--;
				}
			}
			for(int x=0; x<p2.getBullets().getItems().size(); x++)
			{
				if(p2.getBullets().getItems().get(x).getRectangle().intersects(p1.getRectangle())&& p1.getStatus()!=Paddle.DEAD)
				{
					p1.setHealth(p1.getHealth()-1);
					p2.getBullets().getItems().remove(x);
					p2.updateRectangle();
					if(p1.getHealth()==0)
					{
						p1.die();
					}
					x--;
				}
			}	
			for(int x=0; x<p1.getBullets().getItems().size(); x++)
			{
				for(int y=0; y<p2.getBullets().getItems().size(); y++)
				{
					if(((x<p1.getBullets().getItems().size() && x>=0) && (y<p2.getBullets().getItems().size() && y>=0)) && p1.getBullets().getItems().get(x).getRectangle().intersects(p2.getBullets().getItems().get(y).getRectangle()))
					{
						p1.getBullets().getItems().remove(x);
						p2.getBullets().getItems().remove(y);
						x--;
						y--;
					}
				}
			}			
		}
		if(ball.getX()<0-Ball.WIDTH)
		{
			playerTwoScore++;
			resetBall();
			currentTime=System.currentTimeMillis();
			waitBall=currentTime+2000;			
		}
		if(ball.getX()>650)
		{
			playerOneScore++;
			resetBall();
			currentTime=System.currentTimeMillis();
			waitBall=currentTime+2000;									
		}
		if(playerOneScore==7)
		{
			status=PLAYER_1_WINS;
		}
		if(playerTwoScore==7)
		{
			status=PLAYER_2_WINS;
		}
		if(ball.getMove()==false)
		{
			if(System.currentTimeMillis()>waitBall)
			{
				ball.setMove(true);
			}
		}
	}
	
	public int status()
	{
		return status;
	}
	
	public Paddle getPaddle1()
	{
		return p1;
	}
	
	public Paddle getPaddle2()
	{
		return p2;
	}
	
	public Ball getBall()
	{
		return ball;
	}
	
	public void moveBall(double distance)
	{
		double deltaX=(distance*Math.cos(ball.getAngle()*Math.PI/180));
		double deltaY=(distance*Math.sin(ball.getAngle()*Math.PI/180));
		/*if(print==false)
		{
			System.out.println("deltaX "+deltaX);
			System.out.println("deltaY "+deltaY);
			print=true;
		}*/
		double oldX=ball.getX();
		double oldY=ball.getY();
		double newX=ball.getX()+deltaX;
		double newY=ball.getY()+deltaY;
		ball.setX(newX);
		ball.setY(newY);
		ball.updateRectangle();
	//	while(hitTopWall() || hitBottomWall() || ball.getRectangle().intersects(p1.getRectangle()) || ball.getRectangle().intersects(p2.getRectangle()))
	//	{
			if(hitTopWall())
			{
				//System.out.println("TOP BOUNCE");
				double hitY=25;
				double deltaYHit=Math.abs(oldY-hitY);
				double percent_to_wall=deltaYHit/deltaY;
				double hitX=oldX+deltaX*percent_to_wall;
				double leftOverVel=(1-percent_to_wall)*distance;
				ball.setAngle(360-ball.getAngle());
				newX=hitX+leftOverVel*Math.cos(ball.getAngle()*Math.PI/180);
				newY=hitY+leftOverVel*Math.sin(ball.getAngle()*Math.PI/180);
				ball.setX(newX);
				ball.setY(newY);
				ball.updateRectangle();
				print=false;
				//System.out.println("Angle of ball: "+ball.getAngle());
				ball.setSpeed(ball.getSpeed()+speedAdd);
				speedAdd=0;
			}
			if(hitBottomWall())
			{
				//System.out.println("BOTTOM BOUNCE");
				double hitY=465;
				double deltaYHit=Math.abs(oldY-hitY);
				double percent_to_wall=deltaYHit/deltaY;
				double hitX=oldX+deltaX*percent_to_wall;
				double leftOverVel=(1-percent_to_wall)*distance;
				ball.setAngle(360-ball.getAngle());
				newX=hitX+leftOverVel*Math.cos(ball.getAngle()*Math.PI/180);
				newY=hitY+leftOverVel*Math.sin(ball.getAngle()*Math.PI/180);
				ball.setX(newX);
				ball.setY(newY);
				ball.updateRectangle();	
				print=false;
				//System.out.println("Angle of ball: "+ball.getAngle());
				ball.setSpeed(ball.getSpeed()+speedAdd);
				speedAdd=0;		
			}
			if(ball.getRectangle().intersects(p1.getRectangle()) && p1.getStatus()!=Paddle.DEAD)
			{
			//	System.out.println("PADDLE 1 BOUNCE"); 
				double ballCenter=ball.getY()+Ball.HEIGHT/2;
				double paddleCenter=p1.getY()+(Paddle.HEIGHT-30+10*p1.getHealth())/2;
				double hitX;
				if(oldX<p1.getX()+Paddle.WIDTH) //meaning it hit on the top or bottom
				{	
					hitX=ball.getX();
				}
				else
				{
					hitX=p1.getX()+Paddle.WIDTH;
				}
				double deltaXHit=Math.abs(oldX-hitX);
				double percent_to_paddle=Math.abs(deltaXHit/deltaX);
			//	System.out.println("% to paddle "+percent_to_paddle);
				double hitY=oldY+deltaY*percent_to_paddle;
				double leftOverVel=(1-percent_to_paddle)*distance;
			//	System.out.println("leftOverVel "+leftOverVel);
				double percentAng=Math.abs((paddleCenter-ballCenter)/39);
			//	System.out.println("percent Angle" +percentAng);
				if((int)ballCenter==(int)paddleCenter)
				{
					ball.setAngle(0);
				}
				else if(ballCenter<paddleCenter)
				{
					ball.setAngle(360-80*percentAng);
				}
				else if(ballCenter>paddleCenter)
				{
					ball.setAngle(80*percentAng);
				}
				newX=hitX+leftOverVel*Math.cos(Math.toRadians(ball.getAngle()));
				newY=hitY+leftOverVel*Math.sin(ball.getAngle()*Math.PI/180);
				ball.setX(newX);
				ball.setY(newY);
				ball.updateRectangle();
				print=false;
				//System.out.println("Angle of ball: "+ball.getAngle());
				ball.setSpeed(ball.getSpeed()+speedAdd);
				speedAdd=0;	
			}
			if(ball.getRectangle().intersects(p2.getRectangle()) && p2.getStatus()!=Paddle.DEAD)
			{
			//	System.out.println("PADDLE 2 BOUNCE");
				double ballCenter=ball.getY()+Ball.HEIGHT/2;
				double paddleCenter=p2.getY()+(Paddle.HEIGHT-30+10*p2.getHealth())/2;
				//double hitX=p2.getX()-ball.WIDTH;
				double hitX;
				if(oldX>p2.getX())
				{
					hitX=ball.getX();
				}
				else
				{
					hitX=p2.getX()-ball.WIDTH;
				}				
				double deltaXHit=Math.abs(oldX-hitX);
				double percent_to_paddle=Math.abs(deltaXHit/deltaX);
				double hitY=oldY+deltaY*percent_to_paddle;
				double leftOverVel=(1-percent_to_paddle)*distance;
				double percentAng=Math.abs((paddleCenter-ballCenter)/39); 
				if((int)ballCenter==(int)paddleCenter)
				{
					ball.setAngle(180);
				}
				else if(ballCenter<paddleCenter)
				{
					ball.setAngle(180+80*percentAng);
				}
				else if(ballCenter>paddleCenter)
				{
					ball.setAngle(180-80*percentAng);
				}			
				newX=hitX+leftOverVel*Math.cos(ball.getAngle()*Math.PI/180);
				newY=hitY+leftOverVel*Math.sin(ball.getAngle()*Math.PI/180);
				ball.setX(newX);
				ball.setY(newY);
				ball.updateRectangle();
				print=false;
				//System.out.println("Angle of ball: "+ball.getAngle());
				ball.setSpeed(ball.getSpeed()+speedAdd);
				speedAdd=0;						
			}
	//	}
	}
	
	public void resetBall()
	{
		ball=new Ball(318, 243);
	//	ball=new Ball(18, 464);
	}
	
	public int getPlayerOneScore()
	{
		return playerOneScore;
	}
	
	public int getPlayerTwoScore()
	{
		return playerTwoScore;
	}
	
	public void setPlayerOneScore(int playerOneScore)
	{
		this.playerOneScore=playerOneScore;
	}
	
	public void setPlayerTwoScore(int playerTwoScore)
	{
		this.playerTwoScore=playerTwoScore;
	}
	
	public boolean hitTopWall()
	{
		if(ball.getY()<=25)
		{
			return true;
		}
		return false;
	}
	
	public boolean hitBottomWall()
	{
		if(ball.getY()>=465)
		{
			return true;
		}
		return false;
	}
	
	public boolean hitLeftPaddle() //do
	{
		return true;
	}
	
	public boolean hitRightPaddle() //do wait i didnt even need this method
	{
		return true;
	}	
}