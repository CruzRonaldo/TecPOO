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

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class AñadirProducto {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
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

        System.out.println("\n=== Productos en BD ===");
        List<Producto> lista = dao.obtenerTodos();
        for (Producto pr : lista) {
            System.out.printf("%d | %s | S/%.2f | stock: %d | %s%n",
                    pr.getId(), pr.getNombre(), pr.getPrecio(), pr.getStock(), pr.getDescripcion());
        }

        ConexionBD.cerrarConexion();
    }

}
