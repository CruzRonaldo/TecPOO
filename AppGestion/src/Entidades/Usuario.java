/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class Usuario {

    private long id;
    private String usuario;
    private String contraseña;
    private String rol;

    public Usuario(long id, String usuario, String contraseña, String rol) {
        this.id = id;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    public boolean login(String usuario, String contrasena) {
        return this.usuario.equals(usuario) && this.contraseña.equals(contraseña);
    }

    public void logout() {
        System.out.println("Usuario " + usuario + " ha cerrado sesión");
    }

    public long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getRol() {
        return rol;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setId(long id) {
        this.id = id;
    }
}
