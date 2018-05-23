package supportClasses;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;

import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RSATest {
    /* Caution: There may only be one 0011223344 entry in the table in the database, otherwise an error will occur. */
    @Test
    void saveKeyPairTest() {
        RSA rsa = new RSA();
        rsa.saveKeyPair("0011223344");
    }

    @Test
    void EncryptDecryptTest() {
        try {
            KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
            RSA RSA = new RSA();
            String testString = "hello world";
            byte[] encryptedString = RSA.encrypt(testString, keyPair.getPublic());
            System.out.println(encryptedString);
            String decryptedString = RSA.decrypt(encryptedString, keyPair.getPrivate());
            assertEquals(testString, decryptedString);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Tests whether RSA encryption and decryption works properly after public key has been retrieved from the database
     * and private key has been read from local file */
    @Test
    void saveKeyPairEncryptionDecryptionTest() {
        try {
            /* Retrieves privateKey string from local file and converts it into an private key */
            BufferedReader bufferedReader = new BufferedReader(Files.newBufferedReader(Paths.get("C:\\GitHub\\Patient\\src\\privateKeys\\0011223344.txt")));
            /* Saves the content of the file in privateKeyString */
            String privateKeyString =  bufferedReader.lines().collect(Collectors.joining());
            System.out.println(privateKeyString);
            byte[] decodedPrivateKey = Base64.decodeBase64(privateKeyString);
            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodedPrivateKey));


            /* Connects to database, reads the rsapublickey from the entry where CPR is 0011223344 */
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://195.201.113.131/p2?useSSL=false", "p2", "Q23wa!!!");
            Statement stmt = conn.createStatement();
            String query = "SELECT cpr, rsapublickey FROM borger WHERE cpr = 0011223344";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            System.out.println("Received bytes: " + rs.getBytes("rsapublickey"));
            byte[] publicKeyBytes =  rs.getBytes("rsapublickey");

            /* Converts the retrieved public key to an public key */
            byte[] decodedPublicKey = Base64.decodeBase64(publicKeyBytes);
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decodedPublicKey));

            /* Encrypts and decrypts an test string */
            RSA RSA = new RSA();
            String testString = "hello world";
            byte[] encryptedString = RSA.encrypt(testString, publicKey);
            String decryptedString = RSA.decrypt(encryptedString, privateKey);
            assertEquals(testString, decryptedString);

            rs.close();
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}