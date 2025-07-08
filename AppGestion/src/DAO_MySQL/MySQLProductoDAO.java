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
import DAO.DAOException;
import Entidades.Categoria;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class MySQLProductoDAO implements ProductoDAO {

    final String INSERT = "INSERT INTO productos (prod_nombre, prod_precio, prod_stock, prod_descripcion, prod_id_categoria) VALUES (?, ?, ?, ?, ?)";
    final String UPDATE = "UPDATE productos SET prod_nombre = ?, prod_precio = ?, prod_stock = ?, prod_descripcion = ?, prod_id_categoria = ? WHERE prod_id = ?";
    final String DELETE = "DELETE FROM productos WHERE prod_id = ?";
    final String GETALL = "SELECT * FROM productos";
    final String GETONE = "SELECT * FROM productos WHERE prod_id = ?";

    private final Connection conexion;

    public MySQLProductoDAO() throws SQLException {
        this.conexion = ConexionBD.conectar();
    }

    @Override
    public void insertar(Producto producto) throws DAOException {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setString(4, producto.getDescripcion());
            ps.setLong(5, producto.getCategoria().getId());

            if (ps.executeUpdate() == 0) {
                throw new DAOException("❌ No se insertaron filas al registrar el producto.");
            }

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                producto.setId(rs.getLong(1));
                System.out.println("✅ Producto insertado con ID: " + producto.getId());
            }

        } catch (SQLException e) {
            throw new DAOException("Error al insertar el producto: ", e);
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
    public void modificar(Producto producto) throws DAOException {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement(UPDATE);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getStock());
            ps.setString(4, producto.getDescripcion());
            ps.setLong(5, producto.getCategoria().getId());
            ps.setLong(6, producto.getId());

            if (ps.executeUpdate() == 0) {
                throw new DAOException("❌ No se modificaron filas al actualizar el producto.");
            }
            System.out.println("✅ Producto modificado.");
        } catch (SQLException e) {
            throw new DAOException("Error al modificar el producto: ", e);
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
    public void eliminar(Producto producto) throws DAOException {
        try (PreparedStatement ps = conexion.prepareStatement(DELETE)) {
            ps.setLong(1, producto.getId());
            if (ps.executeUpdate() == 0) {
                throw new DAOException("❌ No se eliminaron filas del producto.");
            }
            System.out.println("✅ Producto eliminado.");
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar el producto: ", e);
        }
    }

    @Override
    public List<Producto> obtenerTodos() throws DAOException {
        List<Producto> lista = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(GETALL); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {

                Categoria categoria = new Categoria();
                categoria.setId(rs.getLong("prod_id_categoria"));

                Producto p = new Producto(
                        rs.getLong("prod_id"),
                        rs.getString("prod_nombre"),
                        rs.getDouble("prod_precio"),
                        rs.getInt("prod_stock"),
                        rs.getString("prod_descripcion"),
                        categoria
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener los productos: ", e);
        }
        return lista;
    }

    @Override
    public Producto obtener(Long id) throws DAOException {
        try (PreparedStatement ps = conexion.prepareStatement(GETONE)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    Categoria categoria = new Categoria();
                    categoria.setId(rs.getLong("prod_id_categoria"));

                    return new Producto(
                            rs.getLong("prod_id"),
                            rs.getString("prod_nombre"),
                            rs.getDouble("prod_precio"),
                            rs.getInt("prod_stock"),
                            rs.getString("prod_descripcion"),
                            categoria
                    );
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Error al obtener el producto: ", e);
        }
        return null;
    }
}
