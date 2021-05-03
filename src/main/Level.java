package main;

import effects.Effect;
import effects.walls.WallFragment;
import elements.*;
import tanks.enemies.*;
import tanks.Player;
import tanks.Tank;
import tanks.enemies.moving.*;
import tanks.enemies.stationary.StationaryGrayTank;
import tanks.enemies.stationary.StationaryGreenTank;
import tools.Formulas;
import tanks.weapons.Bomb;
import tanks.weapons.projectiles.Projectile;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class representing a level in the game
 */
public class Level {

	/**
	 * All of the elements
	 */
	private CopyOnWriteArrayList<Element> elements;
	/**
	 * All of the projectiles
	 */
	private CopyOnWriteArrayList<Projectile> projectiles;
	/**
	 * All of the bombs
	 */
	private CopyOnWriteArrayList<Bomb> bombs;
	/**
	 * The size of the level
	 */
	private int sizeX, sizeY;
	/**
	 * The delay between frames
	 */
	private int delay;
	/**
	 * A boolean saying if the player is dead
	 */
	private boolean playerDead;
	/**
	 * An ArrayList of WallFragments for exploding BreakableWalls
	 */
	private CopyOnWriteArrayList<WallFragment> wallFragments;
	/**
	 * An ArrayList of BulletTrails
	 */
	private CopyOnWriteArrayList<Effect> projectileEffects;

	/**
	 * Constructs a new Level
	 * @param sizeX the x size of the Level
	 * @param sizeY the y size of the Level
	 * @param delay the delay between frames
	 */
	Level(int sizeX, int sizeY, int delay) {
		elements = new CopyOnWriteArrayList<>();
		projectiles = new CopyOnWriteArrayList<>();
		bombs = new CopyOnWriteArrayList<>();
		wallFragments = new CopyOnWriteArrayList<>();
		projectileEffects = new CopyOnWriteArrayList<>();
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.delay = delay;
		playerDead = false;
	}

	/**
	 * Changes the level to a new level
	 * @param level the level to be changed to
	 */
	private void changeLevel(Level level) {
		this.elements = level.elements;
		this.projectiles = level.projectiles;
		this.bombs = level.bombs;
		this.sizeX = level.sizeX;
		this.sizeY = level.sizeY;
		this.wallFragments = level.wallFragments;
		level.sortWalls();
	}

	/**
	 * Sorts the walls from top to bottom
	 */
	void sortWalls() {
		ArrayList<Element> wanks = new ArrayList<>();
		for (Element element: elements) {
			if (element instanceof Wall || element instanceof Tank) {
				wanks.add(element);
			}
		}

		elements.removeAll(wanks);

		Collections.sort(wanks);

		elements.addAll(wanks);
	}

	/**
	 * Adds a Player
	 * @param x the x position of the Player
	 * @param y the y position of the Player
	 */
	void addPlayer(int x, int y) {
		elements.add(new Player(x, y, sizeX, sizeY, delay, this));
	}

	/**
	 * Adds a Wall
	 * @param x the x position of the Wall
	 * @param y the y position of the Wall
	 */
	void addWall(int x, int y) {
		elements.add(new Wall(x, y, sizeX, sizeY));
	}

	/**
	 * Adds a BreakableWall
	 * @param x the x position of the BreakableWall
	 * @param y the y position of the BreakableWall
	 */
	void addBreakableWall(int x, int y) {
		elements.add(new BreakableWall(x, y, sizeX, sizeY));
	}

	/**
	 * Adds a Hole
	 * @param x the x position of the Hole
	 * @param y the y position of the Hole
	 */
	void addHole(int x, int y) {
		elements.add(new Hole(x, y, sizeX, sizeY));
	}

	/**
	 * Adds a StationaryGrayTank
	 * @param x the x position of the StationaryGrayTank
	 * @param y the y position of the StationaryGrayTank
	 */
	void addStationaryGrayTank(int x, int y) {
		elements.add(new StationaryGrayTank(x, y, sizeX, sizeY, delay, this));
	}

	/**
	 * Adds a GrayTank
	 * @param x the x position of the GrayTank
	 * @param y the y position of the GrayTank
	 */
	void addGrayTank(int x, int y) {
		elements.add(new GrayTank(x, y, sizeX, sizeY, delay, this));
	}

