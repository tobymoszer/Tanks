package tanks.enemies.moving;

import main.Level;

import java.awt.*;

/**
 * A class representing a GrayTank
 */
public class GrayTank extends MovingTank {

    /**
     * Constructs a new GrayTank,
     * Sets the moveSpeed, AimSpeed, and fireRate
     * @param x the x position of the GrayTank
     * @param y the y position of the GrayTank
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the GrayTank is in
     */
    public GrayTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, levelSizeX, levelSizeY, delay, level);
        color = new Color(120, 111, 100);
        moveSpeed = .002;
        aimSpeed = .01 / 16;
        fireRate = 100000./delay;
        checkWillHitPlayerFrequency = .1;
    }

    /**
     * Updates the GrayTank,
     * Aims near the Player
     */
    @Override
    public void update() {
        super.update();

        aimNearPlayer(Math.PI);
    }

    /**
     * Shoots a bullet,
     * Max bullets = 5
     */
    @Override
    protected void shoot() {
        shootBullet(5);
    }

    /**
     * @return "GrayTank"
     */
    @Override
    public String toString() {
        return "GrayTank";
    }
}
