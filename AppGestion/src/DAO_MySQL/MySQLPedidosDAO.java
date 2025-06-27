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
import java.sql.*;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class MySQLPedidosDAO implements PedidoDAO {

    private final Connection conexion;

    public MySQLPedidosDAO() {
        this.conexion = ConexionBD.conectar();
    }

    @Override
    public void insertar(Pedido pedido) {
        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO pedidos (fecha, estado, id_usuario) VALUES (?, ?, ?)");
            ps.setTimestamp(1, new Timestamp(pedido.getFecha().getTime()));
            ps.setString(2, pedido.getEstado());
            ps.setLong(3, pedido.getUsuario().getId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pedido.setId(rs.getInt(1));
                }
            }
            System.out.println("✅ Producto insertado.");
        } catch (SQLException e) {
            System.out.println("Error al insertar el producto: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Pedido pedido) {
        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE pedidos SET estado = ? WHERE id = ?");
            ps.setString(1, pedido.getEstado());
            ps.setInt(2, pedido.getId());
            ps.executeUpdate();
            System.out.println("✅ Producto modificado.");
        } catch (SQLException e) {
            System.out.println("Error al modificar el producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Pedido pedido) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, pedido.getId());
            ps.executeUpdate();
            System.out.println("✅ Pedido eliminado.");
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar el pedido: " + e.getMessage());
        }
    }

    @Override
    public List<Pedido> obtenerTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT p.*, u.nombre_usuario FROM pedidos p JOIN usuarios u ON p.id_usuario = u.id";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getLong("id_usuario"),
                        rs.getString("nombre_usuario"),
                        null,
                        ""
                );
                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getTimestamp("fecha"),
                        rs.getString("estado"),
                        usuario,
                        new ArrayList<>()
                );
                lista.add(pedido);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener pedidos: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public Pedido obtener(Long id) {
        String sql = "SELECT p.*, u.nombre_usuario FROM pedidos p JOIN usuarios u ON p.id_usuario = u.id WHERE p.id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getLong("id_usuario"),
                            rs.getString("nombre_usuario"),
                            null,
                            ""
                    );

                    return new Pedido(
                            rs.getInt("id"),
                            rs.getTimestamp("fecha"),
                            rs.getString("estado"),
                            usuario,
                            new ArrayList<>()
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al obtener el pedido: " + e.getMessage());
        }
        return null;
    }

}
