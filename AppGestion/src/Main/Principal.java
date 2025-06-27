/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Credenciales.BD_sistema_pedidos;
import Credenciales.ConexionBD;

/**
 *
 * @author ronal
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BD_sistema_pedidos.verificarOCrearBD();
        ConexionBD.cerrarConexion();
    }
}
