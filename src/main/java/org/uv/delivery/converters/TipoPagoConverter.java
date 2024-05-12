/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.dtos.TipoEstadoDTO;
import org.uv.delivery.models.TipoPago;

/**
 *
 * @author juan
 */
@Component
public class TipoPagoConverter implements ConverterNuevo<TipoPago, TipoEstadoDTO>{

    @Override
    public TipoPago dtotoEntity(TipoEstadoDTO dto) {
        TipoPago tipoPago = new TipoPago();
        tipoPago.setDescripcion(dto.getDescripcion());
        return tipoPago;
    }

    @Override
    public List<TipoPago> dtoListtoEntityList(List<TipoEstadoDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
