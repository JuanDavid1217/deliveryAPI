/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.ventas;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterNuevo;
import org.uv.delivery.dtos.ventas.DetalleVentaNuevoDTO;
import org.uv.delivery.models.DetalleVenta;
import org.uv.delivery.models.Producto;
import org.uv.delivery.repository.ProductoRepository;

/**
 *
 * @author juan
 */
@Component
public class DetalleVentaNuevoConverter implements ConverterNuevo<DetalleVenta, DetalleVentaNuevoDTO>{
    
    private final ProductoRepository repository;
    
    public DetalleVentaNuevoConverter(ProductoRepository repository){
        this.repository = repository;
    }
    
    @Override
    public DetalleVenta dtotoEntity(DetalleVentaNuevoDTO dto) {
        DetalleVenta detalle = new DetalleVenta();
        detalle.setCantidad(dto.getCantidad());
        Producto producto = repository.getById(dto.getIdProducto());
        detalle.setProducto(producto);
        return detalle;
    }

    @Override
    public List<DetalleVenta> dtoListtoEntityList(List<DetalleVentaNuevoDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
