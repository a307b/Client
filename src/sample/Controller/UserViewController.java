package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserViewController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXListView<?> list;

    @FXML
    private Label nameOrCPR;

    @FXML
    private JFXButton makeJournal;

    @FXML
    private JFXButton editJournal;

    @FXML
    private JFXButton deleteJournal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initialize
    }

    @FXML
    public void deleteJournalButton(ActionEvent event) {

    }

    @FXML
    public void editJournalButton(ActionEvent event) {

    }

    @FXML
    public void makeJournalButton(ActionEvent event) {

    }

}
