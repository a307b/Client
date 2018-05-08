import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class EncryptFromDBPrivateKeyTest {
    /* Tests whether objects that has been encrypted with an RSA key and uploaded to
     BorgerDB can be decrypted successfully. */
    @Test
    void main() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://195.201.113.131/p2?useSSL=false", "p2", "Q23wa!!!");
            Statement stmt = conn.createStatement();
            String query = "SELECT cpr, rsapublickey FROM BorgerDB WHERE cpr = 123";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                byte[] publicKey  = rs.getBytes("rsapublickey");
                String retrievedBytes = new String(publicKey);
                assert(retrievedBytes.equals("hello world"));
            }

            /*  Code for inserting test field to the database
            String test = "hello world";
            byte[] testbytes = test.getBytes();
            String keyquery = "INSERT INTO BorgerDB VALUES (?,?)";
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(keyquery);
            pstmt.setString(1, "123");
            pstmt.setBytes(2, testbytes);
            pstmt.execute();
            */
            rs.close();
            conn.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}