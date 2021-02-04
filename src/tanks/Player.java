package tanks;

import main.Level;
import tanks.Tank;

import java.awt.*;

/**
 * A class representing a Player
 * Extends Tank
 */
public class Player extends Tank {

	/**
	 * Constructs a new Player
	 * Sets the color and moveSpeed
	 * @param x the x position of the Player
	 * @param y the y position of the Player
	 * @param levelSizeX the x size of the Level
	 * @param levelSizeY the y size of the Level
	 * @param delay the delay between Frames
	 * @param level the Level that the Player is in
	 */
	public Player(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
		super(x, y, levelSizeX, levelSizeY, delay, level);
		color = new Color(80, 120, 200);
		moveSpeed = .005;
	}

	/**
	 * Shoots a bullet
	 * Max bullets = 5
	 */
	public void shoot() {
		super.shootBullet(5);
	}

	/**
	 * Lays a bomb
	 * Max bombs = 3
	 */
	public void layBomb() {
		super.layBomb(3);
	}

	/**
	 * @return "Player"
	 */
	@Override
	public String toString() {
		return "Player";
	}
	
}
