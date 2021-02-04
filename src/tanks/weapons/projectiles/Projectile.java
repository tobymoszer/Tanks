package tanks.weapons.projectiles;

import effects.Effect;
import elements.Element;
import elements.Hole;
import elements.Wall;
import main.Level;
import tanks.Tank;
import tools.Formulas;
import tanks.weapons.Bomb;

import java.awt.*;
import java.util.ArrayList;

/**
 * An abstract class representing a Projectile
 */
public abstract class Projectile {

	/**
	 * The color of the Projectile
	 */
	Color color;
	/**
	 * The x position of the Projectile
	 */
	double x;
	/**
	 * The y position of the Projectile
	 */
	double y;
	/**
	 * The speed of the Projectile
	 */
	double speed;
	/**
	 * The size of the Projectile
	 */
	double size;
	/**
	 * The Level that the Projectile is in
	 */
	private Level level;
	/**
	 * The x size of the Level
	 */
	private int levelSizeX;
	/**
	 * The y size of the Level
	 */
	private int levelSizeY;
	/**
	 * The delay between frames
	 */
	private int delay;
	/**
	 * The direction the Projectile is moving
	 */
	private double direction;
	/**
	 * How many times the Projectile can bounce
	 */
	int bounces;
	/**
	 * The Effects that Projectile leaves behind
	 */
	ArrayList<Effect> effects;

	/**
	 * Constructs a new Projectile,
	 * Sets the size
	 * @param x the x coordinate of the Projectile (center)
	 * @param y the y coordinate of the Projectile (center)
	 * @param direction the direction the Projectile is moving in
	 * @param levelSizeX the x size of the Level
	 * @param levelSizeY the y size of the Level
	 * @param delay the delay between frames
	 * @param level the Level that the Projectile is in
	 */
	Projectile(double x, double y, double direction, int levelSizeX, int levelSizeY, int delay, Level level) {
		size = 1 / 4.;
		//the position given by the tank is the center of the end of the barrel
		//make the actual position the top left
		this.x = x - size / 2;
		this.y = y - size / 2;
		
		this.levelSizeX = levelSizeX;
		this.levelSizeY = levelSizeY;
		this.direction = direction;
		this.delay = delay;
		this.level = level;
		effects = new ArrayList<>();
	}

	/**
	 * Updates the Projectile each frame
	 * Removes effects that have lived their life span
	 * Handles bouncing off Walls
	 * @return true if the Projectile has bounced all of its bounces
	 */
	public boolean update() {
		x += speed * delay * Math.cos(direction);
		y += speed * delay * Math.sin(direction);
		
		if (getCenterX() + size / 2 > levelSizeX) {
			direction = Math.PI - direction;
			bounces--;
		} else if (getCenterX() - size / 2 < 0) {
			direction = Math.PI - direction;
			bounces--;
		}
		if (getCenterY() + size / 2 > levelSizeY) {
			direction = 2 * Math.PI - direction;
			bounces--;
		} else if (getCenterY() - size / 2 < 0) {
			direction = 2 * Math.PI - direction;
			bounces--;
		}
		
		for (Element element: level.getElements()) {
			if (element instanceof Wall) {
				if (intersects((Wall)element)) {
					bounce((Wall)element);
				}
			}
		}

		ArrayList<Effect> toRevmove = new ArrayList<>();
		for (Effect effect: effects) {
			if (effect.isDead()) {
				toRevmove.add(effect);
			}
		}
		effects.removeAll(toRevmove);
		
		direction = Formulas.fixAngle(direction);
		
		return bounces < 0;
		
	}

	/**
	 * Determines if the Projectile intersects a given Tank
	 * @param tank the Tank to be tested
	 * @return true if the Projectile intersects the Tank
	 */
	public boolean intersects(Tank tank) {
		if (tank.contains(getCenterX(), getCenterY())) {
			bounces = -1;
			return true;
		}
		return false;
	}

	/**
	 * Determines if the Projectile intersects a given Wall
	 * @param wall the Wall to be tested
	 * @return true if the Projectile intersects the Wall
	 */
	public boolean intersects(Wall wall) {
		//ignore if the wall is an instance of hole, a projectile can never intersect a hole
		if (wall instanceof Hole) {
			return false;
		}
		//get the closest point on that wall to the center of the projectile
		double closestX = Math.max(wall.x, Math.min(getCenterX(), wall.x + 1));
		double closestY = Math.max(wall.y, Math.min(getCenterY(), wall.y + 1));
		
		//return true if the distance between the projectile and that point is less than the projectile diameter
		return Formulas.distance(getCenterX(), closestX, getCenterY(), closestY) < size / 2;
	}

	/**
	 * Determines if the Projectile intersects another Projectile
	 * @param other the other Projectile to be tested
	 * @return true if the Projectile intersects the other Projectile
	 */
	public boolean intersects (Projectile other) {
		return Formulas.distance(getCenterX(), other.x, getCenterY(), other.y) < size;
	}

	/**
	 * Determine if the Projectile intersects a given Bomb
	 * @param bomb the Bomb to be tested
	 * @return true if the Projectile intersects the Bomb
	 */
	public boolean intersects(Bomb bomb) {
		return Formulas.distance(getCenterX(), bomb.getX(), getCenterY(), bomb.getY()) < bomb.getSize();
	}

