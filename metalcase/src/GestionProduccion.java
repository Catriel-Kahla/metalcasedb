import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

// Definición de la clase GestionProduccion
public class GestionProduccion {
    // Consultas SQL
    private static final String INSERT_PRODUCCION = "INSERT INTO produccion (nombreProduccion, productoTerminado, materiaPrima) VALUES (?, ?, ?)";
    private static final String SELECT_PRODUCCION = "SELECT p.id, p.nombreProduccion, mp.nombreProducto AS materiaPrima, pt.nombreProducto AS productoTerminado FROM produccion p JOIN producto mp ON p.materiaPrima = mp.id JOIN producto pt ON p.productoTerminado = pt.id";

    // Método para mostrar el menú de gestión de producción
    public static void menuGestionProduccion(Scanner scanner, Connection connection) {
        int option;
        do {
            System.out.println("\n=== Gestión de Producción ===");
            System.out.println("1. Registrar Producción");
            System.out.println("2. Listar Producciones");
            System.out.println("3. Volver al Menú Principal");
            System.out.print("Selecciona una opción: ");
            option = scanner.nextInt(); // Lectura de la opción seleccionada por el usuario

            switch (option) {
                case 1:
                    registrarProduccion(scanner, connection); // Llamada para registrar una nueva producción
                    break;
                case 2:
                    listarProducciones(connection); // Llamada para listar todas las producciones
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal..."); // Mensaje de salida
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo."); // Manejo de opción inválida
            }
        } while (option != 3); // Repetir el menú mientras la opción no sea 3 (Volver al Menú Principal)
    }

    // Método para registrar una nueva producción
    private static void registrarProduccion(Scanner scanner, Connection connection) {
        scanner.nextLine(); // Consumir la nueva línea
        System.out.print("Nombre de la Producción: ");
        String nombre = scanner.nextLine();
        GestionProductos.listarProductos(connection); // Mostrar la lista de productos
        System.out.print("ID de la Materia Prima: ");
        int nombreMateriaPrima = scanner.nextInt();
        System.out.print("ID del Producto Terminado: ");
        int nombreProductoTerminado = scanner.nextInt();

        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCCION);
            statement.setString(1, nombre);
            statement.setInt(2, nombreMateriaPrima);
            statement.setInt(3, nombreProductoTerminado);
            int rowsInserted = statement.executeUpdate(); // Ejecución de la inserción
            if (rowsInserted > 0) {
                System.out.println("Producción registrada con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar producción: " + e.getMessage());
        }
    }

    // Método para listar todas las producciones
    private static void listarProducciones(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCCION);
            ResultSet resultSet = statement.executeQuery(); // Ejecución de la consulta
            System.out.println("\n=== Lista de Producciones ===");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                                   ", Nombre: " + resultSet.getString("nombreProduccion") +
                                   ", Materia Prima: " + resultSet.getString("materiaPrima") +
                                   ", Producto Terminado: " + resultSet.getString("productoTerminado"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de producciones: " + e.getMessage());
        }
    }
}
