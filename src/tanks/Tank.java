package tanks;

import effects.tracks.Track;
import elements.Element;
import elements.Wall;
import main.Level;
import main.Main;
import tanks.weapons.*;
import tools.Formulas;
import tanks.weapons.projectiles.Bullet;
import tanks.weapons.projectiles.DoubleBounceMissile;
import tanks.weapons.projectiles.Missile;
import tanks.weapons.projectiles.Projectile;

import java.awt.*;
import java.util.ArrayList;

/**
 * An abstract class representing a Tank
 */
public abstract class Tank extends Element {

	/**
	 * The direction of the Tank
	 */
	protected double direction;
	/**
	 * The Level that the tank is in
	 */
	protected Level level;
	/**
	 * The way the Tank is aiming
	 */
	protected double aim;
	/**
	 * The speed that the Tank can spin
	 */
	private double spinSpeed = .01;
	/**
	 * The speed that the Tank can move
	 */
	protected double moveSpeed = .005;
	/**
	 * The delay between frames
	 */
	protected int delay;
	/**
	 * The time the last Track was laid
	 */
	private long lastTrack;
	/**
	 * The delay between Tracks
	 */
	private long trackDelay = 100;
	/**
	 * The color of the Tank
	 */
	protected Color color;
	/**
	 * The Projectiles of the Tank
	 */
	private ArrayList<Projectile> projectiles;
	/**
	 * The Bombs of the Tank
	 */
	private ArrayList<Bomb> bombs;
	/**
	 * An ArrayList of the 4 polygons making up the sides of the Tank
	 */
	private ArrayList<Polygon> sides;
	/**
	 * The polygon that represents the top of the Tank
	 */
	private Polygon top;
	/**
	 * The polygon that represents the barrel
	 */
	private Polygon barrel;

