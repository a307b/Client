package Doctor;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class JournalTest {
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
            // AES Key Generation prep stuff
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
            byte[] encryptedData = journal.encrypt(journal.toString(), aesKeyBase64);
            String decryptedData = journal.decrypt(encryptedData, aesKeyBase64);
            assertEquals(journal.toString(), decryptedData);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}