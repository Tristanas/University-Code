/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package veliavosfx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Vilius
 */
public class VeliavuBaze implements Serializable{
    private static ArrayList<Veliava> veliavos;
    
    static void Issaugoti(String pavadinimas) throws FileNotFoundException, IOException {
        File file = new File(pavadinimas);
        FileOutputStream fo = new FileOutputStream(file);
        ObjectOutputStream output = new ObjectOutputStream(fo);
        output.writeObject(getVeliavos());
        output.close();
        fo.close();
        
    }
    
    static ArrayList<Veliava> uzkrauti(String pavadinimas) throws FileNotFoundException, IOException, ClassNotFoundException{
        File file = new File(pavadinimas);
        FileInputStream fi = new FileInputStream(file);
        ObjectInputStream input = new ObjectInputStream(fi);
        return (ArrayList<Veliava>)input.readObject();
    }

    /**
     * @return the veliavos
     */
    public static ArrayList<Veliava> getVeliavos() {
        return veliavos;
    }

    /**
     * @param aVeliavos the veliavos to set
     */
    public static void setVeliavos(ArrayList<Veliava> aVeliavos) {
        veliavos = aVeliavos;
    }
}
