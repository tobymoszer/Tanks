package tanks.weapons.projectiles;

import effects.projectiles.BulletTrail;
import main.Level;
import main.Main;

import java.awt.*;

/**
 * A class that represents a Bullet
 */
public class Bullet extends Projectile {
	
	/**
	 * the number of trails added every frame
	 */
	private int trailsPerFrame;

	/**
	 * Constructs a Bullet,
	 * Sets the speed, bounces, and color
	 * @param x the x coordinate of the Bullet (top left)
	 * @param y the y coordinate of the Bullet (top left)
	 * @param direction the direction the Bullet is moving in
	 * @param levelSizeX the x size of the Level
	 * @param levelSizeY the y size of the Level
	 * @param delay the delay between frames
	 * @param level the Level that the Bullet is in
	 */
	public Bullet(double x, double y, double direction, int levelSizeX, int levelSizeY, int delay, Level level) {
		super(x, y, direction, levelSizeX, levelSizeY, delay, level);
		speed = .008;
		bounces = 1;
		trailsPerFrame = 3;
		color = new Color(250, 241, 187);
	}

	/**
	 * Updates the Bullet
	 * Adds and removes Effects of the Bullet
	 * @return true if the Bullet has bounced all of its bounces
	 */
	@Override
	public boolean update() {

		for (int i = 0; i < trailsPerFrame; i++) {
            effects.add(new BulletTrail(getCenterX() + (Math.random() - .5) * size, getCenterY() + (Math.random() - .5) * size));
        }

		return super.update();
	}

	/**
	 * Paints the Bullet
	 * @param g the Graphics to paint to
	 */
	@Override
	public void paint(Graphics g) {

		g.setColor(color.darker().darker().darker());
		g.fillOval((int)(x * Main.scale + Main.scale / 32), (int)(y * Main.scale + Main.scale / 8), (int)(size * Main.scale), (int)(size * Main.scale));

		g.setColor(color);
		g.fillOval((int)(x * Main.scale), (int)(y * Main.scale), (int)(size * Main.scale), (int)(size * Main.scale));
		
	}
}
