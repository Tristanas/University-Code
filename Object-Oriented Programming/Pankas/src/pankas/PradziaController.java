/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pankas;
import classes.ArchController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Vilius
 */
public class PradziaController extends ArchController {
    
    @FXML
            Button registracija;
    @FXML
            Button statistika;
    
  
    
    @FXML
    public void registracijon(ActionEvent paspaudimas)
    {
        Pankas.window.setScene(Pankas.registracija);
        
    }
    @FXML
    public void statistikon(ActionEvent paspaudimas)
    {
        Pankas.window.setScene(Pankas.statistika);
        
    }
}
