/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.List;

/**
 *
 * @author Ronaldo Cruz Alvarez
 * @param <T>
 * @param <K>
 */
public interface DAO<T, K> {

    void insertar(T a);

    void modificar(T a);

    void eliminar(T a);

    List<T> obtenerTodos();

    T obtener(K id);
}
