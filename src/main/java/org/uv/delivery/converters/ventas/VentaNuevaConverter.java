/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.ventas;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterNuevo;
import org.uv.delivery.dtos.ventas.VentaNuevaDTO;
import org.uv.delivery.models.DetalleVenta;
import org.uv.delivery.models.EstadoPago;
import org.uv.delivery.models.EstadoPedido;
import org.uv.delivery.models.TipoPago;
import org.uv.delivery.models.Venta;
import org.uv.delivery.models.usuario.Cliente;
import org.uv.delivery.models.usuario.Repartidor;
import static org.uv.delivery.validations.Validation.stringtoDate;

/**
 *
 * @author juan
 */
@Component
public class VentaNuevaConverter implements ConverterNuevo<Venta, VentaNuevaDTO>{
    private final DetalleVentaNuevoConverter detalleConverter;
    
    public VentaNuevaConverter(DetalleVentaNuevoConverter detalleConverter){
        this.detalleConverter = detalleConverter;
    }
    
    @Override
    public Venta dtotoEntity(VentaNuevaDTO dto) {
        Venta venta = new Venta();
        Cliente cliente = new Cliente();//Revisar si solo los clientes pueden comprar
        cliente.setId(dto.getIdCliente());
        venta.setCliente(cliente);
        
        List<DetalleVenta> detalles = detalleConverter.dtoListtoEntityList(dto.getDetalles());
        venta.setDetalles(detalles);
        
        EstadoPago estadoPago = new EstadoPago();
        estadoPago.setIdEstado(dto.getIdEstadoPago());
        venta.setEstadoPago(estadoPago);
        
        EstadoPedido estadoPedido = new EstadoPedido();
        estadoPedido.setIdEstado(dto.getIdEstadoPedido());
        venta.setEstadoPedido(estadoPedido);
        
        venta.setFechaCreacion(new java.sql.Date(stringtoDate(dto.getFechaCreacion()).getTime()));
        
        Repartidor repartidor = new Repartidor();
        repartidor.setId(dto.getIdRepartidor());
        venta.setRepartidor(repartidor);
        
        TipoPago tipoPago = new TipoPago();
        tipoPago.setIdTipo(dto.getIdTipoPago());
        venta.setTipoPago(tipoPago);
        
        venta.setTotal(BigDecimal.ONE);
        return venta;
    }

    @Override
    public List<Venta> dtoListtoEntityList(List<VentaNuevaDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
