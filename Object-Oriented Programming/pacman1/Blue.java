import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Blue here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Blue extends Ghost
{
    // MyWorld w;
    GreenfootImage img0 = new GreenfootImage("blue-0.png");
    GreenfootImage img1 = new GreenfootImage("blue-1.png");
    GreenfootImage img2 = new GreenfootImage("blue-2.png");
    GreenfootImage img3 = new GreenfootImage("blue-3.png");

    /**
     * Act - do whatever the Blue wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     * @param puck
     */
    public void act()
    {
        if (!puck.eaten) {
        if(!eaten) {
            if(!vulnerable || (vulnerable && (w.frame % 2 == 0)))actAlive();
        }
        else actEaten();
        }
    }
    public void actAlive()
    {
        needToChangeImg = vulnerable;
        checkPoweredUp();
        //if (needToChangeImg != vulnerable) changeImage();
        changeImage();
        teleportAtBorder();
        getLabCoords();
        move(4);
        //if(canTurn == false) canTurn = checkNewTile();
        eat();
        if(atMiddleOfTile(4)) {
            seesPuck = canSeePuck();
            checkChaseOrRun();
            shouldTurn();
        }
    }
     public void shouldTurn()
    {
        {
            getLabCoords();
            checkDirections();
            int fromPuck = (toPuck() + 2) % 4, topuck = toPuck();
            randomTurn(-5);
            if(runningAway && directions[fromPuck]) randomTurn(fromPuck);
            if(followingPuck && directions[topuck]) randomTurn(topuck); 
            int previousRotation = getRotation();
            setRotation(toTurn*90);
            needToTurn = false;
            if(previousRotation != toTurn * 90) changeImage();
            if(followingPuck && destinationReached) {
                toTurn = nextTurn;
                needToTurn = true;
            }
        }
    }
    public Blue(Pacman puck, int delayTimer, int i)
    {
        this.puck = puck;
        respawnDelay = delayTimer;
        eaten = true;
        readyToWait = true;
        rotationMultiplicator = i;
    }
      public Blue(Pacman puck)
    {
        this.puck = puck;
    }
    public void checkChaseOrRun()
    {
        if(seesPuck) {
            if(vulnerable) runningAway = true;
            else followingPuck = true;
        }
        else runningAway = followingPuck = false;
    }
    /**
     * Eat or if you're vulnerable be eaten.
     */
    public void eat()
    {
        Actor pacman;
        pacman = getOneIntersectingObject(Pacman.class);
        if(pacman != null && !vulnerable) {
            //getWorld().removeObject(puck);
            this.puck.setEaten();
        }
        if(vulnerable && pacman != null && !eaten)
        {
            //getWorld().removeObject(this);
            eaten = true;
            this.puck.combo++;
            w.count.addScore(200 * this.puck.combo);

        }
    }
    public void changeImage()
    {
       // if(!vulnerable) setImage("blue-"+toTurn+".png");
       // else setImage("weak-"+toTurn+".png");
       switch(getRotation() / 90)
       {
           case 0:
            if(!vulnerable || (MyWorld.powerUpTimer < 100 && MyWorld.powerUpTimer % 20 > 10)) setImage(img0);
            else setImage(weakImg0);
            break;
           case 1:
            if(!vulnerable || (MyWorld.powerUpTimer < 100 && MyWorld.powerUpTimer % 20 > 10)) setImage(img1);
            else setImage(weakImg1);
            break;
           case 2:
            if(!vulnerable || (MyWorld.powerUpTimer < 100 && MyWorld.powerUpTimer % 20 > 10)) setImage(img2);
            else setImage(weakImg2);
            break;
           case 3:
            if(!vulnerable || (MyWorld.powerUpTimer < 100 && MyWorld.powerUpTimer % 20 > 10)) setImage(img3);
            else setImage(weakImg3);
            break;
       }
    }

}
