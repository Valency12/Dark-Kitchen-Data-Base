# 🍴 Proyecto Dark Kitchen

## 📌 Descripción
Este proyecto implementa un sistema de gestión para una **Dark Kitchen** utilizando **MySQL** y **Java**.  
El objetivo es cubrir operaciones de administración de **clientes, empleados, productos, inventario, compras, pedidos y auditoría de procesos**.

## 🔎 Análisis de la Industria
La industria de la **Dark Kitchen** se dedica a la venta de alimentos preparados exclusivamente para entrega a domicilio, sin contar con un espacio físico para atención directa a clientes. En este modelo de negocio, la información básica que se maneja abarca el menú (platos, precios e ingredientes), los pedidos (con detalles como fecha, cliente, repartidor y estado), los clientes (con sus datos de contacto y direcciones), los proveedores (con información de insumos y precios), el inventario (control de existencias e insumos) y los empleados (cocineros, personal de empaque y repartidores).

Actualmente, gran parte de esta información se gestiona en hojas de Excel, distribuidas en pestañas como **pedidos diarios**, **lista de ingredientes**, **control de proveedores**, **lista de clientes** y **turnos de empleados**. El flujo de comunicación y distribución de datos se realiza mediante correos electrónicos enviados a la gerencia, impresión de pedidos para el área de cocina y mensajes a través de WhatsApp para los repartidores.

Este proyecto busca reemplazar ese flujo manual por un **modelo de datos estructurado** y una aplicación que centralice la información, mejore la trazabilidad, facilite reportes y permita automatizar alertas de inventario y seguimiento de clientes.

---

Incluye:
- Definición de tablas con relaciones y llaves foráneas  
- Inserción de datos iniciales para pruebas  
- Procedimientos almacenados para reportes, inserción y control de inventario  
- Triggers para auditoría y seguimiento automático de clientes  
- Consultas avanzadas con `JOIN`, `UNION`, `GROUP BY`, `ORDER BY` y `HAVING`  
- Aplicación Java con menú interactivo para gestión completa  

---

## 📋 Tablas y Campos Sugeridos

A continuación se listan las entidades principales y sus campos sugeridos.

### 1. Clientes
```
id_cliente (PK, INT)
nombre (VARCHAR)
apellido (VARCHAR)
telefono (VARCHAR)
email (VARCHAR)
direccion (VARCHAR)
```

### 2. Pedidos
```
id_pedido (PK, INT)
id_cliente (FK, INT)
fecha_pedido (DATE)
hora_pedido (TIME)
estado (VARCHAR: "pendiente", "en preparación", "enviado", "entregado")
total (DECIMAL)
```

### 3. Detalle_Pedido (para manejar la relación muchos a muchos entre pedidos y productos)
```
id_detalle (PK, INT)
id_pedido (FK, INT)
id_producto (FK, INT)
cantidad (INT)
subtotal (DECIMAL)
```

### 4. Productos
```
id_producto (PK, INT)
nombre_producto (VARCHAR)
descripcion (TEXT)
precio (DECIMAL)
categoria (VARCHAR: "bebida", "entrada", "plato fuerte", "postre")
```

### 5. Ingredientes
```
id_ingrediente (PK, INT)
nombre_ingrediente (VARCHAR)
unidad_medida (VARCHAR: "kg", "litros", "unidad")
stock (DECIMAL)
```

### 6. Producto_Ingrediente (relación muchos a muchos: qué ingredientes lleva cada producto)
```
id_producto (FK, INT)
id_ingrediente (FK, INT)
cantidad_usada (DECIMAL)
```

### 7. Proveedores
```
id_proveedor (PK, INT)
nombre_proveedor (VARCHAR)
telefono (VARCHAR)
email (VARCHAR)
direccion (VARCHAR)
```

### 8. Compras
```
id_compra (PK, INT)
id_proveedor (FK, INT)
fecha_compra (DATE)
total_compra (DECIMAL)
```

### 9. Detalle_Compra (muchos a muchos entre compras e ingredientes)
```
id_detalle_compra (PK, INT)
id_compra (FK, INT)
id_ingrediente (FK, INT)
cantidad_comprada (DECIMAL)
precio_unitario (DECIMAL)
```

### 10. Empleados
```
id_empleado (PK, INT)
nombre (VARCHAR)
apellido (VARCHAR)
telefono (VARCHAR)
rol (VARCHAR: "cocinero", "repartidor", "administrador")
```

---

## 🏗️ Descripción del Modelo
La Dark Kitchen organiza la información principal que maneja el negocio: clientes, pedidos, productos, ingredientes, proveedores, compras y empleados.

