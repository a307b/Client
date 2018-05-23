package supportClasses;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RSA {
    // Method used to generate and save keys. Private keys are stored in the privateKey directory and
    // public keys are uploaded to BorgerDB
    /* CPR number is used as the private keys local file name and as ID when it is uploaded to BorgerDB */
    public void saveKeyPair(String CPR) {
        /* Generates key-pair */
        KeyPair keyPair = null;
        try {
            keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        /* Makes public and private key into strings withe Base64 */
        String publicKeyString = Base64.encodeBase64String(keyPair.getPublic().getEncoded());
        String privateKeyString = Base64.encodeBase64String(keyPair.getPrivate().getEncoded());




        /* Saves private-key locally to privateKeys directory */
        Path filePath = Paths.get("C:\\GitHub\\Patient\\src\\privateKeys\\" + CPR + ".txt");
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(Files.newBufferedWriter(filePath));
            bufferedWriter.write(privateKeyString);   // Saves file
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /* Uploads public key to database */
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://195.201.113.131/p2?useSSL=false", "p2", "Q23wa!!!");
            String query = "INSERT INTO borger VALUES (?,?)";
            pstmt = (PreparedStatement) conn.prepareStatement(query);
            pstmt.setString(1, CPR);
            pstmt.setString(2, publicKeyString);
            pstmt.execute();
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

    public byte[] encrypt(String dataToBeEncrypted, PublicKey publicKey) {
        try {
            /* Creates cipher object and initializes cipher to encrypt */
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            /* Converts string to bytes and encrypts it */
            byte[] dataToBeEncryptedBytes = dataToBeEncrypted.getBytes("UTF8");
            byte[] encryptedData = cipher.doFinal(dataToBeEncryptedBytes);
            return encryptedData;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(byte[] encryptedBytes, PrivateKey privateKey) {
        try {
            // Creates RSA cipher and activates Decrypt mode with the private key
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedString = new String(decryptedBytes);
            return decryptedString;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
