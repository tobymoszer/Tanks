package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A JPanel that paints the game
 */
public class MyPanel extends JPanel implements KeyListener, MouseListener {

	/**
	 * The game
	 */
	private Game game;
	/**
	 * Booleans representing what keys are pressed for player movement
	 */
	private boolean w = false, a = false, s = false, d = false;
	/**
	 * A boolean representing if the player should shoot
	 */
	private boolean shoot = false;
	/**
	 * A BufferedImage that is the background
	 * This also includes all of the tracks of the tanks
	 */
	public BufferedImage background;

	/**
	 * Constructs a new MyPanel
	 * Creates the background
	 * @param game the game
	 */
	MyPanel(Game game) {

		background = new BufferedImage(Main.sizeX * Main.scale, Main.sizeY * Main.scale, BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < background.getWidth(); i++) {
			for (int j = 0; j < background.getHeight(); j++) {
				background.setRGB(i, j, new Color(217, 181, 115).getRGB());
			}
		}

		this.game = game;
	}

	/**
	 * Updates the game
	 * Moves the player
	 * Calls for the player to shoot
	 * Calls repaint()
	 * @throws FileNotFoundException from game.update()
	 */
	void update() throws FileNotFoundException {
		
		game.update();
		
		if (w) {
			if (a) {
				game.getLevel().movePlayer("upleft");
			} else if (d) {
				game.getLevel().movePlayer("upright");
			} else if (!s) {
				game.getLevel().movePlayer("up");
			}
		} else if (a) {
			if (s) {
				game.getLevel().movePlayer("downleft");
			} else if (!d) {
				game.getLevel().movePlayer("left");
			}
		} else if (s) {
			if (d) {
				game.getLevel().movePlayer("downright");
			} else {
				game.getLevel().movePlayer("down");
			}
		} else if (d) {
			game.getLevel().movePlayer("right");
		}
		
		Point point = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(point, this);

		if (game.getLevel().getPlayer() != null) {
			game.getLevel().getPlayer().lookAt(point);
		}
		
		if (shoot) {
			shoot = false;

			if (game.getLevel().getPlayer() != null) {
				game.getLevel().getPlayer().shoot();
			}
		}
		
		repaint();
	}

	/**
	 * Clears the tracks on the background image
	 */
	void clearTracks() {
		for (int i = 0; i < background.getWidth(); i++) {
			for (int j = 0; j < background.getHeight(); j++) {
				background.setRGB(i, j, new Color(217, 181, 115).getRGB());
			}
		}
	}

	/**
	 * Paints the background, then the game
	 * @param g the Graphics to be painted to
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.drawImage(background, 0, 0, null);

		game.paint(g);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	/**
	 * Sets movement booleans when a key is pressed
	 * @param e the KeyEvent for the key being pressed
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 87) { //w
			w = true;
		}
		if (e.getKeyCode() == 65) { //a
			a = true;
		}
		if (e.getKeyCode() == 83) { //s
			s = true;
		}
		if (e.getKeyCode() == 68) { //d
			d = true;
		}
	}

	/**
	 * Sets movement booleans when a key is released
	 * @param e the KeyEvent for the key being released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 87) { //w
			w = false;
		}
		if (e.getKeyCode() == 65) { //a
			a = false;
		}
		if (e.getKeyCode() == 83) { //s
			s = false;
		}
		if (e.getKeyCode() == 68) { //d
			d = false;
		}
		if (e.getKeyCode() == 32) {
			game.getLevel().getPlayer().layBomb();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	
	}

	/**
	 * Sets the shoot boolean for the player when the mouse is pressed
	 * @param e the MouseEvent for the mouse being pressed
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		shoot = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
	
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
	
	}
	
}
