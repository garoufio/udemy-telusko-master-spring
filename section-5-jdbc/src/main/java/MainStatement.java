import java.sql.*;
import java.time.LocalDateTime;

public class MainStatement {
  
  public static void main(String[] args) throws Exception {
    String url = "jdbc:postgresql://localhost:5432/danvega";
    String username = "postgres";
    String password = "password";
    
    Class.forName("org.postgresql.Driver");
    Connection conn = DriverManager.getConnection(url, username, password);
    System.out.println("Connected established");
    
    Statement stmt = conn.createStatement();
    // ----------create new content
//    create(stmt,
//        "Spring Development in VSCode",
//        "This is a guide for developing Spring applications in VS Code.",
//        "IDEA",
//        "VIDEO",
//        LocalDateTime.now(),
//        "https://www.youtube.com/watch?v=Fjx7poyoZik"
//    );
    // ----------update content
//    update(stmt,
//      16,
//      "Spring Development in VSCode",
//      "This is a guide for developing Spring applications in VS Code.",
//      "IN_PROGRESS",
//      "VIDEO",
//      LocalDateTime.now(),
//      "https://www.youtube.com/watch?v=Fjx7poyoZik"
//    );
    // ----------delete content
//    delete(stmt, 16);
    printAll(stmt);
    
    conn.close();
    System.out.println("Connected closed");
  }
  
  //-------------------------------------------------------------------------------------------------------------------
  
  public static void create(
      Statement stmt,
      String title,
      String description,
      String status,
      String contentType,
      LocalDateTime dateCreated,
      String url
  ) throws SQLException {
    String sql = """
        INSERT INTO content_calendar.content
          (title, description, status, content_type, date_created, url)
        VALUES
          ('%s', '%s', '%s', '%s', '%s', '%s')
        """.formatted(title, description, status, contentType, dateCreated, url);
    
    stmt.execute(sql);
  }
  
  //-------------------------------------------------------------------------------------------------------------------
  
  public static void update(
      Statement stmt,
      int id,
      String title,
      String description,
      String status,
      String contentType,
      LocalDateTime dateUpdated,
      String url
  ) throws SQLException {
    String sql = """
          UPDATE content_calendar.content
            SET title = '%s', description = '%s', status = '%s', content_type = '%s', date_updated = '%s', url = '%s'
          WHERE id = %d
        """.formatted(title, description, status, contentType, dateUpdated, url, id);
    
    stmt.execute(sql);
  }
  
  //-------------------------------------------------------------------------------------------------------------------
  
  public static void delete(Statement stmt, int id) throws SQLException {
    String sql = """
        DELETE FROM content_calendar.content WHERE id = %d
        """.formatted(id);
  
    stmt.execute(sql);
  }
  
  //-------------------------------------------------------------------------------------------------------------------
  
  public static void printAll(Statement stmt) throws SQLException {
    String sql = "SELECT id, title, description, status, content_type, url FROM content_calendar.content";
    
    ResultSet rs = stmt.executeQuery(sql);
    while (rs.next()) { // print all contents
      int id = rs.getInt("id");
      String title = rs.getString("title");
      String description = rs.getString("description");
      String status = rs.getString("status");
      String content_type = rs.getString("content_type");
      String contentUrl = rs.getString("url");
      System.out.printf(
          "ID: %d, Title: %s, Description: %s, Status: %s, Content Type: %s, URL: %s%n",
          id, title, description, status, content_type, contentUrl
      );
    }
  }
  
  //-------------------------------------------------------------------------------------------------------------------
  
}
