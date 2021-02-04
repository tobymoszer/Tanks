package tanks.weapons;

import main.Main;
import tanks.pathfinding.Node;
import tools.Formulas;

import java.awt.*;

/**
 * A class that represents a Bomb
 */
public class Bomb {

	/**
	 * The x position of the Bomb
	 */
	private double x;
	/**
	 * The y position of the Bomb
	 */
	private double y;
	/**
	 * The size of the Bomb
	 */
	private double size = .5;
	/**
	 * The explosion radius of the Bomb
	 */
	private double explosionRadius = 3;
	/**
	 * The time it takes for the Bomb to explode
	 */
	private long timer = 5000;
	/**
	 * The time the Bomb was created
	 */
	private long birthTime;
	/**
	 * The speed the Bomb flickers
	 */
	private long flickerSpeed = 100;
	/**
	 * The time the last flicker occurred
	 */
	private long lastFlicker;
	/**
	 * The color of the Bomb
	 */
	private Color color;

	/**
	 * Constructs a new Bomb,
	 * Sets color, birthTime, and lastFlicker
	 * @param x the x coordinate of the Bomb (top left)
	 * @param y the y coordinate of the Bomb (top left)
	 */
	public Bomb(double x, double y) {
		this.x = x;
		this.y = y;
		color = new Color(218, 218, 57);
		birthTime = System.currentTimeMillis();
		lastFlicker = System.currentTimeMillis();
	}

	/**
	 * Updates the Bomb
	 * Handles flickering
	 * @return true if it is time for the Bomb to explode
	 */
	public boolean update() {
		if ((birthTime + timer) - System.currentTimeMillis() < 1000) {
			flicker();
		}
		
		return System.currentTimeMillis() > birthTime + timer;
	}

	/**
	 * Makes the Bomb flicker if it should
	 */
	private void flicker() {
		if (lastFlicker + flickerSpeed < System.currentTimeMillis()) {
			if (color.equals(new Color(218, 218, 57).darker())) {
				color = new Color(218, 218, 57);
			} else {
				color = new Color(218, 218, 57).darker();
			}
			lastFlicker = System.currentTimeMillis();
		}
	}

	/**
	 * Gets the x coordinate of the Bomb
	 * @return the x coordinate of the Bomb
	 */
	public double getX() {
		return x;
	}

	/**
	 * Gets the y coordinate of the Bomb
	 * @return the y coordinate of the Bomb
	 */
	public double getY() {
		return y;
	}

	/**
	 * Gets the size of the Bomb
	 * @return the size of the Bomb
	 */
	public double getSize() {
		return size;
	}

	/**
	 * Gets the explosion radius of the Bomb
	 * @return the explosion radius of the Bomb
	 */
	public double getExplosionRadius() {
		return explosionRadius;
	}

	/**
	 * Determines if a given Node is within the explosion radius of the Bomb
	 * @param node the Node to be tested
	 * @return true if the Node is within the explosion radius
	 */
	public boolean withinExplosionRadius(Node node) {
		return Formulas.distance(x, node.getX(), y, node.getY()) + 1 < explosionRadius;
	}

	/**
	 * Paints the Bomb
	 * @param g the Graphics to paint to
	 */
	public void paint(Graphics g) {
		g.setColor(color.darker());
		g.fillOval((int)(Main.scale * (x - size/2)),(int)(Main.scale * (y - size/2)), (int)(size * Main.scale), (int)(size * Main.scale));
		g.setColor(color);
		g.fillOval((int)(Main.scale * (x - size/2)),(int)(Main.scale * (y - size/2) - Main.scale / 16), (int)(size * Main.scale), (int)(size * Main.scale));
	}
	
}
