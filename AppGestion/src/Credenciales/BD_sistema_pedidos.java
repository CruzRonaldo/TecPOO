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

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class BD_sistema_pedidos {

    private static final String DB_NAME = "sistema_pedidos";

    public static void verificarOCrearBD() {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/", UserPass.User, UserPass.Pass); Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery("SHOW DATABASES LIKE '" + DB_NAME + "'");
            if (!rs.next()) {
                System.out.println("⚠️ La base de datos no existe. Creándola...");

                // Crear la base de datos
                stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
                stmt.execute("USE " + DB_NAME);

                // Crear tablas
                stmt.executeUpdate("""
                    CREATE TABLE usuarios (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre_usuario VARCHAR(50),
                        contraseña VARCHAR(100),
                        rol VARCHAR(20)
                    );
                """);

                stmt.executeUpdate("""
                    CREATE TABLE productos (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(100),
                        precio DECIMAL(8,2),
                        stock INT,
                        descripcion TEXT
                    );
                """);

                stmt.executeUpdate("""
                    CREATE TABLE pedidos (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        fecha DATETIME DEFAULT NOW(),
                        estado VARCHAR(50),
                        id_usuario INT,
                        FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
                    );
                """);

                stmt.executeUpdate("""
                    CREATE TABLE detalle_pedido (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        id_pedido INT,
                        id_producto INT,
                        cantidad INT,
                        subtotal DECIMAL(8,2),
                        FOREIGN KEY (id_pedido) REFERENCES pedidos(id),
                        FOREIGN KEY (id_producto) REFERENCES productos(id)
                    );
                """);

                stmt.executeUpdate("""
                    INSERT INTO usuarios (nombre_usuario, contraseña, rol)
                    VALUES ('Admin', 'admin123', 'Administrador');
                    """);

                System.out.println("✅ Base de datos y tablas creadas correctamente.");

            } else {
                System.out.println("✅ La base de datos ya existe.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al verificar o crear la BD: " + e.getMessage());
        }
    }
}
