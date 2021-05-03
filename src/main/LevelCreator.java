package main;

import elements.Element;
import tanks.enemies.EnemyTank;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A class to load levels
 */
class LevelCreator {

	/**
	 * The level number
	 */
	private static int index = 0;
	/**
	 * The delay between frames
	 */
	private static int delay;
	/**
	 * The size of the level
	 */
	private static int sizeX = 24, sizeY = 19;

	/**
	 * Sets the delay parameter
	 * @param delay the delay
	 */
	static void setDelay(int delay) {
		LevelCreator.delay = delay;
	}

	/**
	 * Gets the current Level
	 * @return the current Level
	 * @throws FileNotFoundException from generate()
	 */
	static Level getCurrentLevel() throws FileNotFoundException {
		return generate();
	}

	/**
	 * Gets the next level
	 * @return the next Level
	 * @throws FileNotFoundException from generate()
	 */
	static Level getNextLevel() throws FileNotFoundException {
		index++;
		return generate();
	}

	/**
	 * Generates a level from the level folder
	 * @return the generated Level
	 * @throws FileNotFoundException from loadLevel
	 */
	private static Level generate() throws FileNotFoundException {

		Level level = loadLevel(new File("levels/level" + index));

		System.out.println(index);

		for (Element element: level.getElements()) {
			if (element instanceof EnemyTank) {
				((EnemyTank) element).setPlayer();
			}
		}

		return level;
	}

	/**
	 * Loads a Level from a given file
	 * @param file the file to load the Level from
	 * @return the new Level
	 * @throws FileNotFoundException if the level file does not exist
	 */
	private static Level loadLevel(File file) throws FileNotFoundException {

		Scanner scanner;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Level file not found");
		}

		Level level = new Level(sizeX, sizeY, delay);

		while (scanner.hasNextLine()) {

			Scanner lineScan = new Scanner(scanner.nextLine());

			String type = lineScan.next();

			switch (type) {

				case "Wall" -> level.addWall(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));
				case "BreakableWall" -> level.addBreakableWall(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));
				case "Hole" -> level.addHole(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));
				case "GrayTank" -> level.addGrayTank(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));
				case "GreenTank" -> level.addGreenTank(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));
				case "RedTank" -> level.addRedTank(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));
				case "YellowTank" -> level.addYellowTank(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));
				case "StationaryGrayTank" -> level.addStationaryGrayTank(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));
				case "StationaryGreenTank" -> level.addStationaryGreenTank(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));
				case "PurpleTank" -> level.addPurpleTank(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));
				case "WhiteTank" -> level.addWhiteTank(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));
				case "Player" -> level.addPlayer(Integer.valueOf(lineScan.next()), Integer.valueOf(lineScan.next()));

			}

		}
		return level;
	}
}
