package sample.Application.examples;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;

public class AESDecryption {

    @SuppressWarnings("static-access")
    public String decrypt(String encryptedText) throws Exception {

        String password="2b6%u3-JEiL}V'mXzQVKh1j=JY9Oo%ds;<nF(MAznrrtDI-L6Jvy3!|D2?pS.MV";
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        //strip off the salt and IV
        ByteBuffer buffer = ByteBuffer.wrap(new Base64().decode(encryptedText));

        byte[] saltBytes = new byte[20];
        buffer.get(saltBytes, 0, saltBytes.length);
        byte[] ivBytes1 = new byte[cipher.getBlockSize()];
        buffer.get(ivBytes1, 0, ivBytes1.length);
        byte[] encryptedTextBytes = new byte[buffer.capacity() - saltBytes.length - ivBytes1.length];

        buffer.get(encryptedTextBytes);

        //Deriving the key

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65556, 256);

        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");

        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes1));

        byte[] decryptedTextBytes = null;

        try {
            decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return new String(decryptedTextBytes);
    }
}

