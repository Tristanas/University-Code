/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dsimuliatorius;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Vilius
 */
public class Kliutis extends ImageView{
    Image forma;
    public Kliutis(double X, double Y, int plotis, int aukstis) {
        
        setX(X);
        setY(Y);
    }
}
