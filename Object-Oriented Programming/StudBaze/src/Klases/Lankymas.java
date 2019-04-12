/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klases;

import Klases.Grupe;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import studbaze.StudBaze;

/**
 *
 * @author Vilius
 */
public class Lankymas extends Langas{
    
    TableColumn<Studentas, String> lanko = new TableColumn("Pasirinkite Diena");
    DatePicker kalendorius = new DatePicker();
    Button FuckThingsUp = new Button("Baksnoti studenta");
    int zymejimoDiena = -2;
    
    public Lankymas(int lentelesAukstis, int lentelesIlgis){
        this.lentelesAukstis = lentelesAukstis;
        this.lentelesIlgis = lentelesIlgis;
        
        kalendorius.setOnAction((ActionEvent t) -> {
            int dabartineGrupe = grupe.getSelectionModel().getSelectedIndex();
            //System.out.println(dabartineGrupe);
            
            zymejimoDiena = gautiDiena(kalendorius);
            spausdintiGrupe(StudBaze.grupes.get(dabartineGrupe), zymejimoDiena);
            //StudBaze.grupes.get(dabartineGrupe).nustatytiLankyma(zymejimoDiena);
            for (Studentas sd : lentele.getItems()) {
                if (sd.lankymas[zymejimoDiena] == 'n')sd.setEjoIPaskaita("n");
                else sd.setEjoIPaskaita(" ");
            }
            lanko.setText(kalendorius.getValue().toString());
            //lentele.layout();
            lentele.refresh();
            //System.out.println("Parinkta diena: " + zymejimoDiena);
        });
        
        kalendorius.setMaxWidth(100);
        
        inicializuoti(StudBaze.grupiuVardai); //tableview, choice
        saugotiLentele.setOnAction((ActionEvent t) -> atliktiVeiksma(t));
        
        vardai.setMinWidth(100);
        pavardes.setMinWidth(100);
        vardai.setCellValueFactory(new PropertyValueFactory<>("vardas"));
        pavardes.setCellValueFactory(new PropertyValueFactory<>("pavarde"));
//Nuo cia galima tvarkyti valdyma
        
        
        //Sutvarkau nauja stulpeli, skirta lankomumo zymejimui
        lentele.setEditable(true);
        lanko.setMinWidth(100);
        lanko.setEditable(true);
        lanko.setCellValueFactory(new PropertyValueFactory<>("ejoIPaskaita"));
        lanko.setCellFactory(TextFieldTableCell.forTableColumn());
        
        lanko.setOnEditCommit((TableColumn.CellEditEvent<Studentas, String> event) -> {
            int diena = gautiDiena(kalendorius);
//            Studentas stud = event.getTableView().getItems().get(event.getTablePosition().getRow());
//            if (!event.getNewValue().isEmpty())stud.ejoIPaskaita = event.getNewValue().charAt(0);
//            stud.lankymas[diena] = stud.ejoIPaskaita;
            
             ((Studentas) event.getTableView().getItems().get(event.getTablePosition().getRow())
                ).setEjoIPaskaita(event.getNewValue());
             ((Studentas) event.getTableView().getItems().get(event.getTablePosition().getRow())
                ).lankymas[diena] = event.getNewValue().charAt(0);
             lentele.refresh();
        });
        
        FuckThingsUp.setOnAction((ActionEvent ev) -> {
            Studentas stud = lentele.getSelectionModel().getSelectedItem();
            //System.out.println(lentele.getSelectionModel().getSelectedItem().getPavarde());
            stud.setVardas(stud.getVardas() + " piktas");
        });
        saugotiLentele.setOnAction((ActionEvent t) -> atliktiVeiksma(t));
        
        GridPane.setConstraints(saugotiLentele, 2, 1);
        GridPane.setConstraints(kalendorius, 2, 0);
        //GridPane.setConstraints(FuckThingsUp, 2, 1);
        lentele.getColumns().addAll(vardai, pavardes, lanko);
        lentele.setItems(StudBaze.grupes.get(0).studentai);
        grupe.setItems(StudBaze.grupiuVardai);
        grid.getChildren().addAll(kalendorius, saugotiLentele);
        grupe.setValue("PS1");
        grupe.setOnAction((ActionEvent ev) ->  parinktiGrupe());
       
        //grupe.setItems(grupiuVardai);
        //Langas.grupe.getItems()
        
        
    }
    
    void spausdintiGrupe(Grupe grp, int diena)
    {
        for(Studentas stud : grp.studentai)
        {
            System.out.println(stud.getVardas() + " " + stud.getPavarde() + " " + diena + ": " + stud.lankymas[diena] + ", ir " + stud.getEjoIPaskaita()+".");
        }
    }
     @Override
         public void pridetiPavadinima(Document dokumentas) {
            String eilute = "Lentele studentu lankomumui ";
            Paragraph para = new Paragraph(eilute + " per " + kalendorius.getValue().toString() + ": \n");
        try {
            dokumentas.add(para);
        } catch (DocumentException ex) {
            Logger.getLogger(Statistika.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    @Override
    public void pridetiLangelius(Studentas sd, PdfPTable lentele) {
        super.pridetiLangelius(sd, lentele);
        lentele.addCell(sd.arEjo());
    }
    @Override
    void atliktiVeiksma(ActionEvent ac) {
        try {
                kurtiPDF("lankymo-lentele.pdf", lentele);
            } catch (IOException | DocumentException ex) {
                Logger.getLogger(Statistika.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
}
