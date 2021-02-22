package tanks.enemies.moving;

import main.Level;

import java.awt.*;

/**
 * A class representing a RedTank
 */
public class RedTank extends MovingTank {

    /**
     * Constructs a RedTank,
     * Sets color, moveSpeed, aimSpeed, and fireRate
     * @param x the x position of the RedTank
     * @param y the y position of the RedTank
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the RedTank is in
     */
    public RedTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, levelSizeX, levelSizeY, delay, level);
        color = new Color(207, 93, 109);
        moveSpeed = .003;
        aimSpeed = .05 / 16;
        fireRate = 7000./delay;
        checkWillHitPlayerFrequency = .2;
        projectileBounces = 1;
    }

    /**
     * Updates the RedTank
     * Aims near the player
     */
    @Override
    public void update() {
        super.update();

        aimNearPlayer(Math.PI / 2);
    }

    /**
     * Shoots a Bullet,
     * Max bullets = 5
     */
    @Override
    protected void shoot() {
        shootBullet(5);
    }

    /**
     * @return "RedTank"
     */
    @Override
    public String toString() {
        return "RedTank";
    }
}
