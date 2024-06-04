/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.dtos.ventas;

import java.sql.Date;

/**
 *
 * @author juan
 */
public abstract class VentaBaseDTO {
    private String fechaCreacion;
    private long idCliente;
    private long idEstadoPago;
    private long idTipoPago;
    private long idEstadoPedido;
    private long idRepartidor;

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public long getIdEstadoPago() {
        return idEstadoPago;
    }

    public void setIdEstadoPago(long idEstadoPago) {
        this.idEstadoPago = idEstadoPago;
    }

    public long getIdTipoPago() {
        return idTipoPago;
    }

    public void setIdTipoPago(long idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public long getIdEstadoPedido() {
        return idEstadoPedido;
    }

    public void setIdEstadoPedido(long idEstadoPedido) {
        this.idEstadoPedido = idEstadoPedido;
    }

    public long getIdRepartidor() {
        return idRepartidor;
    }

    public void setIdRepartidor(long idRepartidor) {
        this.idRepartidor = idRepartidor;
    }
}
