import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.EOFException;

import java.io.IOException;
import java.net.URI;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public final class MyWorld extends World
{
    private final int ACC = 8; // accuracy constant for placing of pellets
    private final int TILE_SIZE = 16;
    private final int FIRST_PELLET = 9;
    public boolean playerEaten;
    public static boolean poweredUp;
    public static int powerUpTimer = -1, respawnDelay;
    public Counter count, lifeCounter;
    public int spawnXY[][][] = new int[4][5][2];
    private final setUp powerUpArray = new setUp();
    private final int powerUpSet[] = {0, 1, 2, 1};
    Blue b;
    Red r;
    Purple p;
    Orange o;
    Pacman pacman;
    Labirintas lygis;
    public int lifes = 3, level = 1, frame = 0;
    private boolean levelWon;
    /**  Masyvas, parodantis, kur galima eiti. Jis uzpildomas pasauliui dedant sausainius
     * visose pacmanui pasiekiamose vietose. Tai vaiduoklio judejimui reikalingas masyvas.
     */
    public boolean labirintas[][] = new boolean[40][40];
    ArrayList<Labirintas> lab = new ArrayList();
    
      public void act()
    {
        frame++;
        if(poweredUp == true) powerUpTimer--;
        if(powerUpTimer == 0) {
            poweredUp = false;
            powerUpTimer --;
        }

        if(playerEaten && lifes > 0) 
        {
            deleteGhosts();
            respawnDelay = 20;
            playerEaten = false;
            //newLife();
        }
        if(respawnDelay > 1) {
            respawnDelay--;
            if(respawnDelay == 1 && lifes > 0) newLife();
        }
        if(numberOfObjects() < 8) newLevel();
    }
   /**
     * Constructor for objects of class MyWorld.
     * 
     * @throws java.io.IOException
     */
    public MyWorld() throws IOException
    {    
        //super(369, 405, 1); // senesne versija, pritaikyta smallerversion.jpg, 13x13 pacmanui
        super(450, 530, 1);
        setPaintOrder(Pacman.class, Red.class, Blue.class, Purple.class, Orange.class, Powerup.class);
        //deserializacija
        deserialization();
        prepare();
    }
    
 
    private void prepare()
    {
        prepareActors();
        count = new Counter("Score: ");
        lifeCounter = new Counter("Lives: ");
        addObject(count, 100, 515);
        addObject(lifeCounter, 450, 515);
        lifeCounter.setLives(lifes);
        simplerPrepareFood(1);
    }
    
    /**
     * Method, which converts saved maps of levels from binary to an object.
     * Saves computation, as a repeated analysis of images becomes unnecessary.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private void deserialization () throws FileNotFoundException, IOException
    {
        File file = new File("lab.txt");
        FileInputStream fi = new FileInputStream(file);
        ObjectInputStream input = new ObjectInputStream(fi);
        try {
            while (true) {
                lygis = (Labirintas)input.readObject();
                lab.add(lygis);
            }
        } catch (EOFException ex) {
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MyWorld.class.getName()).log(Level.SEVERE, null, ex);
        }

        input.close();
        fi.close();
    }
    /**
     * Each frame the world checks timers and states in order to respawn or set new level or end game;
     */
  
    /**A class for storing where the actors need to be put in case of new level or new life.  */
    private static class spawnXY {
        int[][] level1 = {{233, 233}, {217, 185}, {233, 217}, {217, 233},{ 217, 377}};
        public spawnXY() {}
    }
    /**
     * Prepare ghosts and pacman after each new level or life lost.
     */
    private void prepareActors()
    {
        Blue b1, b2, b3;
        spawnXY lvl1 = new spawnXY();
        pacman = new Pacman();
        addObject(pacman,lvl1.level1[4][0], lvl1.level1[4][1]);
        pacman.w = this;
        b = new Blue(pacman, 160, -1);
        addObject(b, lvl1.level1[0][0], lvl1.level1[0][1]);
        r = new Red(pacman);
        addObject(r, lvl1.level1[1][0], lvl1.level1[1][1]);
        o = new Orange(pacman, 80, 1);
        addObject(o, lvl1.level1[2][0], lvl1.level1[2][1]);
        p = new Purple(pacman, 120, -1);
        addObject(p, lvl1.level1[3][0], lvl1.level1[3][1]);
        b.w = this;
        r.w = this;
        o.w = this;
        p.w = this;
        // b1.w = this;
        // b2.w = this;
        // b3.w = this;
    }
    /**
     * Prepare pellets for pacman to eat and at the same time construct the labirynth, for the ghosts and pacman to navigate inside.
     */
    private void simplerPrepareFood(int level)
    {
        Labirintas lvl;
        lvl = lab.get(level - 1);
        for(int j = 0; j<40; j++)
            for(int i = 0; i<40; i++) {
                labirintas[j][i] = lvl.lygis[j][i];
                if(labirintas[j][i]) addObject(new Pellet(), (i - 2) * TILE_SIZE + FIRST_PELLET, (j - 2) * TILE_SIZE + FIRST_PELLET);
            }
        prepareLevel(powerUpArray.levels[powerUpSet[level - 1]]);

    }
    
    private void prepareFood(int level)
    {
        int y = FIRST_PELLET;
        
        while ( y < getHeight() - 30) {
            addRow(y);
            y += TILE_SIZE;
        }
         prepareLevel(powerUpArray.levels[powerUpSet[level - 1]]);
    }
    /**A method used for smooth reseting of a level once a ghost catches pacman. */
    public void deleteGhosts()
    {
        removeObject(r);
        removeObject(b);
        removeObject(p);
        removeObject(o);
        removeObject(pacman);
    }
    /** Simple method for object interaction, that can be done without this method.  */
    public void setPlayerEaten()
    {
        playerEaten = true;
    }
    /** A method for adding an entire line of pellets.  */
    private void addRow(int y)
    {
        int x = FIRST_PELLET;
        boolean put;
        while ( x < getWidth() - 1) {
            put = false;
            if(shouldAdd(x, y))  {
                //addObject(new Pellet(), x, y);
                put = true;
            }
            labirintas[2 + ((y - FIRST_PELLET)/TILE_SIZE)][2 + ((x - FIRST_PELLET)/TILE_SIZE)] = put;
            lygis.lygis[(y+23)/16][(x+23)/16] = put;
            x += TILE_SIZE;
        }
    }

    

    /**
     * A method that checks whether there are no walls in the tile. If there aren't, a pellet can be put inside.
     */
    private boolean shouldAdd (int x, int y)
    {
        Color clr = getColorAt(x, y);
        int i = 1, lim = TILE_SIZE / 2;
        if (checkColor(clr)) return false;
        while (i < lim + 1) {
            if (checkColor(getColorAt(x + i, y))) return false;
            if (checkColor(getColorAt(x + i, y + i))) return false;
            if (checkColor(getColorAt(x + i, y - i))) return false;
            if (checkColor(getColorAt(x, y + i))) return false;
            if (checkColor(getColorAt(x, y - i))) return false;
            if (checkColor(getColorAt(x - i, y))) return false;
            if (checkColor(getColorAt(x - i, y + i))) return false;
            if (checkColor(getColorAt(x - i, y - i))) return false;

            if(x+i < getWidth() - 1 && y + i < getHeight() - 1)i++;
            else return true;
        }
        return true;
    }
        /** A game over message */
    public void gameOver()
    {
        this.showText("Game over", getWidth()/2 - 3, getHeight() /2 - 32 );
    }
        /** A level reseting and game ending method.  */
    private void newLife()
    {
        lifes--;
        lifeCounter.reduceLives();
        if(lifes == 0) gameOver();
        else prepareActors();
    }
            /** A compact method for checking whether a pixel is a wall. A wall is anything that is brighter than black or the color code 0x(ACC)(ACC)(ACC) */
    private boolean checkColor(Color clr)
    {
        return (clr.getBlue() > ACC || clr.getRed() > ACC || clr.getGreen() > ACC);
    }
   
            /**A class for storing the locations for power pellets for various levels.  */
    private static class setUp {
        int levels[][][] = 
        {{{425,456},{25,439},{345,119},{26,105},{202,121}},
        {{425,456},{25,439},{425,25},{26,105},{201,137}},
        {{425,456},{25,439},{425,25},{26,105},{201,137}, {233, 472}}};
        
    }
            /** A method for putting in the power ups at the start of a new level. TDA - two D array
     * @param TDA */
    public void prepareLevel(int TDA[][])
    {
        int i = 0;
        while (i < 5) {
            addObject (new Powerup(), TDA[i][0], TDA[i][1]);
            i++;
        }
        if(level == 3) addObject(new Powerup(), TDA[i][0], TDA[i][1]);
    }
            /** A method that handles the transition from one level to another. */
    public void newLevel ()
    {
        if(level < 4) {
            level++;
            setBackground("level-"+level+".png");
            deleteGhosts();
            simplerPrepareFood(level);
            prepareActors();
            poweredUp = false;
            powerUpTimer = 0;
        }
        else gameWon();
    }
            /** A message for completing the game. */
    private void gameWon()
    {
        this.showText("Congratulations, \n You win!", getWidth()/2, getHeight() /2 - 32);
    }
 
  private void testing() 
    {
        int sk;
        Labirintas labTest = lab.get(0);
        for(int i = 0; i<30; i++) {
            for(int j = 0; j<30; j++)
            {
                if(labTest.lygis[j][i]) sk = 1;
                else sk = 0;
                System.out.printf("%d", sk);
            }
        System.out.printf("\n");
        }
    }
    
}    
 
        //serializacija objekto
//        FileOutputStream fo = new FileOutputStream(file);
//        ObjectOutputStream output = new ObjectOutputStream(fo);
        
//        for(int i = 1; i<5; i++) {
//            //level++;
//            lygis = new Labirintas();
//            setBackground("level-"+i+".png");
//            prepareFood(i);
//            lab.add(lygis);
//            level++;
//        }
         //deserializacija
//         for (Labirintas labir : lab) {
//            output.writeObject(labir);
//        }    
        //output.close();
        //fo.close();