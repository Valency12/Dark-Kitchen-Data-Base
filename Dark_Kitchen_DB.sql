DROP DATABASE IF EXISTS dark_kitchen;
CREATE DATABASE dark_kitchen;
USE dark_kitchen;

CREATE TABLE Clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100) UNIQUE,
    direccion VARCHAR(150),
    fecha_registro DATE DEFAULT (CURRENT_DATE)
);

CREATE TABLE Empleados (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(15),
    rol ENUM('cocinero','repartidor','administrador') NOT NULL,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE Productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    categoria ENUM('bebida','entrada','plato fuerte','postre'),
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE Ingredientes (
    id_ingrediente INT AUTO_INCREMENT PRIMARY KEY,
    nombre_ingrediente VARCHAR(100) NOT NULL UNIQUE,
    unidad_medida ENUM('kg','litros','unidad'),
    stock DECIMAL(10,2) DEFAULT 0,
    stock_minimo DECIMAL(10,2) DEFAULT 1
);

CREATE TABLE Producto_Ingrediente (
    id_producto INT NOT NULL,
    id_ingrediente INT NOT NULL,
    cantidad_usada DECIMAL(10,2),
    PRIMARY KEY (id_producto, id_ingrediente),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto),
    FOREIGN KEY (id_ingrediente) REFERENCES Ingredientes(id_ingrediente)
);

CREATE TABLE Proveedores (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre_proveedor VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100) UNIQUE,
    direccion VARCHAR(150)
);

CREATE TABLE Compras (
    id_compra INT AUTO_INCREMENT PRIMARY KEY,
    id_proveedor INT NOT NULL,
    fecha_compra DATE NOT NULL,
    total_compra DECIMAL(10,2),
    FOREIGN KEY (id_proveedor) REFERENCES Proveedores(id_proveedor)
);

CREATE TABLE Detalle_Compra (
    id_detalle_compra INT AUTO_INCREMENT PRIMARY KEY,
    id_compra INT NOT NULL,
    id_ingrediente INT NOT NULL,
    cantidad_comprada DECIMAL(10,2),
    precio_unitario DECIMAL(10,2),
    FOREIGN KEY (id_compra) REFERENCES Compras(id_compra),
    FOREIGN KEY (id_ingrediente) REFERENCES Ingredientes(id_ingrediente)
);

