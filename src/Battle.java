import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
public class Battle extends BasicGameState
{
	GameContainer gc;
	StateBasedGame game;
	Image player;
	Image opponent;
	Image fight;
	Image fight1;
	Image item;
	Image item1;
	Image escape;
	Image escape1;
	Input inp;
	PlayerBattle ply;
	Enemy ene;
	boolean isPlayerTurn;
	
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException
	{
		gc = arg0;
		game = arg1;
		isPlayerTurn = true;
		fight = new Image("src/txtr/fight.jpg");
		fight1 = new Image("src/txtr/fightactive.png");
		item = new Image("src/txtr/item.jpg");
		item1 = new Image("src/txtr/itemactive.png");
		escape = new Image("src/txtr/escape.jpg");
		escape1 = new Image("src/txtr/escapeactive.png");
		ply = new PlayerBattle();
		//ene = new Enemy();
	}

	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2) throws SlickException
	{
		arg2.drawRect(50, 400, 700, 150);
		//TODO: Fight, Item, Flee buttons.
		fight.draw(100, 400);
		item.draw(300, 400);
		escape.draw(500, 400);
		
	}

	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException
	{
		inp = gc.getInput();
		//if the mouse is at the fight button and pressed, fight
		if ((inp.getAbsoluteMouseX() > 99 && inp.getAbsoluteMouseX() < 261) && (inp.getAbsoluteMouseY() > 461 && inp.getAbsoluteMouseY() < 399) && inp.isMousePressed(0) && isPlayerTurn)
		{
			ply.attack(ene);
		}
		/*else if (mouse is at item button, is pressed, is player turn)
		{
			//playerBattle.useItem();
		}
		else if (mouse is at flee button, is pressed, is player turn)
		{
			//playerBattle.flee();
		}
		else if (is not player turn)
		{
			enemy.attack();
		}
		*/
		
	}

	public int getID()
	{
		return 2;
	}
	
}
