/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Credenciales;

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class InsertManager {

    public static void insertarDatosIniciales(Statement stmt) throws SQLException {
        // Usuarios
        stmt.executeUpdate("""
        INSERT INTO usuarios (usu_nombre_usuario, usu_contraseña, usu_email, usu_rol)
        VALUES 
        ('Admin', 'admin123', 'admin@sistema.com', 'Administrador'),
        ('CarlosV', 'vendedor123', 'carlos@sistema.com', 'Vendedor'),
        ('MariaC', 'cliente123', 'maria@sistema.com', 'Cliente'),
        ('JuanR', 'repartidor123', 'juan@sistema.com', 'Repartidor')
    """);

        // Métodos de pago
        stmt.executeUpdate("""
        INSERT INTO metodos_pago (mp_nombre, mp_descripcion)
        VALUES 
        ('Efectivo', 'Pago en efectivo al momento de la entrega'),
        ('Tarjeta', 'Pago con tarjeta de crédito/débito'),
        ('Transferencia', 'Transferencia bancaria')
    """);

        // Categorías
        stmt.executeUpdate("""
        INSERT INTO categorias (cat_nombre, cat_descripcion)
        VALUES 
        ('Alimentos', 'Productos comestibles'),
        ('Bebidas', 'Líquidos para consumo'),
        ('Limpieza', 'Artículos de limpieza del hogar'),
        ('Hogar', 'Accesorios para el hogar'),
        ('Electrónica', 'Dispositivos electrónicos'),
        ('Oficina', 'Material de oficina'),
        ('Cuidado personal', 'Higiene y cuidado corporal'),
        ('Otros', 'Productos varios no clasificados')
    """);

        // Productos (usamos IDs de categorías: del 1 al 8)
        stmt.executeUpdate("""
        INSERT INTO productos (prod_nombre, prod_precio, prod_stock, prod_stock_minimo, prod_descripcion, prod_id_categoria)
        VALUES 
        ('Pan integral', 3.50, 50, 10, 'Pan de trigo integral fresco', 1),
        ('Jugo de naranja', 2.00, 80, 20, 'Jugo natural 500ml', 2),
        ('Detergente líquido', 5.50, 40, 5, 'Detergente para ropa 1L', 3),
        ('Almohada suave', 15.00, 25, 5, 'Almohada ergonómica', 4),
        ('Audífonos Bluetooth', 35.00, 30, 5, 'Audífonos inalámbricos con micrófono', 5),
        ('Cuaderno A4', 4.00, 100, 20, 'Cuaderno de 100 hojas', 6),
        ('Shampoo anticaspa', 8.00, 60, 10, 'Shampoo de hierbas 400ml', 7),
        ('Caja sorpresa', 10.00, 15, 2, 'Producto sorpresa para cualquier ocasión', 8)
    """);

        // Pedido (cliente: MariaC -> usu_id = 3, repartidor: JuanR -> usu_id = 4, método de pago: Efectivo -> mp_id = 1)
        stmt.executeUpdate("""
        INSERT INTO pedidos (ped_codigo_pedido, ped_estado, ped_id_cliente, ped_id_repartidor, ped_id_metodo_pago, ped_direccion_entrega, ped_es_urgente)
        VALUES 
        ('PED001', 'Preparación', 3, 4, 1, 'Av. Los Álamos 123, Lima', TRUE)
    """);

        // Detalle pedido (pedido PED001 = id 1)
        stmt.executeUpdate("""
        INSERT INTO detalle_pedido (dp_id_pedido, dp_id_producto, dp_cantidad, dp_precio_unitario, dp_subtotal)
        VALUES 
        (1, 1, 2, 3.50, 7.00),  -- 2 panes
        (1, 2, 1, 2.00, 2.00)   -- 1 jugo
    """);

        // Carrito para MariaC (usu_id = 3)
        stmt.executeUpdate("""
        INSERT INTO carrito (car_id_usuario, car_id_producto, car_cantidad)
        VALUES 
        (3, 3, 1), -- Detergente
        (3, 5, 1)  -- Audífonos
    """);

        // Devolución de un producto del pedido
        stmt.executeUpdate("""
        INSERT INTO devoluciones (dev_id_pedido, dev_id_producto, dev_cantidad, dev_motivo)
        VALUES 
        (1, 2, 1, 'Producto vencido')
    """);

        // Historial de estados del pedido
        stmt.executeUpdate("""
        INSERT INTO historial_pedidos (hp_id_pedido, hp_estado_anterior, hp_estado_nuevo, hp_id_usuario)
        VALUES 
        (1, 'Pendiente', 'Preparación', 1)
    """);

        // Logs del sistema
        stmt.executeUpdate("""
        INSERT INTO logs_sistema (log_accion, log_tabla_afectada, log_id_registro_afectado, log_id_usuario)
        VALUES 
        ('Creación inicial del pedido', 'pedidos', 1, 1),
        ('Cambio de estado del pedido', 'historial_pedidos', 1, 1),
        ('Registro de devolución', 'devoluciones', 1, 3)
    """);
    }
}
