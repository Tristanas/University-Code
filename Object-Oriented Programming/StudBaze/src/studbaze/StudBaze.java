/*
 * Autorius:
 * Vilius Minkevicius
 * PS2
 */
package studbaze;

import Klases.DuomBaze;
import Klases.Studentas;
import Klases.Langas;
import Klases.Lankymas;
import Klases.Redakcija;
import Klases.Statistika;
import Klases.Grupe;
import Klases.GrupeSaugojimui;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Vilius
 */
public class StudBaze extends Application {
    
    private static DuomBaze baze = new DuomBaze();
    static public ArrayList<Grupe> grupes = new ArrayList();
    static public ObservableList<String> grupiuVardai = null;
    Grupe psVienas = new Grupe("PS1", 0, FXCollections.observableArrayList(
        new Studentas("Jonas", "Jonaitis"),
        new Studentas("Vardenis", "Pavardenis"),
        new Studentas("Brone", "Vakariene")
    ));
    int parinktaGrupe = 0;
    @Override
    public void start(Stage primaryStage) throws IOException, FileNotFoundException, ClassNotFoundException {
        ChoiceBox test = new ChoiceBox();
        grupiuVardai = test.getItems();
       
        //grupes.add(psVienas);
        //grupiuVardai.add(psVienas.pavadinimas);
        
        duomenuPakrovimas();
        if (grupiuVardai.isEmpty()) System.out.println("Viskas gerai");
        Redakcija redakcija = new Redakcija(3, 2);
        redakcija.paruostiScena(400, 400);
        
        Lankymas lankymoLangas = new Lankymas(3, 3);
        lankymoLangas.paruostiScena(400, 400);
        
        Statistika vertinimas = new Statistika(3, 3);
        vertinimas.paruostiScena(400, 400);
        Langas.window = primaryStage;
        Langas.scena1 = redakcija.scena;
        Langas.scena2 = lankymoLangas.scena;
        Langas.scena3 = vertinimas.scena;
        primaryStage.setTitle("Studentu duomenu baze");
        primaryStage.setScene(Langas.scena1);
        EventHandler<WindowEvent> ivykis = new EventHandler() {
            @Override
            public void handle(Event event) {
                System.out.println("Bandymas uzdaryti fiksuotas.");
                try {
                    //setBaze(new DuomBaze(grupes));
                    saugotiDuomenis("issaugotaIsjungiant.txt", getBaze());
                    primaryStage.close();
                } catch (IOException ex) {
                    Logger.getLogger(StudBaze.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            
        };
        primaryStage.setOnCloseRequest(ivykis);
        primaryStage.show();
    }
    
    public void duomenuPakrovimas() throws IOException, FileNotFoundException, ClassNotFoundException {
        setBaze(ivestiDuomenis("output.txt"));
        getBaze().grupes.stream().map((gr) -> {
            System.out.println("Grupe: " + gr.pavadinimas);
            return gr;
        }).forEachOrdered((gr) -> {
            gr.studentai.forEach((sd) -> {
                System.out.println(sd.getVardas() + " " + sd.getPavarde());
            });
        });
        getBaze().grupes.stream().map((gs) -> new Grupe(gs.pavadinimas, gs.studentai)).map((naujaGrupe) -> {
            grupes.add(naujaGrupe);
            return naujaGrupe;
        }).forEachOrdered((naujaGrupe) -> {
            grupiuVardai.add(naujaGrupe.pavadinimas);
        });
    }
            
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public DuomBaze ivestiDuomenis(String name) throws FileNotFoundException, IOException, ClassNotFoundException {
        //deserializacija
        File file = new File(name);
        FileInputStream fi = new FileInputStream(file);
        ObjectInputStream input = new ObjectInputStream(fi);
        DuomBaze rezultatas = (DuomBaze) input.readObject();
        return rezultatas;
        
    }
    
    static public void saugotiDuomenis(String name, DuomBaze dataBaze) throws FileNotFoundException, IOException {
        File file = new File(name);
        FileOutputStream fo = new FileOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(fo);
        output.writeObject(dataBaze);
        
        output.close();
        fo.close();
    }

    /**
     * @return the baze
     */
    public static DuomBaze getBaze() {
        return baze;
    }

    /**
     * @param aBaze the baze to set
     */
    public static void setBaze(DuomBaze aBaze) {
        baze = aBaze;
    }
}
