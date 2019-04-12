/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klases;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author Vilius
 */
public class GrupeSaugojimui implements Serializable{
    public ArrayList<Studentas> studentai;
    public String pavadinimas;
    public GrupeSaugojimui(String pavadinimas)
    {
        this.pavadinimas = pavadinimas;
        studentai = new ArrayList();
    }
    public GrupeSaugojimui(String pavadinimas, Object[] studentai)
    {
        this.pavadinimas = pavadinimas;
        ArrayList<Studentas> sudaromasSarasas = new ArrayList();
        for (Object sd : studentai) {
            sudaromasSarasas.add((Studentas)sd);
        }
        this.studentai = sudaromasSarasas;
    }
    
    public void nustatytiLankyma(int diena)
    {
        //System.out.println("Kazkas negerai");
        studentai.forEach((stud) -> {
            stud.setEjo(diena);
            //System.out.println("(" +stud.arEjo() + ")");
        });
    }
    
    public void issaugotiLankyma(int diena)
    {
        studentai.forEach((Studentas stud) -> {
            if (!stud.arEjo().isEmpty())
                stud.lankymas[diena] = stud.arEjo().charAt(0);
        });
    }
}