	/**
	 * Adds a GreenTank
	 * @param x the x position of the GreenTank
	 * @param y the y position of the GreenTank
	 */
	void addGreenTank(int x, int y) {
		elements.add(new GreenTank(x, y, sizeX, sizeY, delay, this));
	}

	/**
	 * Adds a YellowTank
	 * @param x the x position of the YellowTank
	 * @param y the y position of the YellowTank
	 */
	void addYellowTank(int x, int y) {
		elements.add(new YellowTank(x, y, sizeX, sizeY, delay, this));
	}

	/**
	 * Adds a RedTank
	 * @param x the x position of the RedTank
	 * @param y the y position of the RedTank
	 */
	void addRedTank(int x, int y) {
		elements.add(new RedTank(x, y, sizeX, sizeY, delay, this));
	}

	/**
	 * Adds a StationaryGreenTank
	 * @param x the x position of the StationaryGreenTank
	 * @param y the y position of the StationaryGreenTank
	 */
	void addStationaryGreenTank(int x, int y) {
		elements.add(new StationaryGreenTank(x, y, sizeX, sizeY, delay, this));
	}

	/**
	 * Adds a PurpleTank
	 * @param x the x position of the PurpleTank
	 * @param y the y position of the PurpleTank
	 */
	void addPurpleTank(int x, int y) {
		elements.add(new PurpleTank(x, y, sizeX, sizeY, delay, this));
	}

	/**
	 * Adds a WhiteTank
	 * @param x the x position of the WhiteTank
	 * @param y the y positino of the WhiteTank
	 */
	void addWhiteTank(int x, int y) {
		elements.add(new WhiteTank(x, y, sizeX, sizeY, delay, this));
	}

	/**
	 * Handles and updates all Elements, Projectiles, and Bombs
	 * Handles collisions of Elements
	 * Removes any elements that have been destroyed
	 */
	void update() {

		getPlayer().addTracks();
		
		//handles projectile and Tank, Bomb, and other Projectile collision
		//handles WallFragments
		ArrayList<Projectile> removeProjectiles = new ArrayList<>();
		ArrayList<Tank> removeTanks = new ArrayList<>();
		ArrayList<Bomb> removeBombs = new ArrayList<>();
		ArrayList<Bomb> explodeBombs = new ArrayList<>();
		ArrayList<WallFragment> removeWallFragments = new ArrayList<>();
		ArrayList<Effect> removeTrails = new ArrayList<>();
		for (Projectile projectile: projectiles) {

			for (Effect effect: projectile.getEffects()) {
				if (!projectileEffects.contains(effect)) {
					projectileEffects.add(effect);
				}
			}

			if (projectile.intersects(getPlayer())) {
				playerDead = true;
			}
			
			for (Element element: elements) {
				if (element instanceof Tank) {
					if (projectile.intersects((Tank)element)) {
						removeTanks.add((Tank)element);
					}
				}
			}
			
			for (Bomb bomb: bombs) {
				if (projectile.intersects(bomb)) {
					explodeBombs.add(bomb);
				}
			}
			
			for (Projectile other: projectiles) {
				if (other != projectile) {
					if (projectile.intersects(other)) {
						removeProjectiles.add(projectile);
						removeProjectiles.add(other);
					}
				}
			}
			
			if (projectile.update()) {
				removeProjectiles.add(projectile);
			}
		}

		//handles tank and wall collisions
		//updates projectiles and bombs for any new projectiles fired or bombs laid
		for (Element element: elements) {
			if (element instanceof Tank) {
				Tank tank = (Tank)element;

				for (Element other: elements) {
					if (other instanceof Tank) {
						if (other != tank) {
							if (tank.intersects((Tank) other)) {
								tank.push((Tank) other);
							}
						}
					}
				}

				for (Projectile projectile: tank.getProjectiles()) {
					if (!projectiles.contains(projectile)) {
						projectiles.add(projectile);
					}
				}
				for (Bomb bomb: tank.getBombs()) {
					if (!bombs.contains(bomb)) {
						bombs.add(bomb);
					}
				}
				if (tank instanceof EnemyTank) {
					((EnemyTank) tank).update();
				}
			} else if (element instanceof Wall) {

				Wall wall = (Wall) element;
				for (Element other: elements) {
					if (other instanceof Tank) {
						Tank tank = (Tank) other;
						while (tank.intersects(wall)) {
							double dx = (tank.getCenterX() - Math.max(wall.x, Math.min(tank.getCenterX(), wall.x + 1)))/50;
							double dy = (tank.getCenterY() - Math.max(wall.y, Math.min(tank.getCenterY(), wall.y + 1)))/50;

							tank.x += dx;
							tank.y += dy;
						}
					}
				}
			}
		}

		for (Effect effect: projectileEffects) {
			if (effect.isDead()) {
				removeTrails.add(effect);
			}
		}

		for (WallFragment fragment: wallFragments) {
			if (fragment.isDead()) {
				removeWallFragments.add(fragment);
			}
		}
		
		//handles bomb explosions
		for (Bomb bomb: bombs) {
			if (bomb.update()) {
				explosion(bomb.getX(), bomb.getY(), bomb.getExplosionRadius());
				removeBombs.add(bomb);
			} else if (explodeBombs.contains(bomb)) {
				explosion(bomb.getX(), bomb.getY(), bomb.getExplosionRadius());
				removeBombs.add(bomb);
			}
		}
		
		//remove projectiles that are dead
		for (Projectile projectile: removeProjectiles) {
			projectiles.remove(projectile);
			for (Element element: elements) {
				if (element instanceof Tank) {
					Tank tank = (Tank) element;
					tank.removeProjectile(projectile);
				}
			}
		}
		
		//remove bombs that are dead
		for (Bomb bomb: removeBombs) {
			bombs.remove(bomb);
			for (Element element: elements) {
				if (element instanceof Tank) {
					Tank tank = (Tank) element;
					tank.removeBomb(bomb);
				}
			}
		}

		//remove tanks that are dead
		elements.removeAll(removeTanks);

		//remove WallFragments that are dead
		wallFragments.removeAll(removeWallFragments);

		//remove ProjectileTrails that are dead
		projectileEffects.removeAll(removeTrails);
	}

