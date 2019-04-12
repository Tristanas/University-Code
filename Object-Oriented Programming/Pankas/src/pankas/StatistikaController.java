/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pankas;

import classes.ArchController;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
/**
 * FXML Controller class
 *
 * @author Vilius
 */
public class StatistikaController extends ArchController {

    @FXML
    Button atgal;
    @FXML
    Button parodytiMokejimoLaika;
    @FXML
    Button apskaiciuotiMokesti;
    @FXML
    Button apskaiciuotiIntervala;
    @FXML
    Button saugotiDokumente;
    @FXML
    Label mokejimoLaikas;
    @FXML
    Label menesioMokestis;
    @FXML
    Label intervaloMenesiuImokos;
    @FXML
    Label intervaloPabaigosLikutis;
    @FXML
    Label intervaloKreditoSuma;
    @FXML
    Label intervaloPalukanuSuma;
    @FXML
    TextField mokestisMetai;
    @FXML
    TextField mokestisMenesiai;
    @FXML
    TextField intervalasNuoMetai;
    @FXML
    TextField intervalasNuoMenesiai;
    @FXML
    TextField intervalasIkiMenesiai;
    @FXML
    TextField intervalasIkiMetai;
    
    String mokejimoStatistika = "";
    double mokejimoTrukme;    
    int pabMet = 0, pabMen = 0, prMet = 0, prMen = 0;
    
    public void pradzion(ActionEvent event)
    {
        Pankas.window.setScene(Pankas.pradzia);
        System.out.println("Jus ivedete:" );
        System.out.println(" sumosLaukas: " + Integer.toString(Pankas.getSuma()) + " metuLaukas: " 
            + Integer.toString(Pankas.getMetai()) + " menesiai: " + Integer.toString(Pankas.getMenesiai())
            + " proc: " +Float.toString(Pankas.getProcentai())+ " Anuitetas: " + Boolean.toString(Pankas.isAnuitetas()) +
                " Paruosta: " + Boolean.toString(Pankas.isGalimaPlanuoti()));
    }
    
    @FXML
    public void parodytiTrukme(ActionEvent event)
    {
        int metai = Pankas.getMetai(), menesiai = Pankas.getMenesiai();
        mokejimoLaikas.setText(toString(metai) + " m. ir " + toString(menesiai) + " men.");
    }
    @FXML
    public int gautiMenesioMokesti()
    {
        if (mokestisMetai.getLength() == 0 || mokestisMenesiai.getLength() == 0) return 1;
        
        int met = toInt(mokestisMetai.getText()),
            men = toInt(mokestisMenesiai.getText());
        mokejimoTrukme = Pankas.getMenesiai() + 12 * Pankas.getMetai();
        if(Pankas.isAnuitetas()) {
           double rez = gautiAnuitetoMokesti(),
           delta = deltaImoka(ateitiesVerte(12 * met + men, rez));
           menesioMokestis.setText(Double.toString(round(rez - delta, 2))+ " + " + Double.toString(round(delta, 2)) + " eur.");
        }
        else{
            double palukanos = Pankas.getSuma() / mokejimoTrukme * (mokejimoTrukme - met * 12 - men) * procentai(),
                    kreditas = Pankas.getSuma() / mokejimoTrukme;
            menesioMokestis.setText(Double.toString(round(kreditas, 2)) + " + " + Double.toString(round(palukanos, 2)) + " eur.");
        }
        return 0;
    }
    
    @FXML
    public void saugotiDokumente(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException
    {
        fileOut(mokejimoStatistika, "", prMet*12+prMen, pabMet*12+pabMen);
    }
    //Yra paklaida likusios sumos vaizdavime del double naudojimo
    @FXML
    public int gautiIntervaloImokas(ActionEvent event)
    {
        if (intervalasIkiMetai.getLength() == 0 || intervalasIkiMenesiai.getLength() == 0) return 1;
        if (intervalasNuoMetai.getLength() == 0 || intervalasNuoMenesiai.getLength() == 0) return 2;
        
        pabMet = toInt(intervalasIkiMetai.getText());
        pabMen = toInt(intervalasIkiMenesiai.getText());
        prMet = toInt(intervalasNuoMetai.getText());
        prMen = toInt(intervalasNuoMenesiai.getText());
        
        int intervaloGalas = 12*pabMet + pabMen;
        double visosPalukanos = 0, visasKreditas = 0;
        mokejimoTrukme = Pankas.getMenesiai() + 12 * Pankas.getMetai();
        mokejimoStatistika = "";  
        if (Pankas.isAnuitetas())
        {
            double periodoMokestis = gautiAnuitetoMokesti();
            //double isVisoSumoketi = periodoMokestis * mokejimoTrukme;
            double paskola = ateitiesVerte(intervaloGalas, periodoMokestis);
             
            if(intervaloGalas >= mokejimoTrukme) intervaloPabaigosLikutis.setText("0.");
            else {
                intervaloPabaigosLikutis.setText(Double.toString(round(paskola, 2)) + " eur.");
            }
            
            String imoka;
            String palukanoms;
            
            for (int i = 12*prMet + prMen; i <= mokejimoTrukme && i <= intervaloGalas; i++) {
                paskola = ateitiesVerte(i, periodoMokestis);
                imoka = Double.toString(round(periodoMokestis - deltaImoka(paskola) , 2));
                visasKreditas += periodoMokestis - deltaImoka(paskola);
                visosPalukanos += deltaImoka(paskola);
                palukanoms = Double.toString(round(deltaImoka(paskola), 2));
                mokejimoStatistika  += imoka + " + " + palukanoms + " eur; ";
            }
            if(Pankas.getSuma() - visasKreditas < 2) visasKreditas = Pankas.getSuma();
            intervaloMenesiuImokos.setText(mokejimoStatistika);
        }
        else {
            double kreditas = Pankas.getSuma() / mokejimoTrukme;
            double palukanos = procentai();
            String kreditui = Double.toString(round(kreditas , 2)), palukanoms;
            for (int i = 12*prMet + prMen; i <= mokejimoTrukme && i <= intervaloGalas; i++) {
                palukanoms = Double.toString(round(kreditas * (mokejimoTrukme - i) * palukanos, 2));
                mokejimoStatistika  += kreditui + " + " + palukanoms + " eur; ";
                visosPalukanos += kreditas * (mokejimoTrukme - i) * palukanos;
            }
            visasKreditas = kreditas * (intervaloGalas - 12*prMet - prMen);
            intervaloMenesiuImokos.setText(mokejimoStatistika);
            if(intervaloGalas >= mokejimoTrukme) intervaloPabaigosLikutis.setText("0.");
            else {
                intervaloPabaigosLikutis.setText(Double.toString(round(kreditas * (mokejimoTrukme - intervaloGalas), 2)) + " eur.");
            }
        }
        //visosPalukanos, visasKreditas
        intervaloPalukanuSuma.setText(Double.toString(round(visosPalukanos, 2)) + " eur.");
        intervaloKreditoSuma.setText(Double.toString(round(visasKreditas, 2)) + " eur.");
        return 0;
    
        
    }


    
    
    
}
