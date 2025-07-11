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
     * @return the NombreUsuario
     */
    public Usuario getNombreUsuario() {
        return NombreUsuario;
    }

    /**
     * @param NombreUsuario the NombreUsuario to set
     */
    public void setNombreUsuario(Usuario NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

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
    private Usuario NombreUsuario;
    private List<DetallePedido> detalles;
    
    

    public Pedido(long id, Date fecha, String estado, Usuario NombreUsuario, List<DetallePedido> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.estado = estado;
        this.NombreUsuario = NombreUsuario;
        this.detalles = detalles;
    }

    public Pedido() {
    }
}
