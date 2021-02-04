package tanks.weapons.projectiles;

import effects.projectiles.BulletTrail;
import effects.projectiles.MissileTrail;
import main.Level;
import main.Main;

import java.awt.*;

/**
 * A class that represents a Missile
 */
public class Missile extends Projectile {

    /**
     * The number of trails added per frame
     */
    int trailsPerFrame;

    /**
     * Constructs a Missile,
     * Sets speed, bounces, and color
     * @param x the x coordinate of the Missile (top left)
     * @param y the y coordinate of the Missile (top left)
     * @param direction the direction the Missile is moving in
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the Missile is in
     */
    public Missile(double x, double y, double direction, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, direction, levelSizeX, levelSizeY, delay, level);
        speed = .016;
        bounces = 0;
        trailsPerFrame = 3;
        color = new Color(250, 100, 100);
    }

    /**
     * Updates the Missile
     * @return true if the Missile has bounced all of its bounces
     */
    @Override
    public boolean update() {

        for (int i = 0; i < trailsPerFrame; i++) {
            if (this instanceof DoubleBounceMissile) {
                effects.add(new MissileTrail(getCenterX() + (Math.random() - .5) * size, getCenterY() + (Math.random() - .5) * size));
                effects.add(new BulletTrail(getCenterX() + (Math.random() - .5) * size, getCenterY() + (Math.random() - .5) * size));
            } else {
                effects.add(new MissileTrail(getCenterX() + (Math.random() - .5) * size, getCenterY() + (Math.random() - .5) * size));
            }
        }

        return super.update();
    }

    /**
     * Paints the Missile
     * @param g the Graphics to paint to
     */
    @Override
    public void paint(Graphics g) {

        g.setColor(color.darker().darker().darker());
        g.fillOval((int)(x * Main.scale + Main.scale / 32), (int)(y * Main.scale + Main.scale / 8), (int)(size * Main.scale), (int)(size * Main.scale));

        g.setColor(color);
        g.fillOval((int)(x * Main.scale), (int)(y * Main.scale), (int)(size * Main.scale), (int)(size * Main.scale));
    }
}
