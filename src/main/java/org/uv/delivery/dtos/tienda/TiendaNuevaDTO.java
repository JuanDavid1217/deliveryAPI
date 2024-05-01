/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.dtos.tienda;

import org.uv.delivery.dtos.DireccionNuevaDTO;


/**
 *
 * @author juan
 */
public class TiendaNuevaDTO extends TiendaDTO{
    private DireccionNuevaDTO direccion;

    public DireccionNuevaDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionNuevaDTO direccion) {
        this.direccion = direccion;
    }
       
}
