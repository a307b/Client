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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class UserSearchController implements Initializable
{
    //PrivateKey privateKey;
    String privateKeyLocation = "src/Doctor/privatekey";
    String publicKeyLocation = "src/Doctor/publickey";

    PrivateKey privateKey;
    PublicKey publicKey;

    @FXML
    private JFXTextField cprTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            File privateKeyFile = new File(privateKeyLocation);
            File publicKeyFile = new File(publicKeyLocation);

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
            else
            {
                byte[] keyBytes = Base64.decodeBase64(Files.readAllBytes(Paths.get(privateKeyLocation)));

                PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance("RSA");

                privateKey = keyFactory.generatePrivate(spec);
            }

        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

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
            Connection con = DriverManager.getConnection("jdbc:mysql://195.201.113.131:3306/p2?autoReconnect=true&useSSL=false","sembrik","lol123"); // p2 is db name
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT rsapublickey FROM borger WHERE cpr = " + cprString);

            if (!rs.next())
            {
                System.out.println("Could not find user with following CPR-number: " + cprString);
                return;
            }

            byte[] publicKey = rs.getBytes("rsapublickey");
            byte[] bytesEncoded = Base64.encodeBase64(publicKey);

            try
            {
                Socket socket = new Socket("127.0.0.1", 21149);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Send packet using opcode 0
                bw.write(0);
                bw.write(new String(bytesEncoded)); // Send the public key to the blockchain as a string
                bw.newLine();
                bw.flush();

                // Read count of blocks
                int count = br.read();
                for (int i = 0; i < count; ++i)
                {
                    Block block = new Block();
                    block.id = br.readLine();
                    block.data = br.readLine();

                    blockList.add(block);
                }
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }

            try
            {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Design/UserView.fxml"));
                Parent root1 = fxmlLoader.load();

                UserViewController controller = fxmlLoader.getController(); // Pass params to PatientViewController using method
                controller.passBlockList(blockList);
                controller.passPrivateKey(privateKey);


                Stage stage = new Stage();
                stage.setTitle("User View");
                stage.setScene(new Scene(root1,450,500));
                stage.show();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
/*

            if (rs.next())
            {
                try
                {
                    Socket socket = new Socket("127.0.0.1", 21149);
                    socket.setSoTimeout(10000);
                    OutputStream os = socket.getOutputStream();

                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
                    try
                    {
                        writer.write(1);
                        writer.newLine();
                        writer.flush();
                    }
                    catch (IOException e)
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Failed to send packet");
                        alert.setHeaderText(null);
                        alert.setContentText(e.getMessage());
                        alert.showAndWait();
                    }
                }
                catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Connection to Blockchain failed");
                    alert.setHeaderText(null);
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                }
            }
            else
            {
                System.out.println("Could not locate CPR number");
                con.close();
                return;
            }
            con.close();

*/
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        /*

        */
    }
}
