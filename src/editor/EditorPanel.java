package editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

/**
 * A JPanel that paints the Editor
 */
public class EditorPanel extends JPanel implements KeyListener {

    /**
     * The Editor to be painted
     */
    private Editor editor;

    /**
     * Constructs a new EditorPanel
     * Sets the background color
     * @param editor
     * The editor to be painted
     */
    EditorPanel(Editor editor) {
        setBackground(new Color(217, 181, 115));
        this.editor = editor;
    }

    /**
     * Paints the editor
     * @param g
     * The graphics to paint to
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        editor.paint(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Calls keyPressed in the editor
     * @param e
     * The keyEvent of the key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Point point = MouseInfo.getPointerInfo().getLocation();
        try {
            editor.keyPressed(e, point);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
