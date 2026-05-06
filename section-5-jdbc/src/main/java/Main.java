import java.sql.*;

public class Main {
  
  public static void main(String[] args) throws Exception {
    String url = "jdbc:postgresql://localhost:5432/danvega";
    String username = "postgres";
    String password = "password";
    
    Class.forName("org.postgresql.Driver");
    Connection conn = DriverManager.getConnection(url, username, password);
    System.out.println("Connected to the database successfully!");
  }
  
}
