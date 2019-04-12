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
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import studbaze.manoAplikacija;
import static studbaze.StudBaze.grupes;

/**
 *
 * @author Vilius
 */
public abstract class Langas implements manoAplikacija {
    Button saugotiLentele;
    TableView<Studentas> lentele;
    ChoiceBox<String> grupe;
    Button rodyti;
    Button redaguoti;
    Button zymeti;
    Button statistika;
    public static Stage window;
    GridPane grid;
    static public Scene scena1, scena2, scena3;
    public Scene scena;
    int lentelesIlgis = 2, lentelesAukstis = 2;
    static int parinktaGrupe;
    TableColumn<Studentas, String> vardai = new TableColumn("vardas");
    TableColumn<Studentas, String> pavardes = new TableColumn("pavarde");
    
    public void paruostiScena(int x, int y)
    {
        
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        saugotiLentele.setText("Saugoti lentele PDF'e.");
        redaguoti = new Button("Redaguoti");
        zymeti = new Button("Lankomumas");
        statistika = new Button("Statistika");
        redaguoti.setOnAction((ActionEvent event) -> {
            redagaviman();
        });
        zymeti.setOnAction((ActionEvent event) -> {
            lankomuman();
        });
        statistika.setOnAction((ActionEvent event) -> {
            statistikon();
        });
        GridPane.setConstraints(redaguoti, 0, 0);
        GridPane.setConstraints(zymeti, 1, 0);
        GridPane.setConstraints(statistika, 0, 1);
        GridPane.setConstraints(grupe, 1, 1);
        GridPane.setConstraints(lentele, 0, 2, lentelesIlgis, lentelesAukstis);
        grid.getChildren().addAll(grupe, redaguoti, zymeti, statistika, lentele);
        scena = new Scene(grid, x, y);
    }
    abstract void atliktiVeiksma(ActionEvent ac);
    
    public void paruostiVardiniusStulpelius()
    {
        vardai.setEditable(true);
        pavardes.setEditable(true);
        vardai.setMinWidth(100);
        pavardes.setMinWidth(100);
        vardai.setCellValueFactory(new PropertyValueFactory<>("vardas"));
        pavardes.setCellValueFactory(new PropertyValueFactory<>("pavarde"));
        
    }
    static public int gautiDiena(DatePicker kalendorius)
    {
        LocalDate data = kalendorius.getValue();
        //System.out.println(data.getDayOfYear());
        int diena = data.getDayOfYear() + 365*(data.getYear() - 2017) - 244;
        if (data.getYear() > 2020) diena++;
        return diena;
    }
    public void vardiniaiStulpeliaiIKeiciamus()
    {
        vardai.setCellFactory(TextFieldTableCell.forTableColumn());
        vardai.setOnEditCommit((TableColumn.CellEditEvent<Studentas, String> event) -> {
            ((Studentas) event.getTableView().getItems().get(event.getTablePosition().getRow())
                ).setVardas(event.getNewValue());//To change body of generated methods, choose Tools | Templates.
        });
        
        pavardes.setCellFactory(TextFieldTableCell.forTableColumn());
        pavardes.setOnEditCommit((TableColumn.CellEditEvent<Studentas, String> event) -> {
            ((Studentas) event.getTableView().getItems().get(event.getTablePosition().getRow())
                ).setPavarde(event.getNewValue());//To change body of generated methods, choose Tools | Templates.
        });
    }
    public void kurtiPDF(String dest, TableView manoLentele) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();
        PdfPTable table = new PdfPTable(3);
        pridetiPavadinima(document);
        Paragraph naujaEilute = new Paragraph(" ");
        document.add(naujaEilute);
        for(Studentas sd : lentele.getItems()) {
           pridetiLangelius(sd, table);
        }
        document.add(table);
        document.close();
    }
    public void pridetiPavadinima(Document dokumentas) {
        
    }
    public void pridetiLangelius(Studentas sd, PdfPTable pdfLentele) {
        pdfLentele.addCell(sd.getVardas());
        pdfLentele.addCell(sd.getPavarde());
    }
    public void inicializuoti(ObservableList<String> pavadinimuSarasas) {
        lentele = new TableView();
        grupe = new ChoiceBox(pavadinimuSarasas);
        grid = new GridPane();
        saugotiLentele = new Button();
    }
    @Override
    public void redagaviman() {
        window.setScene(scena1);
    }

    @Override
    public void lankomuman() {
        window.setScene(scena2);
    }

    @Override
    public void statistikon() {
        window.setScene(scena3);
    }

    @Override
    public void parinktiGrupe() {
        parinktaGrupe = grupe.getSelectionModel().getSelectedIndex();
        lentele.setItems(grupes.get(parinktaGrupe).studentai);
        //System.out.println(parinktaGrupe);
    }
    
}
