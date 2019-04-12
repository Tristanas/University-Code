/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package veliavosfx;

import java.util.ArrayList;

/**
 *
 * @author Vilius
 */
public class Filtruotojas {
    String salis;
    String imone;
    String kodas;
    String produktas; //galima padaryti string masyvu, mat galima ieskoti imoniu, su keliais produktais, reiketu taikyti OR arba AND operacijas del pasirinkimo ivairoves.
    
    public Filtruotojas(String salis, String imone, String kodas, String produktas) {
        this.salis = salis;        
        this.imone = imone;
        this.kodas = kodas;
        this.produktas = produktas;
        
        if (salis.isEmpty()) this.salis = "-";
        if (imone.isEmpty()) this.imone = "-";
        if (kodas.isEmpty()) this.kodas = "-";
        if (produktas.isEmpty()) this.produktas = "-";
    }
    
    void filtruotiVeliavas(ArrayList<Veliava> veliavos) {
        //System.out.println(salis + "| |" + imone + "| |" + kodas + "| |" + produktas);
        for(Veliava vel : veliavos) {
            //System.out.println(vel.toString());
            boolean tinkaSalis, tinkaImone, tinkaKodas, tinkaProduktas;
            tinkaSalis = salis.equals("-") || salis.equals(vel.getValstybe());
            tinkaImone = imone.equals("-") || imone.equals(vel.getImone());
            tinkaKodas = kodas.equals("-") || kodas.equals(vel.getKodas());
            tinkaProduktas = produktas.equals("-") || produktas.equals(vel.getProduktai());
    //      if (("-".equals(salis) || salis.equals(vel.getValstybe())) && ("-".equals(imone) || imone.equals(vel.getImone()))
    //            && ("-".equals(kodas) || kodas.equals(vel.getKodas())) && ("-".equals(produktas) || produktas.equals(vel.getProduktai()))) rodyti = true;
            vel.setVisible(tinkaSalis && tinkaImone && tinkaKodas && tinkaProduktas);
        }
        
    }
}
