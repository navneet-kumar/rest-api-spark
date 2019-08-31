package services.implementations.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresSQLConnection {
  private static PostgresSQLConnection sqlConnection;
  private Connection connection;

  private PostgresSQLConnection() throws SQLException {
    connection =
        DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
    connection.setAutoCommit(true);
  }

  public static PostgresSQLConnection getInstance() {
    if (sqlConnection == null) {
      try {
        sqlConnection = new PostgresSQLConnection();
      } catch (SQLException ex) {
        System.out.println(ex.getErrorCode() + " " + ex.getLocalizedMessage());
        ex.printStackTrace();
      }
    }
    return sqlConnection;
  }

  public boolean executeNonSelect(String sql) {
    boolean status = false;
    try {
      PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
      status = preparedStatement.execute();
    } catch (SQLException ex) {
      System.out.println(ex.getErrorCode() + " " + ex.getLocalizedMessage());
      ex.printStackTrace();
    }
    return status;
  }

  public ResultSet executeSelect(PreparedStatement preparedStatement) {
    ResultSet resultSet = null;
    try {
      resultSet = preparedStatement.executeQuery();
    } catch (SQLException ex) {
      System.out.println(ex.getErrorCode() + " " + ex.getLocalizedMessage());
      ex.printStackTrace();
    }
    return resultSet;
  }

  public boolean test() {
    return executeNonSelect("select 1");
  }

  public Connection getConnection() {
    return connection;
  }
}
