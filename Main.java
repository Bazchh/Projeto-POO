import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class Main {
    public static void main(String[] args) {

        Connection connection = null;
        try {
          // Estabelecendo conexão com o banco de dados
          connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "mikael", "123456789");
          System.out.println("Conexão bem-sucedida!");
        } catch (SQLException e) {
          System.out.println("Falha ao conectar com o banco de dados.");
          e.printStackTrace();
        } finally {
          try {
            if (connection != null) {
            
              connection.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
      }
    }
