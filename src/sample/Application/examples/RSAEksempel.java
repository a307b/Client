package sample.Application.examples;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class RSAEksempel {
    public static void main(String [] args) throws Exception {
        JSONObject obj =  new JSONObject();
        obj.put("Hello", "World");
        obj.put("Lets", "Dance");

        // Get an instance of the RSA key generator
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

        // Generate the keys
        KeyPair myPair = kpg.generateKeyPair();
        System.out.println(myPair.getPrivate());
        System.out.println(myPair.getPublic());

        // Get an instance of the Cipher for RSA encryption/decryption
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");

        // Initiate the Cipher, telling it that it is going to Encrypt, giving it the public key
        cipher.init(Cipher.ENCRYPT_MODE, myPair.getPublic());
        // Create a secret message
        byte[] text = obj.toString().getBytes("UTF8");
        // Cipher it
        byte[] textEncrypted = cipher.doFinal(text);
        // Write the message to an data file
        Path filePath = Paths.get("C:\\Journal\\RSAEncrypted.dat");
        Files.write(filePath, textEncrypted);

        // Reads the data file and saves it in fileContent.
        byte[] fileContent =  Files.readAllBytes(filePath);
        // Initiate the decipher
        cipher.init(cipher.DECRYPT_MODE, myPair.getPrivate());
        // Decipher the message
        byte[] textDecrypted = cipher.doFinal(fileContent);
        // Converts the bytes to a JSON objects
        JSONParser parser = new JSONParser();
        JSONObject returnedJsonObj = (JSONObject) parser.parse(new String(textDecrypted));

        // Prints the JSON Object
        System.out.println(returnedJsonObj);
    }
}