	/**
	 * Constructs a new Tank
	 * Creates the side and top polygons
	 * @param x the x position of the Tank
	 * @param y the y position of the Tank
	 * @param levelSizeX the x size of the Level
	 * @param levelSizeY the y size of the Level
	 * @param delay the delay between frames
	 * @param level the Level that the tank is in
	 */
	public Tank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
		super(x, y, levelSizeX, levelSizeY);
		this.delay = delay;
		this.level = level;
		moveSpeed *= delay;
		spinSpeed *= delay;
		direction = 0;
		aim = 0;
		projectiles = new ArrayList<>();
		bombs = new ArrayList<>();
		lastTrack = System.currentTimeMillis();
		generatePolygons();
	}

	/**
	 * Makes the Tank look at a point on the screen
	 * @param point the point to be looked at
	 */
	public void lookAt(Point point) {
		double dx = (double)point.x / Main.scale - x - .5;
		double dy = (double)point.y / Main.scale - y - .5;
		if (dx > 0 && dy > 0) {
			aim = Math.atan(dy/dx);
		} else if (dx > 0 && dy < 0) {
			aim = 2 * Math.PI - Math.atan(Math.abs(dy)/dx);
		} else if (dx < 0 && dy < 0) {
			aim = Math.PI + Math.atan(Math.abs(dy) / Math.abs(dx));
		} else if (dx < 0 && dy > 0) {
			aim = Math.PI - Math.atan(dy / Math.abs(dx));
		}
	}

	/**
	 * Determines if the Tank contains a point
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return a boolean representing if the Tank contains the given point
	 */
	public boolean contains(double x, double y) {
		return Formulas.distance(x, this.x + .5, y, this.y + .5) < .5;
	}

	/**
	 * Determines if the Tank intersects a given Wall
	 * @param wall the Wall to test intersection with
	 * @return a boolean representing if the Tank intersects the Wall
	 */
	public boolean intersects(Wall wall) {
		
		//get the closest point on the wall to the center of the tank
		double closestX = Math.max(wall.x, Math.min(getCenterX(), wall.x + 1));
		double closestY = Math.max(wall.y, Math.min(getCenterY(), wall.y + 1));
		
		//return true if the tank contains that point
		return contains(closestX, closestY);
	}

	/**
	 * Determine if the Tank intersects another Tank
	 * @param other the other Tank to test intersection with
	 * @return a boolean representing if this Tank intersects the given Tank
	 */
	public boolean intersects(Tank other) {
		return Formulas.distance(x + .5, other.x + .5, y + .5, other.y + .5) < 1;
	}

	/**
	 * Pushes the given Tank
	 * @param other the other Tank to be pushed
	 */
	public void push(Tank other) {
		while(intersects(other)) {
			double dx = (x - other.x) / 20;
			double dy = (y - other.y) / 20;
			
			x += dx / 2;
			other.x -= dx / 2;
			y += dy / 2;
			other.y -= dy / 2;
		}
		
	}

	/**
	 * Shoots a Bullet
	 * @param max the maximum number of Bullets that the Tank can have at once
	 */
	protected void shootBullet(int max) {
		if (projectiles.size() < max) {
			projectiles.add(new Bullet(
					getCenterX() + Math.cos(aim) * 7 / 8 - 1/16.,
					getCenterY() + Math.sin(aim) * 7 / 8 - 1/4.,
					aim, levelSizeX, levelSizeY, delay, level)
			);

			//if the bullet is being fired directly into a wall,
			//get rid of the bullet, this is bad
			for (Element element: level.getElements()) {
				if (element instanceof Wall) {
					if (projectiles.size() == 0) {
						return;
					}
					if (projectiles.get(projectiles.size() - 1).intersects((Wall)element)) {
						projectiles.remove(projectiles.get(projectiles.size() - 1));
					}
				}
			}
		}
	}

	/**
	 * Shoots a Missile
	 * @param max the maximum number of Missiles that the Tank can have at once
	 */
	protected void shootMissile(int max) {
		if (projectiles.size() < max) {
			projectiles.add(new Missile(
					x + .5 + Math.cos(aim) * 7 / 8 - 1/16.,
					y + .5 + Math.sin(aim) * 7 / 8 - 1/4.,
					aim, levelSizeX, levelSizeY, delay, level)
			);

			//if the missile is being fired directly into a wall,
			//get rid of the missile, this is bad
			for (Element element: level.getElements()) {
				if (element instanceof Wall) {
					if (projectiles.size() == 0) {
						return;
					}
					if (projectiles.get(projectiles.size() - 1).intersects((Wall)element)) {
						projectiles.remove(projectiles.get(projectiles.size() - 1));
					}
				}
			}
		}
	}

	/**
	 * Shoots a Missile that can bounce twice
	 * @param max the maximum number of DoubleBounceMissiles that the Tank can have at once
	 */
	protected void shootDoubleBounceMissile(int max) {
		if (projectiles.size() < max) {
			projectiles.add(new DoubleBounceMissile(
					x + .5 + Math.cos(aim) * 7 / 8 - 1/16.,
					y + .5 + Math.sin(aim) * 7 / 8 - 1/4.,
					aim, levelSizeX, levelSizeY, delay, level)
			);

			//if the missile is being fired directly into a wall,
			//get rid of the missile, this is bad
			for (Element element: level.getElements()) {
				if (element instanceof Wall) {
					if (projectiles.size() == 0) {
						return;
					}
					if (projectiles.get(projectiles.size() - 1).intersects((Wall)element)) {
						projectiles.remove(projectiles.get(projectiles.size() - 1));
					}
				}
			}
		}
	}

	/**
	 * Lays a Bomb
	 * @param max the maximum number of Bombs that the Tank can have at once
	 */
	protected void layBomb(int max) {
		if (bombs.size() < max) {
			bombs.add(new Bomb(x + .5, y + .5));
		}
	}

	/**
	 * Gets all of the Bombs that the Tank has laid
	 * @return an ArrayList of Bombs that the Tank has laid
	 */
	public ArrayList<Bomb> getBombs() {
		return bombs;
	}

	/**
	 * Removes a Bomb from the Tank
	 * @param bomb the Bomb to be removed
	 */
	public void removeBomb(Bomb bomb) {
		bombs.remove(bomb);
	}

	/**
	 * Gets all of the Projectiles that the Tank has fired
	 * @return an ArrayList of Projectiles that the Tank has fired
	 */
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * Removes a Projectile from the Tank
	 * @param projectile the Projectile to be removed
	 */
	public void removeProjectile(Projectile projectile) {
		projectiles.remove(projectile);
	}

	/**
	 * Gets the center x position of the Tank
	 * @return the center x position of the Tank
	 */
	public double getCenterX() {
		return x + .5;
	}

	/**
	 * Gets the center y position of the Tank
	 * @return the center y position of the Tank
	 */
	public double getCenterY() {
		return y + .5;
	}

	/**
	 * Clears all Bombs and Projectiles
	 */
	public void clearWeapons() {
		bombs.clear();
		projectiles.clear();
	}

	/**
	 * Moves the Tank in the direction it is facing
	 */
	private void move() {
		x += moveSpeed * delay * Math.cos(direction);
		y += moveSpeed * delay * Math.sin(direction);
	}

	/**
	 * Adjusts the direction toward a given position and moves at it
	 * @param x the x position of the position to be moved at
	 * @param y the y position of the position to be moved at
	 */
	public void moveAt(double x, double y) {

		double wantedDirection;

		wantedDirection = Formulas.fixAngle(Math.atan2(y - this.y, x - this.x));

		moveAtDirection(wantedDirection);

	}

	protected void moveAtDirection(double wantedDirection) {

		System.out.println(direction + " " + wantedDirection);

		wantedDirection = Formulas.fixAngle(wantedDirection);

		if (Math.abs(direction - wantedDirection) < spinSpeed || Formulas.fixAngle(Math.abs(direction - wantedDirection) + Math.PI) < spinSpeed) {
			direction = wantedDirection;
			move();
		} else if (wantedDirection < Math.PI) {

			double standardDistance = Formulas.angleBetween(direction, wantedDirection);
			double otherDistance = Formulas.angleBetween(Formulas.fixAngle(direction + Math.PI), wantedDirection);

			if (otherDistance < standardDistance) {
				direction = Formulas.fixAngle(direction + Math.PI);
			}

			if (direction < Formulas.fixAngle(wantedDirection + Math.PI) && direction > wantedDirection) {

				direction -= spinSpeed;

			} else if (direction >= Formulas.fixAngle(wantedDirection + Math.PI) || direction <= wantedDirection) {

				direction += spinSpeed;

			}

		} else if (wantedDirection >= Math.PI) {

			double standardDistance = Formulas.angleBetween(direction, wantedDirection);
			double otherDistance = Formulas.angleBetween(Formulas.fixAngle(direction + Math.PI), wantedDirection);

			if (otherDistance < standardDistance) {
				direction = Formulas.fixAngle(direction + Math.PI);
			}

			if (direction < wantedDirection && direction > Formulas.fixAngle(wantedDirection + Math.PI)) {

				direction += spinSpeed;

			} else if (direction >= wantedDirection || direction <= Formulas.fixAngle(wantedDirection + Math.PI)) {

				direction -= spinSpeed;

			}

		} else {
			System.err.println("this is very bad, moveAt failed");
		}

		direction = Formulas.fixAngle(direction);

	}

	/**
	 * Generates the side and top polygons of the Tank,
	 * and the Barrel
	 */
	private void generatePolygons() {

		double[][] rotatedCorners = getRotatedCorners();
		double[][] lowerCorners = getLowerCorners(rotatedCorners);
		sides = new ArrayList<>();

		for (int i = 0; i < 4; i++) {

			sides.add(new Polygon());

			sides.get(i).addPoint((int)rotatedCorners[i][0], (int)rotatedCorners[i][1]);
			sides.get(i).addPoint((int)rotatedCorners[(i + 1) % 4][0], (int)rotatedCorners[(i + 1) % 4][1]);

			sides.get(i).addPoint((int)lowerCorners[(i + 1) % 4][0], (int)lowerCorners[(i + 1) % 4][1]);
			sides.get(i).addPoint((int)lowerCorners[i][0], (int)lowerCorners[i][1]);

		}

		top = new Polygon();

		for (double[] point: rotatedCorners) {
			top.addPoint((int)point[0], (int)point[1]);
		}

		barrel = new Polygon();

		barrel.addPoint(
				(int) (x * Main.scale + Main.scale / 2. - Main.scale / 16.),
				(int) (y * Main.scale + Main.scale / 2. - Main.scale / 8. - Main.scale / 4.)
		);

		barrel.addPoint(
				(int) (x * Main.scale + Main.scale / 2. - Main.scale / 16.),
				(int) (y * Main.scale + Main.scale / 2. - Main.scale / 8. - Main.scale / 4. + Main.scale / 4.)
		);

		barrel.addPoint(
				(int) (x * Main.scale + Main.scale / 2. - Main.scale / 16. + 7 * Main.scale / 8.),
				(int) (y * Main.scale + Main.scale / 2. - Main.scale / 8. - Main.scale / 4. + Main.scale / 4.)
		);

		barrel.addPoint(
				(int) (x * Main.scale + Main.scale / 2. - Main.scale / 16. + 7 * Main.scale / 8.),
				(int) (y * Main.scale + Main.scale / 2. - Main.scale / 8. - Main.scale / 4.)
		);

		barrel = Formulas.rotatePolygon(barrel, x + 1 / 2. - 1 / 16., y + 1 / 2. - 1 / 4., aim);
	}

	/**
	 * Helper for generatePolygons()
	 * Gets the rotated corners of the Tank, rotated by the direction the Tank is facing
	 * @return a double[][] of positions of the rotated corners
	 */
	private double[][] getRotatedCorners() {
		
		double[][] corners = new double[4][2];

		double angle = direction + Math.PI / 4;

		for (int i = 0; i < 4; i++) {

			double currentAngle = angle + i * Math.PI / 2;

			corners[i][0] = (Math.cos(currentAngle) * Math.sqrt(2) / 2 + getCenterX()) * Main.scale - Main.scale / 16.;
			corners[i][1] = (Math.sin(currentAngle) * Math.sqrt(2) / 2 + getCenterY()) * Main.scale - Main.scale / 4.;

		}

		return corners;
	}

	/**
	 * Helper for generatePolygons()
	 * Gets the corners lower than the given corners
	 * @param corners the corners to be lowered
	 * @return a double[][] of lowered corners
	 */
	private double[][] getLowerCorners(double[][] corners) {
		double[][] loweredCorners = new double[4][2];

		for (int i = 0; i < 4; i++) {
			loweredCorners[i][0] = corners[i][0] + Main.scale / 16.;
			loweredCorners[i][1] = corners[i][1] + Main.scale / 4.;
		}

		return loweredCorners;
	}

	/**
	 * Creates a new Track every trackDelay milliseconds, called every frame
	 */
	public void addTracks() {
		if (System.currentTimeMillis() > lastTrack + trackDelay) {
			new Track(getCenterX(), getCenterY(), direction);
			lastTrack = System.currentTimeMillis();
		}
	}

	/**
	 * Paints the Tank
	 * @param g the Graphics to paint to
	 */
	@Override
	public void paint(Graphics g) {
		paintTank(g);
	}

	/**
	 * Paints just the Tank Body
	 * @param g the Graphics to paint to
	 */
	private void paintTank(Graphics g) {

		generatePolygons();

		g.setColor(color.darker());

		for (Polygon polygon: sides) {
			g.fillPolygon(polygon);
		}

		g.setColor(color);

		g.fillPolygon(top);

	}

	/**
	 * Paints just the Tank Barrel
	 * @param g the Graphics to paint to
	 */
	public void paintBarrel(Graphics g) {
		g.setColor(new Color(160, 133, 89));
		g.fillPolygon(barrel);
	}
}

/*
//if the direction is close enough to the wanted direction, set it equal







		//if the direction is close enough to the wanted direction, set it equal
		if (Math.abs(direction - wantedDirection) < spinSpeed || Formulas.fixAngle(Math.abs(direction - wantedDirection) + Math.PI) < spinSpeed) {
			direction = wantedDirection;
			move();
		} else if (wantedDirection < Math.PI) {

			if (direction < Formulas.fixAngle(wantedDirection + Math.PI / 2) && direction > wantedDirection) {

				direction -= spinSpeed;

			} else if (direction >= Formulas.fixAngle(wantedDirection + Math.PI) || direction < Formulas.fixAngle(wantedDirection + 3 * Math.PI / 2)) {

				direction += spinSpeed;

			} else {
				direction = Formulas.fixAngle(direction + Math.PI);
			}

		} else if (wantedDirection >= Math.PI) {

			if (direction < wantedDirection && direction > Formulas.fixAngle(wantedDirection + Math.PI)) {

				direction += spinSpeed;

			} else if (direction >= wantedDirection || direction <= Formulas.fixAngle(wantedDirection + Math.PI)) {

				direction -= spinSpeed;

			}

		} else {
			System.err.println("this is bad");
		}
 */