# 🍽 Dark Kitchen - Sistema de Gestión

## 🎯 Descripción del Proyecto
Sistema de gestión para un restaurante tipo “Dark Kitchen”, que permite administrar clientes, pedidos, productos, ingredientes, compras y proveedores.  
Implementa operaciones **CRUD**, procedimientos almacenados, triggers y consultas avanzadas con MySQL, además de conexión desde Java usando JDBC.

---

## 📚 Estructura de la Base de Datos

### Tablas principales
- **Clientes**: Información de los clientes (nombre, email, dirección).  
- **Empleados**: Datos de los empleados (nombre, rol).  
- **Productos**: Productos ofrecidos (nombre, precio, categoría).  
- **Ingredientes**: Ingredientes y stock.  
- **Proveedores**: Datos de proveedores.  
- **Compras** y **Detalle_Compra**: Control de compras de ingredientes.  
- **Pedidos** y **Detalle_Pedido**: Control de pedidos de clientes.  
- **Producto_Ingrediente**: Relación entre productos e ingredientes.  
- **SeguimientoClientes**: Registro de compras para seguimiento.

---

## ⚡ Funcionalidades

### 1. Operaciones CRUD desde Java (JDBC)
- **Crear**: Insertar nuevos clientes, productos, pedidos.  
- **Leer**: Consultar clientes, pedidos y productos.  
- **Actualizar**: Modificar información de clientes, pedidos o inventario.  
- **Eliminar**: Eliminar registros de clientes, pedidos o productos.

### 2. Procedimientos almacenados
- `VentasDiarias(fecha)` → Devuelve un reporte con todas las ventas de la fecha y total acumulado.  
- `ClientesPrimerTrimestre()` → Lista los clientes que realizaron pedidos del 1 de enero al 31 de marzo.  
- `AgregarCliente(...)` → Inserta un cliente y maneja excepción si el correo ya existe.

### 3. Triggers
- `ControlInventario` → Previene duplicados al insertar un ingrediente.  
- `SeguimientoClientes` → Registra automáticamente en `SeguimientoClientes` cada vez que un cliente hace un pedido.

### 4. Consultas avanzadas
- **JOIN** → Unir tablas, p.ej., pedidos con clientes.  
- **UNION** → Combinar listas de emails de clientes y proveedores.  
- **ORDER BY** → Ordenar por fecha, hora o total de pedidos.  
- **GROUP BY** → Agrupar ventas por cliente y calcular totales.  
- **Manipulación de fechas** → Filtrar pedidos por mes o rango de fechas.

---

## 🛠 Uso del Script SQL

1. Abrir MySQL Workbench o la consola MySQL.  
2. Copiar y ejecutar el **script completo** `dark_kitchen.sql`.  
3. Para ejecutar línea por línea:
   - **CLI:** asegúrate de terminar cada sentencia con `;`.  
   - **Workbench:** selecciona la línea y presiona **Ctrl + Enter**.  
4. Para ejecutar todo de una vez: presiona **F5** (Workbench) o ejecuta todo el script en la CLI.

---

## 💻 Conexión desde Java (JDBC)

1. Configura tu proyecto en Java con el **conector MySQL JDBC**.  
2. Ejemplo de conexión:

```java
String url = "jdbc:mysql://localhost:3306/dark_kitchen";
String user = "root";
String pass = "tu_password";
Connection conn = DriverManager.getConnection(url, user, pass);
```

3. Operaciones CRUD con `PreparedStatement`:
   - `INSERT INTO Clientes(...) VALUES(...)`
   - `SELECT * FROM Clientes`
   - `UPDATE Clientes SET ... WHERE id_cliente=?`
   - `DELETE FROM Clientes WHERE id_cliente=?`

---

## 📊 Pruebas

- Insertar nuevos clientes y validar restricción de correo único.  
- Generar reporte de ventas diarias y clientes del primer trimestre.  
- Insertar pedidos y verificar que el trigger de seguimiento registre correctamente.  
- Consultas avanzadas para unir, combinar, agrupar y ordenar datos.

---

## 📝 Documentación Adicional
- Diagrama entidad-relación (ER) de la base de datos.  
- Relación de tablas y llaves primarias/foráneas.  
- Explicación de triggers y procedimientos almacenados.  
- Ejemplos de consultas avanzadas.  
- Ejemplo completo de CRUD desde Java.

