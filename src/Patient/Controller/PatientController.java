package Patient.Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ResourceBundle;

public class PatientController implements Initializable
{
    String privateKeyLocation = "src/Patient/privatekey";
    String publicKeyLocation = "src/Patient/publickey";

    PrivateKey privateKey;
    PublicKey publicKey;

    @FXML
    private JFXPasswordField cprField;

    @FXML
    private JFXTextField rsaPrivateKeyField;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        File privateKeyFile = new File(privateKeyLocation);
        File publicKeyFile = new File(publicKeyLocation);

        try
        {
            if(!privateKeyFile.exists() || !publicKeyFile.exists())
            {
                KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
                keyGen.initialize(1024);
                KeyPair keyPair = keyGen.genKeyPair();
                privateKey = keyPair.getPrivate();
                publicKey = keyPair.getPublic();

                // Get the bytes of the private key
                byte[] privateKeyBytes = privateKey.getEncoded();
                byte[] publicKeyBytes = publicKey.getEncoded();

                PrintWriter privateKeyWriter = new PrintWriter (privateKeyLocation);
                privateKeyWriter.println(Base64.encodeBase64String(privateKeyBytes));
                privateKeyWriter.close();

                PrintWriter publicKeyWriter = new PrintWriter (publicKeyLocation);
                publicKeyWriter.println(Base64.encodeBase64String(publicKeyBytes));
                publicKeyWriter.close();
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void loginButtonAction(ActionEvent event)
    {
        System.out.println("Lol");
    }


}
