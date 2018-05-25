package supportClasses;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.sql.*;

public class AES {
    /**
     * COPY PASTE FROM https://gist.github.com/itarato/abef95871756970a9dad
     * ALL CREDITS TO : https://gist.github.com/itarato/abef95871756970a9dad
     */
    public byte[] encrypt(String dataToBeEncrypted, String key) throws Exception {
        byte[] clean = dataToBeEncrypted.getBytes();

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

    public void saveAESToDB(String blockID, String AESKey) {
        /* Uploads AES key to database */
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://195.201.113.131/p2?useSSL=false", "p2", "Q23wa!!!");
            String query = "INSERT INTO trans VALUES (?,?)";
            pstmt = (PreparedStatement) conn.prepareStatement(query);
            pstmt.setString(1, blockID);
            pstmt.setString(2, AESKey);
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

    public String getAESFromDB(String blockID) {
        /* Retrieves AES key from database */
        try {
            /* Connects to database, reads the rsapublickey from the entry where CPR is 0011223344 */
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://195.201.113.131/p2?useSSL=false", "p2", "Q23wa!!!");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM `trans` WHERE `blockid` = \""+ blockID +"\";";
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String AESKey =  rs.getString("aeskey");
            stmt.close();
            conn.close();
            return AESKey;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
