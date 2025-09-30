# 🍴 Proyecto Dark Kitchen

## 📌 Descripción
Este proyecto implementa un sistema de gestión para una **Dark Kitchen** utilizando **MySQL** y **Java**.  
El objetivo es cubrir operaciones de administración de **clientes, empleados, productos, inventario, compras, pedidos y auditoría de procesos**.

Incluye:
- Definición de tablas con relaciones y llaves foráneas  
- Inserción de datos iniciales para pruebas  
- Procedimientos almacenados para reportes, inserción y control de inventario  
- Triggers para auditoría y seguimiento automático de clientes  
- Consultas avanzadas con `JOIN`, `UNION`, `GROUP BY`, `ORDER BY` y `HAVING`  
- Aplicación Java con menú interactivo para gestión completa  

---

## 🗄️ Estructura de la Base de Datos
- Clientes, Empleados, Productos, Ingredientes, Proveedores  
- Pedidos y Compras con sus respectivos detalles  
- Auditoría de Inventario con control automático mediante triggers  
- Seguimiento de Clientes basado en pedidos realizados  

![Diagrama ER](https://diagrama_er.png)

---

## ⚙️ Procedimientos Almacenados
- **VentasDiarias** → Reporte detallado de ventas de una fecha específica  
- **ClientesPrimerTrimestre** → Análisis de clientes y compras en el primer trimestre de un año  
- **AgregarCliente** → Inserción con manejo de excepciones y control de duplicados  
- **ActualizarInventario** → Actualiza stock y genera registro en la auditoría de inventario  

---

## 🛡️ Triggers
- **ControlInventario** → Registra cambios de stock y lanza advertencias si está por debajo del mínimo  
- **SeguimientoClientes** → Registra automáticamente cada compra en la tabla de seguimiento  

---

## 🔎 Consultas Avanzadas
El script incluye ejemplos de consultas para:
- `JOIN` de múltiples tablas  
- `UNION` entre clientes y proveedores  
- `GROUP BY` con funciones agregadas  
- Manipulación de fechas y filtros con `HAVING`  

---

## 💻 Aplicación Java
Sistema completo con menú interactivo que incluye **11 opciones de gestión**:

![Menú de la aplicación](https://captura_menu.png)

**Funcionalidades implementadas:**
- ✅ Gestión completa de clientes (CREAR, LEER, ACTUALIZAR)  
- ✅ Reportes de ventas diarias  
- ✅ Consulta de pedidos por fecha  
- ✅ Control de inventario con alertas  
- ✅ Gestión de productos (activar/desactivar)  
- ✅ Análisis de clientes por trimestre  

---

## 🚀 Ejecución del Proyecto

### 📋 Prerrequisitos
- **MySQL 8.0+** instalado y ejecutándose  
- **Java JDK 11+** instalado  
- **MySQL Connector/J** (incluido en el proyecto)  

---

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

---

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

---

### ⚙️ Configuración de Conexión
En el archivo **DarkKitchenApp.java**, verifica estas variables:

```java
private static final String URL = "jdbc:mysql://localhost:3306/dark_kitchen";
private static final String USER = "root";      // Tu usuario MySQL
private static final String PASSWORD = "Valencia123";  // Tu contraseña
```

---

## 🧪 Pruebas Rápidas
Una vez ejecutada la aplicación, prueba estas opciones:
- **Opción 2** → Ver lista de clientes  
- **Opción 4** → Reporte de ventas (usa `2025-01-10`)  
- **Opción 7** → Clientes primer trimestre (usa `2025`)  
- **Opción 8** → Consultar inventario  

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
- 11 tablas relacionadas  
- 4 procedimientos almacenados  
- 2 triggers automáticos  
- Restricciones de integridad referencial  
- Manejo de transacciones con `COMMIT` / `ROLLBACK`  

### Aplicación Java
- Conexión **JDBC optimizada**  
- Manejo de excepciones completo  
- Interfaz de consola intuitiva  
- Operaciones CRUD completas  

---

## 📊 Capturas de Funcionamiento

### Base de Datos
![Captura SQL](https://captura_sql.png)

### Aplicación Java
![Captura Java](https://captura_java.png)

---

## ✅ Estado del Proyecto
- ✅ Completado y funcional  
- ✅ Base de datos optimizada  
- ✅ Aplicación Java estable  
- ✅ Documentación completa  

El sistema está listo para **producción** y puede extenderse con **interfaces gráficas** o **APIs web**.
