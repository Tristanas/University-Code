import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.lang.Math;

/**
 * Write a description of class Pacman here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */ 

public class Ghost extends Actor
{
    boolean directions[] = {false, false, false, false};
    public boolean canTurn = false;
    private int labX = 0, labY = 0;
    public int toTurn, nextTurn;
    public boolean needToTurn, inRespawn, destinationReached = true;
    public boolean vulnerable, eaten, followingPuck, runningAway, seesPuck;
    public int respawnX = 16, respawnY = 13, respawnDelay;
    private int goToX, goToY;
    public boolean reachedRespawn, needToChangeImg, readyToRespawn, goingInsideRespawn;
    public boolean readyToWait;
    MyWorld w;
    Greenfoot gf = new Greenfoot();
    GreenfootImage weakImg0 = new GreenfootImage("weak-0.png");
    GreenfootImage weakImg1 = new GreenfootImage("weak-1.png");
    GreenfootImage weakImg2 = new GreenfootImage("weak-2.png");
    GreenfootImage weakImg3 = new GreenfootImage("weak-3.png");
    Pacman puck;
    private final int TILE_SIZE = 16, FIRST_PELLET = 9;
    public final int sightRange = 6; 
    int rotationMultiplicator = 1;
    /**
     * Act - do whatever the Pacman wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    public void actEaten()
    {
        if(readyToWait) {
            move(4);
            getLabCoords();
            if(atMiddleOfTile(4) && !readyToRespawn) inRespawnRandomRotation();
            respawnDelay--;
            if(respawnDelay < 1 && atMiddleOfTile(4) && (labX == 16 || labX == 15))
            {
                readyToRespawn = true;
                setRotation(270);
            }
            if(labY == 13) {
                eaten = false;
                readyToRespawn = false;
                readyToWait = false;
                inRespawn = false;
            }
        }
        else toRespawnMid();
    }
    public void toRespawnMid()
    {
        if(!inRespawn) {
            teleportAtBorder();
            move(16);
            turnTowards(233, 185);
            getLabCoords();
            int dLabX = java.lang.Math.abs(labX - respawnX);
            int dLabY = java.lang.Math.abs(labY - respawnY);
            if(dLabX < 3 && dLabY < 3) {
                setInRespawn();
                changeImage();
                while(getX() != 233 || getY() != 185) {
                    turnTowards(233, 185);
                    move(1);
                }
                setRotation(90);
                goingInsideRespawn = true;
            }
        }
        else {
            turnTowards(233, 233);
            move(4);
            getLabCoords();
            if(labX == 16 && labY == 16 && atMiddleOfTile(4)) {
                goingInsideRespawn = false;
                readyToWait = true;
                setRotation(270);
                setLocation(233, 233);
                respawnDelay = 96;
            }
        }
    }
    
    public int toRespawn() 
    {
        if(java.lang.Math.abs(labX - respawnX) > java.lang.Math.abs(labY - respawnY)){
            if(labX < respawnX) return 2;
            return 0;
        }
        else 
        {
            if(labY < respawnY) return 1;
            else return 3;
        }
    }

    public void setInRespawn()
    {
        inRespawn = true;
    }

    public void inRespawnRandomRotation()
    {
        setRotation(getRotation() + 90 * rotationMultiplicator);
        changeImage();
    }

    public void getLabCoords()
    {
        // int deltaX = getImage().getHeight() / 2;
        // int deltaY = getImage().getWidth() / 2;
        labX =  2 + ((getX() - FIRST_PELLET ) / TILE_SIZE);
        labY =  2 + ((getY() - FIRST_PELLET ) / TILE_SIZE);
    }

    /**
     * Checks whether the ghost is in the middle of 
     * a tile and keeps it from going into walls 
     * in case of exceptions.
     * @return 
     */
    public boolean atMiddleOfTile(int speed) 
    {
        int spd = speed / 2;
        //move(1);
        getLabCoords();
        int x = (labX - 2) * TILE_SIZE,
        y = (labY - 2) * TILE_SIZE, FP = FIRST_PELLET;
        if (labX == goToX && labY == goToY) 
            destinationReached = true;
        return (getX() >= FP + x - spd && getX()<= FP + x + spd) && (getY() >= FP + y - spd && getY() <= FP + y + spd);
    }

    public void setDirection()
    {
        boolean i = canSeePuck();
        if(i) startFollow();
        else if(!followingPuck && !runningAway) randomTurn(-1);
    }

    public void turnTowardsPuck(int i) 
    {
        toTurn = (toPuck() + 2*i)%4;
    }

