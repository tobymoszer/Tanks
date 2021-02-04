package effects.walls;

import effects.Effect;
import main.Main;

import java.awt.*;

/**
 * A class representing a Wall Fragment
 * when a BreakableWall explodes
 */
public class WallFragment extends Effect {

    /**
     * Constructs a WallFragment,
     * Sets deviance, size, lifeSpan, birthTime and color
     * @param x the x position
     * @param y the y position
     */
    public WallFragment(double x, double y) {
        super(x, y);
        lifeSpan = (int) (Math.pow(Math.random(), 7) * 500);
        color = new Color(223, 175, 135).darker();
        size = 1 / 8.;
    }

    /**
     * Paints the WallFragment
     * @param g the Graphics to paint to
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillOval((int) ((x - size) * Main.scale + (Main.noise.eval(x, y, (System.currentTimeMillis() - birthTime)/800.)) * size * Main.scale), (int) ((y - size) * Main.scale + (Main.noise.eval(x + 1, y + 1, (System.currentTimeMillis() - birthTime)/800.)) * size * Main.scale), (int) (size * Main.scale), (int) (size * Main.scale));
    }
}
