import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pacman here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */ 

public class Pacman extends Actor
{
    Greenfoot gf = new Greenfoot();
    public int imageCounter, loseImageCounter;
    /** direction
     * 0, 1, 2, 3 = virsun, desine, zemyn, kairen
     */
    MyWorld w;
    private final int FIRST_PELLET = 9, TILE_SIZE = 16;
    public int toTurn = 1;
    public boolean eaten, stuck = true, needToTurn, firstTurn;
    public int combo;
    public int labX, labY;
    // int deltaX = getImage().getHeight() / 2;
    // int deltaY = getImage().getWidth() / 2;
    //directions[] = {desine, apacia, kaire, virsus} ar langeliai laisvi
    boolean directions[] = {true, false, true, false};
    
    //private boolean corners[] = new boolean[4];
   
    /**
     * Act - do whatever the Pacman wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public Pacman ()
    {
        setRotation(90);
    }
    
    /**
     *Do a functions in 1 frame.
     */
    public void act() 
    {
        if(!eaten ) {
            getLabCoords();
            getTurn();
            eat();
            shouldTurn();
            checkDirections();
            if(!stuck && firstTurn) {
                move(4);
                animatePuck();
            }
            if(getX() < 12) {
                move(16 - getWorld().getWidth());
                eat();
                move(10);
            }
            if(getX() > getWorld().getWidth() - 12) {
                move(16 - getWorld().getWidth());
                eat();
                move(10);    
            }
        }
        if(eaten) {
            animateLost();
            if(loseImageCounter > 41) w.playerEaten = true;
        }
       
    }  
    
    public void animateLost()
    {
        if(loseImageCounter < 42)setImage ("hitpuck"+ (1 + (loseImageCounter / 3)) +".jpg");
        loseImageCounter ++;
    }
    public void checkStuck()
    {
        int direction = getRotation() / 90;
        stuck = directions[direction] == false;
    }
    
    public void checkDirections()
    {
        directions[0] = w.labirintas[labY][labX + 1];
        directions[1] = w.labirintas[labY + 1][labX];
        directions[2] = w.labirintas[labY][labX - 1];
        directions[3] = w.labirintas[labY - 1][labX];
    }
    
     public void getLabCoords()
    {
        labX =  2 + ((getX() - FIRST_PELLET + FIRST_PELLET) / TILE_SIZE);
        labY =  2 + ((getY() - FIRST_PELLET + FIRST_PELLET) / TILE_SIZE);
    }
    
    public void getTurn()
    {
        if (gf.isKeyDown("up") || gf.isKeyDown("w")) {
            needToTurn = true;
            toTurn = 3;
        } else {
        }
        if (gf.isKeyDown("d") || gf.isKeyDown("right")){
            needToTurn = true;
            toTurn = 0;
        }
        if (gf.isKeyDown("s") || gf.isKeyDown("down")){
            needToTurn = true;
            toTurn = 1;
        }
        if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("left")) {
            needToTurn = true;
            toTurn = 2;
        }
        if (needToTurn && !firstTurn) firstTurn = true;
    }
    
    
    public void setEaten()
    {
        eaten = true;
    }
    
    /**
     * Pacmano animacija - tai nuolatinis nuotrauku keitimas judejimo metu.
     */
    private void animatePuck()
    {
        int bonus = 4;
        if(w.powerUpTimer> 0) bonus = 0;
        setImage("puck-"+(imageCounter / 2 + bonus)+".jpg");
        imageCounter = (imageCounter + 1) % 8;
    }
    
    
    
    /**
     * Suvalgo sausaini arba super sausaini. Jei pacmanas suvalges super sausaini, 
     * suvalgo (jei salia yra) ir vaiduokli.
     */
    public void eat()
    {
        Actor pellet;
        pellet = getOneIntersectingObject(Pellet.class);
        if(pellet != null) {
            getWorld().removeObject(pellet);
            w.count.addScore(10);
        }
        pellet = getOneIntersectingObject(Powerup.class);
        if(pellet != null) {
            getWorld().removeObject(pellet);
            w.count.addScore(40);
            setPoweredUp();
        }
       
    }
    
    public void setPoweredUp()
    {
        MyWorld world = getWorldOfType(MyWorld.class);
        MyWorld.poweredUp = true;
        MyWorld.powerUpTimer = 300;
        combo = 0;
    }
    
    
    public boolean atMiddleOfTile() 
    {
        int TS = TILE_SIZE;
        int x = (labX - 2) * TS, y = (labY - 2) * TS, FP = FIRST_PELLET;
        
        if (getX() >= FP + x - 2 && getX() <= FP + x + 2)
            if (getY() >= FP + y - 2 && getY() <= FP + y + 2) {
                checkStuck();
                return true;
            }
        return false;
    }
     public void shouldTurn()
    {
        if(atMiddleOfTile() && directions[toTurn]) {
            setRotation(toTurn * 90);
            needToTurn = false;
        }
    }

  
}
 
