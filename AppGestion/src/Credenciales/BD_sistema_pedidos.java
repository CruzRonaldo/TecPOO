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
        // Tablas principales
        String[] tablas = {
            """
        CREATE TABLE usuarios (
            id INT AUTO_INCREMENT PRIMARY KEY,
            nombre_usuario VARCHAR(50) UNIQUE,
            contraseña VARCHAR(100),
            email VARCHAR(100),
            telefono VARCHAR(20),
            direccion TEXT,
            rol VARCHAR(20) CHECK (rol IN ('Administrador', 'Vendedor', 'Cliente', 'Repartidor')),
            puntos INT DEFAULT 0,
            preferencias TEXT,
            fecha_registro DATETIME DEFAULT NOW()
        )
        """,
            """
        CREATE TABLE categorias (
            id INT AUTO_INCREMENT PRIMARY KEY,
            nombre VARCHAR(50) UNIQUE,
            descripcion TEXT
        )
        """,
            """
        CREATE TABLE productos (
            id INT AUTO_INCREMENT PRIMARY KEY,
            nombre VARCHAR(100) UNIQUE,
            precio DECIMAL(10,2),
            stock INT,
            stock_minimo INT,
            descripcion TEXT,
            id_categoria INT,
            imagen LONGBLOB,
            FOREIGN KEY (id_categoria) REFERENCES categorias(id)
        )
        """,
            """
        CREATE TABLE metodos_pago (
            id INT AUTO_INCREMENT PRIMARY KEY,
            nombre VARCHAR(50) UNIQUE,
            descripcion TEXT
        )
        """,
            """
        CREATE TABLE pedidos (
            id INT AUTO_INCREMENT PRIMARY KEY,
            codigo_pedido VARCHAR(20) UNIQUE,
            fecha DATETIME DEFAULT NOW(),
            estado VARCHAR(50) CHECK (estado IN ('Pendiente', 'Preparación', 'En camino', 'Entregado', 'Cancelado')),
            id_cliente INT,
            id_repartidor INT,
            id_metodo_pago INT,
            direccion_entrega TEXT,
            es_urgente BOOLEAN DEFAULT FALSE,
            motivo_cancelacion TEXT,
            firma_cliente LONGBLOB,
            FOREIGN KEY (id_cliente) REFERENCES usuarios(id),
            FOREIGN KEY (id_repartidor) REFERENCES usuarios(id),
            FOREIGN KEY (id_metodo_pago) REFERENCES metodos_pago(id)
        )
        """,
            """
        CREATE TABLE detalle_pedido (
            id INT AUTO_INCREMENT PRIMARY KEY,
            id_pedido INT,
            id_producto INT,
            cantidad INT,
            precio_unitario DECIMAL(10,2),
            subtotal DECIMAL(10,2),
            FOREIGN KEY (id_pedido) REFERENCES pedidos(id),
            FOREIGN KEY (id_producto) REFERENCES productos(id)
        )
        """,
            """
        CREATE TABLE carrito (
            id INT AUTO_INCREMENT PRIMARY KEY,
            id_usuario INT,
            id_producto INT,
            cantidad INT,
            fecha_agregado DATETIME DEFAULT NOW(),
            FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
            FOREIGN KEY (id_producto) REFERENCES productos(id)
        )
        """,
            """
        CREATE TABLE devoluciones (
            id INT AUTO_INCREMENT PRIMARY KEY,
            id_pedido INT,
            id_producto INT,
            cantidad INT,
            motivo TEXT,
            fecha DATETIME DEFAULT NOW(),
            FOREIGN KEY (id_pedido) REFERENCES pedidos(id),
            FOREIGN KEY (id_producto) REFERENCES productos(id)
        )
        """,
            """
        CREATE TABLE historial_pedidos (
            id INT AUTO_INCREMENT PRIMARY KEY,
            id_pedido INT,
            estado_anterior VARCHAR(50),
            estado_nuevo VARCHAR(50),
            fecha_cambio DATETIME DEFAULT NOW(),
            id_usuario INT,
            FOREIGN KEY (id_pedido) REFERENCES pedidos(id),
            FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
        )
        """,
            """
        CREATE TABLE logs_sistema (
            id INT AUTO_INCREMENT PRIMARY KEY,
            accion TEXT,
            tabla_afectada VARCHAR(50),
            id_registro_afectado INT,
            id_usuario INT,
            fecha DATETIME DEFAULT NOW(),
            FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
        )
        """
        };

        for (String tabla : tablas) {
            stmt.executeUpdate(tabla);
        }
    }

    private static void insertarDatosIniciales(Statement stmt) throws SQLException {
        stmt.executeUpdate("""
            INSERT INTO usuarios (nombre_usuario, contraseña, email, rol)
            VALUES ('Admin', 'admin123', 'admin@sistema.com', 'Administrador')
        """);

        stmt.executeUpdate("""
            INSERT INTO metodos_pago (nombre, descripcion)
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
