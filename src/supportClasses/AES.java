package supportClasses;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;

public class AES {
    /**
     * HAS TO BE ALTERED!!! -
     * COPY PASTE FROM https://gist.github.com/itarato/abef95871756970a9dad
     * ALL CREDITS TO : https://gist.github.com/itarato/abef95871756970a9dad
     */
    public byte[] encrypt(String plainText, String key) throws Exception {
        byte[] clean = plainText.getBytes();

        // Generating IV.
        int ivSize = 16;
        byte[] iv = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Hashing key.
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(key.getBytes("UTF-8"));
        byte[] keyBytes = new byte[32];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Encrypt.
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encrypted = cipher.doFinal(clean);

        // Combine IV and encrypted part.
        byte[] encryptedIVAndText = new byte[ivSize + encrypted.length];
        System.arraycopy(iv, 0, encryptedIVAndText, 0, ivSize);
        System.arraycopy(encrypted, 0, encryptedIVAndText, ivSize, encrypted.length);

        return encryptedIVAndText;
    }

    /**
     * HAS TO BE ALTERED!!! -
     * COPY PASTE FROM https://gist.github.com/itarato/abef95871756970a9dad
     * ALL CREDITS TO : https://gist.github.com/itarato/abef95871756970a9dad
     */
    public String decrypt(byte[] encryptedIvTextBytes, String key) throws Exception {
        int ivSize = 16;
        int keySize = 32;

        // Extract IV
        byte[] iv = new byte[ivSize];
        System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extract encrypted part
        int encryptedSize = encryptedIvTextBytes.length - ivSize;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedIvTextBytes, ivSize, encryptedBytes, 0, encryptedSize);

        // Hash key
        byte[] keyBytes = new byte[keySize];
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(key.getBytes());
        System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Decrypt
        Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);

        return new String(decrypted);
    }

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
}
