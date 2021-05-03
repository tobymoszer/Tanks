package tanks.enemies.moving;

import main.Level;

import java.awt.*;

/**
 * A class that represents a PurpleTank
 */
public class PurpleTank extends BombLayingTank {

    /**
     * Constructs a PurpleTank,
     * Sets moveSpeed, aimSpeed, fireRate, bombRate, and lastBomb
     * @param x the x position of the PurpleTank
     * @param y the y position of the PurpleTank
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the PurpleTank is in
     */
    public PurpleTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, levelSizeX, levelSizeY, delay, level);
        color = new Color(130, 90, 124);
        moveSpeed = .004;
        aimSpeed = .07 / 16;
        fireRate = 7000./delay;
        bombRate = 70000./delay;
        lastBomb = System.currentTimeMillis() - (int) (Math.random() * bombRate/2);
        checkWillHitPlayerFrequency = .4;
        projectileBounces = 1;
    }

    /**
     * Update the PurpleTank,
     * Aim near the player
     */
    @Override
    public void update() {
        super.update();

        aimNearPlayer(Math.PI);
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
     * @return "PurpleTank"
     */
    @Override
    public String toString() {
        return "PurpleTank";
    }
}
