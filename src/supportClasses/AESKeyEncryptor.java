package supportClasses;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.sql.*;
/* Merged this class with AES, so only a test remains. Move to an proper test later */
public class AESKeyEncryptor {
    public static void main(String[] args) {
        /* Creates AES key for the example */
        SecureRandom random = new SecureRandom();

        byte key[] = new byte[32]; // 256 bits
        random.nextBytes(key);

        SecretKeySpec AESKey = new SecretKeySpec(key, "AES");

        /* Encrypts AES key */
        AES aesKeyEncryptor = new AES();
        aesKeyEncryptor.getPublicKeyAndEncrypt("0011223344", AESKey);
    }
}