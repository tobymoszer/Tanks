package tanks.enemies.stationary;

import main.Level;

import java.awt.*;

/**
 * A class representing a StationaryGreenTank
 */
public class StationaryGreenTank extends StationaryTank {

    /**
     * Constructs a new StationaryGreenTank
     * Sets the fireRate, color, and aimSpeed
     * Sets lastFired to the current time
     * @param x the x position of the StationaryGreenTank
     * @param y the y position of the StationaryGreenTank
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the StationaryGreenTank is in
     */
    public StationaryGreenTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, levelSizeX, levelSizeY, delay, level);
        fireRate = 1000./delay;
        lastFired = System.currentTimeMillis();
        color = new Color(80, 137, 70);
        aimSpeed = .05 / 16;
        projectileBounces = 2;
    }

    /**
     * Shoot a DoubleBounceMissile
     * Max DoubleBounceMissiles = 1
     */
    @Override
    protected void shoot() {
        shootDoubleBounceMissile(1);
    }

    /**
     * @return "StationaryGreenTank"
     */
    @Override
    public String toString() {
        return "StationaryGreenTank";
    }
}
