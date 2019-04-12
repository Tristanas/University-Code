/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klases;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Vilius
 */
public class Grupe implements Serializable{
    ObservableList<Studentas> studentai;
    public String pavadinimas;
    public Grupe(String pavadinimas)
    {
        this.pavadinimas = pavadinimas;
        studentai = FXCollections.observableArrayList();
    }
    
    public Grupe(String pavadinimas, ArrayList<Studentas> studentai)
    {
        this.pavadinimas = pavadinimas;
        ObservableList rezultatas = FXCollections.observableArrayList();
        for (Studentas sd : studentai) {
            rezultatas.add(sd);
        }
        this.studentai = rezultatas;
    }
    
    public Grupe(String pavadinimas, int id, ObservableList<Studentas> studentai)
    {
        this.pavadinimas = pavadinimas;
        this.studentai = studentai;
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
