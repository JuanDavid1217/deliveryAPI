/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.ventas;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterRegistrado;
import org.uv.delivery.dtos.ventas.DetalleVentaRegistradoDTO;
import org.uv.delivery.models.DetalleVenta;

/**
 *
 * @author juan
 */
@Component
public class DetalleVentaRegistradoConverter implements ConverterRegistrado<DetalleVenta, DetalleVentaRegistradoDTO>{

    @Override
    public DetalleVentaRegistradoDTO entitytoDTO(DetalleVenta entity) {
        DetalleVentaRegistradoDTO detalle = new DetalleVentaRegistradoDTO();
        detalle.setCantidad(entity.getCantidad());
        detalle.setDescripcion(entity.getProducto().getDescripcion());
        detalle.setDescuento(entity.getDecuento());
        detalle.setIdDetalleVenta(entity.getIdDetalle());
        detalle.setIdProducto(entity.getProducto().getIdProducto());
        detalle.setIdVenta(entity.getVenta().getIdVenta());
        detalle.setImagen(entity.getProducto().getImagen());
        detalle.setNombre(entity.getProducto().getNombre());
        detalle.setPrecio(entity.getPrecio());
        return detalle;
    }

    @Override
    public List<DetalleVentaRegistradoDTO> entityListtoDTOList(List<DetalleVenta> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
