/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klases;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import studbaze.StudBaze;
import static studbaze.StudBaze.grupes;
import static studbaze.StudBaze.grupiuVardai;


/**
 *
 * @author Vilius
 */
public class Redakcija extends Langas{
    Button saugotiDuomenis;
    Button pridetiGrupe;
    Button pridetiStudenta;
    Button trintiEile;
    TextField naujoVardas = new TextField();
    TextField naujoPavarde = new TextField();
    TextField naujaGrupe = new TextField();
        
    public Redakcija(int x, int y) {
        this.lentelesAukstis = y;
        this.lentelesIlgis = x;
        
        inicializuoti(StudBaze.grupiuVardai);
        saugotiDuomenis = new Button("Saugoti Grupes");
        pridetiStudenta = new Button("Prideti");
        trintiEile = new Button("Trinti pazymetus studentus");
        pridetiGrupe = new Button("Prideti");
        GridPane.setConstraints(pridetiStudenta, 2, 4);
        GridPane.setConstraints(naujoVardas, 0, 4);
        GridPane.setConstraints(naujoPavarde, 1, 4);
        GridPane.setConstraints(naujaGrupe, 0, 5, 2, 1);
        GridPane.setConstraints(pridetiGrupe, 2, 5);
        GridPane.setConstraints(trintiEile, 0, 6);
        GridPane.setConstraints(saugotiDuomenis, 1, 6);
        naujoVardas.setPromptText("Vardas");
        naujoVardas.setMaxWidth(100);
        naujoPavarde.setMaxWidth(100);
        naujaGrupe.setMaxWidth(200);
        
        naujoPavarde.setPromptText("Pavarde");
        naujaGrupe.setPromptText("Naujos Grupes Pavadinimas");
        pridetiStudenta.setOnAction((ActionEvent ev) -> {
            atliktiVeiksma(ev);
        });
        trintiEile.setOnAction((ActionEvent ev) -> {
            atliktiVeiksma(ev);
        });
        pridetiGrupe.setOnAction((ActionEvent ev) -> {
            atliktiVeiksma(ev);
        });
        saugotiDuomenis.setOnAction((ActionEvent ev) -> {
            atliktiVeiksma(ev);
        });
        paruostiVardiniusStulpelius();
        vardiniaiStulpeliaiIKeiciamus();
        
       // grupiuVardai.add("PS1");
        //grupe.getItems().add("PS1");
        grupe.setValue("PS1");
        grupe.setOnAction((ActionEvent ev) -> {
            atliktiVeiksma(ev);
        });
        lentele.setEditable(true);
        lentele.getColumns().addAll(vardai, pavardes);
        lentele.setItems(StudBaze.grupes.get(0).studentai);
        grid.getChildren().addAll(pridetiStudenta, trintiEile, pridetiGrupe, naujoVardas, naujoPavarde, naujaGrupe, saugotiDuomenis);
    }

   

    @Override
    void atliktiVeiksma(ActionEvent ev) {
        if (ev.getSource() == pridetiStudenta) {
            if (!naujoVardas.getText().isEmpty() && !naujoPavarde.getText().isEmpty()) {
                grupes.get(parinktaGrupe).studentai.add(new Studentas(naujoVardas.getText(), naujoPavarde.getText()));
                naujoVardas.clear();
                naujoPavarde.clear();
                //grupes.get(0).studentai.get(0).setVardas("Test");
                System.out.println("Studentas pridetas.");
                //lentele.setItems(StudBaze.grupes.get(parinktaGrupe).studentai);
                lentele.refresh();
        
            }
        }
        else if (ev.getSource() == trintiEile) {
            ObservableList<Studentas> parinktasStudentas, visiStudentai;
            visiStudentai = lentele.getItems();
            parinktasStudentas = lentele.getSelectionModel().getSelectedItems();
            parinktasStudentas.forEach(visiStudentai::remove);
        }
        else if (ev.getSource() == pridetiGrupe){
             if (!naujaGrupe.getText().isEmpty()){
                grupiuVardai.add(naujaGrupe.getText());
                grupe.setItems(grupiuVardai);
                grupes.add(new Grupe(naujaGrupe.getText()));
                //grupiuVardai.add(naujaGrupe.getText());
                naujaGrupe.clear();
                //System.out.println(parinktaGrupe);
            }
        }
        else if (ev.getSource() == grupe) {
            parinktiGrupe();
        }
        else if (ev.getSource() == saugotiDuomenis) {
            try {
                    StudBaze.setBaze(new DuomBaze(grupes));
                    StudBaze.saugotiDuomenis("output.txt", StudBaze.getBaze());
                } catch (IOException ex) {
                    Logger.getLogger(StudBaze.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
    }
    
}
