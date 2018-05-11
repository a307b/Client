package Doctor;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

/**
 * This class will be used to make an connection to the Blockchain
 */

public class Blockchain
{
    private String currentHash;
    private String timeStamp;
    private String transactionID;
    private String aesKey;
    private byte[] journalData;
    private String borgerPublicKey;

    public Blockchain(String transactionID, String aesKey, byte[] journalData, String borgerPublicKey)
    {
        timeStamp = getTimeStamp();
        this.transactionID = transactionID;
        this.aesKey = aesKey;
        this.borgerPublicKey = borgerPublicKey;
        this.journalData = journalData;
    }

    public void setCurrentHash(String currentHash)
    {
        this.currentHash = currentHash;
    }

    public String generateHash(byte[] encryptedData)
    {
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(encryptedData);

            byte byteData[] = md.digest();

            StringBuffer stringBuffer = new StringBuffer();

            for (int i = 0; i < byteData.length; i++) {
                stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            return stringBuffer.toString();

        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private String getTimeStamp()
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        return String.valueOf(timestamp.getTime());
    }

    public void sendBlock()
    {
        System.out.println("Sending block ");
    }

    @Override
    public String toString()
    {
        return "Blockchain{" +
                "currentHash='" + currentHash + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", transactionID='" + transactionID + '\'' +
                ", aesKey='" + aesKey + '\'' +
                ", journalData=" + journalData +
                ", borgerPublicKey='" + borgerPublicKey + '\'' +
                '}';
    }
}
