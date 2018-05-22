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
            String query = "SELECT cpr, rsapublickey FROM borger WHERE cpr = 12345";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                System.out.println("Received bytes: " + rs.getBytes("rsapublickey"));
                byte[] publicKey  = rs.getBytes("rsapublickey");
                String retrievedBytes = new String(publicKey);
                assertEquals("hello world", retrievedBytes);
            }

            // Code for inserting test field to the database
            /*
            String test = "hello world";
            byte[] testbytes = test.getBytes();
            String keyquery = "INSERT INTO borger VALUES (?,?)";
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(keyquery);
            pstmt.setString(1, "12345");
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