    public int toPuck() 
    {
        if(labX < puck.labX) return 0;
        if(labX > puck.labX) return 2;
        if(labY < puck.labY) return 1;
        if(labY > puck.labY) return 3;
        return -1;
    }

    public void startFollow()
    {
        needToTurn = true;
        if(!vulnerable) {
            goToX = puck.labX;
            goToY = puck.labY;
            turnTowardsPuck(0);
            nextTurn = puck.getRotation() / 90;
            destinationReached = false;
            followingPuck = true;
        }
        else  {
            turnTowardsPuck(1);
            runningAway = true;
        }
    }

    public void randomTurn(int prefference)
    {

        boolean directions[] = {false, false, false, false};
        if (w.labirintas[labY][labX + 1]) directions[0] = true;
        if (w.labirintas[labY + 1][labX]) directions[1] = true;
        if (w.labirintas[labY][labX - 1]) directions[2] = true;
        if (w.labirintas[labY - 1][labX]) directions[3] = true;
        if(prefference > -1)  {   
            if(directions[prefference]) {
                toTurn = prefference;
                needToTurn = true;
            }
        }
        else {
            int randomNumber = gf.getRandomNumber(9), rotation = (getRotation() / 90 + 2) % 4;
            boolean changedDirection = false;
            int i = 0;
            while (!changedDirection && i < 4 ) {
                if(directions[(i + randomNumber) % 4] && (i + randomNumber) % 4 != rotation) {
                    if(eaten)setRotation((randomNumber % 4) * 90);
                    toTurn = (i + randomNumber) % 4;
                    changedDirection = true;
                    needToTurn = true;
                }
                i++;
            }
        }
    }   

    /**
     * The method checks in which direction a ghost
     * can turn.
     */
    public void checkDirections()
    {
        directions[0] =  w.labirintas[labY][labX + 1];
        directions[1] = w.labirintas[labY + 1][labX];
        directions[2] = w.labirintas[labY][labX - 1];
        directions[3] = w.labirintas[labY - 1][labX];
    }

    public void changeImage() {}

    /**
     * A method to check whether pacman is within
     * 5 tiles and is not behind a wall.
     * @return 
     */
    public boolean canSeePuck()
    {
        List l;
        followingPuck = false;
        runningAway = false;
        if(!vulnerable)l = getObjectsInRange(sightRange * 16, Pacman.class);
        else l = getObjectsInRange(sightRange * 24, Pacman.class);
        if(l.isEmpty()) return false;
        int puckX = puck.labX, puckY = puck.labY;
        if(labX != puckX && labY != puckY) return false;
        if(labX == puckX)
        {
            int deltaY = puckY - labY;
            if (deltaY > 0) while(deltaY != 0) {
                    if(w.labirintas[puckY + deltaY][labX] == false) 
                        return false;
                    deltaY--;
                }
            else while (deltaY !=0) {
                    if(w.labirintas[puckY - deltaY][labX] == false)
                        return false;
                    deltaY++;
                }

            return true;
        }
        if(labY == puckY)
        {
            int deltaX = puckX - labX;
            if (deltaX > 0) while(deltaX != 0) {
                    if(w.labirintas[puckY][labX + deltaX] == false) 
                        return false;
                    deltaX--;
                }
            else while (deltaX !=0) {
                    if(w.labirintas[puckY][labX - deltaX] == false)
                        return false;
                    deltaX++;
                }

            return true;
        }
        return false;
    }

    public void teleportAtBorder()
    {
        if(getRotation() == 180)
            if(getX() < 15) move((25 - getWorld().getWidth()) / 16 * 16);
        if(getRotation() == 0)
            if(getX() > getWorld().getWidth() - 15)
                move(((25 - getWorld().getWidth()) / 16 * 16));
    }

    
    public void checkPoweredUp()
    {
        vulnerable = getWorldOfType(MyWorld.class).poweredUp;
    }

    public void shouldTurn()    {}

    public void shouldTurnRespawn()
    {
        {
            checkDirections();
            int toRespawn = toRespawn();
            randomTurn(-5);
            if(directions[toRespawn]) randomTurn(toRespawn);
            int previousRotation = getRotation();
            setRotation(toTurn*90);
            needToTurn = false;
            if(previousRotation != toTurn * 90) changeImage();
        }
    }

    /**
     * Eat or if you're vulnerable be eaten.
     */
    public void eat()
    {
        Actor puck;
        puck = getOneIntersectingObject(Pacman.class);
        if(puck != null && !vulnerable) getWorld().removeObject(puck);
    }
}
