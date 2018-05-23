package supportClasses;

import Doctor.Journal;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import supportClasses.AES;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class AESTest {
    Journal journal;

    @BeforeEach
    void setUp() {
        journal = new Journal("Tommy", "1122334455", "1/2/3", "2/3/4", "4/5/6",
                "7/8/9", "Røntennote", "The patient has an broken leg",
                "Broken leg", "Karl",
                "Christinna", "Lars", "Aalborg Hosptial", "Sengeafdelingen", "Lars");
    }

    /* Tests the encrypt and decrypt method on a journal entry */
    @Test
    void encryptDecryptTest() {
        try {
            /* Creates AES key */
            SecureRandom random = new SecureRandom();
            byte key[] = new byte[32]; // 256 bits
            random.nextBytes(key);
            byte IV[] = new byte[16]; // 128 bits
            random.nextBytes(IV);
            byte[] keyIV = new byte[key.length + IV.length];
            System.arraycopy(key, 0, keyIV, 0, key.length);
            System.arraycopy(IV, 0, keyIV, key.length, IV.length);
            String aesKeyBase64 = Base64.encodeBase64String(keyIV);

            // Encryption and decryption
            AES AES = new AES();
            byte[] encryptedData = AES.encrypt(journal.toString(), aesKeyBase64);
            String encodedEncryptedData = Base64.encodeBase64String(encryptedData);

            String decryptedData = AES.decrypt(Base64.decodeBase64(encodedEncryptedData), aesKeyBase64);
            assertEquals(journal.toString(), decryptedData);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Tests that all the encryption-decryption methods work together.
       Encrypting the data with the AES key, encrypts and decrypts the AES key and uses the decrypted AES-key to
       decrypt the data once again */
    @Test
    void encryptDecryptWithEncryptedAESKeyTest() {
        try {
            /* Creates AES key with salt */
            SecureRandom random = new SecureRandom();
            byte key[] = new byte[32]; // 256 bits
            random.nextBytes(key);
            byte IV[] = new byte[16]; // 128 bits
            random.nextBytes(IV);
            byte[] keyIV = new byte[key.length + IV.length];
            System.arraycopy(key, 0, keyIV, 0, key.length);
            System.arraycopy(IV, 0, keyIV, key.length, IV.length);
            String aesKeyBase64 = Base64.encodeBase64String(keyIV);

            /* RSA keyPair */
            KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
            // Encryption and decryption
            RSA RSA = new RSA();
            AES AES = new AES();
            /* Encrypts data */
            byte[] encryptedData = AES.encrypt(journal.toString(), aesKeyBase64);
            String encodedEncryptedData = Base64.encodeBase64String(encryptedData);

            /* Encrypts AES-key and saves it as an String*/
            byte[] encryptedAESKey = RSA.encrypt(aesKeyBase64, keyPair.getPublic());
            String encryptedAESKeyString = Base64.encodeBase64String(encryptedAESKey);
            /* Decrypts AES-key */
            byte[] returnedEncryptedAESKey = Base64.decodeBase64(encryptedAESKeyString);
            String decryptedAESKey = RSA.decrypt(returnedEncryptedAESKey, keyPair.getPrivate());

            /* Decrypts data with decrypted AES-key */
            String decryptedData = AES.decrypt(Base64.decodeBase64(encodedEncryptedData), decryptedAESKey);
            assertEquals(journal.toString(), decryptedData);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    void AESToAndFromDBTest() {
        AES AES = new AES();
        AES.getAESFromDB("EQhLRx6LnBJjlOYWDdBxASQHnG5FN+Z1fMJEKnd1MSE=");
    }
}