CREATE TABLE Pedidos (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    fecha_pedido DATE NOT NULL,
    hora_pedido TIME NOT NULL,
    estado ENUM('pendiente','en preparación','enviado','entregado') DEFAULT 'pendiente',
    total DECIMAL(10,2),
    metodo_pago ENUM('efectivo','tarjeta','transferencia') DEFAULT 'efectivo',
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

CREATE TABLE Detalle_Pedido (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    subtotal DECIMAL(10,2),
    FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

CREATE TABLE SeguimientoClientes (
    id_seguimiento INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT NOT NULL,
    nombre_cliente VARCHAR(100),
    fecha_compra DATE,
    hora_compra TIME,
    tipo_seguimiento ENUM('nueva_compra','queja','satisfaccion') DEFAULT 'nueva_compra',
    fecha_seguimiento DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

CREATE TABLE AuditoriaInventario (
    id_auditoria INT AUTO_INCREMENT PRIMARY KEY,
    id_ingrediente INT,
    nombre_ingrediente VARCHAR(100),
    stock_anterior DECIMAL(10,2),
    stock_nuevo DECIMAL(10,2),
    fecha_cambio DATETIME DEFAULT CURRENT_TIMESTAMP,
    usuario VARCHAR(100)
);

-- Crear usuarios y permisos
CREATE USER IF NOT EXISTS 'dark_kitchen_admin'@'localhost' IDENTIFIED BY 'admin123';
GRANT ALL PRIVILEGES ON dark_kitchen.* TO 'dark_kitchen_admin'@'localhost';

CREATE USER IF NOT EXISTS 'dark_kitchen_user'@'localhost' IDENTIFIED BY 'user123';
GRANT SELECT, INSERT, UPDATE, EXECUTE ON dark_kitchen.* TO 'dark_kitchen_user'@'localhost';

-- Inserción de datos
INSERT INTO Clientes (nombre, apellido, telefono, email, direccion) VALUES
('Ana', 'López', '5511111111', 'ana@example.com', 'Calle A #123, CDMX'),
('Carlos', 'Ramírez', '5522222222', 'carlos@example.com', 'Av. Reforma #456, CDMX'),
('María', 'García', '5533333333', 'maria@example.com', 'Col. Centro #789, CDMX'),
('José', 'Martínez', '5544444444', 'jose@example.com', 'Calle Sur #321, CDMX'),
('Lucía', 'Fernández', '5555555555', 'lucia@example.com', 'Av. Norte #654, CDMX');

INSERT INTO Empleados (nombre, apellido, telefono, rol) VALUES
('Luis', 'Gómez', '5561111111', 'cocinero'),
('Elena', 'Pérez', '5562222222', 'cocinero'),
('Raúl', 'Hernández', '5563333333', 'repartidor'),
('Sofía', 'Torres', '5564444444', 'administrador'),
('Diego', 'Vargas', '5565555555', 'repartidor');

INSERT INTO Productos (nombre_producto, descripcion, precio, categoria) VALUES
('Pizza Margarita', 'Pizza con queso y jitomate', 120.00, 'plato fuerte'),
('Hamburguesa Clásica', 'Hamburguesa con carne de res y queso', 95.00, 'plato fuerte'),
('Ensalada César', 'Ensalada fresca con aderezo César', 80.00, 'entrada'),
('Refresco Cola', 'Bebida gaseosa sabor cola', 25.00, 'bebida'),
('Pastel de Chocolate', 'Porción individual de pastel', 60.00, 'postre');

INSERT INTO Ingredientes (nombre_ingrediente, unidad_medida, stock, stock_minimo) VALUES
('Queso mozzarella', 'kg', 10, 2),
('Carne de res', 'kg', 15, 3),
('Lechuga', 'unidad', 30, 5),
('Refresco lata', 'unidad', 50, 10),
('Chocolate', 'kg', 8, 1);

INSERT INTO Producto_Ingrediente (id_producto, id_ingrediente, cantidad_usada) VALUES
(1, 1, 0.2),
(2, 2, 0.15),
(3, 3, 1),
(4, 4, 1),
(5, 5, 0.1);

INSERT INTO Proveedores (nombre_proveedor, telefono, email, direccion) VALUES
('Lácteos del Valle', '5571111111', 'lacteos@example.com', 'Av. Leche #123, CDMX'),
('Carnes Premium', '5572222222', 'carnes@example.com', 'Av. Ganado #456, CDMX'),
('Verde Market', '5573333333', 'verduras@example.com', 'Col. Fresca #789, CDMX'),
('Bebidas MX', '5574444444', 'bebidas@example.com', 'Calle Refrescos #321, CDMX'),
('Dulces y Cacao', '5575555555', 'choco@example.com', 'Av. Dulce #654, CDMX');

INSERT INTO Compras (id_proveedor, fecha_compra, total_compra) VALUES
(1, '2025-08-01', 1500.00),
(2, '2025-08-02', 2500.00),
(3, '2025-08-03', 800.00),
(4, '2025-08-04', 1200.00),
(5, '2025-08-05', 950.00);

INSERT INTO Detalle_Compra (id_compra, id_ingrediente, cantidad_comprada, precio_unitario) VALUES
(1, 1, 5, 300.00),
(2, 2, 10, 250.00),
(3, 3, 20, 40.00),
(4, 4, 30, 40.00),
(5, 5, 5, 190.00);

INSERT INTO Pedidos (id_cliente, fecha_pedido, hora_pedido, estado, total, metodo_pago) VALUES
(1, '2025-01-10', '12:30:00', 'pendiente', 215.00, 'tarjeta'),
(2, '2025-02-10', '13:00:00', 'en preparación', 120.00, 'efectivo'),
(3, '2025-01-11', '14:15:00', 'enviado', 155.00, 'transferencia'),
(4, '2025-08-11', '15:00:00', 'entregado', 25.00, 'efectivo'),
(5, '2025-08-12', '16:30:00', 'pendiente', 60.00, 'tarjeta');

INSERT INTO Detalle_Pedido (id_pedido, id_producto, cantidad, subtotal) VALUES
(1, 1, 1, 120.00),
(1, 4, 1, 25.00),
(2, 1, 1, 120.00),
(3, 2, 1, 95.00),
(3, 3, 1, 60.00);

-- Procedimientos almacenados
DELIMITER //

CREATE PROCEDURE VentasDiarias(IN p_fecha DATE)
BEGIN
    SELECT 
        p.id_pedido, 
        CONCAT(c.nombre, ' ', c.apellido) AS cliente,
        p.fecha_pedido,
        p.hora_pedido,
        p.total,
        p.estado,
        p.metodo_pago
    FROM Pedidos p
    JOIN Clientes c ON p.id_cliente = c.id_cliente
    WHERE p.fecha_pedido = p_fecha
    ORDER BY p.hora_pedido DESC;

    SELECT 
        COUNT(*) AS total_pedidos,
        SUM(total) AS total_ventas,
        AVG(total) AS promedio_venta
    FROM Pedidos
    WHERE fecha_pedido = p_fecha;
END //

CREATE PROCEDURE ClientesPrimerTrimestre(IN p_year INT)
BEGIN
    SELECT 
        c.id_cliente, 
        CONCAT(c.nombre, ' ', c.apellido) AS cliente,
        c.email,
        c.telefono,
        COUNT(p.id_pedido) AS total_pedidos,
        SUM(p.total) AS monto_total
    FROM Clientes c
    JOIN Pedidos p ON c.id_cliente = p.id_cliente
    WHERE p.fecha_pedido BETWEEN CONCAT(p_year, '-01-01') AND CONCAT(p_year, '-03-31')
    GROUP BY c.id_cliente, c.nombre, c.apellido, c.email, c.telefono
    ORDER BY monto_total DESC;
END //

CREATE PROCEDURE AgregarCliente(
    IN p_nombre VARCHAR(50),
    IN p_apellido VARCHAR(50),
    IN p_telefono VARCHAR(15),
    IN p_email VARCHAR(100),
    IN p_direccion VARCHAR(150)
)
BEGIN
    DECLARE EXIT HANDLER FOR 1062
    BEGIN
        ROLLBACK;
        SELECT 'Error: Ya existe un cliente con ese correo electrónico' AS mensaje_error;
    END;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SELECT 'Error inesperado al agregar cliente' AS mensaje_error;
    END;

    START TRANSACTION;
    
    INSERT INTO Clientes(nombre, apellido, telefono, email, direccion)
    VALUES (p_nombre, p_apellido, p_telefono, p_email, p_direccion);
    
    COMMIT;
    
    SELECT 'Cliente agregado exitosamente' AS mensaje_exito;
END //

CREATE PROCEDURE ActualizarInventario(
    IN p_id_ingrediente INT,
    IN p_cantidad DECIMAL(10,2)
)
BEGIN
    DECLARE stock_actual DECIMAL(10,2);
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SELECT 'Error al actualizar inventario' AS mensaje_error;
    END;

    START TRANSACTION;
    
    SELECT stock INTO stock_actual FROM Ingredientes WHERE id_ingrediente = p_id_ingrediente;
    
    UPDATE Ingredientes 
    SET stock = stock + p_cantidad 
    WHERE id_ingrediente = p_id_ingrediente;
    
    INSERT INTO AuditoriaInventario (id_ingrediente, nombre_ingrediente, stock_anterior, stock_nuevo)
    SELECT p_id_ingrediente, nombre_ingrediente, stock_actual, stock_actual + p_cantidad
    FROM Ingredientes WHERE id_ingrediente = p_id_ingrediente;
    
    COMMIT;
    
    SELECT 'Inventario actualizado exitosamente' AS mensaje_exito;
END //

CREATE PROCEDURE ProcesarPedidoConRollback(IN p_id_pedido INT)
BEGIN
    DECLARE v_stock_actual DECIMAL(10,2);
    
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SELECT 'Error: Transacción revertida' AS resultado;
    END;
    
    START TRANSACTION;
    
    SELECT stock INTO v_stock_actual FROM Ingredientes WHERE id_ingrediente = 1;
    
    IF v_stock_actual > 0 THEN
        UPDATE Ingredientes SET stock = stock - 0.1 WHERE id_ingrediente = 1;
        UPDATE Pedidos SET estado = 'en preparación' WHERE id_pedido = p_id_pedido;
        COMMIT;
        SELECT 'Pedido procesado exitosamente' AS resultado;
    ELSE
        ROLLBACK;
        SELECT 'Error: Stock insuficiente' AS resultado;
    END IF;
END //

-- Triggers
CREATE TRIGGER ControlInventario BEFORE UPDATE ON Ingredientes
FOR EACH ROW
BEGIN
    IF OLD.stock != NEW.stock THEN
        INSERT INTO AuditoriaInventario (id_ingrediente, nombre_ingrediente, stock_anterior, stock_nuevo)
        VALUES (OLD.id_ingrediente, OLD.nombre_ingrediente, OLD.stock, NEW.stock);
    END IF;
    
    IF NEW.stock < NEW.stock_minimo THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Advertencia: Stock por debajo del mínimo permitido';
    END IF;
END //

CREATE TRIGGER SeguimientoClientes AFTER INSERT ON Pedidos
FOR EACH ROW
BEGIN
    INSERT INTO SeguimientoClientes(id_cliente, nombre_cliente, fecha_compra, hora_compra, tipo_seguimiento)
    SELECT 
        c.id_cliente,
        CONCAT(c.nombre, ' ', c.apellido), 
        NEW.fecha_pedido, 
        NEW.hora_pedido,
        'nueva_compra'
    FROM Clientes c
    WHERE c.id_cliente = NEW.id_cliente;
END //

DELIMITER ;

-- Consultas avanzadas
SELECT 
    p.id_pedido,
    CONCAT(c.nombre, ' ', c.apellido) AS cliente,
    p.fecha_pedido,
    p.total,
    pr.nombre_producto,
    dp.cantidad,
    dp.subtotal
FROM Pedidos p
JOIN Clientes c ON p.id_cliente = c.id_cliente
JOIN Detalle_Pedido dp ON p.id_pedido = dp.id_pedido
JOIN Productos pr ON dp.id_producto = pr.id_producto
ORDER BY p.fecha_pedido DESC, p.total DESC;

SELECT 'Cliente' AS tipo, nombre, apellido, email, telefono FROM Clientes
UNION ALL
SELECT 'Proveedor' AS tipo, nombre_proveedor AS nombre, '' AS apellido, email, telefono FROM Proveedores
ORDER BY tipo, nombre;

SELECT * FROM Pedidos 
ORDER BY fecha_pedido DESC, hora_pedido DESC;

SELECT * FROM Productos 
ORDER BY precio ASC;

SELECT 
    categoria,
    COUNT(*) AS total_productos,
    AVG(precio) AS precio_promedio,
    MAX(precio) AS precio_maximo,
    MIN(precio) AS precio_minimo
FROM Productos 
GROUP BY categoria;

SELECT 
    id_pedido,
    fecha_pedido,
    DAYNAME(fecha_pedido) AS dia_semana,
    MONTHNAME(fecha_pedido) AS mes,
    QUARTER(fecha_pedido) AS trimestre,
    YEAR(fecha_pedido) AS año
FROM Pedidos
WHERE YEAR(fecha_pedido) = 2025 AND MONTH(fecha_pedido) = 8;

SELECT 
    id_cliente,
    COUNT(*) AS total_pedidos,
    SUM(total) AS monto_total
FROM Pedidos
GROUP BY id_cliente
HAVING total_pedidos > 1 AND monto_total > 100;

-- Procedimiento de verificación
DELIMITER //

CREATE PROCEDURE VerificacionFinal()
BEGIN
    SELECT 'Base de datos dark_kitchen creada exitosamente' AS resultado;
    
    SELECT 
        (SELECT COUNT(*) FROM Clientes) AS total_clientes,
        (SELECT COUNT(*) FROM Productos) AS total_productos,
        (SELECT COUNT(*) FROM Pedidos) AS total_pedidos,
        (SELECT COUNT(*) FROM information_schema.routines 
         WHERE routine_schema = 'dark_kitchen') AS total_procedimientos;
END //

DELIMITER ;

-- Operaciones CRUD de demostración
INSERT INTO Clientes (nombre, apellido, telefono, email, direccion) 
VALUES ('Test', 'CRUD', '5566666666', 'test_crud@example.com', 'Dirección CRUD');

SELECT * FROM Clientes WHERE email = 'test_crud@example.com';

UPDATE Clientes SET telefono = '5577777777' WHERE email = 'test_crud@example.com';

DELETE FROM Clientes WHERE email = 'test_crud@example.com';

-- Verificación final
CALL VerificacionFinal();

SELECT 'Script ejecutado correctamente' AS resultado;