import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;

    // Constructor para inicializar un objeto Producto
    public Producto(int id, String nombre, String descripcion, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    // Método para insertar un nuevo producto en la base de datos
    public void insertarProducto(Connection connection) {
        String insertQuery = "INSERT INTO producto (nombreProducto, descripcion, precio, stock) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setDouble(3, precio);
            statement.setInt(4, stock);
            statement.executeUpdate(); // Ejecutar la inserción
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
        }
    }

    // Método estático para obtener un producto por su ID desde la base de datos
    public static Producto obtenerProductoPorId(Connection connection, int id) {
        String selectQuery = "SELECT * FROM producto WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Obtener los datos del producto desde el ResultSet
                String nombre = resultSet.getString("nombreProducto");
                String descripcion = resultSet.getString("descripcion");
                double precio = resultSet.getDouble("precio");
                int stock = resultSet.getInt("stock");
                return new Producto(id, nombre, descripcion, precio, stock);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener producto: " + e.getMessage());
        }
        return null;
    }

    // Método estático para obtener todos los productos desde la base de datos
    public static List<Producto> obtenerProductos(Connection connection) {
        List<Producto> productos = new ArrayList<>();
        String selectQuery = "SELECT * FROM producto";
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                // Obtener los datos de cada producto desde el ResultSet y agregarlo a la lista
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombreProducto");
                String descripcion = resultSet.getString("descripcion");
                double precio = resultSet.getDouble("precio");
                int stock = resultSet.getInt("stock");
                productos.add(new Producto(id, nombre, descripcion, precio, stock));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }

    // Método para obtener el stock de un producto por su ID desde la base de datos
    public int obtenerStock(Connection connection, int id) {
        String selectQuery = "SELECT stock FROM producto WHERE id = ?";
        int stock = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                stock = resultSet.getInt("stock");
            } else {
                System.out.println("Producto no encontrado");
                return -1; // Retorna un valor por defecto si no se encuentra el producto
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el stock del producto: " + e.getMessage());
            return -1; // Retorna un valor por defecto en caso de error
        }
        return stock;
    }

    // Método para agregar stock a un producto en la base de datos
    public void agregarStock(Connection connection, int cantidad) {
        String updateQuery = "UPDATE producto SET stock = stock + ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, cantidad);
            statement.setInt(2, id);
            statement.executeUpdate(); // Ejecutar la actualización
        } catch (SQLException e) {
            System.out.println("Error al agregar stock: " + e.getMessage());
        }
    }

    // Método para reducir stock de un producto en la base de datos
    public void reducirStock(Connection connection, int cantidad) {
        String updateQuery = "UPDATE producto SET stock = stock - ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, cantidad);
            statement.setInt(2, id);
            statement.executeUpdate(); // Ejecutar la actualización
        } catch (SQLException e) {
            System.out.println("Error al reducir stock: " + e.getMessage());
        }
    }

    // Método estático para eliminar un producto de la base de datos por su ID
    public static void eliminarProducto(Connection connection, int id) {
        String deleteQuery = "DELETE FROM producto WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, id);
            statement.executeUpdate(); // Ejecutar la eliminación
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }

    // Método toString para representar el objeto Producto como una cadena
    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio + ", stock=" + stock + "]";
    }
}
