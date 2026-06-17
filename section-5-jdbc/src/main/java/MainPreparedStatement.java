import org.jasypt.util.text.BasicTextEncryptor;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainPreparedStatement {

  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  public static final String GET_ALL_SQL = """
    SELECT id, title, description, status, content_type, url, date_created, date_updated FROM content_calendar.content
    """;
  public static final String GET_BY_ID = """
      SELECT id, title, description, status, content_type, url date_created, date_updated FROM content_calendar.content
      WHERE id = ?
      """;
  public static final String CREATE_CONTENT = """
      INSERT INTO content_calendar.content
        (title, description, status, content_type, date_created, url)
      VALUES
        (?, ?, ?, ?, ?, ?)
      """;
  public static final String UPDATE_CONTENT = """
        UPDATE content_calendar.content
          SET title = ?, description = ?, status = ?, content_type = ?, date_updated = ?, url = ?
        WHERE id = ?
        """;
  public static final String DELETE_CONTENT = "DELETE FROM content_calendar.content WHERE id = ?";
  
  public static void main(String[] args) throws Exception {
    BasicTextEncryptor encryptor = new BasicTextEncryptor();
    encryptor.setPassword(System.getenv("JASYPT_ENCRYPTOR_PASSWORD"));
    
    String dbName = System.getenv("DB_NAME");
    String url = System.getenv("DB_URL") + dbName;
    String username = System.getenv("DB_USERNAME");
    String password = encryptor.decrypt(System.getenv("ENC_PASSWORD"));
    
//    Class.forName("org.postgresql.Driver");
    Connection conn = DriverManager.getConnection(url, username, password);
    System.out.println("Connected established");
    
    // ----------create new content
//    PreparedStatement createContent = conn.prepareStatement(CREATE_CONTENT);
//    create(createContent,
//        "Spring Development in VSCode",
//        "This is a guide for developing Spring applications in VS Code.",
//        "IDEA",
//        "VIDEO",
//        LocalDateTime.now(),
//        "https://www.youtube.com/watch?v=Fjx7poyoZik"
//    );
    // ----------update content
//    PreparedStatement updateContent = conn.prepareStatement(UPDATE_CONTENT);
//    update(updateContent,
//      16,
//      "Spring Development in VSCode",
//      "This is a guide for developing Spring applications in VS Code.",
//      "IN_PROGRESS",
//      "VIDEO",
//      LocalDateTime.now(),
//      "https://www.youtube.com/watch?v=Fjx7poyoZik"
//    );
    // ----------delete content
//    PreparedStatement deleteContent = conn.prepareStatement(DELETE_CONTENT);
//    delete(deleteContent, 18);
    // ----------print all content
    PreparedStatement getAll = conn.prepareStatement(GET_ALL_SQL);
    printAll(getAll);
    
    conn.close();
    System.out.println("Connected closed");
  }
  
  //-------------------------------------------------------------------------------------------------------------------
  
  public static void create(
      PreparedStatement stmt,
      String title,
      String description,
      String status,
      String contentType,
      LocalDateTime dateCreated,
      String url
  ) throws SQLException {
    stmt.setString(1, title);
    stmt.setString(2, description);
    stmt.setString(3, status);
    stmt.setString(4, contentType);
    stmt.setObject(5, dateCreated);
    stmt.setString(6, url);
    stmt.execute();
  }
  
  //-------------------------------------------------------------------------------------------------------------------
  
  public static void update(
      PreparedStatement stmt,
      int id,
      String title,
      String description,
      String status,
      String contentType,
      LocalDateTime dateUpdated,
      String url
  ) throws SQLException {
    stmt.setString(1, title);
    stmt.setString(2, description);
    stmt.setString(3, status);
    stmt.setString(4, contentType);
    stmt.setObject(5, dateUpdated);
    stmt.setString(6, url);
    stmt.setInt(7, id);
    stmt.executeUpdate();
  }
  
  //-------------------------------------------------------------------------------------------------------------------
  
  public static void delete(PreparedStatement stmt, int id) throws SQLException {
    stmt.setInt(1, id);
    stmt.execute();
  }
  
  //-------------------------------------------------------------------------------------------------------------------
  
  public static void printById(PreparedStatement stmt, int id) throws SQLException {
    stmt.setInt(1, id);
    ResultSet rs = stmt.executeQuery();
    while (rs.next()) {
      System.out.println(rs.getString(1));
      System.out.println(rs.getString(2));
    }
  }
  
  //-------------------------------------------------------------------------------------------------------------------
  
  public static void printAll(PreparedStatement stmt) throws SQLException {
    ResultSet rs = stmt.executeQuery();
    while (rs.next()) { // print all contents
      int id = rs.getInt("id");
      String title = rs.getString("title");
      String description = rs.getString("description");
      String status = rs.getString("status");
      String content_type = rs.getString("content_type");
      String contentUrl = rs.getString("url");
      LocalDateTime dateCreated = rs.getTimestamp("date_created").toLocalDateTime();
      LocalDateTime dateUpdated = rs.getTimestamp("date_updated") == null ? null : rs.getTimestamp("date_updated").toLocalDateTime();
      System.out.printf(
          "ID: %d, Title: %s, Description: %s, Status: %s, Content Type: %s, URL: %s, Date created: %s, Date updated: %s\n",
          id, title, description, status, content_type, contentUrl,
          dateCreated.format(DATE_TIME_FORMATTER),
          dateUpdated == null ? "" : dateUpdated.format(DATE_TIME_FORMATTER)
      );
    }
  }
  
  //-------------------------------------------------------------------------------------------------------------------
  
}
