package Doctor.Controller;

// jFoenix Imports
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

// JavaFX Imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.codec.binary.Base64;

// Java Imports
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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

    public static String encrypt(byte[] key, byte[] initVector, String value)
    {
        // TODO: Move to own class
        try
        {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeBase64String(encrypted);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO: Put this shit in JournalMakerController
        String cprString = "";// usernameField.getText();

        SecureRandom random = new SecureRandom();

        byte key[] = new byte[32]; // 256 bits
        random.nextBytes(key);

        byte IV[] = new byte[16]; // 128 bits
        random.nextBytes(IV);

        System.out.println(encrypt(key, IV, cprString));

        byte[] keyIV = new byte[key.length + IV.length];
        System.arraycopy(key, 0, keyIV, 0, key.length);
        System.arraycopy(IV, 0, keyIV, key.length, IV.length);

        String aesKeyBase64 = Base64.encodeBase64String(keyIV);

        try
        {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/p2","root","ascent"); // p2 is db name
            Statement stmt = con.createStatement();

            // Execute SQL query to insert the "transaction"
            stmt.executeUpdate("INSERT INTO tran (cpr, trans_id, aes_key) VALUES (3112999999,'f9777aae-ef68-44e7-bc9b-8758c20069a3','"+aesKeyBase64+"')");
        }
        catch(Exception e)
        {
            System.out.printf(e.getMessage());
        }

        //Initialize
    }

    public void saveButtonAction(ActionEvent event)
    {
        // Action when the save button has been pressed should be written here.
    }

    public void cancelButtonAction(ActionEvent event)
    {
        // Action when the cancel button has been pressed should be written here.
    }
}


