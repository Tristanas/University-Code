/*
 * Vilius Minkevicius
 * Obstacle detection
 * lygiai
 * piesimas 
 */
package pkg2dsimuliatorius;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.lang.Math;
/**
 *
 * @author Vilius
 */
public class Player extends ImageView {
    private boolean galiJudeti;
    private double pasukimas;
    private double sukimoKiekis;
    private double sukimoGreitis;
    private boolean piesia;
    private double greitis;
    private Double Xsaugojimui;
    private Double Ysaugojimui;
    private double zemelapioX, zemelapioIlgis, zemelapioY, zemelapioAukstis;
    //double pradinisX = SimuliacijosKontroleris.pradinisX, pradinisY = SimuliacijosKontroleris.pradinisY;
    public Player (Image im, double greitis, double pasukimas, double X, double Y) {
        super(im);
        setX(X);
        setY(Y);
        sukimoKiekis = 0;
        sukimoGreitis = 0;
        this.greitis = greitis;
        this.pasukimas = pasukimas;
    }
    /**
     * @return the greitis
     */
    public double getGreitis() {
        return greitis;
    }

    /**
     * @param greitis the greitis to set
     */
    public void setGreitis(double greitis) {
        this.greitis = greitis;
    }

    /**
     * @return the pasukimas
     */
    public double getPasukimas() {
        return pasukimas;
    }
    
    public void judeti(double kiekis) {
        double aukstis = this.getImage().getHeight();
        double plotis = this.getImage().getWidth();
        setX(getX() + kiekis * Math.cos(pasukimas / 180 * Math.PI));
        setY(getY() + kiekis * Math.sin(pasukimas / 180 * Math.PI));
        
//        if (getX() < pradinisX) setX(pradinisX);
//        if (getY() < pradinisY) setY(pradinisY);
//        if (getX() > pradinisX + zemelapioIlgis - plotis) setX(pradinisX + zemelapioIlgis - plotis);
//        if (getY() > pradinisY + zemelapioAukstis - aukstis) setY(pradinisY + zemelapioAukstis - aukstis);
        
    }
    public boolean arGaliJudeti(){
        boolean galiJudeti = true;
        if (getX() < this.getBoundsInParent().getMinX()) galiJudeti = false;
        if (getY() < this.getBoundsInParent().getMinY() || getY() + getImage().getHeight() > getBoundsInParent().getMaxY()) galiJudeti = false;
        return galiJudeti;
    }
    public void pasukti() {
        //System.out.println("Rotacija: " + pasukimas);
        if (sukimoKiekis > sukimoGreitis) {
            pasukimas = (pasukimas + sukimoGreitis) % 360;
            sukimoKiekis -= sukimoGreitis;
        }
        else {
            if (sukimoKiekis > 0) {
                pasukimas = (pasukimas + sukimoKiekis) % 360;
                sukimoKiekis = 0;
            }
        }
        if (sukimoKiekis < - sukimoGreitis) {
            pasukimas = (pasukimas - sukimoGreitis) % 360;
            sukimoKiekis += sukimoGreitis;
        }
        else if (sukimoKiekis < 0) {
            pasukimas = (pasukimas + sukimoKiekis) % 360;
            sukimoKiekis = 0;
        }
    }
    /**
     * @param pasukimas the pasukimas to set
     */
    public void setPasukimas(double pasukimas) {
        this.pasukimas = pasukimas;
    }

    /**
     * @param zemelapioX the zemelapioX to set
     */
    public void setZemelapioX(double zemelapioX) {
        this.zemelapioX = zemelapioX;
    }

    /**
     * @param zemelapioIlgis the zemelapioIlgis to set
     */
    public void setZemelapioIlgis(double zemelapioIlgis) {
        this.zemelapioIlgis = zemelapioIlgis;
    }

    /**
     * @param zemelapioY the zemelapioY to set
     */
    public void setZemelapioY(double zemelapioY) {
        this.zemelapioY = zemelapioY;
    }

    /**
     * @param zemelapioAukstis the zemelapioAukstis to set
     */
    public void setZemelapioAukstis(double zemelapioAukstis) {
        this.zemelapioAukstis = zemelapioAukstis;
    }

    /**
     * @return the sukimoKiekis
     */
    public double getSukimoKiekis() {
        return sukimoKiekis;
    }

    /**
     * @param sukimoKiekis the sukimoKiekis to set
     */
    public void setSukimoKiekis(double sukimoKiekis) {
        this.sukimoKiekis = sukimoKiekis;
    }

    /**
     * @return the sukimoGreitis
     */
    public double getSukimoGreitis() {
        return sukimoGreitis;
    }

    /**
     * @param sukimoGreitis the sukimoGreitis to set
     */
    public void setSukimoGreitis(double sukimoGreitis) {
        this.sukimoGreitis = sukimoGreitis;
    }

    /**
     * @return the piesia
     */
    public boolean isPiesia() {
        return piesia;
    }

    /**
     * @param piesia the piesia to set
     */
    public void setPiesia(boolean piesia) {
        this.piesia = piesia;
    }
    
}
