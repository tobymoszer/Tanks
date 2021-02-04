package tanks.weapons.projectiles;

import main.Level;

import java.awt.*;

/**
 * A class representing a PhantomBullet
 * This is used to simulate shots for EnemyTanks
 */
public class PhantomBullet extends Projectile {

	/**
	 * Constructs a new PhantomBullet,
	 * Sets speed
	 * @param x the x coordinate of the PhantomBullet (top left)
	 * @param y the y coordinate of the PhantomBullet (top left)
	 * @param direction the direction the PhantomBullet is moving in
	 * @param levelSizeX the x size of the Level
	 * @param levelSizeY the y size of the Level
	 * @param delay the delay between frames
	 * @param bounces the number of bounces   
	 * @param level the Level that the PhantomBullet is in
	 */
	public PhantomBullet(double x, double y, double direction, int levelSizeX, int levelSizeY, int delay, int bounces, Level level) {
		super(x, y, direction, levelSizeX, levelSizeY, delay, level);
		speed = .008;
		this.bounces = bounces;
	}

	/**
	 * Paints the PhantomBullet
	 * This never gets called
	 * @param g the Graphics to paint to
	 */
	@Override
	public void paint(Graphics g) {

	}
}
