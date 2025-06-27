/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author ronal
 */
public class Producto {

    private long id;
    private String nombre;
    private double precio;
    private int stock;
    private String descripcion;

    public Producto(long id, String nombre, double precio, int stock, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.descripcion = descripcion;
    }

    public void actualizarStock(int cantidad) {
        this.stock -= cantidad;
    }

    public double obtenerPrecio() {
        return precio;
    }

    // Getters y setters
    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
