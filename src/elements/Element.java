package elements;

import java.awt.*;

/**
 * An abstract class representing an Element in the level
 */
public abstract class Element implements Comparable<Element> {

	/**
	 * The position of the element
	 */
	public double x, y;
	/**
	 * The size of the level
	 */
	protected int levelSizeX, levelSizeY;

	/**
	 * Constructs a new Element
	 * @param x the x position of the element (top left)
	 * @param y the y position of the element (top left)
	 * @param levelSizeX the x size of the level
	 * @param levelSizeY the y size of the level
	 */
	protected Element(double x, double y, int levelSizeX, int levelSizeY) {
		this.x = x;
		this.y = y;
		this.levelSizeX = levelSizeX;
		this.levelSizeY = levelSizeY;
	}

	/**
	 * Paints the element
	 * @param g the Graphics to paint to
	 */
	public abstract void paint(Graphics g);

	/**
	 * Overrides the compareTo method of Object
	 * @param other the other element to compareTo
	 * @return negative if this has a lower y value than other,
	 * positive if this has a higher y value
	 * If the y values are equal, compares x values,
	 * negative if this has a lower x value than other,
	 * positive if this has a higher x value
	 */
	@Override
	public int compareTo(Element other) {
		return Double.compare(y * 24 + x, other.y * 24 + other.x);
	}
}
