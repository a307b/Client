package Doctor.Controller;

// jFoenix Imports
import Doctor.Block;
import Doctor.Blockchain;
import Doctor.Journal;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

// JavaFX Imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.codec.binary.Base64;

// Java Imports
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

public class JournalMakerController implements Initializable
{
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField patientName;
    @FXML
    private JFXTextField CPR;
    @FXML
    private JFXDatePicker printDate;
    @FXML
    private JFXDatePicker startTDate;
    @FXML
    private JFXDatePicker endTDate;
    @FXML
    private JFXDatePicker dateWritten;
    @FXML
    private JFXTextField noteType;
    @FXML
    private JFXTextArea examinationDetails;
    @FXML
    private JFXTextField diagnose;
    @FXML
    private JFXTextField interpretedBy;
    @FXML
    private JFXTextField writtenBy;
    @FXML
    private JFXTextField authenticatedBy;
    @FXML
    private JFXTextField hospitalName;
    @FXML
    private JFXTextField departmentName;
    @FXML
    private JFXTextField uploadedBy;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;

    private PrivateKey privateKey;
    private String patientPublicKey;
    private String journalBlockId;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //Initialize

    }

    public void saveButtonAction(ActionEvent event) throws Exception
    {
        checkIfEmptyField();
        // Action when the save button has been pressed should be written here.
        Journal journal = new Journal(patientName.getText(), CPR.getText(), "", "Start Date", "End Date ",
                " Date Written", noteType.getText(), examinationDetails.getText(), diagnose.getText(), interpretedBy.getText(),
                writtenBy.getText(), authenticatedBy.getText(), hospitalName.getText(), departmentName.getText(), uploadedBy.getText());
        System.out.println(journal.toString());

        // AES Key Generation prep stuff
        SecureRandom random = new SecureRandom();
        byte key[] = new byte[32]; // 256 bits
        random.nextBytes(key);
        byte IV[] = new byte[16]; // 128 bits
        random.nextBytes(IV);
        byte[] keyIV = new byte[key.length + IV.length];
        System.arraycopy(key, 0, keyIV, 0, key.length);
        System.arraycopy(IV, 0, keyIV, key.length, IV.length);
        String aesKeyBase64 = Base64.encodeBase64String(keyIV);

        // Encryption and decryption test
        System.out.println("AES Key : " + aesKeyBase64);
        byte[] encryptedData = journal.encrypt(journal.toString(), aesKeyBase64);
        String encryptedDataString = Base64.encodeBase64String(encryptedData);
        String decryptedData = journal.decrypt(encryptedData, aesKeyBase64);
        System.out.println("Decrypted : " + decryptedData);

        /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
        // Send to blockchain, if succesful we add it to DB


        Signature sig = Signature.getInstance("SHA256WithRSA");
        sig.initSign(privateKey);

        if (journalBlockId != null)
            sig.update((journalBlockId+encryptedDataString).getBytes());
        else
            sig.update(encryptedDataString.getBytes());

        byte[] signatureBytes = sig.sign();
        System.out.println("Signature: " + Base64.encodeBase64String(signatureBytes));


        Socket socket = new Socket("127.0.0.1", 21149);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        System.out.println(patientPublicKey);

        // Send packet using opcode 1
        /*bw.write(0);

        bw.write(Base64.encodeBase64String(signatureBytes));
        bw.newLine();

        bw.write(Base64.encodeBase64String(signatureBytes));
        bw.newLine();

        bw.write(Base64.encodeBase64String(signatureBytes));
        bw.newLine();

        bw.write(Base64.encodeBase64String(signatureBytes));
        bw.newLine();

        bw.flush();*/



        // Send to DB
        /*Connection con = DriverManager.getConnection("jdbc:mysql://195.201.113.131:3306/p2?autoReconnect=true&useSSL=false","sembrik","lol123"); // p2 is db name
        Statement stmt = con.createStatement();
        stmt.executeUpdate("INSERT INTO trans (cpr, transid, aeskey) VALUES ('"+CPR.getText()+"','"+getTransID()+"','"+aesKeyBase64+"')");*/

/*        // Encrypt AES Key
        String sql = ("SELECT rsapublickey FROM BorgerDB WHERE cpr = " + CPR.getText()+"");
        ResultSet rs = stmt.executeQuery(sql);
        byte[] borgerPublicKey = rs.getBytes("rsapublickey");
        System.out.println(borgerPublicKey.length);*/

        // Blockchain Creation
        // Blockchain blockchain = new Blockchain(transID, aesKeyBase64, encryptedData,"Public Key");
        // blockchain.setCurrentHash(blockchain.generateHash(encryptedData));

        // TODO implement send block
        // blockchain.sendBlock();
    }

    public void cancelButtonAction(ActionEvent event)
    {
        // Action when the cancel button has been pressed should be written here.
        // get a handle to the stage
        System.exit(0);
    }

    private void alertTextFieldNotFilled(JFXTextField textField)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Journal Creation Error");
        alert.setHeaderText("There has been an error creating a journal!");
        alert.setContentText("You can not leave " + textField.getId() + " empty!" );
        alert.show();
    }

    private void alertTextAreaNotFilled(JFXTextArea textArea)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Journal Creation Error");
        alert.setHeaderText("There has been an error creating a journal!");
        alert.setContentText("You can not leave " + textArea.getId() + " empty!  ");
        alert.show();
    }

    // to be implemented
    private void alertDateFieldNotFilled(JFXDatePicker datePicker)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Journal Creation Error");
        alert.setHeaderText("There has been an error creating a journal!");
        alert.setContentText("You can not leave " + datePicker.getId() + " empty! ");
        alert.show();
    }

    private void checkIfEmptyField()
    {
        if (patientName.getText().equals(""))
            alertTextFieldNotFilled(patientName);

        if (CPR.getText().equals(""))
            alertTextFieldNotFilled(CPR);

        if (diagnose.getText().equals(""))
            alertTextFieldNotFilled(diagnose);

        if (interpretedBy.getText().equals(""))
            alertTextFieldNotFilled(interpretedBy);

        if (writtenBy.getText().equals(""))
            alertTextFieldNotFilled(writtenBy);

        if (authenticatedBy.getText().equals(""))
            alertTextFieldNotFilled(authenticatedBy);

        if (hospitalName.getText().equals(""))
            alertTextFieldNotFilled(hospitalName);

        if (departmentName.getText().equals(""))
            alertTextFieldNotFilled(departmentName);

        if (uploadedBy.getText().equals(""))
            alertTextFieldNotFilled(uploadedBy);

        if (examinationDetails.getText().equals(""))
            alertTextAreaNotFilled(examinationDetails);
    }

    public void passPrivateKey(PrivateKey privKey)
    {
        privateKey = privKey;
    }

    public void passBlockId(String blockId)
    {
        journalBlockId = blockId;
    }

    public void passPatientPublicKey(String pubKey)
    {
        patientPublicKey = pubKey;
    }
}