- **Clientes:** permiten identificar a las personas que realizan pedidos en la plataforma.
- **Pedidos:** registran las órdenes realizadas por los clientes y se relacionan con los productos solicitados.
- **Detalle_Pedido:** sirve para controlar qué productos y en qué cantidad están incluidos en cada pedido.
- **Productos:** representan los platillos o artículos ofrecidos en el menú.
- **Ingredientes:** almacenan los insumos necesarios para preparar cada producto.
- **Producto_Ingrediente:** permite establecer la relación entre los productos y los ingredientes que los componen.
- **Proveedores:** gestionan a las empresas o personas que suministran ingredientes.
- **Compras:** registran las adquisiciones de insumos realizadas a los proveedores.
- **Detalle_Compra:** especifica los ingredientes comprados en cada operación de compra.
- **Empleados:** representan al personal encargado de la preparación y entrega de los pedidos.

En conjunto, este modelo asegura que se pueda llevar un control completo de las operaciones de la Dark Kitchen, desde la adquisición de ingredientes hasta la entrega final de los pedidos a los clientes.

![Dark_kitchen_proyect](https://github.com/user-attachments/assets/bf1f64a2-15d7-4a36-95c9-86d402ea3ca6)

https://drive.google.com/file/d/1XzvHg_xuG9in2XR1RgIyBq0DozPF-agu/view?usp=sharing

---

## 🧩 Entidades (resumen)
- Clientes
- Pedidos
- Detalle_Pedido
- Productos
- Ingredientes
- Producto_Ingrediente
- Proveedores
- Compras
- Detalle_Compra
- Empleados

---

## 🔗 Relaciones
- Un cliente puede generar varios pedidos, pero cada pedido pertenece a un único cliente (**1:N**).
- Un pedido puede incluir varios productos, y un producto puede estar en varios pedidos; esto se resuelve mediante la entidad **Detalle_Pedido** (**N:M**).
- Un producto se elabora con varios ingredientes, y un ingrediente puede usarse en varios productos; la entidad **Producto_Ingrediente** gestiona esta relación (**N:M**).
- Un proveedor puede estar asociado a varias compras, pero cada compra se registra con un único proveedor (**1:N**).
- Una compra puede incluir varios ingredientes, y un ingrediente puede estar en varias compras; esta relación se gestiona mediante **Detalle_Compra** (**N:M**).
- Un empleado puede preparar o entregar varios pedidos, pero cada pedido está asociado a un solo empleado (**1:N**).

---

## ⚖️ Restricciones de Integridad
- Todo pedido debe estar vinculado a un cliente válido.
- No se puede generar un detalle de pedido si no existe un pedido y un producto asociados.
- Un producto solo puede elaborarse con ingredientes que estén previamente registrados en inventario.
- Una compra no puede existir sin estar vinculada a un proveedor.
- Todo detalle de compra debe asociar una compra válida con un ingrediente existente.
- Cada pedido debe estar asignado a un empleado responsable (cocinero o repartidor).

---

## 🗝️ Elementos Clave para la Correcta Creación
- Definición de **claves primarias** en todas las entidades principales (ejemplo: `id_cliente`, `id_pedido`, `id_producto`, etc.).
- Uso de **claves foráneas** para garantizar la integridad referencial en las relaciones.
- Implementación de tablas intermedias (`Detalle_Pedido`, `Producto_Ingrediente`, `Detalle_Compra`) para manejar relaciones muchos a muchos.
- Aplicación de restricciones de estado en los pedidos (`pendiente`, `en preparación`, `enviado`, `entregado`).
- Control de inventario mediante la relación entre ingredientes, productos y compras.

---

## ⚙️ Procedimientos Almacenados
- **VentasDiarias** → Reporte detallado de ventas de una fecha específica.  
- **ClientesPrimerTrimestre** → Análisis de clientes y compras en el primer trimestre de un año.  
- **AgregarCliente** → Inserción con manejo de excepciones y control de duplicados.  
- **ActualizarInventario** → Actualiza stock y genera registro en la auditoría de inventario.  

---

## 🛡️ Triggers
- **ControlInventario** → Registra cambios de stock y lanza advertencias si está por debajo del mínimo.  
- **SeguimientoClientes** → Registra automáticamente cada compra en la tabla de seguimiento.  

---

## 🔎 Consultas Avanzadas
El script incluye ejemplos de consultas para:
- `JOIN` de múltiples tablas.  
- `UNION` entre clientes y proveedores.  
- `GROUP BY` con funciones agregadas.  
- Manipulación de fechas y filtros con `HAVING`.  

---

## 💻 Aplicación Java
Sistema completo con menú interactivo que incluye **11 opciones de gestión**:

<img width="956" height="549" alt="MenuPrincipal" src="https://github.com/user-attachments/assets/517e29d1-6df6-4624-87af-f7b3ce9063ae" />

**Funcionalidades implementadas:**
- Gestión completa de clientes (CREAR, LEER, ACTUALIZAR).
- <img width="807" height="356" alt="AgregarCliente" src="https://github.com/user-attachments/assets/c49e8bf9-8228-4b99-9d92-4654d9e63278" />
- Reportes de ventas diarias.
- <img width="953" height="421" alt="ReporteVentasDiarias4" src="https://github.com/user-attachments/assets/8f9494a9-f787-4339-80bb-984b33a01c8e" />
- Consulta de pedidos por fecha.
- <img width="993" height="481" alt="ConsultarPedidosFecha3" src="https://github.com/user-attachments/assets/dce0b594-7d7b-4d11-a2a7-e8b93792f1d5" />
- Control de inventario.
- <img width="818" height="366" alt="ActualizarStockIngrediente9" src="https://github.com/user-attachments/assets/f531728e-a9e9-4864-a34c-e1103b23f952" />
<img width="940" height="330" alt="EliminarProducto6" src="https://github.com/user-attachments/assets/1ac7b850-55f6-45b3-bd71-4d6a5aefa47f" />
<img width="964" height="395" alt="ActualizarProducto5" src="https://github.com/user-attachments/assets/3bf67df9-e298-4c0b-861c-f1e749ed5988" />
- Gestión de productos (activar/desactivar).  
- Análisis de clientes por trimestre.
- <img width="973" height="432" alt="ConsultarClientes2" src="https://github.com/user-attachments/assets/400176e7-108b-444a-a6c5-c060231c8e04" />
<img width="1147" height="425" alt="LeerClientesPrimerTrimestre7" src="https://github.com/user-attachments/assets/4438fdce-f428-4a4e-b4ac-3e9796ab7fed" />

---

## 🚀 Ejecución del Proyecto

### 📋 Prerrequisitos
- MySQL 8.0+ instalado y ejecutándose.  
- Java JDK 11+ instalado.  
- MySQL Connector/J (incluido en el proyecto).

### 🗄️ Configuración de la Base de Datos
Ejecutar el script SQL:

```sql
-- En MySQL Workbench o línea de comandos:
source script_dark_kitchen.sql;
```

Verificar la instalación:

```sql
CALL VerificacionFinal();
```

### ☕ Ejecución de la Aplicación Java
Compilar la aplicación:

```bash
javac -cp "mysql-connector-j-9.4.0.jar" DarkKitchenApp.java
```

Ejecutar la aplicación:

```bash
# Windows:
java -cp ".;mysql-connector-j-9.4.0.jar" DarkKitchenApp

# Linux/Mac:
java -cp ".:mysql-connector-j-9.4.0.jar" DarkKitchenApp
```

### ⚙️ Configuración de Conexión
En el archivo `DarkKitchenApp.java`, verifica estas variables:

```java
private static final String URL = "jdbc:mysql://localhost:3306/dark_kitchen";
private static final String USER = "root";      // Tu usuario MySQL
private static final String PASSWORD = "Valencia123";  // Tu contraseña
```

---

## 🧪 Pruebas Rápidas
Una vez ejecutada la aplicación, prueba estas opciones:
- **Opción 2** → Ver lista de clientes.  
- **Opción 4** → Reporte de ventas (usa `2025-01-10`).  
- **Opción 7** → Clientes primer trimestre (usa `2025`).  
- **Opción 8** → Consultar inventario.  

---

## 📂 Estructura del Proyecto
```text
dark-kitchen-project/
├── README.md
├── script_dark_kitchen.sql          # Script completo de la base de datos
├── DarkKitchenApp.java              # Aplicación Java principal
├── mysql-connector-j-9.4.0.jar      # Conector MySQL para Java
├── diagrama_er.png                  # Diagrama entidad-relación
└── captura_menu.png                 # Captura del menú en funcionamiento
```

---

## 🎯 Características Técnicas

### Base de Datos
- 11 tablas relacionadas.  
- 4 procedimientos almacenados.  
- 2 triggers automáticos.  
- Restricciones de integridad referencial.  
- Manejo de transacciones con `COMMIT` / `ROLLBACK`.  

### Aplicación Java
- Conexión JDBC optimizada.  
- Manejo de excepciones completo.  
- Interfaz de consola intuitiva.  
- Operaciones CRUD completas.  

---

## 📊 Capturas de Funcionamiento
**Base de Datos**  

---

## ✅ Estado del Proyecto
- ✅ Completado y funcional.  
- ✅ Base de datos optimizada.  
- ✅ Aplicación Java estable.  
- ✅ Documentación completa.  

El sistema está listo para **producción** y puede extenderse con **interfaces gráficas** o **APIs web**.  
