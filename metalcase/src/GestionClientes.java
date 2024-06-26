import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

// Definición de la clase GestionClientes
public class GestionClientes {
    // Consultas SQL
    private static final String INSERT_CLIENTE = "INSERT INTO cliente (nombre, direccion, telefono, email) VALUES (?, ?, ?, ?)";
    private static final String SELECT_CLIENTES = "SELECT * FROM cliente";
    private static final String DELETE_CLIENTE = "DELETE FROM cliente WHERE id = ?";
    private static final String UPDATE_CLIENTE = "UPDATE cliente SET nombre = ?, direccion = ?, telefono = ?, email = ? WHERE id = ?";

    // Método para mostrar el menú de gestión de clientes
    public static void menuGestionClientes(Scanner scanner, Connection connection) {
        int option;
        do {
            System.out.println("\n=== Gestión de Clientes ===");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Modificar Cliente");
            System.out.println("4. Eliminar Cliente");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Selecciona una opción: ");
            option = scanner.nextInt(); // Lectura de la opción seleccionada por el usuario

            switch (option) {
                case 1:
                    registrarCliente(scanner, connection); // Llamada para registrar un cliente
                    break;
                case 2:
                    listarClientes(connection); // Llamada para listar todos los clientes
                    break;
                case 3:
                    modificarCliente(scanner, connection); // Llamada para modificar un cliente
                    break;
                case 4:
                    eliminarCliente(scanner, connection); // Llamada para eliminar un cliente
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal..."); // Mensaje de salida
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo."); // Manejo de opción inválida
            }
        } while (option != 5); // Repetir el menú mientras la opción no sea 5 (Volver al Menú Principal)
    }

    // Método para registrar un nuevo cliente
    private static void registrarCliente(Scanner scanner, Connection connection) {
        scanner.nextLine(); // Limpia el buffer del scanner
        System.out.print("Nombre del Cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Dirección del Cliente: ");
        String direccion = scanner.nextLine();
        System.out.print("Teléfono del Cliente: ");
        String telefono = scanner.nextLine();
        System.out.print("Email del Cliente: ");
        String email = scanner.nextLine();

        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_CLIENTE);
            statement.setString(1, nombre);
            statement.setString(2, direccion);
            statement.setString(3, telefono);
            statement.setString(4, email);
            int rowsInserted = statement.executeUpdate(); // Ejecución de la inserción
            if (rowsInserted > 0) {
                System.out.println("Cliente registrado con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
        }
    }

    // Método para listar todos los clientes
    private static void listarClientes(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_CLIENTES);
            ResultSet resultSet = statement.executeQuery(); // Ejecución de la consulta
            System.out.println("\n=== Lista de Clientes ===");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                                   ", Nombre: " + resultSet.getString("nombre") +
                                   ", Dirección: " + resultSet.getString("direccion") +
                                   ", Teléfono: " + resultSet.getString("telefono") +
                                   ", Email: " + resultSet.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de clientes: " + e.getMessage());
        }
    }

    // Método para modificar un cliente existente
    private static void modificarCliente(Scanner scanner, Connection connection) {
        System.out.print("ID del Cliente a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpia el buffer del scanner
        System.out.print("Nuevo Nombre del Cliente: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva Dirección del Cliente: ");
        String direccion = scanner.nextLine();
        System.out.print("Nuevo Teléfono del Cliente: ");
        String telefono = scanner.nextLine();
        System.out.print("Nuevo Email del Cliente: ");
        String email = scanner.nextLine();

        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENTE);
            statement.setString(1, nombre);
            statement.setString(2, direccion);
            statement.setString(3, telefono);
            statement.setString(4, email);
            statement.setInt(5, id);
            int rowsUpdated = statement.executeUpdate(); // Ejecución de la actualización
            if (rowsUpdated > 0) {
                System.out.println("Cliente modificado con éxito.");
            } else {
                System.out.println("No se encontró el cliente con ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar cliente: " + e.getMessage());
        }
    }

    // Método para eliminar un cliente existente
    private static void eliminarCliente(Scanner scanner, Connection connection) {
        listarClientes(connection); // Llama a listarClientes para mostrar los clientes antes de eliminar
        System.out.print("ID del Cliente a eliminar: ");
        int id = scanner.nextInt();

        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_CLIENTE);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate(); // Ejecución de la eliminación
            if (rowsDeleted > 0) {
                System.out.println("Cliente eliminado con éxito.");
            } else {
                System.out.println("No se encontró el cliente con ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
}
