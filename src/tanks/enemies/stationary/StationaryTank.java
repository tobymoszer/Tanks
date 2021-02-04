package tanks.enemies.stationary;

import main.Level;
import tanks.enemies.EnemyTank;

/**
 * An abstract class representing a Stationary Tank
 */
public abstract class StationaryTank extends EnemyTank {

    /**
     * Constructs a new StationaryTank
     * @param x the x position of the StationaryTank
     * @param y the y position of the StationaryTank
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the StationaryTank is in
     */
    StationaryTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, levelSizeX, levelSizeY, delay, level);
    }

    /**
     * Updates the StationaryTank
     * Randomly changes the direction the aim of the StationaryTank is changing in
     * Shoots if it would hit the Player
     */
    @Override
    public void update() {

        if (Math.random() < .02) {
            aimSpeed *= -1;
        }

        aim += aimSpeed * delay;

        if (System.currentTimeMillis() > lastFired + fireRate && willHitPlayer(1)) {
            shoot();
            lastFired = System.currentTimeMillis();
        }
    }

}
