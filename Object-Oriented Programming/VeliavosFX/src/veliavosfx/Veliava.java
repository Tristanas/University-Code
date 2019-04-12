/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package veliavosfx;

import java.io.Serializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Vilius
 */
public class Veliava extends ImageView implements Serializable{
    
    private String valstybe;
    private String imone;
    private String kodas;
    private String produktai;
    private Double Xsaugojimui;
    private Double Ysaugojimui;
           
    public Veliava(Image im, double X, double Y) {
        super(im);
        valstybe = new String();
        imone = new String();
        kodas = new String();
        Xsaugojimui = X;
        Ysaugojimui = Y;
        setX(X);
        setY(Y);
    }
    
    public Veliava(Veliava vel, Image im, double X, double Y) {
        super(im);
        valstybe = vel.getValstybe();
        imone = vel.getImone();
        produktai = vel.getProduktai();
        kodas = vel.getKodas();
        Xsaugojimui = X;
        Ysaugojimui = Y;
        setX(X);
        setY(Y);
    }
    
    public void nustatytiXY() {
        setX(Xsaugojimui);
        setY(Ysaugojimui);
    }
    public String toString() {
        return valstybe+";"+imone+";"+kodas+";"+produktai;
    }
    /**
     * @return the valstybe
     */
    public String getValstybe() {
        return valstybe;
    }

    /**
     * @param valstybe the valstybe to set
     */
    public void setValstybe(String valstybe) {
        this.valstybe = valstybe;
    }

    /**
     * @return the imone
     */
    public String getImone() {
        return imone;
    }

    /**
     * @param imone the imone to set
     */
    public void setImone(String imone) {
        this.imone = imone;
    }

    /**
     * @return the kodas
     */
    public String getKodas() {
        return kodas;
    }

    /**
     * @param kodas the kodas to set
     */
    public void setKodas(String kodas) {
        this.kodas = kodas;
    }

    /**
     * @return the produktai
     */
    public String getProduktai() {
        return produktai;
    }

    /**
     * @param produktai the produktai to set
     */
    public void setProduktai(String produktai) {
        this.produktai = produktai;
    }

    /**
     * @return the Xsaugojimui
     */
    public Double getXsaugojimui() {
        return Xsaugojimui;
    }

    /**
     * @param Xsaugojimui the Xsaugojimui to set
     */
    public void setXsaugojimui(Double Xsaugojimui) {
        this.Xsaugojimui = Xsaugojimui;
    }

    /**
     * @return the Ysaugojimui
     */
    public Double getYsaugojimui() {
        return Ysaugojimui;
    }

    /**
     * @param Ysaugojimui the Ysaugojimui to set
     */
    public void setYsaugojimui(Double Ysaugojimui) {
        this.Ysaugojimui = Ysaugojimui;
    }
    
    
    
}
