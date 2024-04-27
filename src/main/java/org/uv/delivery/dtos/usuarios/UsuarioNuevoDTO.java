/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.dtos.usuarios;

import org.uv.delivery.dtos.DireccionNuevaDTO;


/**
 *
 * @author juan
 */
public abstract class UsuarioNuevoDTO extends UsuarioDTO{
    private long idGenero;
    private DireccionNuevaDTO direccion;
    private String password;

    public long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(long idGenero) {
        this.idGenero = idGenero;
    }

    public DireccionNuevaDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionNuevaDTO direccion) {
        this.direccion = direccion;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
