# 🍴 Proyecto Dark Kitchen  

## 📌 Descripción  
Este proyecto implementa un **sistema de gestión para una Dark Kitchen** utilizando **MySQL** y **Java**.  
El objetivo es cubrir operaciones de administración de clientes, empleados, productos, inventario, compras, pedidos y auditoría de procesos.  

Incluye:  
- Definición de tablas con **relaciones y llaves foráneas**.  
- Inserción de datos iniciales para pruebas.  
- Procedimientos almacenados para reportes, inserción y control de inventario.  
- Triggers para auditoría y seguimiento automático de clientes.  
- Consultas avanzadas con **JOIN, UNION, GROUP BY, ORDER BY y HAVING**.  
- Procedimiento de verificación final para asegurar la correcta creación de la base de datos.  

## 🗄️ Estructura de la Base de Datos  
- **Clientes, Empleados, Productos, Ingredientes, Proveedores**  
- **Pedidos y Compras** con sus respectivos detalles  
- **Auditoría de Inventario** con control automático mediante triggers  
- **Seguimiento de Clientes** basado en pedidos realizados  
![Dark_kitchen_proyect](https://github.com/user-attachments/assets/36afc1e2-8353-4478-9c26-5cca1de62c08)
https://app.diagrams.net/#G1XzvHg_xuG9in2XR1RgIyBq0DozPF-agu#%7B%22pageId%22%3A%22aA2aKCnQj_j6rVb8hOUM%22%7D

## ⚙️ Procedimientos Almacenados  
- **VentasDiarias** → Reporte detallado de ventas de una fecha específica.  
- **ClientesPrimerTrimestre** → Análisis de clientes y compras en el primer trimestre de un año.  
- **AgregarCliente** → Inserción con manejo de excepciones y control de duplicados.  
- **ActualizarInventario** → Actualiza stock y genera registro en la auditoría de inventario.  

## 🛡️ Triggers  
- **ControlInventario** → Registra cambios de stock y lanza advertencias si está por debajo del mínimo.  
- **SeguimientoClientes** → Registra automáticamente cada compra en la tabla de seguimiento.  

## 🔎 Consultas Avanzadas  
El script incluye ejemplos de consultas para:  
- **JOIN** de múltiples tablas.  
- **UNION** entre clientes y proveedores.  
- **GROUP BY** con funciones agregadas.  
- **Manipulación de fechas** y **filtros con HAVING**.  

## 💻 Código en Java  
Se incluye un archivo en **Java** para conexión con la base de datos y ejecución de operaciones principales.  
Este código permite interactuar con el sistema desde una aplicación de consola o interfaz gráfica.  

> 📷 Aquí puedes agregar capturas del código ejecutándose.  

## 🚀 Ejecución  
1. Ejecutar el script SQL completo en MySQL Workbench o consola.  
2. Verificar con el procedimiento `CALL VerificacionFinal();`.  
3. Compilar y ejecutar el código en Java para conectarse a la base de datos.  

## 📂 Archivos del Proyecto  
- `dark_kitchen.sql` → Script completo con DDL, DML, SP, triggers y consultas.  
- `Main.java` (u otro nombre que elijas) → Código Java para interacción con la BD.  

## ✅ Estado del Proyecto  
El script incluye inserción de datos de prueba y consultas listas para ejecutar.  
Se recomienda extender la funcionalidad agregando **interfaces gráficas o APIs** que consuman esta base de datos.  

---
✍️ Proyecto académico desarrollado para prácticas avanzadas en **Bases de Datos y Java**.  
