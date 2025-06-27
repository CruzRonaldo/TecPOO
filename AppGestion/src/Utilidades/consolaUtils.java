/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.util.Scanner;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public class consolaUtils {

    public int leerValor(String Mensaje) {
        Scanner sc = new Scanner(System.in);
        System.out.println(Mensaje);
        return sc.nextInt();
    }
}
