package tanks.enemies.moving;

import elements.Element;
import main.Level;
import main.Main;
import tanks.pathfinding.Node;
import tanks.pathfinding.PathFinder;
import tanks.Tank;
import tanks.enemies.EnemyTank;
import tanks.weapons.Bomb;
import tanks.weapons.projectiles.PhantomBullet;
import tanks.weapons.projectiles.Projectile;
import tools.Formulas;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An abstract class representing a MovingTank
 */
public abstract class MovingTank extends EnemyTank {

    /**
     * An ArrayList of the Nodes of the path that the MovingTank is following
     */
    private ArrayList<Node> path;
    
    /**
     * An instance of the pathfinder used to find paths in the level
     */
    private PathFinder finder;
    
    /**
     * How often it will check if a projectile fired would hit the player
     */
    double checkWillHitPlayerFrequency;
    
    /**
     * The number of frames the tank will look into the future to avoid bullets
     */
    private int avoidBulletLookaheadFrames;

    /**
     * True if the conditions of the tank allow for bombs to be laid
     * At the moment this is false if avoiding bombs/projectiles or shooting at player
     * Non bomb laying tanks have this, but don't use it
     */
    protected boolean canLayBomb;

    /**
     * True if the tank is actively avoiding any projectiles
     */
    private boolean isAvoidingProjectiles;

    /**
     * Constructs a MovingTank
     * Sets lastFired to the current time
     * @param x the x position of the MovingTank
     * @param y the y position of the MovingTank
     * @param levelSizeX the x size of the Level
     * @param levelSizeY the y size of the Level
     * @param delay the delay between frames
     * @param level the Level that the MovingTank is in
     */
    MovingTank(double x, double y, int levelSizeX, int levelSizeY, int delay, Level level) {
        super(x, y, levelSizeX, levelSizeY, delay, level);
        lastFired = System.currentTimeMillis();
        path = new ArrayList<>();
        avoidBulletLookaheadFrames = 200;
    }

    /**
     * Avoids a given position
     * @param x the x coordinate of the position to be avoided
     * @param y the y coordinate of the position to be avoided
     */
    protected void avoid(double x, double y) {
        moveAt(this.x + this.x - x, this.y + this.y - y);
        path.clear();
    }

    /**
     * Generates a path if one does not exist
     * If one does, follows the path
     * Does not generate a path to a destination within the explosion radius of a Bomb
     */
    private void generatePathAndFollow() {

        if (path.size() == 0) {
            finder = new PathFinder(level, this);
            int[] position = new int[2];
            if (Math.random() < .4) {
                for (Element element: level.getElements()) {
                    if (element instanceof Tank && element != this) {
                        position[0] = (int)((Tank) element).getCenterX();
                        position[1] = (int)((Tank) element).getCenterY();
                    }
                }
            } else {
                do {
                    position[0] = (int) (Math.random() * (levelSizeX - 2) + 1);
                    position[1] = (int) (Math.random() * (levelSizeY - 2) + 1);
                } while (level.wallAt(position[0], position[1]));
            }

            path = finder.generatePath(position);

        } else {

            try {
                for (Bomb bomb: level.getBombs()) {
                    if (bomb.withinExplosionRadius(path.get(path.size() - 1))) {
                        path.clear();
                        generatePathAndFollow();
                    }
                }

                if (Math.abs(x - path.get(0).getX()) < .5 && Math.abs(y - path.get(0).getY()) < .5) {
                    path.remove(path.get(0));
                }

            } catch (IndexOutOfBoundsException e) {
                System.err.println("Path was empty, multithreading sucks");
            }

            //this causes the "wiggle" in the movement
            if (path.size() != 0 && !isAvoidingProjectiles) {
                double offset = Main.noise.eval(x, y, (System.currentTimeMillis() - Main.startTime) / 4000.)/2.;
                if (level.pointInWall(path.get(0).getX() + offset, path.get(0).getY() + offset)) {
                    moveAt(path.get(0).getX(), path.get(0).getY());
                } else {
                    moveAt(path.get(0).getX() + offset, path.get(0).getY() + offset);
                }
            }
        }
    }

    /**
     * Updates the MovingTank
     * Adds the Tracks
     * Generates a path and follows it
     * Shoots if enough time has elapsed since the last shot and it will hit the Player
     */
    @Override
    public void update() {
        
        addTracks();
        generatePathAndFollow();
        
        //get a path that doesn't go through any bombs
        //this excludes the path starting in a bomb, that is sometimes unavoidable
        while (finder.goesThroughBombPath(path)) {
            generatePathAndFollow();
        }
        
        avoidBombs();

        canLayBomb = !isNearOtherTanks(3);
        
        if (Math.random() < checkWillHitPlayerFrequency) {
            if (System.currentTimeMillis() > lastFired + fireRate && willHitPlayer(projectileBounces)) {
                shoot();
                lastFired = System.currentTimeMillis();
            }
        }
        
    }

