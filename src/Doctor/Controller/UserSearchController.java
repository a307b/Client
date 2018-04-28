package Doctor.Controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class UserSearchController implements Initializable
{
    @FXML
    private JFXTextField usernameField;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        System.out.println("do nothing here");
    }

    public void CPRButtonAction(ActionEvent event)
    {
        //System.out.println("establish mysql connection and save connection. if failed, notify using messagebox");

        String cprString = usernameField.getText();

        if (cprString.length() != 10)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Søgningsfejl");
            alert.setHeaderText("Fejl med input af CPR-nummer");
            alert.setContentText("CPR-nummeret skal være på 10 cifre");
            alert.show();
            return;
        }

        if (!cprString.matches("[0-9]+"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Søgningsfejl");
            alert.setHeaderText("Fejl med input af CPR-nummer");
            alert.setContentText("Der må kun være tal i et CPR-nummer");
            alert.show();
            return;
        }




        /*

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database forbindelsesfejl");
            alert.setHeaderText("Fejl med forbindelse til database");
            alert.setContentText("Der opstod et problem med forbindelsen til databasen, venligst prøv igen.");
            alert.show();
         */


        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Design/UserView.fxml"));
            Parent root1 = fxmlLoader.load();

            UserViewController controller = fxmlLoader.getController(); // Pass params to UserViewController using method
            controller.test(usernameField.getText());

            Stage stage = new Stage();
            stage.setTitle("User View");
            stage.setScene(new Scene(root1,500,500));
            stage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
