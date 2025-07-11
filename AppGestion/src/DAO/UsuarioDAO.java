/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entidades.Usuario;

/**
 *
 * @author Ronaldo Cruz Alvarez
 */
public interface UsuarioDAO extends DAO<Usuario, Long> {
    
    public Usuario ObtenerPorCorreo(String correo) throws DAOException;
    
    public void actualizar(Usuario usuario) throws DAOException;
}
