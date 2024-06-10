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

    public Producto(String nombre, String descripcion, double precio, int stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public Producto(int id, String nombre, String descripcion, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    public void insertarProducto(Connection connection) {
        String insertQuery = "INSERT INTO producto (nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.setString(1, nombre);
            statement.setString(2, descripcion);
            statement.setDouble(3, precio);
            statement.setInt(4, stock);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar producto: " + e.getMessage());
        }
    }

    public static Producto obtenerProductoPorId(Connection connection, int id) {
        String selectQuery = "SELECT * FROM producto WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
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

    public static List<Producto> obtenerProductos(Connection connection) {
        List<Producto> productos = new ArrayList<>();
        String selectQuery = "SELECT * FROM producto";
        try {
            PreparedStatement statement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
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

    public void agregarStock(Connection connection, int cantidad) {
        String updateQuery = "UPDATE producto SET stock = stock + ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, cantidad);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar stock: " + e.getMessage());
        }
    }

    public void reducirStock(Connection connection, int cantidad) {
        String updateQuery = "UPDATE producto SET stock = stock - ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setInt(1, cantidad);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al reducir stock: " + e.getMessage());
        }
    }

    public static void eliminarProducto(Connection connection, int id) {
        String deleteQuery = "DELETE FROM producto WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio + ", stock=" + stock + "]";
    }
}
