/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.dtos.TipoEstadoDTO;
import org.uv.delivery.models.EstadoPago;

/**
 *
 * @author juan
 */
@Component
public class EstadoPagoConverter implements ConverterNuevo<EstadoPago, TipoEstadoDTO>{

    @Override
    public EstadoPago dtotoEntity(TipoEstadoDTO dto) {
        EstadoPago estadoPago = new EstadoPago();
        estadoPago.setDescripcion(dto.getDescripcion());
        return estadoPago;
    }

    @Override
    public List<EstadoPago> dtoListtoEntityList(List<TipoEstadoDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
