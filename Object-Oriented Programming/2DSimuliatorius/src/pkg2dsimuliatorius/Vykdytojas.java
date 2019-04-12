/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2dsimuliatorius;

import java.util.ArrayList;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
/**
 *
 * @author Vilius
 */
public class Vykdytojas implements Runnable{
    long periodas;
    String pavadinimas;
    ArrayList<String> argumentai;
    Player zaidejas;
    ImageView zemelapis;
    Boolean galiDirbti;
    ArrayList<Komanda> komandos;
    public Vykdytojas(ArrayList<Komanda> komandos, Player zaidejas, long periodas, ImageView zemelapis){
        galiDirbti = true;
        this.zaidejas = zaidejas;
        this.komandos = komandos;
        this.periodas = periodas;
        this.zemelapis = zemelapis;
    }
    
    

    @Override
    public void run() {
        for (Komanda kmd : komandos) {
            pavadinimas = kmd.getPavadinimas();
            argumentai = kmd.getArgumentai();
            if ("eiti".equals(pavadinimas)) {
                int kiekis = Integer.parseInt(argumentai.get(0));
                piesti(zemelapis, Color.BLACK, 5);
                if (zaidejas.getGreitis() > 0) {
                    int judeti = kiekis;
                    for (; judeti > zaidejas.getGreitis(); judeti -= zaidejas.getGreitis()) {
                        zaidejas.judeti(zaidejas.getGreitis());
                        if (zaidejas.getSukimoKiekis() != 0) {
                            zaidejas.pasukti();
                        };
                        try {
                            Thread.sleep(periodas);
                            if (!galiDirbti) judeti = 0;
                            //System.out.println("Veikejas eina miegoti");
                        }
                        catch(InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                    }
                    zaidejas.judeti(judeti);
                }
            }
            if ("sukti".equals(pavadinimas) && argumentai.size() > 1) {
                //System.out.println("Veikejas pasuko " + argumentai.get(0));
                if (argumentai.get(0).equals("pagalLR")) zaidejas.setPasukimas(zaidejas.getPasukimas()+Integer.parseInt(argumentai.get(1)));
                if (argumentai.get(0).equals("priesLR")) zaidejas.setPasukimas(zaidejas.getPasukimas()-Integer.parseInt(argumentai.get(1)));
            }
            if ("nustatyti".equals(pavadinimas)) {
                if (argumentai.size() > 1){
                    switch(argumentai.get(0)) {
                        case "greiti":
                            zaidejas.setGreitis(Double.parseDouble(argumentai.get(1)));
                            break;
                        case "pasukima":
                            zaidejas.setPasukimas(Double.parseDouble(argumentai.get(1)));
                            break;
                        case "sukimo_kieki":
                            zaidejas.setSukimoKiekis(Double.parseDouble(argumentai.get(1)));
                            break;
                        case "sukimo_greiti":
                            zaidejas.setSukimoGreitis(Double.parseDouble(argumentai.get(1)));
                            break;
                    }
                }
            }
        }
    }
     private void piesti(ImageView zemelapis, Color spalva, int spindulys) {
        
    }
}
