package tanks.enemies.moving;

import main.Level;

import java.awt.*;

/**
 * A class representing a YellowTank
 */
public class YellowTank extends BombLayingTank {

    /**
     * Constructs a YellowTank,
     * Sets color, moveSpeed, aimSpeed, fireRate, bombRate, and lastBomb
     * @param x the x position of the YellowTank
     * @param y the y position of the YellowTank
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the YellowTank is in
     */
    public YellowTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, levelSizeX, levelSizeY, delay, level);
        color = new Color(226, 198, 66);
        moveSpeed = .0025;
        aimSpeed = .05 / 16;
        fireRate = 20000./delay;
        bombRate = 50000./delay;
        bombCount = 4;
        lastBomb = System.currentTimeMillis() - (int) (Math.random() * bombRate/2);
        checkWillHitPlayerFrequency = .2;
        projectileBounces = 1;
    }

    /**
     * Updates the YellowTank,
     * Aims near the Player
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
     * @return "YellowTank"
     */
    @Override
    public String toString() {
        return "YellowTank";
    }
}
