package Doctor.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;


public class UserSearchController implements Initializable
{
    @FXML
    private JFXTextField usernameField;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //initialize
    }

    public void CPRButtonAction(ActionEvent event)
    {
        System.out.println("Dette er en test");
    }
}
