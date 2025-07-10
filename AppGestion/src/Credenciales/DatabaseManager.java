/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Credenciales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class DatabaseManager {

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

            TableManager.crearTablas(stmt);

            InsertManager.insertarDatosIniciales(stmt);

            System.out.println("Base de datos y tablas creadas correctamente.");
        }

    }

    private static void manejarErrorBD(SQLException e) {
        System.err.println("❌ Error en la base de datos: " + e.getMessage());
        JOptionPane.showMessageDialog(null,
                "Error crítico al inicializar la base de datos:\n" + e.getMessage(),
                "Error de BD",
                JOptionPane.ERROR_MESSAGE);
    }

    public static boolean verificarCredenciales(String correo, String contraseña) {
        boolean accesoConcedido = false;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexionBD.conectar();
            stmt = conn.createStatement();

            String sql = String.format(
                    "SELECT * FROM usuarios WHERE usu_email = '%s' AND usu_contraseña = '%s'",
                    correo.replace("'", "''"), // protección mínima contra inyección
                    contraseña.replace("'", "''")
            );

            rs = stmt.executeQuery(sql);

            if (rs.next()) {
                accesoConcedido = true;
            }

        } catch (SQLException e) {
            manejarErrorBD(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
            }
            ConexionBD.cerrarConexion();
        }

        return accesoConcedido;
    }

}