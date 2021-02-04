package tanks.weapons.projectiles;

import main.Level;

/**
 * A class representing a Missile that bounces twice
 */
public class DoubleBounceMissile extends Missile {

    /**
     * Constructs a new DoubleBounceMissile,
     * Sets speed and bounces
     * @param x the x coordinate of the DoubleBounceMissile (top left)
     * @param y the y coordinate of the DoubleBounceMissile (top left)
     * @param direction the direction the DoubleBounceMissile is moving in
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the DoubleBounceMissile is in
     */
    public DoubleBounceMissile(double x, double y, double direction, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, direction, levelSizeX, levelSizeY, delay, level);
        bounces = 2;
        trailsPerFrame = 10;
    }
}
