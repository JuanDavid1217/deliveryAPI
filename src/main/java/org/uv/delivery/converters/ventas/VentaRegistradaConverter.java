/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.ventas;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterRegistrado;
import org.uv.delivery.dtos.ventas.DetalleVentaRegistradoDTO;
import org.uv.delivery.dtos.ventas.VentaRegistradaDTO;
import org.uv.delivery.models.Venta;
import static org.uv.delivery.validations.Validation.datetoSring;

/**
 *
 * @author juan
 */
@Component
public class VentaRegistradaConverter implements ConverterRegistrado<Venta, VentaRegistradaDTO>{
    
    private final DetalleVentaRegistradoConverter detalleConverter;
    
    public VentaRegistradaConverter(DetalleVentaRegistradoConverter detalleConverter){
        this.detalleConverter = detalleConverter;
    }
    
    @Override
    public VentaRegistradaDTO entitytoDTO(Venta entity) {
        VentaRegistradaDTO venta = new VentaRegistradaDTO();
        
        List<DetalleVentaRegistradoDTO> detalles = detalleConverter.entityListtoDTOList(entity.getDetalles());
        venta.setDetalles(detalles);
        
        venta.setFechaCreacion(datetoSring(entity.getFechaCreacion()));
        venta.setIdCliente(entity.getCliente().getId());
        venta.setIdEstadoPago(entity.getEstadoPago().getIdEstado());
        venta.setIdEstadoPedido(entity.getEstadoPedido().getIdEstado());
        venta.setIdRepartidor(entity.getRepartidor().getId());
        venta.setIdTipoPago(entity.getTipoPago().getIdTipo());
        venta.setIdVenta(entity.getIdVenta());
        return venta;
    }

    @Override
    public List<VentaRegistradaDTO> entityListtoDTOList(List<Venta> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
