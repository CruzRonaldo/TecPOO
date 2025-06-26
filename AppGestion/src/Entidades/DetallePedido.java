/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author ronal
 */
public class DetallePedido {

    private int id;
    private int cantidad;
    private double subtotal;
    private Producto producto;

    public DetallePedido(int id, int cantidad, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.producto = producto;
        this.subtotal = calcularSubtotal();
    }

    public double calcularSubtotal() {
        return cantidad * producto.obtenerPrecio();
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

}
