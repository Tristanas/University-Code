/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klases;

import java.io.Serializable;

/**
 *
 * @author Vilius
 */
public class Studentas implements Serializable{

    /**
     * @return the kiekPraleido
     */
    public int getKiekPraleido() {
        return kiekPraleido;
    }

    /**
     * @param kiekPraleido the kiekPraleido to set
     */
    public void setKiekPraleido(int kiekPraleido) {
        this.kiekPraleido = kiekPraleido;
    }

    
    private String vardas;
    private String pavarde;
    public char[] lankymas; // true - praleido, false - buvo.
    private String ejoIPaskaita;
    private String praleido = "0";
    private int kiekPraleido;
    public Studentas(String vardas, String pavarde)
    {
        
        this.vardas = vardas;
        this.pavarde = pavarde;
        lankymas = new char[365*4 + 1];
        int a = lankymas[0];
        //System.out.println("Kiekvienas narys pradzioje lygus " + a);
        ejoIPaskaita = " ";
        kiekPraleido = 0;
    }
    /**
     * @return the vardas
     */
    public String getVardas() {
        return vardas;
    }
    
    
    public void pazymetiLankomuma(int diena)
    {
        lankymas[diena] = getEjoIPaskaita().charAt(0);
        
    }
    
    /**
     * @param vardas the vardas to set
     */
    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    /**
     * @return the pavarde
     */
    public String getPavarde() {
        return pavarde;
    }

    public void setEjo(int diena)
    {
        if(lankymas[diena] == 'n') this.ejoIPaskaita = "n";
        else this.ejoIPaskaita = " ";
    }
    
    public String arEjo()
    {
        return this.getEjoIPaskaita();
    }
    /**
     * @param pavarde the pavarde to set
     */
    public void setPavarde(String pavarde) {
        this.pavarde = pavarde;
    }

    public void setEjoIPaskaita(String str) {
        this.ejoIPaskaita = str;
    }
    
    /**
     * @return the ejoIPaskaita
     */
    public String getEjoIPaskaita() {
        return ejoIPaskaita;
    }

    /**
     * @return the praleido
     */
    public String getPraleido() {
        return praleido;
    }

    /**
     * @param praleido the praleido to set
     */
    public void setPraleido(String praleido) {
        this.praleido = praleido;
    }
}
