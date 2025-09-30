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
- Creacion de Tablas
<img width="909" height="613" alt="crearTablas" src="https://github.com/user-attachments/assets/05b7add6-b8b1-4250-9244-93045d0a6f29" />
<img width="887" height="416" alt="tablaClientes" src="https://github.com/user-attachments/assets/5029ae83-cc78-4ecd-9969-efc7e0523026" />
<img width="823" height="408" alt="tablaCompras" src="https://github.com/user-attachments/assets/b1f248bb-5dbb-4d06-ada2-3405e4898076" />
<img width="835" height="402" alt="tablaDetalleCompras" src="https://github.com/user-attachments/assets/dd133301-e4b5-4f6b-baef-ecf18f120144" />
<img width="906" height="421" alt="tablaDetallePedido" src="https://github.com/user-attachments/assets/ec8c8fba-e403-438c-9cbd-ceaf18ceabb8" />
<img width="802" height="396" alt="tablaProductoIngrediente" src="https://github.com/user-attachments/assets/e0b61cb6-9db5-42e8-b37b-55609d4266f7" />
<img width="891" height="423" alt="tablaPedidos" src="https://github.com/user-attachments/assets/6ce7658e-0db8-48ca-a55e-d49d917f9890" />
<img width="896" height="422" alt="tablaIngredientes" src="https://github.com/user-attachments/assets/2b9ddd49-c017-4e76-ad9e-b05eddaaaa03" />
<img width="838" height="418" alt="tablaEmpleados" src="https://github.com/user-attachments/assets/ec6b0534-8f30-4f63-a5fd-06548610e861" />
<img width="872" height="395" alt="tablaProveedores" src="https://github.com/user-attachments/assets/ad319a00-9289-4cc5-9c04-c579a6a12e6b" />
<img width="768" height="393" alt="tablaProductos" src="https://github.com/user-attachments/assets/3570fee5-52b3-4d41-936b-0f21aed8a1d3" />
- Creacion de usuarios
<img width="804" height="394" alt="crearUsuario" src="https://github.com/user-attachments/assets/713173ed-05a0-4aa4-b5be-731d71b3f653" />
- Procedimientos
<img width="839" height="612" alt="procedimientos" src="https://github.com/user-attachments/assets/18bd59ba-7ccd-4ae4-bdd6-52b3925a0c3c" />
- Consultas
<img width="893" height="606" alt="consultas" src="https://github.com/user-attachments/assets/dc0a6f3b-b41a-42d7-8427-ccc9be01c459" />
<img width="883" height="433" alt="consultas2" src="https://github.com/user-attachments/assets/64523ab0-b6c9-46fd-b9b9-803e889b3999" />
<img width="862" height="429" alt="consultas3" src="https://github.com/user-attachments/assets/9e36e3d0-c1a9-4ebb-a626-430493136f21" />
<img width="795" height="441" alt="consultas4" src="https://github.com/user-attachments/assets/78c4d5a8-185d-4f48-a676-8916e541feea" />
<img width="903" height="399" alt="consultas5" src="https://github.com/user-attachments/assets/192944e0-4900-455a-91f6-86d926996a85" />
<img width="883" height="403" alt="consultas6" src="https://github.com/user-attachments/assets/3dcd5de6-b650-423d-995b-14780322f3dc" />
- Crud 
<img width="885" height="260" alt="crud" src="https://github.com/user-attachments/assets/9db77ca1-dc68-4d50-be02-e7bdb43e1f0c" />
- Verificacion
<img width="908" height="325" alt="verificacion" src="https://github.com/user-attachments/assets/a5b64887-51e9-4452-a650-bc0e15efd44b" />

---

## ✅ Estado del Proyecto
- ✅ Completado y funcional.  
- ✅ Base de datos optimizada.  
- ✅ Aplicación Java estable.  
- ✅ Documentación completa.  

El sistema está listo para **producción** y puede extenderse con **interfaces gráficas** o **APIs web**.  
