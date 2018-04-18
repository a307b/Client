package sample.Application;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class PrivateKeys {
    // I dont know how we are going to receive a private key without making an key pair
    // Perhaps sending it from another program health-card code like Jakob explained
    // For now this only has the private key

    // Overvej at lave private keys til en map
    static KeyPair keypair1;
    static PrivateKey privateKey1;
    static PublicKey publicKey1;

    KeyPair keypair2;
    PrivateKey privateKey2;
    PublicKey publicKey2;

    public PrivateKeys() throws Exception{
        this.keypair1 = KeyPairGenerator.getInstance("RSA").generateKeyPair();;
        this.privateKey1 = KeyPairGenerator.getInstance("RSA").generateKeyPair().getPrivate();
        this.publicKey1 = KeyPairGenerator.getInstance("RSA").generateKeyPair().getPublic();
        this.keypair2 = KeyPairGenerator.getInstance("RSA").generateKeyPair();;
        this.privateKey2 = KeyPairGenerator.getInstance("RSA").generateKeyPair().getPrivate();
        this.publicKey2 = KeyPairGenerator.getInstance("RSA").generateKeyPair().getPublic();
    }
}
