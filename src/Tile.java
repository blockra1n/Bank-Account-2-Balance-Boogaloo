import org.newdawn.slick.Image;
public class Tile
{
	public Image sprite;
	public int xsize = 30;
	public int ysize = 30;
	public String name;
	public int cornerY = 0;
	public int cornerX = 0;
	double chn;
	public String interact = null;
	public boolean interactable = false;
	

	public boolean[] properties;
	

	public Tile(String x, boolean w, String y)
	{
		properties = new boolean[5];
		properties[0] = w;
		
		name = x;
		interact = y;

		try
		{
			//System.out.println(x);
			sprite = new Image(x);
		}
		catch(Exception e)
		{
			System.out.println("Image " + name + " not found!");
		}
	}
	

}
