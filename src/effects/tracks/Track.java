package effects.tracks;

import effects.Effect;
import main.Main;
import tools.Formulas;

import java.awt.*;

/**
 * A class representing a Track Effect
 */
public class Track extends Effect {

    /**
     * The angle of the track
     */
    private double angle;
    /**
     * The thickness of the track
     */
    private final double thickness = 1 / 12.;
    /**
     * The distance of the track away from the center of the tank
     */
    private final double distanceAway = 3 / 8.;
    /**
     * The length of the track
     */
    private final double length = 1 / 5.;
    /**
     * The polygons representing the upper and lower parts of the track
     */
    private Polygon upper, lower;

    /**
     * Constructs a new Track
     * Sets the color of the Effect
     * @param x the center x position of the Track
     * @param y the center y position of the Track
     * @param angle the angle of the Track
     */
    public Track(double x, double y, double angle) {
        super(x, y);
        this.angle = angle;
        color = new Color(183, 146, 80);
        initializeTrack();
        addTrackToBackground();
    }

    /**
     * Creates the upper and lower polygons
     */
    private void initializeTrack() {

        upper = new Polygon();
        upper.addPoint((int) ((x - thickness / 2.) * Main.scale), (int) ((y + distanceAway) * Main.scale));
        upper.addPoint((int) ((x + thickness / 2.) * Main.scale), (int) ((y + distanceAway) * Main.scale));
        upper.addPoint((int) ((x + thickness / 2.) * Main.scale), (int) ((y + distanceAway - length) * Main.scale));
        upper.addPoint((int) ((x - thickness / 2.) * Main.scale), (int) ((y + distanceAway - length) * Main.scale));

        upper = Formulas.rotatePolygon(upper, x, y, angle);
        lower = Formulas.rotatePolygon(upper, x, y, Math.PI);
    }

    /**
     * Adds the track to the background image of the level
     */
    private void addTrackToBackground() {

        //get the graphics to draw to for the background
        Graphics g = Main.panel.background.createGraphics();

        //set the graphics color to the color of the tracks
        g.setColor(color);

        //draw and fill the polygons
        g.drawPolygon(upper);
        g.fillPolygon(upper);

        g.drawPolygon(lower);
        g.fillPolygon(lower);
    }
}
