/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_MySQL;

import DAO.UsuarioDAO;
import Entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import Credenciales.ConexionBD;
import DAO.DAOException;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class MySQLUsuarioDAO implements UsuarioDAO {

    final String INSERT = "INSERT INTO usuarios (usu_nombre_usuario, usu_contraseña,usu_email, usu_rol) VALUES (?, ?, ?, ?)";
    final String UPDATE = "UPDATE usuarios SET usu_nombre_usuario = ?, usu_contraseña = ?, usu_rol = ? WHERE usu_id = ?";
    final String DELETE = "DELETE FROM usuarios WHERE usu_id = ?";
    final String GETALL = "SELECT usu_id, usu_nombre_usuario, usu_contraseña, usu_rol FROM usuarios";
    final String GETONE = "SELECT usu_id, usu_nombre_usuario, usu_contraseña, usu_rol FROM usuarios WHERE usu_id = ?";

    private final Connection conexion;

    public MySQLUsuarioDAO() throws SQLException {
        this.conexion = ConexionBD.conectar();
    }

    @Override
    public void insertar(Usuario usuario) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContraseña());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getRol());
            if (ps.executeUpdate() == 0) {
                throw new DAOException("Error al insertar el usuario: no se afectaron filas.");
            }
            // Obtener el ID generado automáticamente
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long idGenerado = rs.getLong(1);
                usuario.setId(idGenerado); // Asigna el ID al objeto, si tienes un setter
                System.out.println("✅ Usuario insertado con ID: " + idGenerado);
            } else {
                throw new DAOException("No se pudo obtener el ID generado.");
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al insertar el usuario: ", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("Error cerrando recursos", ex);
            }
        }
    }

    @Override
    public void modificar(Usuario usuario) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(UPDATE);
            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getContraseña());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getRol());
            ps.setLong(5, usuario.getId());

            if (ps.executeUpdate() == 0) {
                throw new DAOException("Error al modificar el usuario: No se afectaron filas.");
            }
            System.out.println("✅ Usuario modificado.");
        } catch (SQLException ex) {
            throw new DAOException("Error al modificar el usuario: ", ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }
    }

    @Override
    public void eliminar(Usuario usuario) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(DELETE);
            ps.setLong(1, usuario.getId());

            if (ps.executeUpdate() == 0) {
                throw new DAOException("Error al eliminar el usuario: No se afectaron filas.");
            }
            System.out.println("✅ Usuario eliminado.");
        } catch (SQLException ex) {
            throw new DAOException("Error al eliminar el usuario: ", ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }
    }

    @Override
    public List<Usuario> obtenerTodos() throws DAOException {

        PreparedStatement ps = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            ps = conexion.prepareStatement(GETALL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getLong("usu_id"),
                        rs.getString("usu_nombre_usuario"),
                        rs.getString("usu_contraseña"),
                        rs.getString("usu_email"),
                        rs.getString("usu_rol")
                );
                usuarios.add(usuario);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al eliminar el usuario: ", ex);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }
        return usuarios;
    }

    @Override
    public Usuario obtenerPorID(Long id) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(GETONE);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getLong("usu_id"),
                        rs.getString("usu_nombre_usuario"),
                        rs.getString("usu_contraseña"),
                        rs.getString("usu_email"),
                        rs.getString("usu_rol")
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener el usuario: ", e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new DAOException("Error en SQL", ex);
                }
            }
        }
        return null;
    }

    @Override
    public Usuario ObtenerPorCorreo(String correo) throws DAOException {
        String sql = "SELECT * FROM usuarios WHERE usu_email = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getLong("usu_id"),
                        rs.getString("usu_nombre_usuario"),
                        rs.getString("usu_contraseña"),
                        rs.getString("usu_email"),
                        rs.getString("usu_rol")
                );
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al buscar usuario por correo", ex);
        }
        return null;
    }

    @Override
    public void actualizar(Usuario usuario) throws DAOException {
        String sql = "UPDATE usuarios SET usu_contraseña = ? WHERE usu_id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getContraseña());
            ps.setLong(2, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new DAOException("Error al actualizar la contraseña", ex);
        }
    }
}
