/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dsimuliatorius;

import java.util.ArrayList;

/**
 *
 * @author Vilius
 */
public class Komanda {
    private String pavadinimas;
    private final ArrayList<String> argumentai;
    
    public Komanda(String[] eilute) {
        argumentai = new ArrayList<>();
        int counter = 0;
        for (String str : eilute) {
            if (counter == 0) pavadinimas = str;
            else argumentai.add(str);
            counter++;
        }
    }
    
    public String toString() {
        String rezultatas = getPavadinimas() + " ";
        for (String str : getArgumentai()) {
            rezultatas += str + " ";
        }
        return rezultatas;
    }

    /**
     * @return the pavadinimas
     */
    public String getPavadinimas() {
        return pavadinimas;
    }

    /**
     * @return the argumentai
     */
    public ArrayList<String> getArgumentai() {
        return argumentai;
    }
}
