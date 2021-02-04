package tanks.enemies.moving;

import main.Level;

import java.awt.*;

/**
 * A Class representing a GreenTank
 */
public class GreenTank extends MovingTank {

    /**
     * Constructs a GreenTank,
     * Sets the moveSpeed, aimSpeed, and fireRate
     * @param x the x position of the GreenTank
     * @param y the y position of the GreenTank
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the GreenTank is in
     */
    public GreenTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, levelSizeX, levelSizeY, delay, level);
        color = new Color(66, 130, 121);
        moveSpeed = .002;
        aimSpeed = .05 / 16;
        fireRate = 10000./delay;
        checkWillHitPlayerFrequency = .2;
    }

    /**
     * Update the GreenTank,
     * Aim near the player
     */
    @Override
    public void update() {
        super.update();

        aimNearPlayer(Math.PI / 16);

    }

    /**
     * Shoot a Missile,
     * Max missiles = 1
     */
    @Override
    protected void shoot() {
        shootMissile(1);
    }

    /**
     * @return "GreenTank"
     */
    @Override
    public String toString() {
        return "GreenTank";
    }
}
