/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jobcenter;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author vangfc
 */
public class Confirm_jobController extends JobCenterMainController 
    implements Initializable{

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void confirmJobGo(ActionEvent event) {
        System.out.println("job v: "+ getTitle); 
        System.out.println("customer: "+ jCustField.getText()); 
        System.out.println("job name: "+ jNameField.getText()); 
        System.out.println("start date: "+ jStartField.getText()); 
        
        
        System.out.println("job type");
        for(int i=0;i<jobTypePicked.size();i++)
        {
            System.out.print(jobTypePicked.get(i));
            System.out.print(",");
        }
        
        System.out.println("equipment");
        for(int i=0;i<vehList.size();i++)
        {
            System.out.print(vehList.get(i));
            System.out.print(",");
        }
        
        System.out.println("employees");
        for(int i=0;i<empListSel.size();i++)
        {
            System.out.println(empListSel.get(i));
            System.out.print(",");
        }
          
        
    }

    @FXML
    private void cancelJobGo(ActionEvent event) {
        System.out.println("test2");
        stageJob.close();
        
        
    }
}
