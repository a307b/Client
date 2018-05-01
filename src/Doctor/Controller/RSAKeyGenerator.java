package Doctor.Controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

// Class used to generate and save keys. Private keys are stored in the privateKey directory and
// public keys are uploaded to BorgerDB
public class RSAKeyGenerator {
    /* CPR number is used as the private keys local file name and as ID when it is uploaded to BorgerDB */
    public void saveKeyPair(String CPR) {
        /* Generates key-pair */
        KeyPair keyPair = null;
        try {
            keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        /* Makes public and private key into bytes */
        byte[] publicKey = keyPair.getPublic().getEncoded();
        byte[] privateKey1 = keyPair.getPrivate().getEncoded();


        /* Saves private-key locally to privateKeys directory */
        Path filePath = Paths.get("C:\\GitHub\\Client\\src\\Patient\\" + CPR + ".dat");
        BufferedOutputStream os = null;
        try {
            os = new BufferedOutputStream(Files.newOutputStream(filePath));
            os.write(privateKey1);   // Saves file
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /* Uploads public key to database */
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://195.201.113.131/p2?useSSL=false", "p2", "Q23wa!!!");
            String query = "INSERT INTO `BorgerDB` (`cpr`, `rsapublickey`) VALUES (?,?)";
            pstmt = (PreparedStatement) conn.prepareStatement(query);
            pstmt.setString(1, CPR);
            pstmt.setBytes(2, publicKey);
        }catch (java.sql.SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        RSAKeyGenerator keys = new RSAKeyGenerator();
        keys.saveKeyPair("0011223344");
    }
}