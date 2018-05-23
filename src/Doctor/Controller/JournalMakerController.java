package Doctor.Controller;

// jFoenix Imports
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
import javafx.scene.layout.AnchorPane;
import org.apache.commons.codec.binary.Base64;
import supportClasses.AES;
import supportClasses.RSA;

// Java Imports
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.security.*;
import java.util.ResourceBundle;

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
    private PublicKey publicKey;
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
        Journal journal = new Journal(patientName.getText(), CPR.getText(), "Print Date", "Start Date", "End Date ",
                " Date Written", noteType.getText(), examinationDetails.getText(), diagnose.getText(), interpretedBy.getText(),
                writtenBy.getText(), authenticatedBy.getText(), hospitalName.getText(), departmentName.getText(), uploadedBy.getText());
        System.out.println(journal.toString());

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


/*
        PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decodeBase64(patientPublicKey)));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.PUBLIC_KEY, publicKey);
        byte[] encryptedAesKeyIV = cipher.doFinal(keyIV);
*/

        /* Encrypts journalData */
        RSA RSA = new RSA();
        AES AES = new AES();
        byte[] encryptedJournalData = AES.encrypt(journal.toString(), aesKeyBase64);
        String encryptedJournalDataString = Base64.encodeBase64String(encryptedJournalData);

        /* Encrypts AES-key */
        byte[] encryptedAESKey = RSA.encrypt(aesKeyBase64, publicKey);
        String encryptedAESKeyString = Base64.encodeBase64String(encryptedAESKey);




        // Send to blockchain, if successful we add it to DB
        Signature sig = Signature.getInstance("SHA256WithRSA");
        sig.initSign(privateKey);

        if (journalBlockId != null)
            sig.update((journalBlockId+patientPublicKey).getBytes());
        else
            sig.update(patientPublicKey.getBytes());

        byte[] signedBlock = sig.sign();
        //System.out.println("Signature: " + Base64.encodeBase64String(signatureBytes));


        Socket socket = new Socket("127.0.0.1", 21149);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Send packet using opcode 1
        bufferedWriter.write(1);
        /* sends unique block ID, which is the signed private key */
        bufferedWriter.write(Base64.encodeBase64String(signedBlock));
        bufferedWriter.newLine();

        bufferedWriter.write(patientPublicKey);
        bufferedWriter.newLine();

        //bufferedWriter.write(Base64.encodeBase64String(encryptedAesKeyIV));
        bufferedWriter.write(encryptedAESKeyString);
        bufferedWriter.newLine();

        bufferedWriter.write(encryptedJournalDataString);
        bufferedWriter.newLine();

        bufferedWriter.flush();

        //int success = bufferedReader.read();   DENNE LINJE FOR CLIENT TIL AT KRASHE EFTER CREATION
        // success == 1

//        if (true)
//        {
//            String blockId = bufferedReader.readLine();
//            Connection con = DriverManager.getConnection("jdbc:mysql://195.201.113.131:3306/p2?autoReconnect=true&useSSL=false","sembrik","lol123"); // p2 is db name
//            Statement stmt = con.createStatement();
//            stmt.executeUpdate("INSERT INTO trans (blockid, aeskey) VALUES ('"+blockId+"','" + "LOL" + /* Base64.encodeBase64String(encryptedAesKeyIV) */ "')");

//            /* I ENDELIG LØSNING SKAL DEN RSA KRYPTERES FØR DEN UPLOADES */
//            /* Uploads AES key to database */
//            Connection conn = null;
//            PreparedStatement pstmt = null;
//            try {
 //               conn = DriverManager.getConnection("jdbc:mysql://195.201.113.131/p2?useSSL=false", "p2", "Q23wa!!!");
 //               String query = "INSERT INTO `trans` (`blockid`, `aeskey`) VALUES (?,?)";
   //             pstmt = (PreparedStatement) conn.prepareStatement(query);
     //           pstmt.setString(1, journalBlockId);
       //         pstmt.setString(2, aesKeyBase64);
         //   }catch (java.sql.SQLException e) {
//                e.printStackTrace();
  //          }finally {
    //            try {
      //              pstmt.close();
        //            conn.close();
//          //      } catch (SQLException e) {
//                  e.printStackTrace();
//              }
//          }
//
//        }

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

    public void passPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }
}


