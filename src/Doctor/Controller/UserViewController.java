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

    private PrivateKey patientPrivateKey;
    private PublicKey patientPublicKey;
    private String patientPublicKeyAsString;
    private List<Block> blockList;

    private int clickedJournal;
    private boolean aJournalHasBeenClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle rb)
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
                /* The name of the saved file is the 10 first characters of the encrypted journal. */
                String saveFileName = blockList.get(getClickedJournal()).encryptedData.substring(0, 10);
                /* Inserts all the variables in JournalGenerator */
                journalGenerator.makeJournal(saveFileName, splitLinesList.get(0), splitLinesList.get(1), splitLinesList.get(2), splitLinesList.get(3),
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

            /* Passes patientPrivateKey, patientPublicKey and publicKeyString to JournalMakerController */
            JournalMakerController journalMakerController = fxmlLoader.getController();
            journalMakerController.passPrivateKey(patientPrivateKey);
            journalMakerController.passPublicKey(patientPublicKey);
            journalMakerController.passPatientPublicKey(patientPublicKeyAsString);

            /* If the blockList for the user is not empty, pass the most recent block's ID. */
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
        /* If any element that is not white space (-1) is clicked save the index of that element in clickedJournal */
        if(jfxViewList.getSelectionModel().getSelectedIndex() != -1) {
            setClickedJournal(jfxViewList.getSelectionModel().getSelectedIndex());
            setaJournalHasBeenClicked(true);
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
        this.patientPrivateKey = privateKey;
    }

    public void passPublicKeyAsString(String publicKeyAsString)
    {
        this.patientPublicKeyAsString = publicKeyAsString;
    }

    public void passPublicKey(PublicKey publicKey) {
        this.patientPublicKey = publicKey;
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

