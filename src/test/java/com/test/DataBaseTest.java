package src.test.java.com.test;

import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseTest {
  public static void main(String[] args) {
    try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/medicalApp", "postgres", "postgres")) {

      System.out.println("Java JDBC PostgreSQL Example");
      // When this class first attempts to establish a connection, it automatically loads any JDBC 4.0 drivers found within
      // the class path. Note that your application must manually load any JDBC drivers prior to version 4.0.
//          Class.forName("org.postgresql.Driver");

      System.out.println("Connected to PostgreSQL database!");
      Statement statement = connection.createStatement();
      System.out.println("Reading car records...");
      System.out.printf("%-30.30s  %-30.30s%n", "Model", "Price");
      ResultSet resultSet = statement.executeQuery("SELECT * FROM public.cancer");
      while (resultSet.next()) {

        String regimenString = resultSet.getString("regimen");

        if (null != regimenString && regimenString.split(",").length > 0)
        {
          for (String regimenId: regimenString.split(",")) {
            if (!StringUtils.isEmpty(regimenId)) {
              System.out.printf("insert into cancer_regimen_link (cancer_id, regimen_id) values (\"%s\", \"%s\")%n", resultSet.getInt("id"),regimenId);
            }
          }
        }
      }

    } /*catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        }*/ catch (SQLException e) {
      System.out.println("Connection failure.");
      e.printStackTrace();
    }
  }
}