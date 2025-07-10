/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Credenciales.DatabaseManager;
import Credenciales.ConexionBD;
import DAO.DAOException;
import DAO_MySQL.MySQLUsuarioDAO;
import Entidades.Usuario;
import java.sql.SQLException;

/**
 *
 * @author ronal
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)  {

         try {
            // Verifica y crea la base de datos si no existe
            DatabaseManager.verificarOCrearBD();

            // Crear instancia del DAO
            MySQLUsuarioDAO usuarioDAO = new MySQLUsuarioDAO();

            // Crear un nuevo usuario (ID será generado automáticamente)
            Usuario nuevoUsuario = new Usuario(
                0,                  // id autogenerado
                "usuario_prueba",  // nombre de usuario
                "contraseña123",   // contraseña
                "Cliente"          // rol
            );

            // Insertar usuario
            usuarioDAO.insertar(nuevoUsuario);

            // Confirmar ID generado (si el DAO lo establece)
            System.out.println("ID del usuario insertado: " + nuevoUsuario.getId());

        } catch (DAOException | SQLException e) {
            System.err.println("❌ Error: " + e.getMessage());
        } finally {
            // Cerrar la conexión a la base de datos
            ConexionBD.cerrarConexion();
        }
    }
}
