package appgestion;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class VentanaPrincipal extends JFrame {

    private listUsuarios listaUsuarios;
    private boolean sesionIniciada = false;

    public VentanaPrincipal() {
        listaUsuarios = new listUsuarios();

        setTitle("Gestión de Usuarios");
        setSize(480, 854);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear botones
        JButton btnRegistrar = new JButton("Registrar Usuario");
        JButton btnIniciarSesion = new JButton("Iniciar Sesión");
        JButton btnIniciarApp = new JButton("Iniciar Aplicación");
        btnIniciarApp.setEnabled(false); // deshabilitado por defecto

        // Acciones
        btnRegistrar.addActionListener(e -> registrarUsuario());
        btnIniciarSesion.addActionListener(e -> {
            if (iniciarSesion()) {
                sesionIniciada = true;
                btnIniciarApp.setEnabled(true);
            }
        });
        btnIniciarApp.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "✅ Aplicación iniciada correctamente.");
        });

        // Layout
        setLayout(new GridLayout(4, 1, 10, 10));
        add(btnRegistrar);
        add(btnIniciarSesion);
        add(btnIniciarApp);
    }
    public VentanaPrincipal() {
        initComponents();
        listaUsuarios = new listUsuarios();
    }
}
