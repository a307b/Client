package Doctor.Controller;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;

public class AESKeyEncryptor {
    byte[] getPublicKeyAndEncrypt(String cpr, SecretKeySpec AESKey) {
        byte[] queryPublicKey = null;
        byte[] encryptedAESKey = null;

        Connection conn = null;
        Statement stmt = null;

        /* Retrieves public key from database */
        try {
            conn = DriverManager.getConnection("jdbc:mysql://195.201.113.131/p2?useSSL=false", "p2", "Q23wa!!!");
            stmt = conn.createStatement();
            String query = "SELECT cpr, rsapublickey FROM BorgerDB WHERE cpr = " + cpr;
            ResultSet resultSet = stmt.executeQuery(query);
            while(resultSet.next()){
                queryPublicKey  = resultSet.getBytes("rsapublickey");
                System.out.print("Public key " + queryPublicKey + "\n");
            }
        }catch (java.sql.SQLException sqlException) {
            sqlException.printStackTrace();
        }finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        /* Encrypts AES key with public AES key */
        try {
            /* Creates an instance of RSA cipher */
            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            /* Converts the queried public key bytes to an public key */
            PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(queryPublicKey));
            /* Initialize the cipher, telling it is going to encrypt */
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            /* Encrypts the key */
            encryptedAESKey = cipher.doFinal(AESKey.getEncoded());
            //System.out.println("AES key before encryption: " + AESKey);
            //System.out.println("Encrypted AES-Key: " + encryptedAESKey);
        }catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }catch (InvalidKeyException e) {
            e.printStackTrace();
        }catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }catch (BadPaddingException e) {
            e.printStackTrace();
        }
        //System.out.println("Returned encrypted key: " + encryptedAESKey);
        return encryptedAESKey;
    }

    public static void main(String[] args) {
        /* Creates AES key for the example */
        SecureRandom random = new SecureRandom();

        byte key[] = new byte[32]; // 256 bits
        random.nextBytes(key);

        SecretKeySpec AESKey = new SecretKeySpec(key, "AES");

        /* Encrypts AES key */
        AESKeyEncryptor aesKeyEncryptor = new AESKeyEncryptor();
        aesKeyEncryptor.getPublicKeyAndEncrypt("0011223344", AESKey);
    }
}
