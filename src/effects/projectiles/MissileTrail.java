package effects.projectiles;

import effects.Effect;
import main.Main;

import java.awt.*;

/**
 * A class that represents a MissileTrail effect
 */
public class MissileTrail extends Effect {

    /**
     * The x position displayed on the screen
     */
    private double displayX;
    /**
     * The y position displayed on the screen
     */
    private double displayY;

    /**
     * Constructs a new MissileTrail
     * Sets the deviance, size, and color of the Effect
     * @param x the x position of the MissileTrail
     * @param y the y position of the MissileTrail
     */
    public MissileTrail(double x, double y) {
        super(x, y);

        lifeSpan = (int) (Math.pow(Math.random(), 2) * 200);
        size = 1 / 8.;
        color = new Color(255, 139, 2);
    }

    /**
     * Updates the positions of the display coordinates with noise
     */
    private void updatePosition() {
        displayX = (x - size) * Main.scale + (Main.noise.eval(x, y, (System.currentTimeMillis() - birthTime)/800.)) * size * Main.scale;
        displayY = (y - size) * Main.scale + (Main.noise.eval(x + 1, y + 1, (System.currentTimeMillis() - birthTime)/800.)) * size * Main.scale;
    }

    /**
     * Paints the MissileTrail
     * @param g the Graphics to paint to
     */
    @Override
    public void paint(Graphics g) {
        updatePosition();
        super.paint(g);
        g.fillOval((int) displayX, (int) displayY, (int) (size * Main.scale), (int) (size * Main.scale));
    }
}
