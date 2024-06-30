// App.java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Definición de la clase principal App
public class App {

    // Método estático para establecer la conexión con la base de datos
    public static Connection connectDatabase() {
        Connection connection = null; // Inicialización de la variable Connection a null
        try {
            // Intento de establecer la conexión utilizando DriverManager y los parámetros de conexión
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/metalcase_bd", "root", "");
        } catch (SQLException e) {
            // Captura y manejo de excepciones en caso de que ocurra un error al conectar
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return connection; // Retorno de la conexión establecida (o null si falló)
    }

    // Método estático para cerrar la conexión con la base de datos
    public static void disconnectDatabase(Connection connection) {
        if (connection != null) { // Verificación de que la conexión no es null
            try {
                connection.close(); // Intento de cerrar la conexión
                System.out.println("Desconexión exitosa de la base de datos.");
            } catch (SQLException e) {
                // Captura y manejo de excepciones en caso de que ocurra un error al desconectar
                System.out.println("Error al desconectar de la base de datos: " + e.getMessage());
            }
        }
    }
}
