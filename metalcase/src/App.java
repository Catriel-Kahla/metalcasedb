// App.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {


    public static Connection connectDatabase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/metalcase_bd", "root", "");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return connection;
    }

    public static void disconnectDatabase(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Desconexión exitosa de la base de datos.");
            } catch (SQLException e) {
                System.out.println("Error al desconectar de la base de datos: " + e.getMessage());
            }
        }
    }
}
