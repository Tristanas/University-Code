/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pankas;

import classes.ArchController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import static pankas.Pankas.pradzia;
/**
 *
 * @author Vilius
 */
public class BankController extends ArchController {
    
    VBox layout1 = new VBox(20);
    @FXML
    private Button btn;
    @FXML
    private Label label;
    
    @FXML
    private TextField sumosLaukas;
    @FXML
    private TextField metuLaukas;
    @FXML
    private TextField men;
    @FXML
    private TextField proc;
   
    @FXML
    private RadioButton RB1;
    @FXML
    private RadioButton RB2;
    
      @Override
    public void nustatytiScena(Scene scn)
    {
        Pankas.setSuma(toInt(sumosLaukas.getText()));
        Pankas.setMenesiai(toInt(men.getText()));
        Pankas.setMetai(toInt(metuLaukas.getText()));
        Pankas.setProcentai(toFloat(proc.getText()));
        Pankas.setAnuitetas(RB1.isSelected());
        Pankas.setGalimaPlanuoti(true);
//        System.out.println("Jus ivedete:" );
//        System.out.println(" sumosLaukas: " + Integer.toString(Pankas.suma) + " metuLaukas: " 
//            + Integer.toString(Pankas.metai) + " menesiai: " + Integer.toString(Pankas.menesiai)
//            + " proc: " +Float.toString(Pankas.procentai)+ " Anuitetas: " + Boolean.toString(RB1.isSelected()));
        super.nustatytiScena(scn);
    }
    
     
    @FXML
    private void handleButtonAction(ActionEvent event) {

        String sum = sumosLaukas.getText(), met = metuLaukas.getText();
        if (sum.isEmpty() || met.isEmpty() ||  men.getText().isEmpty() || proc.getText().isEmpty())
        {
            label.setText("Negalima registruoti, forma nepilna.");
        }
        else {
            if(checkFields()) {
                label.setText("Registracija pavyko.");
                nustatytiScena(pradzia);
            }
        }
        
    }

   
    public boolean checkFields()
    {
        if(!isTextFieldNumeric(sumosLaukas)) {
            label.setText("Patikrinkite, ar tinkamai užpildėte sumą.");
            return false;
        }
        if(!isTextFieldNumeric(men)) {
            label.setText("Patikrinkite, ar tinkamai užpildėte menesius.");
            return false;
        }
        if(!isTextFieldNumeric(metuLaukas)) {
            label.setText("Patikrinkite, ar tinkamai užpildėte metus.");
            return false;
        }
        
        if(!isFloat(proc.getText())) {
            label.setText("Patikrinkite, ar tinkamai užpildėte procentus.");
            return false;
        }
        
        return true;
    }
    
  
}
