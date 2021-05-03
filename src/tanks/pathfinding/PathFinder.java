package tanks.pathfinding;

import main.Level;
import tanks.enemies.EnemyTank;
import tanks.weapons.Bomb;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A class representing a PathFinder for a Level
 */
public class PathFinder {

    /**
     * The Level the path finding will take place in
     */
    private Level level;
    /**
     * The EnemyTank that the path will be found for
     */
    private EnemyTank tank;
    /**
     * ArrayLists for the open and closed nodes
     */
    private ArrayList<Node> open, closed;
    /**
     * A Node[][] for all possible nodes in the level
     */
    private Node[][] nodes;

    /**
     * Constructs a new PathFinder
     * @param level the Level the path finding will take place in
     * @param tank the EnemyTank that the path will be found for
     */
    public PathFinder(Level level, EnemyTank tank) {
        this.level = level;
        this.tank = tank;
        nodes = new Node[level.getSizeX()][level.getSizeY()];
        open = new ArrayList<>();
        closed = new ArrayList<>();
        resetNodes();
    }

    /**
     * Generates a path to a destination
     * @param destination the destination of the path
     * @return an ArrayList of Nodes that contains each Node in the path
     */
    public ArrayList<Node> generatePath(int[] destination) {

        open.clear();
        closed.clear();
        resetNodes();
        Node current = nodes[(int) tank.x][(int) tank.y];
        Node start = nodes[(int) tank.x][(int) tank.y];
        Node target = nodes[destination[0]][destination[1]];

        ArrayList<Node> path = new ArrayList<>();

        open.add(current);

        while (true) {

            if (open.size() != 0) {
                current = null;
            }

            for (Node node: open) {
                if (current == null || node.f < current.f) {
                    current = node;
                }
            }

            open.remove(current);
            closed.add(current);

            if (current == target) {
                break;
            }

            ArrayList<Node> neighbors = getNeighbors(current, target);

            for (Node neighbor: neighbors) {
                if (closed.contains(neighbor)) {
                    continue;
                }

                if (open.contains(neighbor)) {
                    if (neighbor.g > current.g + getNewG(neighbor, current)) {
                        neighbor.g = current.g + getNewG(neighbor, current);
                        neighbor.f = neighbor.g + neighbor.h;
                        neighbor.parent = current;
                    }
                } else {

                    open.add(neighbor);
                    neighbor.parent = current;

                    neighbor.g = current.g + getNewG(neighbor, current);
                    neighbor.h = Math.pow(Math.pow(neighbor.x - destination[0], 2) + Math.pow(neighbor.y - destination[1], 2), .5) * 10;

                    //check for near walls and adjust heuristic
                    if (isWallNearby(neighbor.getX(), neighbor.getY())) {
                        neighbor.h += 50;
                    }

                    neighbor.f = neighbor.g + neighbor.h;
                }
            }

            if (open.size() == 0) {
                System.out.println("No Path Found");
                return path;
            }
        }

        ArrayList<Node> backwardsPath = new ArrayList<>();
        while (current != start) {
            backwardsPath.add(current);
            current = current.parent;
        }

        for (int i = backwardsPath.size() - 1; i >= 0; i--) {
            path.add(backwardsPath.get(i));
        }

        return path;

    }

    /**
     * Calculates the g of a Node based on the parent
     * @param current the Node to calculate for
     * @param parent the Parent of that Node
     * @return the new g value
     */
    private int getNewG(Node current, Node parent) {
        if (current.x == parent.x || current.y == parent.y) {
            return 10;
        } else {
            return 14;
        }
    }

    /**
     * Gets the neighbors of a given Node
     * This does not include neighbors that are pushed up against Walls,
     * unless the Node is very near the beginning or end of the path
     * @param current the Node to get the neighbors of
     * @param target the target Node for the path
     * @return an ArrayList of Nodes of all neighbors
     */
    private ArrayList<Node> getNeighbors(Node current, Node target) {

        ArrayList<Node> neighbors = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if ((i != 0 || j != 0) && !level.wallAt(current.x + i, current.y + j) && (i == 0 || j == 0)) {
                    if (!cutsCorner(current.x, i, current.y, j) && current.x + i > 0 && current.y + j > 0) {
                        neighbors.add(nodes[current.x + i][current.y + j]);
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Determines if a node is near a wall in a 3x3 square
     * Used to increase the heuristic for nodes near walls
     * @param x the x position of the node
     * @param y the y position of the node
     * @return a boolean representing if there is a nearby wall
     */
    private boolean isWallNearby(int x, int y) {

        for (int j = -1; j <= 1; j ++) {
            for (int i = -1; i <= 1; i++) {
                if (level.wallAt(x + i, y + j)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Determines if a position is pushed up against a wall
     * This is only the case if it is a flat wall with no other walls around
     * @param x the x position
     * @param y the y position
     * @return a boolean representing if the position is pushed up against a Wall
     */
    private boolean isPushedUpAgainstWall(int x, int y) {

        if (closed.size() < 3) {
            return false;
        }

        int count = 0;
        boolean flag = false;

        for (int j = -1; j <= 1; j += 2) {
            for (int i = -1; i <= 1; i++) {
                if (level.wallAt(x + i, y + j)) {
                    count++;
                }
            }
            if (flag) {
                return false;
            }
            if (count == 3) {
                flag = true;
            }

            for(int i = -1; i <= 1; i++) {
                if (level.wallAt(x + j, i)) {
                    count++;
                }
            }
            if (flag) {
                return false;
            }
            if (count == 3) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Determine if a path cuts a corner
     * @param x the current x position
     * @param xOff the next x position
     * @param y the current y position
     * @param yOff the next y position
     * @return a boolean representing if the path cuts a corner
     */
    private boolean cutsCorner(int x, int xOff, int y, int yOff) {
        return level.wallAt(x + xOff, y) && level.wallAt(x, y + yOff);
    }

    /**
     * Resets all nodes
     * This is when a new path is starting its generation
     */
    private void resetNodes() {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[0].length; j++) {
                nodes[i][j] = new Node(null, i, j);
            }
        }
    }
    
    public boolean goesThroughBombPath(ArrayList<Node> path) {
        CopyOnWriteArrayList<Bomb> bombs = level.getBombs();
        
        int startIndex = getFirstNodeOutsideOfBombs(bombs, path);
        
        for (int i = startIndex; i < path.size(); i++) {
            for (Bomb bomb: bombs) {
                if (bomb.withinExplosionRadius(path.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private int getFirstNodeOutsideOfBombs(CopyOnWriteArrayList<Bomb> bombs, ArrayList<Node> path) {
        
        int index = 0;
        
        for (int i = 0; i < path.size(); i++) {
            boolean flag = false;
            for (Bomb bomb: bombs) {
                if (bomb.withinExplosionRadius(path.get(i))) {
                    flag = true;
                }
            }
            if (!flag) {
                index = i;
            }
        }
        return index;
    }
}
