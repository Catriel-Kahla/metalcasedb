import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GestionProveedores {
    // Consultas SQL
    private static final String INSERT_PROVEEDOR = "INSERT INTO proveedor (nombreProveedor, telefono, nombreProducto, productoStock, precioProducto) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_PROVEEDORES = "SELECT p.id, p.nombreProveedor, p.telefono, pr.nombreProducto AS nombreProducto, p.productoStock, p.precioProducto FROM proveedor p JOIN producto pr ON p.nombreProducto = pr.id";
    private static final String DELETE_PROVEEDOR = "DELETE FROM proveedor WHERE id = ?";
    private static final String UPDATE_PROVEEDOR = "UPDATE proveedor SET nombreProveedor = ?, telefono = ?, nombreProducto = ?, productoStock = ?, precioProducto = ? WHERE id = ?";

    // Método para mostrar el menú de gestión de proveedores
    public static void menuGestionProveedores(Scanner scanner, Connection connection) {
        int option;
        do {
            System.out.println("\n=== Gestión de Proveedores ===");
            System.out.println("1. Registrar Proveedor");
            System.out.println("2. Listar Proveedores");
            System.out.println("3. Eliminar Proveedor");
            System.out.println("4. Modificar Proveedor");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Selecciona una opción: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    registrarProveedor(scanner, connection);
                    break;
                case 2:
                    listarProveedores(connection);
                    break;
                case 3:
                    eliminarProveedor(scanner, connection);
                    break;
                case 4:
                    modificarProveedor(scanner, connection);
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        } while (option != 5); // Repetir el menú mientras la opción no sea 5 (Volver al Menú Principal)
    }

    // Método para registrar un nuevo proveedor en la base de datos
    private static void registrarProveedor(Scanner scanner, Connection connection) {
        scanner.nextLine(); // Consumir la nueva línea
        System.out.print("Nombre del Proveedor: ");
        String nombre = scanner.nextLine();
        System.out.print("Teléfono del Proveedor: ");
        String telefono = scanner.nextLine();
        GestionProductos.listarProductos(connection);
        System.out.print("ID del Producto: ");
        int nombreProducto = scanner.nextInt();
        System.out.print("Stock del Producto: ");
        int stock = scanner.nextInt();
        System.out.print("Precio del Producto: ");
        int precio = scanner.nextInt();

        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PROVEEDOR);
            statement.setString(1, nombre);
            statement.setString(2, telefono);
            statement.setInt(3, nombreProducto);
            statement.setInt(4, stock);
            statement.setInt(5, precio);
            int rowsInserted = statement.executeUpdate(); // Ejecutar la inserción
            if (rowsInserted > 0) {
                System.out.println("Proveedor registrado con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar proveedor: " + e.getMessage());
        }
    }

    // Método para listar todos los proveedores y sus productos asociados
    private static void listarProveedores(Connection connection) {
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_PROVEEDORES);
            ResultSet resultSet = statement.executeQuery(); // Ejecutar la consulta
            System.out.println("\n=== Lista de Proveedores ===");
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id") +
                                   ", Nombre: " + resultSet.getString("nombreProveedor") +
                                   ", Teléfono: " + resultSet.getString("telefono") +
                                   ", Producto: " + resultSet.getString("nombreProducto") +
                                   ", Stock: " + resultSet.getInt("productoStock") +
                                   ", Precio: " + resultSet.getInt("precioProducto"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de proveedores: " + e.getMessage());
        }
    }

    // Método para eliminar un proveedor de la base de datos
    private static void eliminarProveedor(Scanner scanner, Connection connection) {
        System.out.print("ID del Proveedor a eliminar: ");
        int id = scanner.nextInt();

        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_PROVEEDOR);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate(); // Ejecutar la eliminación
            if (rowsDeleted > 0) {
                System.out.println("Proveedor eliminado con éxito.");
            } else {
                System.out.println("No se encontró el proveedor con ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar proveedor: " + e.getMessage());
        }
    }

    // Método para modificar los datos de un proveedor en la base de datos
    private static void modificarProveedor(Scanner scanner, Connection connection) {
        System.out.print("ID del Proveedor a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir la nueva línea
        System.out.print("Nuevo Nombre del Proveedor: ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevo Teléfono del Proveedor: ");
        String telefono = scanner.nextLine();
        GestionProductos.listarProductos(connection);
        System.out.print("Nuevo ID del Producto: ");
        int nombreProducto = scanner.nextInt();
        System.out.print("Nuevo Stock del Producto: ");
        int stock = scanner.nextInt();
        System.out.print("Nuevo Precio del Producto: ");
        int precio = scanner.nextInt();

        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_PROVEEDOR);
            statement.setString(1, nombre);
            statement.setString(2, telefono);
            statement.setInt(3, nombreProducto);
            statement.setInt(4, stock);
            statement.setInt(5, precio);
            statement.setInt(6, id);
            int rowsUpdated = statement.executeUpdate(); // Ejecutar la actualización
            if (rowsUpdated > 0) {
                System.out.println("Proveedor modificado con éxito.");
            } else {
                System.out.println("No se encontró el proveedor con ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar proveedor: " + e.getMessage());
        }
    }
}
