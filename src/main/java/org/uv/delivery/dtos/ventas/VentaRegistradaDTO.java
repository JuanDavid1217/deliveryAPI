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
public class VentaRegistradaDTO extends VentaBaseDTO{
    private long idVenta;
    private List<DetalleVentaRegistradoDTO> detalles;

    public long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
    }

    public List<DetalleVentaRegistradoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaRegistradoDTO> detalles) {
        this.detalles = detalles;
    }
    
    
}
