package main;

import java.awt.*;
import java.io.FileNotFoundException;

/**
 * A class representing a Game
 */
class Game {

	/**
	 * The current level loaded in the game
	 */
	private Level level;

	/**
	 * Loads a level into the game
	 * @param level the level to be loaded
	 */
	void loadLevel(Level level) {
		this.level = level;
		level.sortWalls();
	}

	/**
	 * Updates the current level
	 * If the player is dead, reloads the current level
	 * @throws FileNotFoundException from LevelCreator.getCurrentLevel()
	 */
	void update() throws FileNotFoundException {
		if (level.isPlayerDead()) {
			loadLevel(LevelCreator.getCurrentLevel());
			Main.panel.clearTracks();
		} if (level.levelFinished()) {
			level.nextLevel();
		} else {
			level.update();
		}
	}

	/**
	 * @return the current level
	 */
	Level getLevel() {
		return level;
	}

	/**
	 * Paints the game
	 * @param g the Graphics to be painted to
	 */
	void paint(Graphics g) {
		level.paint(g);
	}
	
}
