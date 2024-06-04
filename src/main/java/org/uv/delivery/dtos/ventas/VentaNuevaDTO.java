/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.dtos.ventas;

import java.util.List;

/**
 *
 * @author juan
 */
public class VentaNuevaDTO extends VentaBaseDTO{
    private List<DetalleVentaNuevoDTO> detalles;

    public List<DetalleVentaNuevoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaNuevoDTO> detalles) {
        this.detalles = detalles;
    }
    
}
