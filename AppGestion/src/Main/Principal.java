/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import MySQL.ConexionBD;
import Ventanas.PantallaInicio;

/**
 *
 * @author ronal
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if (ConexionBD.conectar() != null) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                new PantallaInicio().setVisible(true);
            });
        } else {
            System.out.println("Error al conectar a la base de datos");
        }
    }
}
