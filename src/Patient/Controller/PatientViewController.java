package Patient.Controller;

import com.itextpdf.kernel.crypto.AESCipher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import supportClasses.AESKeyEncryptor;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PatientViewController implements Initializable
{
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXListView<Label> list;

    @FXML
    private Label nameOrCPR;

    @FXML
    private JFXButton makeJournal;

    @FXML
    private JFXButton editJournal;

    @FXML
    private JFXButton deleteJournal;

    private String passedEZ = "123";

    private ArrayList<AESKeyEncryptor> AESKeyStore;



    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        System.out.println("pass mysql connection to this controller from UserSearchController then use it");
    }

    @FXML
    public void deleteJournalButton(ActionEvent event)
    {

    }

    @FXML
    public void editJournalButton(ActionEvent event)
    {

    }

    @FXML
    public void makeJournalButton(ActionEvent event)
    {

    }

    @FXML public void handleMouseClick(MouseEvent mouseEvent)
    {
        if(mouseEvent.getClickCount() == 2)
            System.out.println("Double clicked");

        System.out.println("clicked on " + list.getSelectionModel().getSelectedItem().getText());
    }

    public void test(String nice)
    {
        //System.out.println("ez method call, here's our string " + nice);

        /*
        try
        {
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/p2","root","ascent"); // p2 is db name
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from testtable");
            while(rs.next())
                list.getItems().add(new Label(rs.getString(1)));

            con.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        */
        //list.getItems().add(new Label(nice));
    }

}