    /**
     * Check if this tank is near other tanks
     * @return if this tank is near other tanks
     */
    private boolean isNearOtherTanks(int distance) {
        for (Element element: level.getElements()) {
            if (element instanceof Tank) {
                if (element != this) {
                    if (Formulas.distance(getCenterX(), ((Tank) element).getCenterX(), getCenterY(), ((Tank) element).getCenterY()) < distance) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private void avoidBombs() {

        canLayBomb = true;

        ArrayList<Bomb> avoidBombs = new ArrayList<>();
        for (Bomb bomb: level.getBombs()) {
            if (Formulas.distance(getCenterX(), bomb.getX(), getCenterY(), bomb.getY()) < bomb.getExplosionRadius() + 1 && Formulas.distance(getCenterX(), bomb.getX(), getCenterY(), bomb.getY()) > .5) {
                avoidBombs.add(bomb);
                canLayBomb = false;
            }
        }
    
        //move directly away from any bombs that are close
        double avoidX = 0;
        double avoidY = 0;
        for (Bomb bomb: avoidBombs) {
            avoidX += bomb.getX();
            avoidY += bomb.getY();
        }
    
        avoidX /= avoidBombs.size();
        avoidY /= avoidBombs.size();
    
        if (avoidBombs.size() != 0) {
            avoid(avoidX, avoidY);
        }
    }
    
    /**
     * Calculates if the tank will be hit by a projectile within the next
     * avoidBulletLookaheadFrames frames, and avoids the projectile if so
     */
    protected void avoidProjectiles() {

        canLayBomb = true;

        CopyOnWriteArrayList<Projectile> projectiles = level.getProjectiles();

        boolean avoidingFlag = false;

        for (Projectile projectile: projectiles) {

            if (Formulas.distance(getCenterX(), projectile.getCenterX(), getCenterY(), projectile.getCenterY()) < 5) {

                //System.out.println("near");

                boolean hitFlag = false;

                //copy the projectile into a phantom bullet
                PhantomBullet phantomBullet = new PhantomBullet(projectile.getCenterX(),

                        projectile.getCenterY(),
                        projectile.getDirection(), levelSizeX, levelSizeY, delay, projectile.getBounces(), level);

                for (int i = 0; i < avoidBulletLookaheadFrames; i++) {

                    phantomBullet.update();

                    if (contains(phantomBullet.getCenterX(), phantomBullet.getCenterY())) {

                        //this is the hard part
                        //System.out.println("oh no");
                        hitFlag = true;
                        canLayBomb = false;
                        break;
                    }
                }
                if (hitFlag) {

                    isAvoidingProjectiles = true;
                    avoidingFlag = true;

                    double moveRightWeight = Math.abs(direction - (projectile.getDirection() + Math.PI/2));
                    double moveLeftWeight = Math.abs(direction - (projectile.getDirection() - Math.PI/2));

                    if (moveRightWeight < moveLeftWeight) {
                        moveAtDirection(Formulas.fixAngle(projectile.getDirection() + Math.PI/2));
                    } else {
                        moveAtDirection(Formulas.fixAngle(projectile.getDirection() - Math.PI/2));
                    }

                    //moveAtDirection(Formulas.fixAngle(Math.min(Math.abs(direction - (projectile.getDirection() + Math.PI/2)), Math.abs(direction - (projectile.getDirection() - Math.PI/2)))));

                    //moveAtDirection(Formulas.fixAngle(projectile.getDirection() + Math.PI/2));

                    //System.out.println("avoiding");

                    //avoid(projectile.getCenterX(), projectile.getCenterY());

                } else {
                    if (Formulas.distance(getCenterX(), projectile.getCenterX(), getCenterY(), projectile.getCenterY()) < 2) {
                        avoid(projectile.getCenterX(), projectile.getCenterY());
                    }
                }
            }
        }

        if (!avoidingFlag) {
            isAvoidingProjectiles = false;
        }

    }

    /**
     * Gets the direction to the Player
     * @return an angle representing the direction to the Player
     */
    private double getDirectionToPlayer() {
        double dx = player.getCenterX() - getCenterX();
        double dy = player.getCenterY() - getCenterY();
        if (dx > 0 && dy > 0) {
            return Math.atan(dy/dx);
        } else if (dx > 0 && dy <= 0) {
            return 2 * Math.PI - Math.atan(Math.abs(dy)/dx);
        } else if (dx <= 0 && dy <= 0) {
            return Math.PI + Math.atan(Math.abs(dy) / Math.abs(dx));
        } else if (dx <= 0 && dy > 0) {
            return Math.PI - Math.atan(dy / Math.abs(dx));
        }
        throw new IllegalArgumentException("getDirectionToPlayer failed");
    }

    /**
     * Aims near the Player
     * @param allowedOffset the allowed offset of the angle to the Player
     */
    protected void aimNearPlayer(double allowedOffset) {

        double directionToPlayer = getDirectionToPlayer();

        double offset = Main.noise.eval(x, y, (System.currentTimeMillis() - Main.startTime) * aimSpeed);
        double wantedAimDirection = directionToPlayer + offset * allowedOffset;

        if (wantedAimDirection > aim) {
            if (wantedAimDirection - aim > Math.PI) {
                if (Math.random() < (wantedAimDirection - aim) / allowedOffset + .5) {
                    aim -= aimSpeed * delay;
                } else {
                    aim += aimSpeed * delay;
                }
            } else {
                if (Math.random() < (wantedAimDirection - aim) / allowedOffset + .5) {
                    aim += aimSpeed * delay;
                } else {
                    aim -= aimSpeed * delay;
                }
            }
        } else {
            if (aim - wantedAimDirection > Math.PI) {
                if (Math.random() < (aim - wantedAimDirection) / allowedOffset + .5) {
                    aim += aimSpeed * delay;
                } else {
                    aim -= aimSpeed * delay;
                }
            } else {
                if (Math.random() < (aim - wantedAimDirection) / allowedOffset + .5) {
                    aim -= aimSpeed * delay;
                } else {
                    aim += aimSpeed * delay;
                }
            }
        }

        aim = Formulas.fixAngle(aim);
    }

}
