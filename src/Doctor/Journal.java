package Doctor;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class Journal
{
    private String patientName;
    private String CPR;
    private String printDate;
    private String startTDate;
    private String endTDate;
    private String dateWritten;
    private String noteType;
    private String examinationDetails;
    private String diagnose;
    private String interpretedBy;
    private String writtenBy;
    private String authenticatedBy;
    private String hospitalName;
    private String departmentName;
    private String uploadedBy;

    public Journal(String patientName, String CPR, String printDate, String startTDate, String endTDate, String dateWritten, String noteType, String examinationDetails, String diagnose, String interpretedBy, String writtenBy, String authenticatedBy, String hospitalName, String departmentName, String uploadedBy)
    {
        this.patientName = patientName;
        this.CPR = CPR;
        this.printDate = printDate;
        this.startTDate = startTDate;
        this.endTDate = endTDate;
        this.dateWritten = dateWritten;
        this.noteType = noteType;
        this.examinationDetails = examinationDetails;
        this.diagnose = diagnose;
        this.interpretedBy = interpretedBy;
        this.writtenBy = writtenBy;
        this.authenticatedBy = authenticatedBy;
        this.hospitalName = hospitalName;
        this.departmentName = departmentName;
        this.uploadedBy = uploadedBy;
    }

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
        byte[] keyBytes = new byte[16];
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
        int keySize = 16;

        // Extract IV.
        byte[] iv = new byte[ivSize];
        System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extract encrypted part.
        int encryptedSize = encryptedIvTextBytes.length - ivSize;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedIvTextBytes, ivSize, encryptedBytes, 0, encryptedSize);

        // Hash key.
        byte[] keyBytes = new byte[keySize];
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(key.getBytes());
        System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.length);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        // Decrypt.
        Cipher cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decrypted = cipherDecrypt.doFinal(encryptedBytes);

        return new String(decrypted);
    }


    @Override
    public String toString()
    {
        return  patientName + ":"
                + CPR + ":"
                + printDate + ":"
                + startTDate + ":"
                + endTDate + ":"
                + dateWritten + ":"
                + noteType + ":"
                + examinationDetails + ":"
                + diagnose + ":"
                + interpretedBy + ":"
                + writtenBy + ":"
                + authenticatedBy + ":"
                + hospitalName + ":"
                + departmentName + ":";
    }


}
