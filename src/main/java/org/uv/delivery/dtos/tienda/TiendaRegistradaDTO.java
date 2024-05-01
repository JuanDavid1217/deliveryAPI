/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.dtos.tienda;

import org.uv.delivery.models.Direccion;

/**
 *
 * @author juan
 */
public class TiendaRegistradaDTO extends TiendaDTO{
    private long idTienda;
    private long idEncargado;
    private Direccion direccion;

    public long getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(long idTienda) {
        this.idTienda = idTienda;
    }

    public long getIdEncargado() {
        return idEncargado;
    }

    public void setIdEncargado(long idEncargado) {
        this.idEncargado = idEncargado;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
    
    
}
