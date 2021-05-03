package tanks.enemies.moving;

import main.Level;

import java.awt.*;

/**
 * A class that represents a WhiteTank
 */
public class WhiteTank extends BombLayingTank{


    /**
     * Constructs a White Tank
     * Sets moveSpeed, aimSpeed, fireRate, bombRate and lastBomb
     * @param x the x position of the WhiteTank
     * @param y the y position of the WhiteTank
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the WhiteTank is in
     */
    public WhiteTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, levelSizeX, levelSizeY, delay, level);
        color = new Color(236,221,180);
        moveSpeed = .0025;
        aimSpeed = .07 / 16;
        fireRate = 7000./delay;
        bombRate = 70000./delay;
        bombCount = 2;
        lastBomb = System.currentTimeMillis() - (int) (Math.random() * bombRate/2);
        checkWillHitPlayerFrequency = .4;
        projectileBounces = 1;
    }

    /**
     * Update the WhiteTank,
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
     * @return "WhiteTank"
     */
    @Override
    public String toString() {
        return "WhiteTank";
    }
}
