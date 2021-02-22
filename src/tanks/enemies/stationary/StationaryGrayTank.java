package tanks.enemies.stationary;

import main.Level;

import java.awt.*;

/**
 * A Class representing a StationaryGrayTank
 */
public class StationaryGrayTank extends StationaryTank {

	/**
	 * Constructs a new StationaryGrayTank
	 * Sets the fireRate, color, and aimSpeed
	 * Sets lastFired to the current time
	 * @param x the x position of the StationaryGrayTank
	 * @param y the y position of the StationaryGrayTank
	 * @param levelSizeX the x size of the Level
	 * @param levelSizeY the y size of the Level
	 * @param delay the delay between frames
	 * @param level the Level that the StationaryGrayTank is in
	 */
	public StationaryGrayTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
		super(x, y, levelSizeX, levelSizeY, delay, level);
		fireRate = 100000./delay;
		lastFired = System.currentTimeMillis();
		color = new Color(160, 122, 65);
		aimSpeed = .02 / 16;
		projectileBounces = 1;
	}

	/**
	 * Shoots a bullet
	 * Max bullets = 5
	 */
	@Override
	protected void shoot() {
		shootBullet(5);
	}

	/**
	 * @return "StationaryGrayTank"
	 */
	@Override
	public String toString() {
		return "StationaryGrayTank";
	}
}
