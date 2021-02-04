package elements;

import effects.walls.WallFragment;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class representing a BreakableWall wall
 */
public class BreakableWall extends Wall {

	/**
	 * How many WallFragments are created when exploded
	 */
	private int wallFragmentCount = 50;

	/**
	 * Constructs a new BreakableWall
	 * Sets the color of the BreakableWall
	 * @param x the x position of the BreakableWall (top left)
	 * @param y the y position of the BreakableWall (top left)
	 * @param levelSizeX the x size of the level
	 * @param levelSizeY the y size of the level
	 */
	public BreakableWall(double x, double y, int levelSizeX, int levelSizeY) {
		super(x, y, levelSizeX, levelSizeY);
		color = new Color(223, 175, 135);
	}

	/**
	 * Creates an ArrayList of WallFragments
	 * @return an ArrayList of WallFragments
	 */
	public ArrayList<WallFragment> getWallFragments() {

		ArrayList<WallFragment> fragments = new ArrayList<>(wallFragmentCount);
		for (int i = 0; i < wallFragmentCount; i++) {
			fragments.add(new WallFragment(getCenterX() + Math.random() - .5, getCenterY() + Math.random() - .5));
		}

		return fragments;
	}

	/**
	 * @return "BreakableWall"
	 */
	@Override
	public String toString() {
		return "BreakableWall";
	}
}