	/**
	 * Determines if there is a wall at the given position
	 * @param x the x position of the given position
	 * @param y the y position of the given position
	 * @return if there is a wall at the given point
	 */
	public boolean wallAt(int x, int y) {
		for (Element element: elements) {
			if (element instanceof Wall) {
				if (element.x == x && element.y == y) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Determine if there is a wall that contains the given point
	 * @param x the x position of the point in question
	 * @param y the y position of the point in question
	 * @return if there is a wall that contains the given point
	 */
	public boolean pointInWall(double x, double y) {
		for (Element element: elements) {
			if (element instanceof Wall) {
				if (((Wall) element).contains(x, y)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Moves the player in a given direction
	 * @param direction the direction to move the player
	 */
	void movePlayer(String direction) {
		Player player = getPlayer();

		//if there is no player
		if (player == null) {
			return;
		}

		switch (direction) {
			case "up" -> player.moveAt(player.x, player.y - 1);
			case "down" -> player.moveAt(player.x, player.y + 1);
			case "left" -> player.moveAt(player.x - 1, player.y);
			case "right" -> player.moveAt(player.x + 1, player.y);
			case "upright" -> player.moveAt(player.x + 1, player.y - 1);
			case "upleft" -> player.moveAt(player.x - 1, player.y - 1);
			case "downright" -> player.moveAt(player.x + 1, player.y + 1);
			case "downleft" -> player.moveAt(player.x - 1, player.y + 1);
		}
	}

	/**
	 * Handle an explosion at the given position and radius
	 * Handles Wall Fragments
	 * @param x the x position of the explosion
	 * @param y the y position of the explosion
	 * @param radius the radius of the explosion
	 */
	private void explosion(double x, double y, double radius) {

		//initialize arrayLists of Tanks, Projectiles, and BreakableWalls to be removed by the explosion
		ArrayList<Tank> removeTanks = new ArrayList<>();
		ArrayList<Projectile> removeProjectiles = new ArrayList<>();
		ArrayList<BreakableWall> removeWalls = new ArrayList<>();

		//handles Tanks (including Player) and BreakableWalls
		for (Element element: elements) {
			if (element instanceof Tank) {
				Tank tank = (Tank) element;
				if (Formulas.distance(x, tank.getCenterX(), y, tank.getCenterY()) < radius) {
					if (tank instanceof Player) {
						playerDead = true;
					} else {
						removeTanks.add(tank);
					}
				}
			} else if (element instanceof BreakableWall) {
				BreakableWall wall = (BreakableWall) element;
				if (Formulas.distance(x, wall.getCenterX(), y, wall.getCenterY()) < radius) {
					removeWalls.add(wall);
					wallFragments.addAll(wall.getWallFragments());
				}
			}
		}

		//handles projectiles
		for (Projectile projectile: projectiles) {
			if (Formulas.distance(projectile.getCenterX(), x, projectile.getCenterY(), y) < radius) {
				removeProjectiles.add(projectile);
			}
		}

		//removes tanks to be removed
		for (Tank tank: removeTanks) {
			elements.remove(tank);
		}

		//removes BreakableWalls to be removed
		for (BreakableWall wall: removeWalls) {
			elements.remove(wall);
		}

		//removes Projectiles to be removed
		//also removes the Projectiles from the tank that fired them
		for (Projectile projectile: removeProjectiles) {
			for (Element element: elements) {
				if (element instanceof Tank) {
					((Tank) element).getProjectiles().remove(projectile);
				}
			}
			projectiles.remove(projectile);
		}
		
	}

	/**
	 * Gets the Player
	 * @return the Player
	 */
	public Player getPlayer() {
		for (Element element: elements) {
			if (element instanceof Player) {
				return (Player)element;
			}
		}
		return null;
	}

	/**
	 * Gets all Elements
	 * @return an ArrayList of Elements
	 */
	public CopyOnWriteArrayList<Element> getElements() {
		return elements;
	}

	/**
	 * Gets all Bombs
	 * @return an ArrayList of Bombs
	 */
	public CopyOnWriteArrayList<Bomb> getBombs() {
		return bombs;
	}
	
	/**
	 * Get all Projectiles
	 * @return an ArrayList of Projectiles
	 */
	public CopyOnWriteArrayList<Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * Gets if the player is dead
	 * @return a boolean
	 */
	boolean isPlayerDead() {
		return playerDead;
	}

	/**
	 * Changes the level to the next level
	 * @throws FileNotFoundException from LevelCreator.getNextLevel()
	 */
	void nextLevel() throws FileNotFoundException {
		Main.panel.clearTracks();
		getPlayer().clearWeapons();
		projectiles.clear();
		bombs.clear();
		changeLevel(LevelCreator.getNextLevel());
	}
	
	boolean levelFinished() {
		for (Element element: elements) {
			if (element instanceof EnemyTank) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the x size of the Level
	 * @return x size of the Level
	 */
	public int getSizeX() {
		return sizeX;
	}

	/**
	 * Gets the y size of the Level
	 * @return y size of the Level
	 */
	public int getSizeY() {
		return sizeY;
	}

	/**
	 * Paint the Level
	 * Paints all Elements, Projectiles, Bombs, and WallFragments
	 * @param g the Graphics to be painted to
	 */
	void paint(Graphics g) {
		//paints Bombs
		for (Bomb bomb: bombs) {
			bomb.paint(g);
		}

		//paints Holes
		for (Element element: elements) {
			if (element instanceof Hole) {
				element.paint(g);
			}
		}

		//paints Projectile Trails
		for (Effect effect: projectileEffects) {
			effect.paint(g);
		}

		//paints Projectiles
		for (Projectile projectile: projectiles) {
			projectile.paint(g);
		}

		//paints WallFragments
		for (WallFragment fragment: wallFragments) {
			fragment.paint(g);
		}

		//paints sides of Walls and Tanks
		double currentY = 0;
		for (Element element: elements) {
			if (element instanceof Wall && !(element instanceof Hole)) {
				element.paint(g);
				currentY = element.y;
			}
			for (Element other: elements) {
				if (other instanceof Tank && other.y > currentY) {
					other.paint(g);
				}
			}
		}

		//paints tops of Walls
		for (Element element: elements) {
			if (element instanceof Wall && !(element instanceof Hole)) {
				((Wall) element).paintTop(g);
			}
		}
		
		for (Element element: elements) {
			if (element instanceof Tank) {
				((Tank) element).paintBarrel(g);
			}
		}
	}
}
