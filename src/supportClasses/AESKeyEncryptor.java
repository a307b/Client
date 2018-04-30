package supportClasses;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/* TO DO:
 * Query public key og krypter aes nøgle (og eventuelt tjekke om den kan dekrypteres)
  * */
public class AESKeyEncryptor {
    void getPublicKeyAndEncrypt() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://195.201.113.131/p2?useSSL=false", "p2", "Q23wa!!!");
            Statement stmt = conn.createStatement();
        }catch (java.sql.SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    PublicKey publicKey;
/*
    {
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    */
}
