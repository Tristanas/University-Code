/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klases;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Vilius
 */
public class DuomBaze implements Serializable {
    public ArrayList<GrupeSaugojimui> grupes;
    
    public DuomBaze() {
        
    }
    public DuomBaze(ArrayList<Grupe> netinkamasSarasas) {
        grupes = new ArrayList<>();
        GrupeSaugojimui geraGrupe;
        for(Grupe gr : netinkamasSarasas) {
            geraGrupe = new GrupeSaugojimui(gr.pavadinimas, gr.studentai.toArray());
            grupes.add(geraGrupe);
        }
    }
}
