package Doctor.Controller;

import Doctor.Block;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class UserSearchController implements Initializable
{
    PrivateKey privateKey;
    /* The public key is needed as both an String and publicKey throughout the program. To avoid unnecessary typecasting
     * which if done carelessly might result in padding-errors, there exist an variable for both. */
    PublicKey publicKey;
    String publicKeyAsString;

    @FXML
    private JFXTextField cprTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

    }

    public String getCprTextField()
    {
        return cprTextField.getText();
    }

    public void CPRButtonAction(ActionEvent event)
    {
        String cprString = cprTextField.getText();
/*
        if (cprString.length() != 10)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Søgningsfejl");
            alert.setHeaderText("Fejl med input af CPR-nummer");
            alert.setContentText("CPR-nummeret skal være på 10 cifre");
            alert.show();
            return;
        }
*/
        if (!cprString.matches("[0-9]+"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Søgningsfejl");
            alert.setHeaderText("Fejl med input af CPR-nummer");
            alert.setContentText("Der må kun være tal i et CPR-nummer");
            alert.show();
            return;
        }

        List<Block> blockList = new ArrayList<>();
        try
        {
            /* Connects to database */
            Connection con = DriverManager.getConnection("jdbc:mysql://195.201.113.131:3306/p2?autoReconnect=true&useSSL=false","sembrik","lol123"); // p2 is db name
            Statement stmt = con.createStatement();
            /* Searches for public key that match entered cpr */
            ResultSet rs = stmt.executeQuery("SELECT rsapublickey FROM borger WHERE cpr = " + cprString);
            /* If no match is found throw error message */
            if (!rs.next())
            {
                System.out.println("Could not find user with following CPR-number: " + cprString);
                return;
            }

            /* Reads public key from database */
            byte[] publicKeyBytes =  rs.getBytes("rsapublickey");
            publicKeyAsString = rs.getString("rsapublickey");

            /* Converts the retrieved public key to an public key */
            byte[] decodedPublicKey = Base64.decodeBase64(publicKeyBytes);
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedPublicKey));


            /* Reads and save the private key of the entered CPR */
            try {
                /* Retrieves privateKey string from local file and converts it into an private key */
                BufferedReader bufferedReader = new BufferedReader(Files.newBufferedReader(Paths.get("C:\\GitHub\\Patient\\src\\privateKeys\\"+ cprString +".txt")));
                /* Saves the content of the file in privateKeyString */
                String privateKeyString = bufferedReader.lines().collect(Collectors.joining());
                System.out.println(privateKeyString);
                byte[] decodedPrivateKey = Base64.decodeBase64(privateKeyString);
                privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodedPrivateKey));
            }catch (Exception e) {
                e.printStackTrace();
            }

            try
            {
                /* Connects to blockchain */
                Socket socket = new Socket("127.0.0.1", 21149);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                /* First sends 0 to the blockchain to activate case 0 in the switch.
                 * Afterwards sends the public key to search for existing blocks of that cpr. */

                bufferedWriter.write(0);
                bufferedWriter.write(publicKeyAsString);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                /* Reads received blocks and adds them to blockList */
                int count = bufferedReader.read();
                for (int i = 0; i < count; ++i)
                {
                    Block block = new Block();
                    block.id = bufferedReader.readLine();
                    block.publicKey = bufferedReader.readLine();
                    block.encryptedAESKey = bufferedReader.readLine();
                    block.encryptedData = bufferedReader.readLine();

                    blockList.add(block);
                }
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
            /* The button has been clicked and data has been retrieved. Activates the next scene UserViewController
            *  and passes the retrieved data. */
            try
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Design/UserView.fxml"));
                Parent root1 = fxmlLoader.load();

                UserViewController userViewController = fxmlLoader.getController();
                userViewController.passBlockList(blockList);
                userViewController.passPrivateKey(privateKey);
                userViewController.passPublicKey(publicKey);
                userViewController.passPublicKeyAsString(publicKeyAsString);


                Stage stage = new Stage();
                stage.setTitle("User View");
                stage.setScene(new Scene(root1,450,500));
                stage.show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}
