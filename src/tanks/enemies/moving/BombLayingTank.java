package tanks.enemies.moving;

import main.Level;

/**
 * An abstract class representing a MovableTank that can lay bombs
 */
public abstract class BombLayingTank extends MovingTank {

    /**
     * The rate at which bombs can be laid
     */
    protected double bombRate;
    /**
     * The time when the last bomb was laid
     */
    protected long lastBomb;

    /**
     * Constructs a new BombLayingTank
     * @param x the x position of the BombLayingTank
     * @param y the y position of the BombLayingTank
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the BombLayingTank is in
     */
    BombLayingTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, levelSizeX, levelSizeY, delay, level);
    }

    /**
     * Updates the BombLayingTank
     * If enough time has elapsed since the last bomb was laid, there is a random chance to lay another
     */
    @Override
    public void update() {
        super.update();
        if (System.currentTimeMillis() > lastBomb + bombRate && Math.random() < .001) {
            layBomb(3);
            lastBomb = System.currentTimeMillis();
        }
    }
}
