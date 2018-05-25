package Doctor.Controller;

import Doctor.Journal;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

// JavaFX Imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.codec.binary.Base64;
import supportClasses.AES;
import supportClasses.RSA;

// Java Imports
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class JournalMakerController implements Initializable
{
    @FXML
    private AnchorPane pane;
    @FXML
    private JFXTextField patientName;
    @FXML
    private JFXTextField CPR;
    @FXML
    private JFXTextField printDate;
    @FXML
    private JFXTextField startTDate;
    @FXML
    private JFXTextField endTDate;
    @FXML
    private JFXTextField dateWritten;
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

    private PrivateKey patientPrivateKey;
    private PublicKey patientPublicKey;
    private String patientPublicKeyAsString;
    private String journalBlockId;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
    }

    public void saveButtonAction(ActionEvent event) throws Exception
    {
        checkIfEmptyField();
        /* Creates journal with inputted data from the GUI */
        Journal journal = new Journal(patientName.getText(), CPR.getText(), printDate.getText(), startTDate.getText(), endTDate.getText(),
                dateWritten.getText(), noteType.getText(), examinationDetails.getText(), diagnose.getText(), interpretedBy.getText(),
                writtenBy.getText(), authenticatedBy.getText(), hospitalName.getText(), departmentName.getText(), uploadedBy.getText());
        System.out.println(journal.toString());

        RSA RSA = new RSA();
        AES AES = new AES();

        /* Creates AES key with salt */
        SecureRandom random = new SecureRandom();
        byte key[] = new byte[32]; // 256 bits
        random.nextBytes(key);
        byte IV[] = new byte[16]; // 128 bits
        random.nextBytes(IV);
        byte[] keyIV = new byte[key.length + IV.length];
        System.arraycopy(key, 0, keyIV, 0, key.length);
        System.arraycopy(IV, 0, keyIV, key.length, IV.length);
        String aesKeyBase64 = Base64.encodeBase64String(keyIV);

        /* Encrypts journalData */
        byte[] encryptedJournalData = AES.encrypt(journal.toString(), aesKeyBase64);
        String encryptedJournalDataString = Base64.encodeBase64String(encryptedJournalData);

        /* Encrypts AES-key */
        byte[] encryptedAESKey = RSA.encrypt(aesKeyBase64, patientPublicKey);
        String encryptedAESKeyString = Base64.encodeBase64String(encryptedAESKey);


        /* The block should contain an SHA256-RSA-private-key-signed string of the journal-data named journalBlockID.
         * In order to sign the data, an private key the blockchain recognize as legit is loaded first. */
        BufferedReader bufferedReader = new BufferedReader(Files.newBufferedReader(Paths.get("C:\\GitHub\\DoctorClient\\src\\acceptedClientPrivateKey\\acceptedPrivateKey.txt")));
        /* Saves the content of the file in privateKeyString */
        String acceptedPrivateKeyString =  bufferedReader.lines().collect(Collectors.joining());
        byte[] decodedPrivateKey = Base64.decodeBase64(acceptedPrivateKeyString);
        PrivateKey acceptedPrivateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodedPrivateKey));

        /* Creates signature */
        Signature signWithPrivateKey = Signature.getInstance("SHA256WithRSA");
        /* Initializes signature with the private key the blockchain accepts */
        signWithPrivateKey.initSign(acceptedPrivateKey);
        /* If there existed no blocks in the patients blockList prior to this, the journalBlockID is the patients publicKey.
           If there did exist an block the journalBlockID is the previous block id + the patients public key.
           This makes sure the journalBlockID is always unique for each block.
           After creating journalBLockID the journalBlockID is signed with the acceptedPrivateKey */
        if (journalBlockId == null) {
            signWithPrivateKey.update(patientPublicKeyAsString.getBytes());
            System.out.println("Creating first block for this user");
        }
        else {
            signWithPrivateKey.update((journalBlockId + patientPublicKeyAsString).getBytes());
            System.out.println("There was an previous block");
        }

        byte[] signedJournalBlockID = signWithPrivateKey.sign();
        String signedJournalBlockIDasString = Base64.encodeBase64String(signedJournalBlockID);

        /* Uploads AES-key to database */
        AES.saveAESToDB(signedJournalBlockIDasString, aesKeyBase64);

        Socket socket = new Socket("127.0.0.1", 21149);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        // Send packet using opcode 1
        bufferedWriter.write(1);
        /* sends unique block ID, which is the signed private key */
        bufferedWriter.write(signedJournalBlockIDasString);
        bufferedWriter.newLine();

        bufferedWriter.write(patientPublicKeyAsString);
        bufferedWriter.newLine();

        bufferedWriter.write(encryptedAESKeyString);
        bufferedWriter.newLine();

        bufferedWriter.write(encryptedJournalDataString);
        bufferedWriter.newLine();

        bufferedWriter.flush();
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
        patientPrivateKey = privKey;
    }

    public void passBlockId(String blockId)
    {
        journalBlockId = blockId;
    }

    public void passPatientPublicKey(String pubKey)
    {
        patientPublicKeyAsString = pubKey;
    }

    public void passPublicKey(PublicKey publicKey) {
        this.patientPublicKey = publicKey;
    }
}


