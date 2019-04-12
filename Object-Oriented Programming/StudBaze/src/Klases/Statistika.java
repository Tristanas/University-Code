/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klases;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import studbaze.StudBaze;

/**
 *
 * @author Vilius
 */
public class Statistika extends Langas{
    Button tikrinti = new Button("Skaiciuoti kiek praleista");
    DatePicker pradzia = new DatePicker();
    DatePicker pabaiga = new DatePicker();
    int iPradzia; // i. - intervalas
    int iPabaiga;
    TableColumn<Studentas, String> praleista = new TableColumn("praleido");
    
    public Statistika (int plotis, int aukstis)
    {
        this.lentelesAukstis = aukstis;
        this.lentelesIlgis = plotis;
        
        
        inicializuoti(StudBaze.grupiuVardai); //tableview, choice
        paruostiVardiniusStulpelius();
        praleista.setMinWidth(100);
        praleista.setCellValueFactory(new PropertyValueFactory<>("praleido"));
        
        pradzia.setPromptText("Nuo:");
        pabaiga.setPromptText("Iki:");
        GridPane.setConstraints(pradzia, 2, 0);
        GridPane.setConstraints(pabaiga, 2, 1);
        GridPane.setConstraints(tikrinti, 0, 5, 2, 1);
        GridPane.setConstraints(saugotiLentele, 2, 5, 2, 1);
        
        tikrinti.setOnAction((ActionEvent t) -> atliktiVeiksma(t));
        saugotiLentele.setOnAction((ActionEvent t) -> atliktiVeiksma(t));
        lentele.setItems(StudBaze.grupes.get(parinktaGrupe).studentai);
        lentele.getColumns().addAll(vardai, pavardes, praleista);
        grupe.setValue("PS1");
        grupe.setOnAction((ActionEvent ev) ->  parinktiGrupe());
        grid.getChildren().addAll(tikrinti, pradzia, pabaiga, saugotiLentele);
        
    }
    
    
    @Override
    public void pridetiLangelius(Studentas sd, PdfPTable lentele) {
        super.pridetiLangelius(sd, lentele);
        lentele.addCell(Integer.toString(sd.getKiekPraleido()));
    }
    
    @Override
    void atliktiVeiksma(ActionEvent ac) {
        if(ac.getSource() == tikrinti) {
            iPradzia = gautiDiena(pradzia);
            iPabaiga = gautiDiena(pabaiga);
            for (Studentas stud : lentele.getItems()) {
                int counter = 0;
                for (int i = iPradzia; i < iPabaiga; i++) {
                    if (stud.lankymas[i] == 'n') counter++;
                }
                stud.setKiekPraleido(counter);
                stud.setPraleido(Integer.toString(counter));
                System.out.println(stud.getVardas() + " " + stud.getPavarde() + " praleido " + stud.getPraleido());
            }
        //lentele.setItems(StudBaze.grupes.get(parinktaGrupe).studentai);
        lentele.refresh();
        }
        else if(ac.getSource() == saugotiLentele) {
            try {
                kurtiPDF("statistikos-lentele.pdf", lentele);
            } catch (IOException | DocumentException ex) {
                Logger.getLogger(Statistika.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
     @Override
         public void pridetiPavadinima(Document dokumentas) {
            String eilute = "Lentele studentu lankomumui ";
            Paragraph para = new Paragraph(eilute + "tarp " + pradzia.getValue().toString() + " ir " + pabaiga.getValue().toString() + ": \n");
        try {
            dokumentas.add(para);
        } catch (DocumentException ex) {
            Logger.getLogger(Statistika.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
         
}
