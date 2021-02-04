package elements;

import main.Main;

import java.awt.*;

/**
 * A class that represents a Wall Element
 */
public class Wall extends Element {

	/**
	 * The color of the Wall
	 */
	Color color;

	/**
	 * The front of the wall
	 */
	Polygon front;

	/**
	 * The right side of the wall
	 */
	Polygon right;

	/**
	 * Constructs a new Wall
	 * Sets the color of the Wall
	 * Sets up the polygons for front and right
	 * @param x the x position of the wall (top left)
	 * @param y the y position of the wall (top left)
	 * @param levelSizeX the x size of the level
	 * @param levelSizeY the y size of the level
	 */
	public Wall(double x, double y, int levelSizeX, int levelSizeY) {
		super(x, y, levelSizeX, levelSizeY);
		color = new Color(128, 105, 72);
		setupPolygons();
	}

	/**
	 * Creates the polygons for the front and right of the wall
	 */
	private void setupPolygons() {

		double offset = 8;

		front = new Polygon();
		front.addPoint((int)(x * Main.scale), (int) (y * Main.scale + Main.scale));
		front.addPoint((int)(x * Main.scale + Main.scale), (int) (y * Main.scale + Main.scale));
		front.addPoint((int)(x * Main.scale + Main.scale - Main.scale / offset), (int) (y * Main.scale + Main.scale / 2));
		front.addPoint((int)(x * Main.scale - Main.scale / offset), (int) (y * Main.scale + Main.scale / 2));

		right = new Polygon();
		right.addPoint((int)(x * Main.scale + Main.scale), (int) (y * Main.scale + Main.scale));
		right.addPoint((int)(x * Main.scale + Main.scale - Main.scale / offset), (int) (y * Main.scale + Main.scale / 2));
		right.addPoint((int)(x * Main.scale + Main.scale - Main.scale / offset), (int) (y * Main.scale - Main.scale / 2));
		right.addPoint((int)(x * Main.scale + Main.scale), (int) (y * Main.scale));
	}

	/**
	 * Method that returns if this wall contains a point
	 * @param x the x value of the point to be tested
	 * @param y the y value of the point to be tested
	 * @return if the wall contains the point
	 */
	public boolean contains(double x, double y) {
		return x > this.x && x < this.x + 1 && y > this.y && y < this.y + 1;
	}

	/**
	 * @return the center x position of the wall
	 */
	public double getCenterX() {
		return x + .5;
	}

	/**
	 * @return the center y position of the wall
	 */
	public double getCenterY() {
		return y + .5;
	}

	/**
	 * Paints the front and right side of the wall
	 * @param g the Graphics to paint to
	 */
	@Override
	public void paint(Graphics g) {

		g.setColor(color.darker());
		g.fillPolygon(front);

		g.setColor(color.darker().darker());
		g.fillPolygon(right);
	}

	/**
	 * Paints the top of the wall
	 * @param g the Graphics to paint to
	 */
	public void paintTop(Graphics g) {

		double offset = 8;

		g.setColor(color);
		g.fillRect((int)(x * Main.scale - Main.scale / offset), (int)y * Main.scale - Main.scale/2, Main.scale, Main.scale);
	}

	/**
	 * @return "Wall"
	 */
	@Override
	public String toString() {
		return "Wall";
	}
}
