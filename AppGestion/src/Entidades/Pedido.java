/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.*;

/**
 *
 * @author ronal
 */
public class Pedido {

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the detalles
     */
    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    /**
     * @param detalles the detalles to set
     */
    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    private long id;
    private Date fecha;
    private String estado;
    private Usuario usuario;
    private List<DetallePedido> detalles;

    public Pedido(long id, Date fecha, String estado, Usuario usuario, List<DetallePedido> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.usuario = usuario;
        this.detalles = detalles;
    }

    public Pedido() {
    }

    public void agregarDetalle(DetallePedido detalle) {
        getDetalles().add(detalle);
    }

    public double calcularTotal() {
        double total = 0;
        for (DetallePedido detalle : getDetalles()) {
            total += detalle.getSubtotal();
        }
        return total;
    }

    public void cambiarEstado(String nuevoEstado) {
        this.setEstado(nuevoEstado);
    }
}
