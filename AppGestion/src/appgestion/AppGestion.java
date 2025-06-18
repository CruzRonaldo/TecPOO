
package appgestion;

/**
 *
 * @Grupo 2
 */
public class AppGestion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        listUsuarios lista = new listUsuarios();

        lista.registrarUsuario("juan@mail.com", "juan123", "pass", "pass");
        lista.registrarUsuario("ana@mail.com", "ana456", "clave", "clave");

        lista.iniciarSesion("juan123", "pass");
        lista.iniciarSesion("ana@mail.com", "clave");

        lista.iniciarSesion("juan@mail.com", "error");

        lista.mostrarUsuarios();


        System.out.println("Hola Cursor");
    }

}
