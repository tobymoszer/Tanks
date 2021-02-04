package elements;

import main.Main;

import java.awt.*;

/**
 * A class that represents a Hole Wall
 */
public class Hole extends Wall {

    /**
     * Constructs a new Hole
     * Sets the color of the hole
     * @param x the x position of the hole (top left)
     * @param y the y position of the hole (top left)
     * @param levelSizeX the x size of the level
     * @param levelSizeY the y size of the level
     */
    public Hole(double x, double y, int levelSizeX, int levelSizeY) {
        super(x, y, levelSizeX, levelSizeY);
        color = new Color(121, 103, 70);
    }

    /**
     * Paints the hole
     * @param g the Graphics to paint to
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x * Main.scale + Main.scale / 16, (int) y * Main.scale + Main.scale / 16, 7 * Main.scale / 8, 7 * Main.scale / 8);
    }

    /**
     * @return "Hole"
     */
    @Override
    public String toString() {
        return "Hole";
    }
}
