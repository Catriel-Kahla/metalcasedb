import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionProductos {
    // Consultas SQL
    private static final String INSERT_PRODUCTO = "INSERT INTO producto (nombreProducto, descripcion, precio, stock) VALUES (?, ?, ?, ?)";
    private static final String SELECT_PRODUCTOS = "SELECT * FROM producto";
    private static final String DELETE_PRODUCTO = "DELETE FROM producto WHERE id = ?";

    public static void menuGestionProductos(Scanner scanner, Connection connection) {
        int option;
        do {
            System.out.println("\n=== Gestión de Productos ===");
            System.out.println("1. Registrar Producto");
            System.out.println("2. Registrar Entrada de Producto");
            System.out.println("3. Registrar Salida de Producto");
            System.out.println("4. Listar Productos");
            System.out.println("5. Eliminar Producto");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Selecciona una opción: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    registrarProducto(scanner, connection);
                    break;
                case 2:
                    registrarEntradaProducto(scanner, connection);
                    break;
                case 3:
                    registrarSalidaProducto(scanner, connection);
                    break;
                case 4:
                    listarProductos(connection);
                    break;
                case 5:
                    eliminarProducto(scanner, connection);
                    break;
                case 6:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (option != 6);
    }

    private static void registrarProducto(Scanner scanner, Connection connection) {
        scanner.nextLine();
        System.out.print("Nombre del Producto: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción del Producto: ");
        String descripcion = scanner.nextLine();
        System.out.print("Precio del Producto: ");
        int precio = scanner.nextInt();
        System.out.print("Stock del Producto: ");
        int stock = scanner.nextInt();

        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCTO);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setInt(3, precio);
            statement.setInt(4, stock);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Producto registrado con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar producto: " + e.getMessage());
        }
    }

    private static void registrarEntradaProducto(Scanner scanner, Connection connection) {
        listarProductos(connection);
        System.out.print("ID del Producto: ");
        int id = scanner.nextInt();
        System.out.print("Cantidad a ingresar: ");
        int cantidad = scanner.nextInt();

        Producto producto = Producto.obtenerProductoPorId(connection, id); // Obtener producto desde la base de datos

        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        producto.agregarStock(connection, cantidad); // Agregar stock del producto en la base de datos
        System.out.println("Entrada de producto registrada con éxito.");
    }

    private static void registrarSalidaProducto(Scanner scanner, Connection connection) {
        listarProductos(connection);
        System.out.print("ID del Producto: ");
        int id = scanner.nextInt();
        System.out.print("Cantidad a salir: ");
        int cantidad = scanner.nextInt();

        Producto producto = Producto.obtenerProductoPorId(connection, id); // Obtener producto desde la base de datos

        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        producto.reducirStock(connection, cantidad); // Reducir stock del producto en la base de datos
        System.out.println("Salida de producto registrada con éxito.");
    }

    public static void listarProductos(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCTOS);
            ResultSet resultSet = statement.executeQuery();
            System.out.println("\n=== Lista de Productos ===");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                                   ", Nombre: " + resultSet.getString("nombreProducto") +
                                   ", Descripción: " + resultSet.getString("descripcion") +
                                   ", Precio: " + resultSet.getString("precio") +
                                   ", Stock: " + resultSet.getString("stock"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de productos: " + e.getMessage());
        }
    }

    private static void eliminarProducto(Scanner scanner, Connection connection) {
        listarProductos(connection);
        System.out.print("ID del Producto a eliminar: ");
        int id = scanner.nextInt();

        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCTO);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Producto eliminado con éxito.");
            } else {
                System.out.println("No se encontró el producto con ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }
}
