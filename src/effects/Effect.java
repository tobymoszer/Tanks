package effects;

import java.awt.*;

/**
 * An abstract class that represents an Effect
 */
public abstract class Effect {

    /**
     * The position of the effect
     */
    protected double x, y;
    /**
     * The deviance in the painted location of the effect
     */
    protected double deviance;
    /**
     * The color of the effect
     */
    protected Color color;
    /**
     * The size of the effect
     */
    protected double size;
    /**
     * How long the WallFragment lasts
     */
    protected long lifeSpan;
    /**
     * When the WallFragment was created
     */
    protected long birthTime;
    /**
     * Constructs a new effect
     * @param x the x position
     * @param y the y position
     */
    protected Effect(double x, double y) {
        this.x = x;
        this.y = y;
        birthTime = System.currentTimeMillis();
    }

    /**
     * Determines if the WallFragment has
     */
    public boolean isDead() {
        return System.currentTimeMillis() > birthTime + lifeSpan;
    }

    /**
     * Paints the effect
     * @param g the Graphics to paint to
     */
    public void paint(Graphics g) {
        g.setColor(color);
    }
}
