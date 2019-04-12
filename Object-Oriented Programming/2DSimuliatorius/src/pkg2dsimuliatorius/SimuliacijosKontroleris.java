/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dsimuliatorius;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
//import java.
/**
 *
 * @author Vilius
 */
public class SimuliacijosKontroleris implements Initializable {
    @FXML 
    private Button prideti;
    @FXML
    private Label label;
    @FXML
    private Button atstatyti;
    @FXML
    private Button vykdyti;
    @FXML
    private Button isvalyti;
    @FXML
    private TextArea kodas;
    @FXML
    private ListView funkcijos;
    @FXML
    private ImageView zemelapis;
    @FXML
    private Pane wraper;
    String zaidejoPNG = "pradinis.png";
    @FXML
    private AnchorPane SimulationPane;
    @FXML
    private Slider simuliacijosGreitis;
    
    Vykdytojas vykdytojas;
    Thread t;
    static double pradinisX = 33, pradinisY = 51;
    double periodas;
    private Player zaidejas;
    ArrayList<Komanda> komandos = new ArrayList<>();
    String[] komanduSarasas = {"eiti", "sukti", "nustatyti sukimo_greiti", "nustatyti sukimo_kieki", "nustatyti greiti", "nustatyti pasukima"};
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            funkcijos.getItems().addAll(Arrays.asList(komanduSarasas));
            simuliacijosGreitis.setMax(100);
            simuliacijosGreitis.setMin(10);
            simuliacijosGreitis.setValue(100);
            periodas = 100;
            FileInputStream paveiksloIvestis = new FileInputStream(zaidejoPNG);
            Image veikejoNuotrauka = new Image(paveiksloIvestis);
            zaidejas = new Player(veikejoNuotrauka, 2, 0, pradinisX, pradinisY);
            zemelapis.setImage(new Image(new FileInputStream("level0.png")));
            WritablePixelFormat pixelformat = (WritablePixelFormat) zemelapis.getImage().getPixelReader().getPixelFormat();
            
            zaidejas.setZemelapioAukstis(zemelapis.getImage().getHeight());
            zaidejas.setZemelapioIlgis(zemelapis.getImage().getWidth());
            zaidejas.setZemelapioX(zemelapis.getX());
            zaidejas.setZemelapioY(zemelapis.getY());
            wraper.getChildren().add(zaidejas);
            System.out.println("Nuotrauka sekmingai rasta");
        } catch (FileNotFoundException ex) {
            System.out.println("Nuotrauka nerasta");
            Logger.getLogger(SimuliacijosKontroleris.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    @FXML
    private void handleButtonAction(ActionEvent event) throws InterruptedException, FileNotFoundException {
        if (event.getSource() == atstatyti) {
            zaidejas.setX(pradinisX);
            zaidejas.setY(pradinisY);
            zaidejas.setPasukimas(0);
            vykdytojas.galiDirbti = false;
            zemelapis.setImage(new Image(new FileInputStream("level0.png"))); 
        } else  if (event.getSource() == vykdyti) {
            String kodoTekstas = kodas.getText();
            String[] neformatuotosKomandos = kodoTekstas.split("\n");
            komandos = new ArrayList<>();
            for (String str : neformatuotosKomandos) {
                if (!str.isEmpty()) komandos.add(new Komanda(sutvarkytiKomanda(str).split(" ")));
            }
            for (Komanda kmd : komandos) {
                System.out.println(kmd.toString());
            }
            vykdytojas = new Vykdytojas(komandos, zaidejas, (long) periodas, zemelapis);
            t = new Thread(vykdytojas);
            t.start();
        } 
        else if (event.getSource() == isvalyti) {
            kodas.clear();
        } 
        else if (event.getSource() == prideti) {
            kodas.setText(kodas.getText() + funkcijos.getSelectionModel().getSelectedItem() + " (kintamasis)\n");
        }
        
    }
    
    @FXML
    private void keistiGreiti(MouseEvent event) {
        periodas = simuliacijosGreitis.getValue();
    }
    @FXML
    private void printClickCoords(MouseEvent click) {
        System.out.println("Paspaudei: " + click.getX() + ":" + click.getY());
        System.out.println("Zemelapio X: " + zemelapis.getX() + " Y:" + zemelapis.getY());
    }
    
   
    public String sutvarkytiKomanda(String ivestis) {
        String rezultatas = ivestis.trim();
        String[] bePapildomuTarpu = new String[20];
        int counter = 0;
        for (String str : rezultatas.split(" ")) {
            if (!str.isEmpty()) {
                bePapildomuTarpu[counter++] = str;
            }
        }
        String[] tmp = bePapildomuTarpu[counter - 1].split("\n");
        bePapildomuTarpu[counter - 1] = tmp[0];
        rezultatas = "";
        for (int i = 0; i < counter; i++) {
            rezultatas += bePapildomuTarpu[i];
            if (i < counter - 1) rezultatas += " ";
            //System.out.println(i + ": '" + bePapildomuTarpu[i] + "'");
        }
        return rezultatas;
    }
    
    public Color gautiSpalva(int x, int y) {
        return zemelapis.getImage().getPixelReader().getColor(x - (int) zemelapis.getX(), y -  (int) zemelapis.getY());
    }
    
    
//            System.out.println("Simuliacijos duomenys:");
//            System.out.println("Periodas: " + periodas);
//            System.out.println("Zaidejo X: " + zaidejas.getX() + " Y: " + zaidejas.getY());
//            System.out.println("Zaidejo greitis: " + zaidejas.getGreitis() + " , pasisukimas: " + zaidejas.getPasukimas());
}
