import java.sql.*;
import java.util.Scanner;

/**
 * Sistema de gestión para Dark Kitchen - Aplicación principal
 *
 * <p>Esta aplicación proporciona una interfaz de consola para gestionar
 * clientes, productos, pedidos e inventario de un dark kitchen.</p>
 *
 * @author Tu Nombre
 * @version 1.0
 * @since 2024
 */
public class DarkKitchenApp {
    /** URL de conexión a la base de datos MySQL */
    private static final String URL = "jdbc:mysql://localhost:3306/dark_kitchen?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    /** Usuario de la base de datos */
    private static final String USER = "root";

    /** Contraseña de la base de datos */
    private static final String PASSWORD = "Valencia123";

    /** Conexión a la base de datos */
    private static Connection connection;

    /** Scanner para entrada de usuario */
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Método principal que inicia la aplicación
     *
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        try {
            System.out.println("🚀 Iniciando Dark Kitchen App...");
            System.out.println("📦 Versión MySQL Connector: 9.4.0");
            System.out.println("💾 Base de datos: dark_kitchen");
            System.out.println("=" .repeat(50));

            conectarBD();
            System.out.println("✅ Conexión a MySQL exitosa!");

            menuPrincipal();

        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            System.out.println("🔧 Solución: Verifica que:");
            System.out.println("   - MySQL esté ejecutándose");
            System.out.println("   - Usuario y password sean correctos");
            System.out.println("   - La base de datos 'dark_kitchen' exista");
        } finally {
            cerrarConexion();
        }
    }

    /**
     * Establece conexión con la base de datos MySQL
     *
     * @throws SQLException Si ocurre un error al conectar
     */
    private static void conectarBD() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Cierra la conexión con la base de datos de forma segura
     */
    private static void cerrarConexion() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔌 Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }

    /**
     * Muestra el menú principal y gestiona las opciones del usuario
     *
     * @throws SQLException Si ocurre un error en la base de datos
     */
    private static void menuPrincipal() throws SQLException {
        int opcion;

        do {
            System.out.println("\n🍕 DARK KITCHEN - SISTEMA DE GESTIÓN 🍕");
            System.out.println("=" .repeat(45));
            System.out.println("1. CREAR - Agregar nuevo cliente");
            System.out.println("2. LEER - Consultar todos los clientes");
            System.out.println("3. LEER - Pedidos por fecha");
            System.out.println("4. LEER - Reporte de ventas diarias");
            System.out.println("5. ACTUALIZAR - Modificar precio producto");
            System.out.println("6. ELIMINAR - Desactivar producto");
            System.out.println("7. LEER - Clientes del primer trimestre");
            System.out.println("8. LEER - Consultar inventario");
            System.out.println("9. ACTUALIZAR - Actualizar stock ingrediente");
            System.out.println("10. LEER - Ver todos los productos");
            System.out.println("11. ACTUALIZAR - Reactivar producto");
            System.out.println("0. Salir del sistema");
            System.out.println("=" .repeat(45));
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> crearCliente();
                case 2 -> leerClientes();
                case 3 -> leerPedidosPorFecha();
                case 4 -> reporteVentasDiarias();
                case 5 -> actualizarProducto();
                case 6 -> eliminarProducto();
                case 7 -> clientesPrimerTrimestre();
                case 8 -> consultarInventario();
                case 9 -> actualizarStockIngrediente();
                case 10 -> verTodosLosProductos();
                case 11 -> reactivarProducto();
                case 0 -> System.out.println("👋 ¡Gracias por usar Dark Kitchen!");
                default -> System.out.println("❌ Opción inválida, intente nuevamente");
            }

        } while (opcion != 0);
    }

    /**
     * Crea un nuevo cliente en el sistema
     */
    private static void crearCliente() {
        try {
            System.out.println("\n➕ AGREGAR NUEVO CLIENTE");
            System.out.println("-" .repeat(30));
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Apellido: ");
            String apellido = scanner.nextLine();
            System.out.print("Teléfono: ");
            String telefono = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Dirección: ");
            String direccion = scanner.nextLine();

            String sql = "CALL AgregarCliente(?, ?, ?, ?, ?)";
            CallableStatement stmt = connection.prepareCall(sql);

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, telefono);
            stmt.setString(4, email);
            stmt.setString(5, direccion);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("✅ " + rs.getString("mensaje_exito"));
            }

            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Error al crear cliente: " + e.getMessage());
        }
    }

    /**
     * Muestra todos los clientes registrados en el sistema
     */
    private static void leerClientes() {
        try {
            System.out.println("\n👥 LISTA DE CLIENTES REGISTRADOS");
            System.out.println("-" .repeat(70));

            String sql = "SELECT id_cliente, nombre, apellido, email, telefono FROM Clientes ORDER BY id_cliente";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.printf("%-5s %-20s %-30s %-15s%n", "ID", "Nombre", "Email", "Teléfono");
            System.out.println("-".repeat(70));

            int contador = 0;
            while (rs.next()) {
                contador++;
                int id = rs.getInt("id_cliente");
                String nombre = rs.getString("nombre") + " " + rs.getString("apellido");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");

                System.out.printf("%-5d %-20s %-30s %-15s%n", id, nombre, email, telefono);
            }

            System.out.println("-".repeat(70));
            System.out.println("📊 Total de clientes: " + contador);

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Error al leer clientes: " + e.getMessage());
        }
    }

    /**
     * Consulta pedidos por una fecha específica
     */
    private static void leerPedidosPorFecha() {
        try {
            System.out.println("\n📋 CONSULTAR PEDIDOS POR FECHA");
            System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
            String fecha = scanner.nextLine();

            String sql = "SELECT p.id_pedido, c.nombre, c.apellido, p.total, p.estado, p.hora_pedido " +
                    "FROM Pedidos p JOIN Clientes c ON p.id_cliente = c.id_cliente " +
                    "WHERE p.fecha_pedido = ? ORDER BY p.hora_pedido DESC";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, fecha);
            ResultSet rs = stmt.executeQuery();

            System.out.printf("%-8s %-20s %-10s %-15s %-12s%n", "Pedido", "Cliente", "Total", "Estado", "Hora");
            System.out.println("-".repeat(75));

            int contador = 0;
            while (rs.next()) {
                contador++;
                int idPedido = rs.getInt("id_pedido");
                String cliente = rs.getString("nombre") + " " + rs.getString("apellido");
                double total = rs.getDouble("total");
                String estado = rs.getString("estado");
                String hora = rs.getString("hora_pedido");

                System.out.printf("%-8d %-20s $%-9.2f %-15s %-12s%n", idPedido, cliente, total, estado, hora);
            }

            System.out.println("-".repeat(75));
            if (contador > 0) {
                System.out.println("📊 Total de pedidos: " + contador);
            } else {
                System.out.println("❌ No hay pedidos para la fecha: " + fecha);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Error al consultar pedidos: " + e.getMessage());
        }
    }

    /**
     * Genera reporte de ventas para una fecha específica
     */
    private static void reporteVentasDiarias() {
        try {
            System.out.println("\n💰 REPORTE DE VENTAS DIARIAS");
            System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
            String fecha = scanner.nextLine();

            String sql = "CALL VentasDiarias(?)";
            CallableStatement stmt = connection.prepareCall(sql);
            stmt.setString(1, fecha);

            boolean tieneResultados = stmt.execute();

            if (tieneResultados) {
                System.out.println("\n📊 DETALLE DE VENTAS:");
                ResultSet rs = stmt.getResultSet();

                System.out.printf("%-8s %-20s %-12s %-10s %-15s%n", "Pedido", "Cliente", "Fecha", "Total", "Estado");
                System.out.println("-".repeat(75));

                int contador = 0;
                while (rs.next()) {
                    contador++;
                    int idPedido = rs.getInt("id_pedido");
                    String cliente = rs.getString("cliente");
                    String fechaPedido = rs.getString("fecha_pedido");
                    double total = rs.getDouble("total");
                    String estado = rs.getString("estado");

                    System.out.printf("%-8d %-20s %-12s $%-9.2f %-15s%n", idPedido, cliente, fechaPedido, total, estado);
                }
                rs.close();

                if (stmt.getMoreResults()) {
                    rs = stmt.getResultSet();
                    if (rs.next()) {
                        int totalPedidos = rs.getInt("total_pedidos");
                        double totalVentas = rs.getDouble("total_ventas");
                        double promedio = rs.getDouble("promedio_venta");

                        System.out.println("\n💰 TOTALES DEL DÍA:");
                        System.out.println("📦 Pedidos realizados: " + totalPedidos);
                        System.out.printf("💵 Ventas totales: $%.2f%n", totalVentas);
                        System.out.printf("📊 Promedio por pedido: $%.2f%n", promedio);
                    }
                    rs.close();
                }
            } else {
                System.out.println("❌ No hay ventas para la fecha: " + fecha);
            }

            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Error en reporte de ventas: " + e.getMessage());
        }
    }

    /**
     * Consulta clientes activos en el primer trimestre de un año
     */
    private static void clientesPrimerTrimestre() {
        try {
            System.out.println("\n📅 CLIENTES DEL PRIMER TRIMESTRE");
            System.out.print("Ingrese el año (ej: 2025): ");
            int año = scanner.nextInt();
            scanner.nextLine();

            String sql = "CALL ClientesPrimerTrimestre(?)";
            CallableStatement stmt = connection.prepareCall(sql);
            stmt.setInt(1, año);

            ResultSet rs = stmt.executeQuery();

            System.out.printf("%-5s %-20s %-30s %-15s %-12s %-10s%n",
                    "ID", "Cliente", "Email", "Teléfono", "Pedidos", "Monto Total");
            System.out.println("-".repeat(95));

            int contador = 0;
            while (rs.next()) {
                contador++;
                int id = rs.getInt("id_cliente");
                String cliente = rs.getString("cliente");
                String email = rs.getString("email");
                String telefono = rs.getString("telefono");
                int pedidos = rs.getInt("total_pedidos");
                double monto = rs.getDouble("monto_total");

                System.out.printf("%-5d %-20s %-30s %-15s %-12d $%-9.2f%n",
                        id, cliente, email, telefono, pedidos, monto);
            }

            System.out.println("-".repeat(95));
            if (contador > 0) {
                System.out.println("📊 Total de clientes activos: " + contador);
            } else {
                System.out.println("❌ No hay clientes para el trimestre del año " + año);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Error al consultar clientes: " + e.getMessage());
        }
    }

    /**
     * Consulta el estado actual del inventario
     */
    private static void consultarInventario() {
        try {
            System.out.println("\n📦 INVENTARIO DE INGREDIENTES");
            System.out.println("-".repeat(70));

            String sql = "SELECT id_ingrediente, nombre_ingrediente, stock, unidad_medida, stock_minimo " +
                    "FROM Ingredientes ORDER BY nombre_ingrediente";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.printf("%-5s %-25s %-10s %-8s %-12s%n",
                    "ID", "Ingrediente", "Stock", "Unidad", "Stock Mínimo");
            System.out.println("-".repeat(70));

            int contador = 0;
            int alertas = 0;
            while (rs.next()) {
                contador++;
                int id = rs.getInt("id_ingrediente");
                String nombre = rs.getString("nombre_ingrediente");
                double stock = rs.getDouble("stock");
                String unidad = rs.getString("unidad_medida");
                double minimo = rs.getDouble("stock_minimo");

                String alerta = stock < minimo ? "⚠️ " : "";
                if (stock < minimo) alertas++;

                System.out.printf("%-5d %-25s %-10.2f %-8s %-12.2f %s%n",
                        id, nombre, stock, unidad, minimo, alerta);
            }

            System.out.println("-".repeat(70));
            System.out.println("📊 Total de ingredientes: " + contador);
            if (alertas > 0) {
                System.out.println("🚨 Ingredientes con stock bajo: " + alertas);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Error al consultar inventario: " + e.getMessage());
        }
    }

    /**
     * Actualiza el precio de un producto
     */
    private static void actualizarProducto() {
        try {
            System.out.println("\n✏️ ACTUALIZAR PRECIO DE PRODUCTO");
            System.out.print("ID del producto a actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Nuevo precio: $");
            double nuevoPrecio = scanner.nextDouble();
            scanner.nextLine();

            String sql = "UPDATE Productos SET precio = ? WHERE id_producto = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setDouble(1, nuevoPrecio);
            stmt.setInt(2, id);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("✅ Producto actualizado exitosamente");
            } else {
                System.out.println("❌ No se encontró el producto con ID: " + id);
            }

            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar producto: " + e.getMessage());
        }
    }

    /**
     * Actualiza el stock de un ingrediente
     */
    private static void actualizarStockIngrediente() {
        try {
            System.out.println("\n📊 ACTUALIZAR STOCK DE INGREDIENTE");
            System.out.print("ID del ingrediente: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Cantidad a agregar (usar negativo para restar): ");
            double cantidad = scanner.nextDouble();
            scanner.nextLine();

            String sql = "CALL ActualizarInventario(?, ?)";
            CallableStatement stmt = connection.prepareCall(sql);
            stmt.setInt(1, id);
            stmt.setDouble(2, cantidad);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("✅ " + rs.getString("mensaje_exito"));
            }

            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar inventario: " + e.getMessage());
        }
    }

    /**
     * Desactiva un producto (eliminación lógica)
     */
    private static void eliminarProducto() {
        try {
            System.out.println("\n🗑️ ELIMINAR (DESACTIVAR) PRODUCTO");
            System.out.print("ID del producto a eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("¿Está seguro de eliminar el producto? (s/n): ");
            String confirmacion = scanner.nextLine();

            if (!confirmacion.equalsIgnoreCase("s")) {
                System.out.println("❌ Eliminación cancelada");
                return;
            }

            String sql = "UPDATE Productos SET activo = FALSE WHERE id_producto = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("✅ Producto eliminado (desactivado) exitosamente");
            } else {
                System.out.println("❌ No se encontró el producto con ID: " + id);
            }

            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar producto: " + e.getMessage());
        }
    }

    /**
     * Muestra todos los productos incluyendo estado activo/inactivo
     */
    private static void verTodosLosProductos() {
        try {
            System.out.println("\n📦 LISTA COMPLETA DE PRODUCTOS");
            System.out.println("-".repeat(80));

            String sql = "SELECT id_producto, nombre_producto, precio, categoria, activo " +
                    "FROM Productos ORDER BY activo DESC, id_producto";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.printf("%-5s %-25s %-10s %-15s %-10s%n",
                    "ID", "Producto", "Precio", "Categoría", "Estado");
            System.out.println("-".repeat(80));

            int activos = 0, inactivos = 0;
            while (rs.next()) {
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre_producto");
                double precio = rs.getDouble("precio");
                String categoria = rs.getString("categoria");
                boolean activo = rs.getBoolean("activo");

                String estado = activo ? "✅ ACTIVO" : "❌ INACTIVO";
                if (activo) activos++; else inactivos++;

                System.out.printf("%-5d %-25s $%-9.2f %-15s %-10s%n",
                        id, nombre, precio, categoria, estado);
            }

            System.out.println("-".repeat(80));
            System.out.println("📊 Resumen: " + activos + " activos, " + inactivos + " inactivos");

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Error al consultar productos: " + e.getMessage());
        }
    }

    /**
     * Reactiva un producto previamente desactivado
     */
    private static void reactivarProducto() {
        try {
            System.out.println("\n🔄 REACTIVAR PRODUCTO");
            System.out.print("ID del producto a reactivar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            String sql = "UPDATE Productos SET activo = TRUE WHERE id_producto = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("✅ Producto reactivado exitosamente");
            } else {
                System.out.println("❌ No se encontró el producto con ID: " + id);
            }

            stmt.close();

        } catch (SQLException e) {
            System.err.println("❌ Error al reactivar producto: " + e.getMessage());
        }
    }
}