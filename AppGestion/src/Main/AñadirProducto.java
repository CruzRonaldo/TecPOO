/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import DAO_MySQL.MySQLProductoDAO;
import Entidades.Producto;
import java.util.List;
import java.util.Scanner;
import Credenciales.ConexionBD;
import DAO.DAOException;
import java.sql.SQLException;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class AñadirProducto {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            MySQLProductoDAO dao = new MySQLProductoDAO();

            System.out.println("=== Registro de Producto ===");
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Precio: ");
            double precio = sc.nextDouble();

            System.out.print("Stock: ");
            int stock = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            System.out.print("Descripción: ");
            String descripcion = sc.nextLine();

            Producto p = new Producto(0, nombre, precio, stock, descripcion);
            dao.insertar(p);

            System.out.println("\n✅ Producto registrado exitosamente.");
            System.out.println("\n=== Productos en BD ===");
            List<Producto> lista = dao.obtenerTodos();
            for (Producto pr : lista) {
                System.out.printf("%d | %s | S/%.2f | stock: %d | %s%n",
                        pr.getId(), pr.getNombre(), pr.getPrecio(), pr.getStock(), pr.getDescripcion());
            }

        } catch (DAOException e) {
            System.err.println("❌ Error de DAO: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("❌ Error de conexión SQL: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Error inesperado: " + e.getMessage());
        } finally {
            ConexionBD.cerrarConexion();
        }
    }

}
