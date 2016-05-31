import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.FontUtils;
import org.newdawn.slick.Image;

import java.util.Scanner;
import java.util.Set;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class LocalMap extends BasicGameState
{

	ArrayList<Map> maps;
	
	TrueTypeFont crux;
	
	Map currentMap;
	GameContainer gc;
	boolean interacting = true;
<<<<<<< HEAD
	String introText = null;
	boolean intro = true;

	
	//temporary static player positions

	Player p1;
	
	//Return 2d array of the correct size for tile.
	
	public void changeMap(int mapnum)
	{
		tiles = new Tile[20][20];
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
		int maxCol = 0;
		while(x.hasNext())
		{
			col++;
			if(x.next().equals("k"))
			{
				row++;
				col--;
				if(col > maxCol)
				{
					maxCol = col;
				}
				col = 0;
			}
		}
		System.out.println("row: " + row + " col: " + maxCol);
		
		tiles = new Tile[row][maxCol];
		
		
		try
		{
			x.close();
			x = new Scanner(stage);
			x.nextLine();
		}
		catch(Exception e)
		{
			//System.out.println(e);
		}
		
		
		row = 0;
		col = 0;
		
		String current = "k";
		while(!current.equals("j"))
		{
			current = x.next();
			
			//TODO: make map generation draw nothing on null.
			if(current.equals("x"))
			{
				tiles[row][col] = null;
			}
			else if(!current.equals("k") && !current.equals("j"))
			{
				//System.out.println(current);
				String path = "src/txtr/";
				path += Integer.parseInt(current) + ".png";
				//tiles[row][col] = new Tile(path,true);
				
				
				//Generalize further, implement to file.
				/*if(Integer.parseInt(current) == 0 || Integer.parseInt(current) == 2 || Integer.parseInt(current) == 3)
				{
					tiles[row][col].properties[0] = false;
					tiles[row][col].interactable = true;
					if(Integer.parseInt(current) == 0)
					{
						tiles[row][col].interact = "You seem to be able to interact with this block...\nWhat a well thought out proof of concept!";
					}
				}*/
				
				
				Scanner tileScanner = null;
				try
				{
					tileScanner = new Scanner(new File("src/data/tile" + current + ".txt"));
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
				/*String spritePath = tileScanner.next();
				String walk = tileScanner.nextBoolean();
				String interact = "";
				String current
				while()
				*/
				tiles[row][col] = new Tile(tileScanner.next(), tileScanner.nextBoolean(), tileScanner.next());
				//System.out.println("Tile: " + tiles[row][col].name + " " + tiles[row][col].interact);
				col++;
			}
			else if(current.equals("j"))
			{
				row--;
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
		changeMap(0);
		
		
		
	}
//<<<<<<< HEAD
    
	
	//FOR PLAYER: Render player, use arrow key to add 30 to player position, lock input until they move to tile.
//=======
//>>>>>>> origin/master
=======
	public String introText = null;
	public boolean intro = true;

	Player p1;
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		//Store the gamecontainer for use and create the player object
		gc = arg0;
		p1 = new Player(0,9,18);
		//crux = new Font("Coder's Crux", Font.PLAIN,24);
		crux = new TrueTypeFont(new Font("Coder's Crux", Font.PLAIN,24), false);
		//read the amount of files in folder of maps, create array of maps
		
		maps = new ArrayList<Map>();
		try
		{	
			int g = 0;
			while(true)
			{
				Map x = new Map(this);
				
				if(!x.loadMap(g))
				{
					break;
				}
				
				maps.add(x);
				
				g++;
				System.out.println(g);
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		changeMap(0);

		if(introText.equals(""))
		{
			intro = false;
		}
		
	}
	
	//Changes the map to the filenumber denoted by id
	public void changeMap(int id)
	{
		System.out.println(maps);
		currentMap = maps.get(id);
		currentMap.loadMap(id);
	}
>>>>>>> Jake
	
	//Renders the graphics and does basic calculations on where the player is standing, interactions, etc.
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException
	{
		
		arg2.setFont(crux);
		
		int xloc = 100;
		int yloc = 0;
		
		for(Tile[] x : currentMap.tiles)
		{
			for(Tile y: x)
			{
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
			p1.draw(currentMap.tiles[p1.x][p1.y]);
		}
		else
		{
			
			if(p1.direction == 3)
			{
				p1.sprite.draw(currentMap.tiles[p1.x][p1.y].cornerX-(float)p1.between , currentMap.tiles[p1.x][p1.y].cornerY);
			}
			else if(p1.direction == 1)
			{
				p1.sprite.draw(currentMap.tiles[p1.x][p1.y].cornerX+(float)p1.between , currentMap.tiles[p1.x][p1.y].cornerY);
			}
			else if(p1.direction == 2)
			{
				p1.sprite.draw(currentMap.tiles[p1.x][p1.y].cornerX, currentMap.tiles[p1.x][p1.y].cornerY+(float)p1.between);
			}
			else if(p1.direction == 0)
			{
				p1.sprite.draw(currentMap.tiles[p1.x][p1.y].cornerX, currentMap.tiles[p1.x][p1.y].cornerY-(float)p1.between);
			}
				
			p1.between+=1.5;
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
					
					interacted = currentMap.tiles[p1.x-1][p1.y];
					break;
				case 1:
					interacted = currentMap.tiles[p1.x][p1.y+1];
					break;
				case 2:
					interacted = currentMap.tiles[p1.x+1][p1.y];
					break;
				case 3:
					interacted = currentMap.tiles[p1.x][p1.y-1];
					break;
			}
			String dialogue = interacted.interact;
<<<<<<< HEAD
			System.out.println(dialogue);
=======

>>>>>>> Jake
			if(!dialogue.equals("null"))
			{
				arg2.drawString(dialogue, 230, 10);
			}
			else if(dialogue.equals("null") && !intro)
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
<<<<<<< HEAD
				if(tiles[p1.x-1][p1.y].properties[0])
=======
				if(currentMap.tiles[p1.x-1][p1.y].walkable)
>>>>>>> Jake
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
<<<<<<< HEAD
				if(tiles[p1.x+1][p1.y].properties[0])
=======
				if(currentMap.tiles[p1.x+1][p1.y].walkable)
>>>>>>> Jake
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
<<<<<<< HEAD
				if(tiles[p1.x][p1.y-1].properties[0])
=======
				if(currentMap.tiles[p1.x][p1.y-1].walkable)
>>>>>>> Jake
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
<<<<<<< HEAD
				if(tiles[p1.x][p1.y+1].properties[0])
=======
				if(currentMap.tiles[p1.x][p1.y+1].walkable)
>>>>>>> Jake
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
	
	
	public void showDialogue(String dia)
	{
		
	}
	
}
