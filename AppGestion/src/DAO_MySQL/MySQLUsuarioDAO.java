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
import MySQL.ConexionBD;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class MySQLUsuarioDAO implements UsuarioDAO {

    private Connection conexion;

    public MySQLUsuarioDAO() {
        this.conexion = ConexionBD.conectar();
    }

    @Override
    public void insertar(Usuario usuario) {
        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO usuarios (usuario, contrasena, rol) VALUES (?, ?, ?)");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getRol());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Usuario usuario) {
        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE usuarios SET usuario = ?, contrasena = ?, rol = ? WHERE id = ?");
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getContrasena());
            ps.setString(3, usuario.getRol());
            ps.setLong(4, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Usuario usuario) {
        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM usuarios WHERE id = ?");
            ps.setLong(1, usuario.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getLong("id"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol")
                );
                lista.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los usuarios: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Usuario obtener(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void cerrarConexion() {
        ConexionBD.cerrarConexion();
    }
}
