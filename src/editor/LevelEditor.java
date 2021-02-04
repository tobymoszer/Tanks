package editor;

import main.Main;

import javax.swing.*;

/**
 * A Main class for the LevelEditor
 */
public class LevelEditor {

    /**
     * The level number to be edited
     */
    private static final int levelNumber = 0;

    /**
     * Main runnable method for the LevelEditor
     * Sets up JFrame and JPanel
     * @param args args for main
     * @throws InterruptedException from Thread.sleep()
     */
    public static void main(String[] args) throws InterruptedException {

        Editor editor = new Editor(Main.sizeX, Main.sizeY, levelNumber);

        JFrame frame = new JFrame("main");
        frame.setSize((Main.sizeX) * Main.scale + 17, (Main.sizeY) * Main.scale + 40);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        EditorPanel panel = new EditorPanel(editor);
        frame.add(panel);
        frame.addKeyListener(panel);

        panel.setVisible(true);
        frame.setVisible(true);

        frame.repaint();
        panel.repaint();

        while (true) {
            panel.repaint();
            Thread.sleep(17);
        }

    }

}
