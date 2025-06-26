/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ronal
 */
public class ConexionBD {

    static String url = "jdbc:mysql://localhost:3306/sistema_pedidos";
    static String user = "root";
    static String pass = "Gestion123";

    private static Connection conexion = null;

    public static Connection conectar() {
        try {
            if (conexion == null || conexion.isClosed()) {
            conexion = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Conexión exitosa a MySQL");
            }
        }catch(SQLException e) {
            System.out.println("❌ Error al conectar con MySQL: " + e.getMessage());
        }
        return conexion;
    }

    public static void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                conexion = null;
                System.out.println("✅ Conexión cerrada correctamente");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
