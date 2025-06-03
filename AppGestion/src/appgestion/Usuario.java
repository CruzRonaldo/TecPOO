package appgestion;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class Usuario {

    private String correo;
    private String nombreUsuario;
    private String contraseña;

    public Usuario(String correo, String nombreUsuario, String contraseña) {
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }
    
     @Override
    public String toString() {
        return "Usuario{" + "usuario='" + nombreUsuario + '\'' + ", correo='" + correo + '\'' + '}';
    }
}
