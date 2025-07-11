/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_MySQL;

import Credenciales.ConexionBD;
import DAO.DAOException;
import DAO.DetallePedidoDAO;
import Entidades.DetallePedido;
import Entidades.Pedido;
import Entidades.Producto;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class MySQLDetallePedidosDAO implements DetallePedidoDAO {

    final String INSERT = "INSERT INTO detalle_pedido (dp_id_pedido, dp_id_producto, dp_cantidad, dp_precio_unitario, dp_subtotal) VALUES (?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE detalle_pedido SET dp_id_pedido = ?, dp_id_producto = ?, dp_cantidad = ?, dp_precio_unitario = ?, dp_subtotal = ? WHERE dp_id = ?";
    final String DELETE = "DELETE FROM detalle_pedido WHERE dp_id = ?";
    final String GETALL = "SELECT dp_id, dp_id_pedido, dp_id_producto, dp_cantidad, dp_precio_unitario, dp_subtotal FROM detalle_pedido";
    final String GETONE = "SELECT dp_id, dp_id_pedido, dp_id_producto, dp_cantidad, dp_precio_unitario, dp_subtotal FROM detalle_pedido WHERE dp_id = ?";

    private final Connection conexion;

    public MySQLDetallePedidosDAO() throws SQLException {
        this.conexion = ConexionBD.conectar();
    }

    @Override
    public void insertar(DetallePedido detalle) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setLong(1, detalle.getPedido().getId());
            ps.setLong(2, detalle.getProducto().getId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario()); // Faltaba en tu código anterior
            ps.setDouble(5, detalle.getSubtotal());

            if (ps.executeUpdate() == 0) {
                throw new DAOException("❌ No se insertaron filas en detalle_pedido.");
            }

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                detalle.setId(rs.getLong(1));
                System.out.println("✅ DetallePedido insertado con ID: " + rs.getLong(1));
            } else {
                throw new DAOException("⚠️ No se pudo obtener el ID generado del detalle.");
            }

        } catch (SQLException e) {
            throw new DAOException("Error al insertar el detalle de pedido: ", e);
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
    public void modificar(DetallePedido detalle) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(UPDATE);
            ps.setLong(1, detalle.getPedido().getId());
            ps.setLong(2, detalle.getProducto().getId());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getSubtotal());
            ps.setLong(6, detalle.getId());

            if (ps.executeUpdate() == 0) {
                throw new DAOException("❌ No se modificó ningún detalle de pedido.");
            }

        } catch (SQLException ex) {
            throw new DAOException("Error al modificar detalle de pedido.", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar PreparedStatement.", ex);
            }
        }
    }

    @Override
    public void eliminar(DetallePedido detalle) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(DELETE);
            ps.setLong(1, detalle.getId());
            if (ps.executeUpdate() == 0) {
                throw new DAOException("❌ No se eliminó ningún detalle de pedido.");
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al eliminar detalle de pedido.", ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar PreparedStatement.", ex);
            }
        }
    }

    @Override
    public List<DetallePedido> obtenerTodos() throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<DetallePedido> detalles = new ArrayList<>();
        try {
            ps = conexion.prepareStatement(GETALL);
            rs = ps.executeQuery();
            while (rs.next()) {

                Pedido pedido = new Pedido();
                pedido.setId(rs.getLong("dp_id_pedido"));

                Producto producto = new Producto();
                producto.setId(rs.getLong("dp_id_producto"));

                DetallePedido detalle = new DetallePedido(
                        rs.getInt("dp_id"),
                        pedido,
                        producto,
                        rs.getInt("dp_cantidad"),
                        rs.getDouble("dp_precio_unitario"),
                        rs.getDouble("dp_subtotal")
                );

                detalles.add(detalle);
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al obtener los detalles de pedido.", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar recursos SQL.", ex);
            }
        }
        return detalles;
    }

    @Override
    public DetallePedido obtenerPorID(Long id) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement(GETONE);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {

                Pedido pedido = new Pedido();
                pedido.setId(rs.getLong("dp_id_pedido"));

                Producto producto = new Producto();
                producto.setId(rs.getLong("dp_id_producto"));

                return new DetallePedido(
                        rs.getInt("dp_id"),
                        pedido,
                        producto,
                        rs.getInt("dp_cantidad"),
                        rs.getDouble("dp_precio_unitario"),
                        rs.getDouble("dp_subtotal")
                );
            }
        } catch (SQLException ex) {
            throw new DAOException("Error al obtener el detalle de pedido.", ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar recursos SQL.", ex);
            }
        }
        return null;
    }
}
