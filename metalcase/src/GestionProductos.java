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

    // Método para mostrar el menú de gestión de productos
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
        } while (option != 6); // Repetir el menú mientras la opción no sea 6 (Volver al Menú Principal)
    }

    // Método para registrar un nuevo producto
    private static void registrarProducto(Scanner scanner, Connection connection) {
        scanner.nextLine(); // Consumir la nueva línea
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
            int rowsInserted = statement.executeUpdate(); // Ejecutar la inserción
            if (rowsInserted > 0) {
                System.out.println("Producto registrado con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar producto: " + e.getMessage());
        }
    }

    // Método para registrar una entrada de producto en el inventario
    private static void registrarEntradaProducto(Scanner scanner, Connection connection) {
        listarProductos(connection); // Mostrar la lista de productos disponible
        System.out.print("ID del Producto: ");
        int id = scanner.nextInt();
        System.out.print("Cantidad a ingresar: ");
        int cantidad = scanner.nextInt();

        Producto producto = Producto.obtenerProductoPorId(connection, id); // Obtener el producto desde la base de datos

        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        producto.agregarStock(connection, cantidad); // Agregar stock del producto en la base de datos
        System.out.println("Entrada de producto registrada con éxito.");
    }

    // Método para registrar una salida de producto del inventario
    private static void registrarSalidaProducto(Scanner scanner, Connection connection) {
        listarProductos(connection); // Mostrar la lista de productos disponible
        System.out.print("ID del Producto: ");
        int id = scanner.nextInt();
        System.out.print("Cantidad a salir: ");
        int cantidad = scanner.nextInt();

        Producto producto = Producto.obtenerProductoPorId(connection, id); // Obtener el producto desde la base de datos

        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        // Verificar si hay suficiente stock para la salida solicitada
        if (cantidad <= producto.obtenerStock(connection, id) && producto.obtenerStock(connection, id) != 0) {
            producto.reducirStock(connection, cantidad); // Reducir stock del producto en la base de datos
            System.out.println("Salida de producto registrada con éxito.");
        } else {
            System.out.println("No hay stock suficiente para retirar");
        }
    }

    // Método para listar todos los productos en la base de datos
    public static void listarProductos(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_PRODUCTOS);
            ResultSet resultSet = statement.executeQuery(); // Ejecutar la consulta
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

    // Método para eliminar un producto de la base de datos
    private static void eliminarProducto(Scanner scanner, Connection connection) {
        listarProductos(connection); // Mostrar la lista de productos disponible
        System.out.print("ID del Producto a eliminar: ");
        int id = scanner.nextInt();

        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCTO);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate(); // Ejecutar la eliminación
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
