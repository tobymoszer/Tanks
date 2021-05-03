package editor;

import elements.BreakableWall;
import elements.Element;
import elements.Hole;
import elements.Wall;
import main.Main;
import tanks.Player;
import tanks.enemies.moving.*;
import tanks.enemies.stationary.StationaryGrayTank;
import tanks.enemies.stationary.StationaryGreenTank;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A class that represents the level editor
 */
class Editor {

    /**
     * An ArrayList of all of the elements in the editor
     */
    private ArrayList<Element> elements;
    /**
     * The size of the level editor
     */
    private int sizeX, sizeY;
    /**
     * The number of the level being edited
     */
    private int levelNumber;

    /**
     * Constructs a new Editor
     * Fills the border of the editor with walls
     * @param sizeX the x size of the Editor
     * @param sizeY the y size of the Editor
     * @param levelNumber the number of the level being edited
     */
    Editor(int sizeX, int sizeY, int levelNumber) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.levelNumber = levelNumber;
        elements = new ArrayList<>();

        setupLevel();
    }

    /**
     * @param e the KeyEvent of the key being pressed
     * @param point the point of the mouse
     * @throws FileNotFoundException if the level file is not found
     */
    void keyPressed(KeyEvent e, Point point) throws FileNotFoundException {

        //convert the mouse point to the correct coordinates (level coordinates)
        int x = point.x / Main.scale;
        int y = point.y / Main.scale;

        if (e.getKeyChar() == 'w') {
            placeWall(x, y);
        } else if (e.getKeyChar() == 'b') {
            placeBreakableWall(x, y);
        } else if (e.getKeyCode() == 8) {
            removeElement(x, y);
        } else if (e.getKeyChar() == 'h') {
            placeHole(x, y);
        } else if (e.getKeyChar() == 'p') {
            placePlayer(x, y);
        } else if (e.getKeyChar() == 'c') {
            elements.clear();
        } else if (e.getKeyChar() == 's') {
            placeStationaryGrayGreenTank(x, y);
        } else if (e.getKeyChar() == 'g') {
            placeGrayGreenTank(x, y);
        } else if (e.getKeyChar() == 'r') {
            placeRedTank(x, y);
        } else if (e.getKeyCode() == 10) {
            saveLevel();
        } else if (e.getKeyChar() == 'y') {
            placeYellowTank(x, y);
        } else if (e.getKeyChar() == 'l') {
            placePurpleTank(x, y);
        } else if (e.getKeyChar() == 'i') {
            placeWhiteTank(x, y);
        }
    }

    /**
     * Removes any element at the given position
     * @param x the x position of the element to be removed
     * @param y the y position of the element to be removed
     */
    private void removeElement(int x, int y) {

        ArrayList<Element> toRemove = new ArrayList<>();

        for (Element element: elements) {
            if (element.x == x && element.y == y) {
                toRemove.add(element);
            }
        }

        elements.removeAll(toRemove);
    }

    /**
     * Places a Wall at the given position
     * If an element exists there already, does nothing and returns
     * @param x the x position of the Wall
     * @param y the y position of the Wall
     */
    private void placeWall(int x, int y) {
        for (Element element: elements) {
            if (element.x == x && element.y == y) {
                return;
            }
        }
        elements.add(new Wall(x, y, sizeX, sizeY));
        Collections.sort(elements);
    }

    /**
     * Places a BreakableWall at the given position
     * If an element exists there already, does nothing and returns
     * @param x the x position of the BreakableWall
     * @param y the y position of the BreakableWall
     */
    private void placeBreakableWall(int x, int y) {
        for (Element element: elements) {
            if (element.x == x && element.y == y) {
                return;
            }
        }
        elements.add(new BreakableWall(x, y, sizeX, sizeY));
        Collections.sort(elements);
    }

    /**
     * Places a Hole at the given position
     * If an element exists there already, does nothing and returns
     * @param x the x position of the Hole
     * @param y the y position of the Hole
     */
    private void placeHole(int x, int y) {
        for (Element element: elements) {
            if (element.x == x && element.y == y) {
                return;
            }
        }
        elements.add(new Hole(x, y, sizeX, sizeY));
        Collections.sort(elements);
    }

    /**
     * Places a Player at the given position
     * If an element exists there already, does nothing and returns
     * @param x the x position of the Player
     * @param y the y position of the Player
     */
    private void placePlayer(int x, int y) {
        for (Element element: elements) {
            if (element.x == x && element.y == y) {
                return;
            }
        }
        elements.add(new Player(x, y, sizeX, sizeY, 0, null));
        Collections.sort(elements);
    }

    /**
     * Places a StationaryGrayTank at the given position
     * If a StationaryGrayTank exists at the position, places a StationaryGreenTank
     * If a StationaryGreenTank exists at the position, places a StationaryGrayTank
     * If any other element exists at the position, does nothing and returns
     * @param x the x position of the StationaryGrayTank/StationaryGreenTank
     * @param y the y position of the StationaryGrayTank/StationaryGreenTank
     */
    private void placeStationaryGrayGreenTank(int x, int y) {
        boolean elementFlag = false, grayFlag = false, greenFlag = false;
        for (Element element: elements) {
            if (element.x == x && element.y == y) {
                elementFlag = true;
                if (element instanceof StationaryGreenTank) {
                    greenFlag = true;
                } else if (element instanceof StationaryGrayTank) {
                    grayFlag = true;
                } else {
                    return;
                }
            }
        }

        if (!elementFlag) {
            elements.add(new StationaryGrayTank(x, y, sizeX, sizeY, 0, null));
        } else {
            removeElement(x, y);
            if (greenFlag) {
                elements.add(new StationaryGrayTank(x, y, sizeX, sizeY, 0, null));
            } else if (grayFlag) {
                elements.add(new StationaryGreenTank(x, y, sizeX, sizeY, 0, null));
            }
        }

        Collections.sort(elements);
    }

    /**
     * Places a GrayTank at the given position
     * If a GrayTank exists at the position, places a GreenTank
     * If a GreenTank exists at the position, places a GrayTank
     * If any other element exists at the position, does nothing and returns
     * @param x the x position of the GrayTank/GreenTank
     * @param y the y position of the GrayTank/GreenTank
     */
    private void placeGrayGreenTank(int x, int y) {
        boolean elementFlag = false, grayFlag = false, greenFlag = false;
        for (Element element: elements) {
            if (element.x == x && element.y == y) {
                elementFlag = true;
                if (element instanceof GreenTank) {
                    greenFlag = true;
                } else if (element instanceof GrayTank) {
                    grayFlag = true;
                } else {
                    return;
                }
            }
        }

        if (!elementFlag) {
            elements.add(new GrayTank(x, y, sizeX, sizeY, 0, null));
        } else {
            removeElement(x, y);
            if (greenFlag) {
                elements.add(new GrayTank(x, y, sizeX, sizeY, 0, null));
            } else if (grayFlag) {
                elements.add(new GreenTank(x, y, sizeX, sizeY, 0, null));
            }
        }

        Collections.sort(elements);
    }

    /**
     * Places a YellowTank at the given position
     * If an element exists there already, does nothing and returns
     * @param x the x position of the YellowTank
     * @param y the y position of the YellowTank
     */
    private void placeYellowTank(int x, int y) {
        for (Element element: elements) {
            if (element.x == x && element.y == y) {
                return;
            }
        }
        elements.add(new YellowTank(x, y, sizeX, sizeY, 0, null));
        Collections.sort(elements);
    }

    /**
     * Places a RedTank at the given position
     * If an element exists there already, does nothing and returns
     * @param x the x position of the RedTank
     * @param y the y position of the RedTank
     */
    private void placeRedTank(int x, int y) {
        for (Element element: elements) {
            if (element.x == x && element.y == y) {
                return;
            }
        }
        elements.add(new RedTank(x, y, sizeX, sizeY, 0, null));
        Collections.sort(elements);
    }

    /**
     * Places a PurpleTank at the given position
     * If an element exists there already, does nothing and returns
     * @param x the x position of the PurpleTank
     * @param y the y position of the PurpleTank
     */
    private void placePurpleTank(int x, int y) {
        for (Element element: elements) {
            if (element.x == x && element.y == y) {
                return;
            }
        }
        elements.add(new PurpleTank(x, y, sizeX, sizeY, 0, null));
        Collections.sort(elements);
    }

    /**
     * Places a WhiteTank at the given position
     * If an element exists there already, does nothing and returns
     * @param x the x position of the WhiteTank
     * @param y the y position of the WhiteTank
     */
    private void placeWhiteTank(int x, int y) {
        for (Element element: elements) {
            if (element.x == x && element.y == y) {
                return;
            }
        }
        elements.add(new WhiteTank(x, y, sizeX, sizeY, 0, null));
        Collections.sort(elements);
    }

    /**
     * Saves the current state of the level
     * This gets saved into the levels folder
     * @throws FileNotFoundException if the level file is not found
     */
    private void saveLevel() throws FileNotFoundException {
        File file = new File("levels/level" + levelNumber);

        PrintStream stream = new PrintStream(file);

        for (Element element: elements) {
            stream.println(element + " " + (int) element.x + " " + (int) element.y);
        }

    }

    /**
     * Places walls on the border of the level
     */
    private void setupLevel() {

        for (int i = 0; i < sizeX; i++) {
            placeWall(i, 0);
            placeWall(i, sizeY - 1);
        }
        for (int i = 0; i < sizeY; i++) {
            placeWall(0, i);
            placeWall(sizeX - 1, i);
        }
    }

    /**
     * Paints the current state of the level
     * @param g the Graphics to be painted to
     */
    void paint(Graphics g) {
        for (Element element: elements) {
            element.paint(g);
            if (element instanceof Wall && !(element instanceof Hole)) {
                ((Wall) element).paintTop(g);
            }
        }
    }
}
