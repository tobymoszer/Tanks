package main;

import tools.OpenSimplexNoise;

import javax.swing.*;
import java.io.FileNotFoundException;

/**
 * Main class for the Tanks game
 */
public class Main {

    /**
     * The x size of the level
     */
    public static final int sizeX = 24;
    /**
     * The y size of the level
     */
    public static final int sizeY = 19;
    /**
     * The scale of the panel
     */
    public static final int scale = 40;
    /**
     * The delay between frames
     */
    private static final int delay = 8;
    /**
     * The level that the game starts at
     */
    private static final int startingLevel = 3;
    /**
     * The time that the game starts at
     */
    public static final long startTime = System.currentTimeMillis();
    /**
     * An instance of OpenSimplexNoise for use anywhere in the program
     */
    public static final OpenSimplexNoise noise = new OpenSimplexNoise();
    /**
     * The panel that contains the game and handles drawing
     */
    public static MyPanel panel;

    /**
     * Main runnable method for the game
     * @param args args for main
     * @throws InterruptedException from Thread.sleep()
     * @throws FileNotFoundException from LevelCreator.getNextLevel()
     */
    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        
        Game game = new Game();
        LevelCreator.setDelay(delay);
        
        Level currentLevel = LevelCreator.getCurrentLevel();
        
        game.loadLevel(currentLevel);
        for (int i = 0; i < startingLevel; i++) {
            game.loadLevel(LevelCreator.getNextLevel());
        }
        
        JFrame frame = new JFrame("main");
        //frame.getContentPane().setLayout(null);
        frame.setSize((sizeX) * scale + 17, (sizeY) * scale + 40);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        panel = new MyPanel(game);
        //panel.setLocation(100, 100);
        //panel.setSize(100, 100);
        frame.add(panel);
        frame.addKeyListener(panel);
        frame.addMouseListener(panel);
        
        panel.setVisible(true);
        frame.setVisible(true);
        
        frame.repaint();
        panel.repaint();
        
        while (true) {
            panel.update();
            Thread.sleep(8);
        }
    }
}
