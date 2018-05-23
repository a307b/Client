package Doctor.Controller;

import Doctor.Block;
import Doctor.journalMakerClasses.JournalGenerator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;
import supportClasses.AES;

import java.io.IOException;
import java.net.URL;
import java.security.PrivateKey;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class UserViewController implements Initializable
{
    @FXML
    private AnchorPane pane;

    @FXML
    private JFXListView<Label> jfxViewList;

    @FXML
    private Label nameOrCPR;

    @FXML
    private JFXButton makeJournal;

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String publicKeyAsString;
    private List<Block> blockList;

    private int clickedJournal;
    private boolean aJournalHasBeenClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }

    @FXML
    public void deleteJournalButton(ActionEvent event)
    {

    }

    @FXML
    public void getJournalButton(ActionEvent event)
    {
        try{
            System.out.println("It reaches here");
            JournalGenerator journalGenerator = new JournalGenerator();
            AES AES = new AES();
            if(aJournalHasBeenClicked) {
                String AESKeyFromDB = AES.getAESFromDB(blockList.get(getClickedJournal()).id);
                String retrievedJournal = AES.decrypt(Base64.decodeBase64(blockList.get(getClickedJournal()).encryptedData), AESKeyFromDB);
                System.out.println(retrievedJournal);
                /* Splits lines separated by semicolon */
                List<String> splitLinesList = Arrays.asList(retrievedJournal.split(":"));
                System.out.println("It reaches here and the list is so long: " + splitLinesList.size());
                // TJEK OM DER ER LIGE SÅ MANGE VARIABLER som der skal være
                /* Inserts all the variables in JournalGenerator */
                journalGenerator.makeJournal(splitLinesList.get(0), splitLinesList.get(1), splitLinesList.get(2), splitLinesList.get(3),
                        splitLinesList.get(4), splitLinesList.get(5), splitLinesList.get(6), splitLinesList.get(7),
                        splitLinesList.get(8), splitLinesList.get(9), splitLinesList.get(10), splitLinesList.get(11),
                        splitLinesList.get(12), splitLinesList.get(13), splitLinesList.get(14));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void makeJournalButton(ActionEvent event)
    {
        try
        {
            /* Activates the JournalMakerController scene */
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Design/Journalmaker.fxml"));
            Parent root1 = fxmlLoader.load();

            /* Passes privateKey, publicKey and publicKeyString to JournalMakerController */
            JournalMakerController journalMakerController = fxmlLoader.getController();
            journalMakerController.passPrivateKey(privateKey);
            journalMakerController.passPublicKey(publicKey);
            journalMakerController.passPatientPublicKey(publicKeyAsString);

            if (blockList.isEmpty())
                journalMakerController.passBlockId(null);
            else
                journalMakerController.passBlockId(blockList.get(0).id);

            Stage stage = new Stage();
            stage.setTitle("Journal Maker");
            stage.setScene(new Scene(root1, 830, 600));
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleMouseClick(MouseEvent mouseEvent)
    {
        //System.out.println("clicked on " + jfxViewList.getSelectionModel().getSelectedItem().getText());
        /* If any element that is not white space (-1) is clicked save the index of that element in clickedJournal */
        if(jfxViewList.getSelectionModel().getSelectedIndex() != -1) {
            setClickedJournal(jfxViewList.getSelectionModel().getSelectedIndex());
            setaJournalHasBeenClicked(true);
            System.out.println("clicked on " + jfxViewList.getSelectionModel().getSelectedIndex());
        }
    }

    public void passBlockList(List<Block> blockListParam)
    {
        // A blockchain goes from oldest to newest, therefore reverse the jfxViewList to get the latest journal at the top
        Collections.reverse(blockListParam);

        for (Block block : blockListParam)
            jfxViewList.getItems().add(new Label(block.encryptedData));

        blockList = blockListParam;
    }

    public void passPrivateKey(PrivateKey privateKey)
    {
        this.privateKey = privateKey;
    }

    public void passPublicKeyAsString(String publicKeyAsString)
    {
        this.publicKeyAsString = publicKeyAsString;
    }

    public void passPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public int getClickedJournal() {
        return clickedJournal;
    }

    public void setClickedJournal(int clickedJournal) {
        this.clickedJournal = clickedJournal;
    }

    public void setaJournalHasBeenClicked(boolean aJournalHasBeenClicked) {
        this.aJournalHasBeenClicked = aJournalHasBeenClicked;
    }
}