	/**
	 * Bounces the Projectile off of the given Wall
	 * Decrements the bounces of the Projectile
	 * @param wall the Wall to bounce off of
	 */
	private void bounce(Wall wall) {
		//decrease the number of bounces
		bounces--;
		
		//get the intersection side of the wall the projectile is intersecting
		String intersectionSide = getIntersectionSide(wall);
		
		//check each intersection side and bounce off that side if the projectile is able to do so
		if (intersectionSide.equals("left") && canBounceOffLeft()) {
			direction = Math.PI - direction;
		} else if (intersectionSide.equals("right") && canBounceOffRight()) {
			direction = Math.PI - direction;
		} else if (intersectionSide.equals("top") && canBounceOffTop()) {
			direction = 2 * Math.PI - direction;
		} else if (intersectionSide.equals("bottom") && canBounceOffBottom()) {
			direction = 2 * Math.PI - direction;
		}
		//else cannot bounce
		//this means that the bullet hits the corner in a way that it cannot bounce off of
		
		//while the projectile intersects the wall, move it in the new direction after bouncing
		while (intersects(wall)) {
			x += speed * delay * Math.cos(direction);
			y += speed * delay * Math.sin(direction);
		}
	}
	
	/**
	 * Determines if the projectile is traveling in a direction that allows it to bounce off of a left wall
	 * @return if the projectile can bounce off of a left wall
	 */
	private boolean canBounceOffLeft() {
		return direction < Math.PI / 2 || direction > 3 * Math.PI / 2;
	}
	
	/**
	 * Determines if the projectile is traveling in a direction that allows it to bounce off of a right wall
	 * @return if the projectile can bounce off of a right wall
	 */
	private boolean canBounceOffRight() {
		return direction > Math.PI / 2 && direction < 3 * Math.PI / 2;
	}
	
	/**
	 * Determines if the projectile is traveling in a direction that allows it to bounce off of a top wall
	 * @return if the projectile can bounce off of a top wall
	 */
	private boolean canBounceOffTop() {
		return direction < Math.PI;
	}
	
	/**
	 * Determines if the projectile is traveling in a direction that allows it to bounce off of a bottom wall
	 * @return if the projectile can bounce off of a bottom wall
	 */
	private boolean canBounceOffBottom() {
		return direction > Math.PI;
	}

	/**
	 * Gets the side of the Projectile that is intersecting the given Wall
	 * @param wall the Wall that is being intersected
	 * @return the side of the Projectile intersecting the wall
	 */
	private String getIntersectionSide(Wall wall) {
		
		//initialize intersectionIndex
		int intersectionIndex = -1;
		
		//get the closest point on the wall to the center of the projectile
		double closestX = Math.max(wall.x, Math.min(getCenterX(), wall.x + 1));
		double closestY = Math.max(wall.y, Math.min(getCenterY(), wall.y + 1));
		
		//standard cases
		if (closestX == wall.x) {
			intersectionIndex = 2;
		} else if (closestX == wall.x + 1) {
			intersectionIndex = 3;
		} else if (closestY == wall.y) {
			intersectionIndex = 0;
		} else if (closestY == wall.y + 1) {
			intersectionIndex = 1;
		}
		//corner cases
		if (closestX == wall.x && closestY == wall.y){
			if (level.wallAt((int)wall.x - 1, (int)wall.y)) {
				intersectionIndex = 0;
			} else if (level.wallAt((int)wall.x, (int)wall.y - 1)) {
				intersectionIndex = 2;
			} else {
				intersectionIndex = 2;
			}
		} else if (closestX == wall.x && closestY == wall.y + 1){
			if (level.wallAt((int)wall.x - 1, (int)wall.y)) {
				intersectionIndex = 1;
			} else if (level.wallAt((int)wall.x, (int)wall.y + 1)) {
				intersectionIndex = 2;
			} else {
				intersectionIndex = 2;
			}
		} else if (closestX == wall.x + 1 && closestY == wall.y){
			if (level.wallAt((int)wall.x + 1, (int)wall.y)) {
				intersectionIndex = 0;
			} else if (level.wallAt((int)wall.x, (int)wall.y - 1)) {
				intersectionIndex = 3;
			} else {
				intersectionIndex = 3;
			}
		} else if (closestX == wall.x + 1 && closestY == wall.y + 1){
			if (level.wallAt((int)wall.x + 1, (int)wall.y)) {
				intersectionIndex = 1;
			} else if (level.wallAt((int)wall.x, (int)wall.y + 1)) {
				intersectionIndex = 3;
			} else {
				intersectionIndex = 3;
			}
		}

		return switch (intersectionIndex) {
			case 0 -> "top";
			case 1 -> "bottom";
			case 2 -> "left";
			case 3 -> "right";
			default -> "no";
		};
	}

	/**
	 * Gets the center x coordinate of the Projectile
	 * @return the center x coordinate of the Projectile
	 */
	public double getCenterX() {
		return x + size/2;
	}

	/**
	 * Gets the center y coordinate of the Projectile
	 * @return the center y coordinate of the Projectile
	 */
	public double getCenterY() {
		return y + size/2;
	}
	
	/**
	 * Return the direction the projectile is moving in
	 * @return the direction the projectile is moving in
	 */
	public double getDirection() {
		return direction;
	}
	
	/**
	 * Return the number of bounces the projectile has left
	 * @return the number of bounces the projectile has left
	 */
	public int getBounces() {
		return bounces;
	}

	public ArrayList<Effect> getEffects() {
		return effects;
	}

	/**
	 * Paints the Projectile
	 * @param g the Graphics to paint to
	 */
	public abstract void paint(Graphics g);
	
}
