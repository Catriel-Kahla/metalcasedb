import java.util.Scanner;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = App.connectDatabase();

        if (connection != null) {
            System.out.println("Conexión exitosa a la base de datos.");
            int option;
            do {
                System.out.println("\n=== Menú Principal ===");
                System.out.println("1. Gestión de Clientes");
                System.out.println("2. Gestión de Productos");
                System.out.println("3. Gestión de Proveedores");
                System.out.println("4. Gestión de Producción");
                System.out.println("5. Salir");
                System.out.print("Selecciona una opción: ");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        GestionClientes.menuGestionClientes(scanner, connection);
                        break;
                    case 2:
                        GestionProductos.menuGestionProductos(scanner, connection);
                        break;
                    case 3:
                        GestionProveedores.menuGestionProveedores(scanner, connection);
                        break;
                    case 4:
                        GestionProduccion.menuGestionProduccion(scanner, connection);
                        break;
                    case 5:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            } while (option != 5);

            App.disconnectDatabase(connection); // Desconectar la base de datos al salir del programa
        } else {
            System.out.println("No se pudo establecer conexión a la base de datos.");
        }

        scanner.close();
    }
}
 