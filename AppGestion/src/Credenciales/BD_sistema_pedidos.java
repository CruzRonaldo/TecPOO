/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Credenciales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class BD_sistema_pedidos {

    private static final String URL = "jdbc:mysql://" + UserPass.Host + ":" + UserPass.Puerto + "/";

    public static void verificarOCrearBD() {
        try {
            // 1. Verificar/Crear la base de datos
            if (!existeBaseDatos()) {
                crearBaseDatosCompleta();
            } else {
                System.out.println("La base de datos ya existe.");
            }
        } catch (SQLException e) {
            manejarErrorBD(e);
        }
    }

    private static boolean existeBaseDatos() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, UserPass.User, UserPass.Pass); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + UserPass.nombreBaseDatos + "'")) {

            return rs.next();
        }
    }

    private static void crearBaseDatosCompleta() throws SQLException {
        try (Connection con = DriverManager.getConnection(URL, UserPass.User, UserPass.Pass); Statement stmt = con.createStatement()) {

            System.out.println("La base de datos no existe. Creándola...");

            // Crear la base de datos
            stmt.executeUpdate("CREATE DATABASE " + UserPass.nombreBaseDatos);
            stmt.execute("USE " + UserPass.nombreBaseDatos);

            crearTablas(stmt);

            insertarDatosIniciales(stmt);

            System.out.println("Base de datos y tablas creadas correctamente.");
        }

    }

    private static void crearTablas(Statement stmt) throws SQLException {
        String[] tablas = {
            """
            CREATE TABLE usuarios (
                usu_id INT AUTO_INCREMENT PRIMARY KEY,
                usu_nombre_usuario VARCHAR(50) UNIQUE,
                usu_contraseña VARCHAR(100),
                usu_email VARCHAR(100),
                usu_telefono VARCHAR(20),
                usu_direccion TEXT,
                usu_rol VARCHAR(20) CHECK (usu_rol IN ('Administrador', 'Vendedor', 'Cliente', 'Repartidor')),
                usu_puntos INT DEFAULT 0,
                usu_preferencias TEXT,
                usu_fecha_registro DATETIME DEFAULT NOW()
            )
            """,
            """
            CREATE TABLE categorias (
                cat_id INT AUTO_INCREMENT PRIMARY KEY,
                cat_nombre VARCHAR(50) UNIQUE,
                cat_descripcion TEXT
            )
            """,
            """
            CREATE TABLE productos (
                prod_id INT AUTO_INCREMENT PRIMARY KEY,
                prod_nombre VARCHAR(100) UNIQUE,
                prod_precio DECIMAL(10,2),
                prod_stock INT,
                prod_stock_minimo INT,
                prod_descripcion TEXT,
                prod_id_categoria INT,
                prod_imagen LONGBLOB,
                FOREIGN KEY (prod_id_categoria) REFERENCES categorias(cat_id)
            )
            """,
            """
            CREATE TABLE metodos_pago (
                mp_id INT AUTO_INCREMENT PRIMARY KEY,
                mp_nombre VARCHAR(50) UNIQUE,
                mp_descripcion TEXT
            )
            """,
            """
            CREATE TABLE pedidos (
                ped_id INT AUTO_INCREMENT PRIMARY KEY,
                ped_codigo_pedido VARCHAR(20) UNIQUE,
                ped_fecha DATETIME DEFAULT NOW(),
                ped_estado VARCHAR(50) CHECK (ped_estado IN ('Pendiente', 'Preparación', 'En camino', 'Entregado', 'Cancelado')),
                ped_id_cliente INT,
                ped_id_repartidor INT,
                ped_id_metodo_pago INT,
                ped_direccion_entrega TEXT,
                ped_es_urgente BOOLEAN DEFAULT FALSE,
                ped_motivo_cancelacion TEXT,
                ped_firma_cliente LONGBLOB,
                FOREIGN KEY (ped_id_cliente) REFERENCES usuarios(usu_id),
                FOREIGN KEY (ped_id_repartidor) REFERENCES usuarios(usu_id),
                FOREIGN KEY (ped_id_metodo_pago) REFERENCES metodos_pago(mp_id)
            )
            """,
            """
            CREATE TABLE detalle_pedido (
                dp_id INT AUTO_INCREMENT PRIMARY KEY,
                dp_id_pedido INT,
                dp_id_producto INT,
                dp_cantidad INT,
                dp_precio_unitario DECIMAL(10,2),
                dp_subtotal DECIMAL(10,2),
                FOREIGN KEY (dp_id_pedido) REFERENCES pedidos(ped_id),
                FOREIGN KEY (dp_id_producto) REFERENCES productos(prod_id)
            )
            """,
            """
            CREATE TABLE carrito (
                car_id INT AUTO_INCREMENT PRIMARY KEY,
                car_id_usuario INT,
                car_id_producto INT,
                car_cantidad INT,
                car_fecha_agregado DATETIME DEFAULT NOW(),
                FOREIGN KEY (car_id_usuario) REFERENCES usuarios(usu_id),
                FOREIGN KEY (car_id_producto) REFERENCES productos(prod_id)
            )
            """,
            """
            CREATE TABLE devoluciones (
                dev_id INT AUTO_INCREMENT PRIMARY KEY,
                dev_id_pedido INT,
                dev_id_producto INT,
                dev_cantidad INT,
                dev_motivo TEXT,
                dev_fecha DATETIME DEFAULT NOW(),
                FOREIGN KEY (dev_id_pedido) REFERENCES pedidos(ped_id),
                FOREIGN KEY (dev_id_producto) REFERENCES productos(prod_id)
            )
            """,
            """
            CREATE TABLE historial_pedidos (
                hp_id INT AUTO_INCREMENT PRIMARY KEY,
                hp_id_pedido INT,
                hp_estado_anterior VARCHAR(50),
                hp_estado_nuevo VARCHAR(50),
                hp_fecha_cambio DATETIME DEFAULT NOW(),
                hp_id_usuario INT,
                FOREIGN KEY (hp_id_pedido) REFERENCES pedidos(ped_id),
                FOREIGN KEY (hp_id_usuario) REFERENCES usuarios(usu_id)
            )
            """,
            """
            CREATE TABLE logs_sistema (
                log_id INT AUTO_INCREMENT PRIMARY KEY,
                log_accion TEXT,
                log_tabla_afectada VARCHAR(50),
                log_id_registro_afectado INT,
                log_id_usuario INT,
                log_fecha DATETIME DEFAULT NOW(),
                FOREIGN KEY (log_id_usuario) REFERENCES usuarios(usu_id)
            )
            """
        };

        for (String tabla : tablas) {
            stmt.executeUpdate(tabla);
        }
    }

    private static void insertarDatosIniciales(Statement stmt) throws SQLException {
        stmt.executeUpdate("""
            INSERT INTO usuarios (usu_nombre_usuario, usu_contraseña, usu_email, usu_rol)
            VALUES ('Admin', 'admin123', 'admin@sistema.com', 'Administrador')
        """);

        stmt.executeUpdate("""
            INSERT INTO metodos_pago (mp_nombre, mp_descripcion)
            VALUES ('Efectivo', 'Pago en efectivo al momento de la entrega'),
                   ('Tarjeta', 'Pago con tarjeta de crédito/débito'),
                   ('Transferencia', 'Transferencia bancaria')
        """);
    }

    private static void manejarErrorBD(SQLException e) {
        System.err.println("❌ Error en la base de datos: " + e.getMessage());
        JOptionPane.showMessageDialog(null,
                "Error crítico al inicializar la base de datos:\n" + e.getMessage(),
                "Error de BD",
                JOptionPane.ERROR_MESSAGE);
    }
}
