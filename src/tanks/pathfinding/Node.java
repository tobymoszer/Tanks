package tanks.pathfinding;

/**
 * A class representing a Node for tanks.pathfinding
 */
public class Node {

    /**
     * The parent node
     */
    Node parent;
    /**
     * The position of the node
     */
    int x, y;
    /**
     * The scores of the node
     */
    double g, h, f;

    /**
     * Constructs a new Node
     * @param parent the parent Node
     * @param x the x position of the Node
     * @param y the y position of the Node
     */
    Node(Node parent, int x, int y) {
        this.parent = parent;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x position of the Node
     * @return the x position of the Node
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y position of the Node
     * @return the y position of the Node
     */
    public int getY() {
        return y;
    }

    /**
     * @return the x and y positions in a Stirng
     */
    @Override
    public String toString() {
        return "" + x + " " + y;
    }
}
