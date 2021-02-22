package tanks.enemies;

import elements.Element;
import main.Level;
import tanks.Player;
import tanks.Tank;
import tools.Formulas;
import tanks.weapons.projectiles.PhantomBullet;

/**
 * A class representing an EnemyTank
 */
public abstract class EnemyTank extends Tank {

	/**
	 * The Player in the current Level
	 */
	protected Player player;
	/**
	 * The fireRate of the EnemyTank
	 */
	protected double fireRate;
	/**
	 * When the last Projectile was fired
	 */
	protected long lastFired;
	/**
	 * The speed that the EnemyTank can change its Aim
	 */
	protected double aimSpeed;

	/**
	 * The number of bounces the tank's projectile has
	 */
	protected int projectileBounces;

	/**
	 * Constructs an EnemyTank
	 * Sets the direction and aim
	 * @param x the x position of the EnemyTank
	 * @param y the y position of the EnemyTank
	 * @param levelSizeX the x size of the Level
	 * @param levelSizeY the y size of the Level
	 * @param delay the delay between frames
	 * @param level the Level that the EnemyTank is in
	 */
	protected EnemyTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
		super(x, y, levelSizeX, levelSizeY, delay, level);
		this.level = level;
		direction = 3 * Math.PI / 2;
		aim = 0;
	}

	/**
	 * Sets the Player to the Player in the Level
	 */
	public void setPlayer() {
		player = level.getPlayer();
	}

	/**
	 * Update the EnemyTank
	 */
	public abstract void update();

	/**
	 * Shoot a Projectile
	 * The type is determined by the subclass
	 */
	protected abstract void shoot();

	/**
	 * Determines if a projectile shot would hit the Player
	 * @param bounces the allowed number of bounces for the Projectile
	 * @return a boolean representing if the Projectile would hit the Player
	 * Returns false if the Projectile would hit another EnemyTank or itself
	 */
	protected boolean willHitPlayer(int bounces) {
		
		PhantomBullet bullet = new PhantomBullet(x + .5 + Math.cos(aim) * 3 / 4 - 1/16.,
				y + .5 + Math.sin(aim) * 3 / 4 - 1/4.,
				aim, levelSizeX, levelSizeY, delay, bounces, level);
		
		while (!bullet.update()) {
			if (bullet.intersects(player)) {
				return true;
			}

			for (Element element: level.getElements()) {
				if (element instanceof EnemyTank) {
					if (bullet.intersects((Tank)element)) {
						return false;
					}
				}
			}

		}
		
		return false;
	}
}
