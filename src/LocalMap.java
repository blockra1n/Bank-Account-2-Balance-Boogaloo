import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class LocalMap extends BasicGameState
{
	
	Tile[][] tiles;
	//boolean moving;
	GameContainer gc;
	boolean interacting = true;
	String introText = null;
	boolean intro = true;
	
	//temporary static player positions

	Player p1;
	
	public void changeMap(int mapnum)
	{
		File stage = new File("src/maps/" + mapnum + ".txt");
		Scanner x = null;
		try 
		{
			x = new Scanner(stage);
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		introText = x.nextLine();
		intro = true;
		interacting = true;
		
		int row = 0;
		int col = 0;
		
		while(x.hasNext())
		{
			String current = x.next();
			if(!current.equals("k"))
			{
				//System.out.println(current);
				String path = "src/txtr/";
				path += Integer.parseInt(current) + ".png";
				tiles[row][col] = new Tile(path,true);
				if(Integer.parseInt(current) == 0 || Integer.parseInt(current) == 2 || Integer.parseInt(current) == 3)
				{
					tiles[row][col].walkable = false;
					tiles[row][col].interactable = true;
					if(Integer.parseInt(current) == 0)
					{
						tiles[row][col].interact = "You seem to be able to interact with this block...\nWhat a well thought out proof of concept!";
					}
				}
				col++;
			}
			else
			{
				col = 0;
				row++;
			}
		}
		
		x.close();
	}
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		gc = arg0;
		p1 = new Player(0,9,18);
		tiles = new Tile[20][20];
		changeMap(0);

		
		
	}

	
	//FOR PLAYER: Render player, use arrow key to add 30 to player position, lock input until they move to tile.
	
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException
	{
		int xloc = 100;
		int yloc = 0;
		
		//tiles[0][1].sprite.draw(0,0);
		
		for(Tile[] x : tiles)
		{
			for(Tile y: x)
			{
				//System.out.println(y.name);
				y.sprite.draw(xloc,yloc);
				y.cornerX = xloc;
				y.cornerY = yloc;
				xloc+=y.xsize;
			}
			xloc=100;
			yloc+=x[0].ysize;
		}
		
		if(!p1.moving)
		{
			p1.draw(tiles[p1.x][p1.y]);
		}
		else
		{
			
			if(p1.direction == 3)
			{
				p1.sprite.draw(tiles[p1.x][p1.y].cornerX-(float)p1.between , tiles[p1.x][p1.y].cornerY);
			}
			else if(p1.direction == 1)
			{
				p1.sprite.draw(tiles[p1.x][p1.y].cornerX+(float)p1.between , tiles[p1.x][p1.y].cornerY);
			}
			else if(p1.direction == 2)
			{
				p1.sprite.draw(tiles[p1.x][p1.y].cornerX, tiles[p1.x][p1.y].cornerY+(float)p1.between);
			}
			else if(p1.direction == 0)
			{
				p1.sprite.draw(tiles[p1.x][p1.y].cornerX, tiles[p1.x][p1.y].cornerY-(float)p1.between);
			}
				
			p1.between+=.2;
			if(p1.between >= 0.0)
			{
				p1.between = -30;
				p1.moving = false;
			}

		}
		
		
		if(interacting && !intro)
		{
			Tile interacted = null;
			switch(p1.direction)
			{
				case 0:
					
					interacted = tiles[p1.x-1][p1.y];
					break;
				case 1:
					interacted = tiles[p1.x][p1.y+1];
					break;
				case 2:
					interacted = tiles[p1.x+1][p1.y];
					break;
				case 3:
					interacted = tiles[p1.x][p1.y-1];
					break;
			}
			String dialogue = interacted.interact;
			if(interacted.name.equals("src/txtr/2.png"))
			{
				changeMap(1);
			}
			if(dialogue != null)
			{
				System.out.println("ayy");
				arg2.drawString(dialogue, 230, 10);
			}
			else if(dialogue == null && !intro)
			{
				interacting = !interacting;
			}
			
		}
		
		
		else if(intro)
		{
			arg2.drawString(introText, 230, 10);
		}
		
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException
	{
		
	}
	
	public void keyPressed(int key, char c)
	{
		if(key == 1)
		{
			gc.exit();
		}
		System.out.println(key);
		/*Up: 200
		 *Down:208
		 * Left:203
		 * Right:205
		 */
		//TODO: Interactable objects.
		
		if(key == 44)
		{
			interacting = !interacting;
			intro = false;
		}
		
		if(!interacting && !p1.moving)
		{
		
			switch(key)
			{
			//up
			case 200:
				
				p1.direction = 0;
				p1.sprite = p1.upSprite;
				if(tiles[p1.x-1][p1.y].walkable)
				{
					p1.x--;
					p1.between = -30;
					p1.moving = true;
				}

				break;
				
			//down
			case 208:
				
				
				p1.sprite = p1.downSprite;
				p1.direction = 2;
				if(tiles[p1.x+1][p1.y].walkable)
				{
					p1.x++;
					p1.between = -30;
					p1.moving = true;
				}
				break;
				
			//left
			case 203:
				
				p1.direction = 3;
				p1.sprite = p1.leftSprite;
				if(tiles[p1.x][p1.y-1].walkable)
				{
					p1.moving = true;
					p1.between = -30;
					p1.y--;
				}
				break;
				
			//right
			case 205:
				
				p1.direction = 1;
				p1.sprite = p1.rightSprite;
				if(tiles[p1.x][p1.y+1].walkable)
				{
					p1.y++;
					p1.between = -30;
					p1.moving = true;
				}
				break;
				

			}
		}
		
		
	}

	public int getID()
	{
		return 1;
	}
	
}
