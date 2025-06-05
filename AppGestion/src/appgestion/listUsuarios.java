/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appgestion;
import java.util.ArrayList;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class listUsuarios {

    private ArrayList<Usuario> usuarios;

    public listUsuarios() {
        usuarios = new ArrayList<>();
    }

    public void registrarUsuario(String correo, String nombre, String pass1, String pass2) {
        if (!pass1.equals(pass2)) {
            System.out.println("⚠️ Las contraseñas no coinciden.");
            return;
        }

        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo) || u.getNombreUsuario().equalsIgnoreCase(nombre)) {
                System.out.println("⚠️ Ya existe un usuario con ese correo o nombre.");
                return;
            }
        }

        usuarios.add(new registrarUsuario(correo, nombre, pass1));
        System.out.println("✅ Usuario registrado con éxito.");
    }

    public void iniciarSesion(String login, String password) {
        for (Usuario u : usuarios) {
            if ((u.getCorreo().equalsIgnoreCase(login) || u.getNombreUsuario().equalsIgnoreCase(login))
                    && u.getContraseña().equals(password)) {
                System.out.println("✅ Inicio de sesión exitoso.");
                return;
            }
        }
        System.out.println("❌ Usuario o contraseña incorrectos.");
    }

    public void mostrarUsuarios() {
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }
}
