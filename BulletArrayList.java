import java.util.*;

public class BulletArrayList
{
	private ArrayList<Bullet> bulletList;
	
	public BulletArrayList()
	{
		bulletList=new ArrayList<Bullet>();
	}
	
	public ArrayList<Bullet> getItems()
	{
		return bulletList;
	}
	
	public void update()
	{
		for(int x=0; x<bulletList.size(); x++)
		{
			bulletList.get(x).update();
			if(bulletList.get(x).getX()>650 || bulletList.get(x).getX()<0-Bullet.WIDTH)
			{
				bulletList.remove(x);
				x--;
			}
		}
	}
}