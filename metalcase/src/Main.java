import java.util.Scanner;
import java.sql.Connection;

// Definición de la clase principal Main
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Creación de un objeto Scanner para leer la entrada del usuario
        Connection connection = App.connectDatabase(); // Llamada al método para conectar a la base de datos

        // Verificación de que la conexión se estableció correctamente
        if (connection != null) {
            System.out.println("Conexión exitosa a la base de datos.");
            int option;
            do {
                // Presentación del menú principal al usuario
                System.out.println("\n=== Menú Principal ===");
                System.out.println("1. Gestión de Clientes");
                System.out.println("2. Gestión de Productos");
                System.out.println("3. Gestión de Proveedores");
                System.out.println("4. Gestión de Producción");
                System.out.println("5. Salir");
                System.out.print("Selecciona una opción: ");
                option = scanner.nextInt(); // Lectura de la opción seleccionada por el usuario

                // Manejo de la opción seleccionada usando un switch
                switch (option) {
                    case 1:
                        GestionClientes.menuGestionClientes(scanner, connection); // Llamada al menú de gestión de clientes
                        break;
                    case 2:
                        GestionProductos.menuGestionProductos(scanner, connection); // Llamada al menú de gestión de productos
                        break;
                    case 3:
                        GestionProveedores.menuGestionProveedores(scanner, connection); // Llamada al menú de gestión de proveedores
                        break;
                    case 4:
                        GestionProduccion.menuGestionProduccion(scanner, connection); // Llamada al menú de gestión de producción
                        break;
                    case 5:
                        System.out.println("Saliendo del programa..."); // Mensaje de salida
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo."); // Manejo de opción inválida
                }
            } while (option != 5); // Repetir el menú mientras la opción no sea 5 (Salir)

            App.disconnectDatabase(connection); // Desconectar la base de datos al salir del programa
        } else {
            System.out.println("No se pudo establecer conexión a la base de datos."); // Mensaje de error si la conexión falla
        }

        scanner.close(); // Cierre del objeto Scanner
    }
}
