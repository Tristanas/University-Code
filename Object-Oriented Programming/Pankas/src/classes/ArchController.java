/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import static pankas.BankController.isNumeric;
import pankas.Pankas;
/**
 *
 * @author Vilius
 */
public class ArchController implements Initializable {

/*
 * FXML Controller class
 *
 * @author Vilius
 */

    @FXML
    private Button btn;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }
    
    public String toString(int sk)
    {
        return Integer.toString(sk);
    }
    public void nustatytiScena(Scene scn)
    {
        Pankas.window.setScene(scn);
    }
    
    @FXML
    public void pradzion()
    {
        Pankas.window.setScene(Pankas.pradzia);
    }
    @FXML
    public void registracijon()
    {
        Pankas.window.setScene(Pankas.registracija);
        
    }
    @FXML
    public void statistikon()
    {
        Pankas.window.setScene(Pankas.statistika);
        
    }
    public float toFloat(String str)
    {
        String splitStr[] = str.split(",");
        if(splitStr.length == 1) return Float.parseFloat(splitStr[0]);
        else return Float.parseFloat(splitStr[0]+"."+splitStr[1]);
    }
    public int toInt(String str)
    {
        return Integer.parseInt(str);
    }
    public boolean isFloat(String str)
    {
        String splitStr[] = str.split(",");
        //System.out.println(splitStr[0]+" " + splitStr[1]);
        boolean isfloat = isNumeric(splitStr[0]) && isNumeric(splitStr[1]);
        //System.out.println(isfloat);
       // System.out.println(isNumeric(splitStr[1]));
        //if(isfloat)label.setText("true");
        //else label.setText("false");
        return isfloat;
    }
     public static boolean isTextFieldNumeric(TextField field)
    {
      String str = field.getText();
      NumberFormat formatter = NumberFormat.getInstance();
      ParsePosition pos = new ParsePosition(0);
      formatter.parse(str, pos);
      return str.length() == pos.getIndex();
    }
    public static boolean isNumeric(String str)
    {
      NumberFormat formatter = NumberFormat.getInstance();
      ParsePosition pos = new ParsePosition(0);
      formatter.parse(str, pos);
      return str.length() == pos.getIndex();
    }
    public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();

    BigDecimal bd = new BigDecimal(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
    }
    //Kokia bus paskola mokejus intervaloGalas kieki menesiu
    public static double ateitiesVerte(int intervaloGalas, double periodoMokestis)
    {
            double periodoPalukanos = Pankas.getProcentai() / 1200;
            double t = 1 + periodoPalukanos;
            double tnt = Math.pow(t, intervaloGalas + 1);
            return Pankas.getSuma() * tnt - periodoMokestis * (tnt - t) / periodoPalukanos;
    }
    //Grazina kiek pinigu dingsta palukanoms moketi
    public static double deltaImoka(double ateitiesVerte)
    {
        return ateitiesVerte * Pankas.getProcentai() / 1200;
    }
     public static double gautiAnuitetoMokesti()
    {
         double pal = Pankas.getProcentai(), koeficientas;
            pal /= 1200;
            int periodai = Pankas.getMenesiai() + 12 * Pankas.getMetai();
            double magicNumber = Math.pow(1 + pal, periodai);
            koeficientas = (pal * magicNumber) / (magicNumber - 1);
            return koeficientas * Pankas.getSuma();
    }
     
     public static double procentai()
     {
         return Pankas.getProcentai() / 1200;
     }
     
     public static void fileOut(String str, String pav, int pradzia, int pabaiga) throws FileNotFoundException, UnsupportedEncodingException
     {
        if(pav.isEmpty()) {
            pav = "statistika.txt";
        }
        try (PrintWriter writer = new PrintWriter(pav, "UTF-8")) {
            writer.println("Paskolos duomenys:");
            writer.println("Suma: " + Integer.toString(Pankas.getSuma()));
            writer.println("Trukme: " + Integer.toString(Pankas.getMetai()) + " m. " + Integer.toString(Pankas.getMenesiai()) + " men.");
            writer.println("Procentai: " + Float.toString(Pankas.getProcentai()) + "%");
            String tipas = "Linijinis";
            if (Pankas.isAnuitetas()) tipas = "Anuitetas";
            writer.println("Paskolos tipas: " + tipas);
            String data = Integer.toString(pradzia / 12) + " m. " + Integer.toString(pradzia % 12)
            + " men. ir " + Integer.toString(pabaiga / 12) + " m. " + Integer.toString(pabaiga % 12) + " men.";
            writer.println("Menesines imokos intervale "+data+":");
            String  structuredStr[] = str.split("; ");
            int counter = 0;
            String toPrint = "";
            for(String text : structuredStr){
                if (counter % 6 == 0) {
                    writer.println(toPrint);
                    toPrint = new String();
                    counter %= 6;
                }
                toPrint += text + "; ";
                counter++;
                
            }
            writer.print(toPrint);
            writer.close();
            System.out.println("Statistika issaugota faile");
        }
     }
}
