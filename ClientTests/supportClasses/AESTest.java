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
        //AES.saveAESToDB("123", "123");
        //String test = AES.getAESFromDB("123");
        String test2 = AES.getAESFromDB("ESLvGvU8IcjBq8iBhVan189q3L60PRTRcWDeJ556Cy1bm9ZmxXrqkTHXlGQfYAUUy2E6igAm3tPLPf1UPZS3EjuuzBFnopSpBQ4NiUhDYPSnl7KsP0LhQac9DgZeQcnnKbOVckMIlDQC+CopOxqv3SdoOpgmPVcvOBGYaM/zHRyF6oXwcx8e3EaBGasq1Aj7GbECRadGdcE32QWFzfUELeEGJR/RWhUk2Tcr9HlzLvf5rOyPQ20GWlvaA/Rb9Lzgv5rhVMr1DirGjtrEYTUxYAEq9rwr2uwTX0uiDJuXwQ5d0lKrdbm7Jt0QRWpxw2+Bf/X8H9OxiOHm98w5yWf5wQ==");
        String hello = "hello world";
    }

    /* Tests whether an AES stays the same after been uploaded to and received from server.
     * One instance this will fail, is if duplicate ID's exist. */
    @Test
    void encryptDecryptFromDB() {
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
            AES.saveAESToDB("123", aesKeyBase64);

            String AESFromDB = AES.getAESFromDB("123");
            assertEquals(AESFromDB, aesKeyBase64);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}