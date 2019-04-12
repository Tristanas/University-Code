/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pankas;

import javafx.application.Application;;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Vilius
 */
public class Pankas extends Application {

   
    public static Stage window;
    public static Scene registracija, pradzia, statistika;
    private static int suma;
    private static int metai;
    private static int menesiai;
    private static float procentai;
    private static boolean anuitetas;
    private static boolean galimaPlanuoti;
    @Override
    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Bank.fxml"));
        
        Pankas.window = window;
        registracija = new Scene(root);
        root = FXMLLoader.load(getClass().getResource("pradzia.fxml"));
        pradzia = new Scene(root);
        root = FXMLLoader.load(getClass().getResource("statistika.fxml"));
        statistika = new Scene(root);
        window.setScene(pradzia);
        window.show();
        
       
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
     /**
     * @return the suma
     */
    public static int getSuma() {
        return suma;
    }

    /**
     * @param aSuma the suma to set
     */
    public static void setSuma(int aSuma) {
        suma = aSuma;
    }

    /**
     * @return the metai
     */
    public static int getMetai() {
        return metai;
    }
    
     public static int getLaikas() {
        return getMetai() * 12 +getMenesiai();
    }

    /**
     * @param aMetai the metai to set
     */
    public static void setMetai(int aMetai) {
        metai = aMetai;
    }

    /**
     * @return the menesiai
     */
    public static int getMenesiai() {
        return menesiai;
    }

    /**
     * @param aMenesiai the menesiai to set
     */
    public static void setMenesiai(int aMenesiai) {
        menesiai = aMenesiai;
    }

    /**
     * @return the procentai
     */
    public static float getProcentai() {
        return procentai;
    }

    /**
     * @param aProcentai the procentai to set
     */
    public static void setProcentai(float aProcentai) {
        procentai = aProcentai;
    }

    /**
     * @return the anuitetas
     */
    public static boolean isAnuitetas() {
        return anuitetas;
    }

    /**
     * @param aAnuitetas the anuitetas to set
     */
    public static void setAnuitetas(boolean aAnuitetas) {
        anuitetas = aAnuitetas;
    }

    /**
     * @return the galimaPlanuoti
     */
    public static boolean isGalimaPlanuoti() {
        return galimaPlanuoti;
    }

    /**
     * @param aGalimaPlanuoti the galimaPlanuoti to set
     */
    public static void setGalimaPlanuoti(boolean aGalimaPlanuoti) {
        galimaPlanuoti = aGalimaPlanuoti;
    }
}
