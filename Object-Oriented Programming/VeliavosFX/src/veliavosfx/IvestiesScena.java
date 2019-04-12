/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package veliavosfx;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Vilius
 */
public class IvestiesScena extends GridPane{
    
    private TextField salis;
    private TextField imone;
    private TextField kodas;
    private TextField produktai;
    private Button sukurti;
    
    
    public IvestiesScena(String mygtukoTekstas) {
        this.sukurti = new Button(mygtukoTekstas);
        this.produktai = new TextField();
        this.kodas = new TextField();
        this.imone = new TextField();
        this.salis = new TextField();
        
        salis.setPromptText("Valstybe");
        imone.setPromptText("Imone");
        kodas.setPromptText("Pasto kodas");
        produktai.setPromptText("Pagr. produktai");
        GridPane.setConstraints(salis, 0, 0);
        GridPane.setConstraints(imone, 1, 0);
        GridPane.setConstraints(kodas, 0, 2);
        GridPane.setConstraints(produktai, 0, 1, 2, 1);
        GridPane.setConstraints(sukurti, 1, 2);
        //sukurti.setText("0");
        this.getChildren().addAll(salis, imone, kodas, produktai, sukurti);
    }
    
    public void isvalytiLaukus()
    {
        salis.clear();
        imone.clear();
        kodas.clear();
        produktai.clear();
    }
    /**
     * @return the sukurti
     */
    public Button getSukurti() {
        return sukurti;
    }

    /**
     * @param sukurti the sukurti to set
     */
    public void setSukurti(Button sukurti) {
        this.sukurti = sukurti;
    }

    /**
     * @return the salis
     */
    public TextField getSalis() {
        return salis;
    }

    /**
     * @return the imone
     */
    public TextField getImone() {
        return imone;
    }

    /**
     * @return the kodas
     */
    public TextField getKodas() {
        return kodas;
    }

    /**
     * @return the produktai
     */
    public TextField getProduktai() {
        return produktai;
    }
}
