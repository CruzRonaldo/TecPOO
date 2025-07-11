/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_MySQL;

import DAO.PedidoDAO;
import Entidades.Pedido;
import Entidades.Usuario;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import Credenciales.ConexionBD;
import DAO.DAOException;
import java.sql.*;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class MySQLPedidosDAO implements PedidoDAO {

    private final Connection conexion;

    // Sentencias SQL actualizadas
    final String INSERT = "INSERT INTO pedidos (ped_fecha, ped_estado, ped_id_cliente) VALUES (?, ?, ?)";
    final String UPDATE = "UPDATE pedidos SET ped_estado = ? WHERE ped_id = ?";
    final String DELETE = "DELETE FROM pedidos WHERE ped_id = ?";
    final String GETALL = "SELECT p.*, u.usu_nombre_usuario FROM pedidos p JOIN usuarios u ON p.ped_id_cliente = u.usu_id";
    final String GETONE = "SELECT p.*, u.usu_nombre_usuario FROM pedidos p JOIN usuarios u ON p.ped_id_cliente = u.usu_id WHERE p.ped_id = ?";

    public MySQLPedidosDAO() throws SQLException {
        this.conexion = ConexionBD.conectar();
    }

    @Override
    public void insertar(Pedido pedido) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDate(1, new Date(pedido.getFecha().getTime()));
            ps.setString(2, pedido.getEstado());
            ps.setLong(3, pedido.getNombreUsuario().getId());

            if (ps.executeUpdate() == 0) {
                throw new DAOException("❌ No se insertaron filas al registrar el pedido.");
            }

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                long idGenerado = rs.getLong(1);
                pedido.setId(idGenerado);
                System.out.println("✅ Pedido insertado con ID: " + idGenerado);
            } else {
                throw new DAOException("⚠️ No se pudo obtener el ID generado del pedido.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error al insertar el pedido: ", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar recursos SQL en insertar.", ex);
            }
        }
    }

    @Override
    public void modificar(Pedido pedido) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(UPDATE);
            ps.setString(1, pedido.getEstado());
            ps.setLong(2, pedido.getId());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("Error al modificar el pedido: No se modificaron filas.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error al modificar el pedido: ", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar recursos SQL en modificar.", ex);
            }
        }
    }

    @Override
    public void eliminar(Pedido pedido) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(DELETE);
            ps.setLong(1, pedido.getId());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DAOException("Error al eliminar el pedido: No se eliminaron filas.");
            }
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar el pedido: ", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar recursos SQL en eliminar.", ex);
            }
        }
    }

    @Override
    public List<Pedido> obtenerTodos() throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Pedido> lista = new ArrayList<>();
        try {
            ps = conexion.prepareStatement(GETALL);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getLong("ped_id_cliente"),
                        rs.getString("usu_nombre_usuario"),
                        null,
                        ""
                );
                Pedido pedido = new Pedido(
                        rs.getInt("ped_id"),
                        rs.getTimestamp("ped_fecha"),
                        rs.getString("ped_estado"),
                        usuario,
                        new ArrayList<>()
                );
                lista.add(pedido);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los pedidos: ", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar recursos SQL en obtenerTodos.", ex);
            }
        }
        return lista;
    }

    @Override
    public Pedido obtenerPorID(Long id) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(GETONE);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getLong("ped_id_cliente"),
                        rs.getString("usu_nombre_usuario"),
                        null,
                        ""
                );
                return new Pedido(
                        rs.getInt("ped_id"),
                        rs.getTimestamp("ped_fecha"),
                        rs.getString("ped_estado"),
                        usuario,
                        new ArrayList<>()
                );
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener el pedido: ", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar recursos SQL en obtener.", ex);
            }
        }
        return null;
    }
}
