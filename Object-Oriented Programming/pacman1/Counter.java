import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Counter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Counter extends Actor
{
    private int data;
    private final String msg;
    public Counter(String msg) {
        data = 0;
        this.msg = msg;
        setImage(new GreenfootImage(200, 40));
        update();
    }
    /**
     * Act - do whatever the Counter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void update() {
        GreenfootImage pav = getImage();
        pav.clear();
        pav.setColor(Color.WHITE);
        pav.drawString(msg+data, 5, 22);
    }
    
    public void reduceLives () {
        data--;
        update();
    }
    
    public void setLives(int amount) {
        data = amount;
        update();
    }

    public void addScore(int amount) {
        data+=amount;
        update();
    }
}
