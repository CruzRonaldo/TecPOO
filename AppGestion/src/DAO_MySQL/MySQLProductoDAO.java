/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO_MySQL;

import Entidades.Producto;
import DAO.ProductoDAO;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import Credenciales.ConexionBD;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class MySQLProductoDAO implements ProductoDAO {

    private final Connection conexion;

    public MySQLProductoDAO() {
        this.conexion = ConexionBD.conectar();
    }

    @Override
    public void insertar(Producto producto) {
        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO productos (nombre, precio, stock, descripcion) VALUES (?, ?, ?, ?)");
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setString(4, producto.getDescripcion());
            ps.executeUpdate();
            System.out.println("✅ Producto insertado.");
        } catch (SQLException e) {
            System.out.println("Error al insertar el producto: " + e.getMessage());
        }
    }

    @Override
    public void modificar(Producto producto) {
        try {
            PreparedStatement ps = conexion.prepareStatement("UPDATE productos SET nombre = ?, precio = ?, stock = ?, descripcion = ? WHERE id = ?");
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setString(4, producto.getDescripcion());
            ps.setLong(5, producto.getId());
            ps.executeUpdate();
            System.out.println("✅ Producto modificado.");
        } catch (SQLException e) {
            System.out.println("Error al modificar el producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Producto producto) {
        try {
            PreparedStatement ps = conexion.prepareStatement("DELETE FROM productos WHERE id = ?");
            ps.setLong(1, producto.getId());
            ps.executeUpdate();
            System.out.println("✅ Producto eliminado.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("stock"),
                        rs.getString("descripcion")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los productos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Producto obtener(Long id) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                            rs.getLong("id"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getInt("stock"),
                            rs.getString("descripcion")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el producto: " + e.getMessage());
        }
        return null;
    }

}
