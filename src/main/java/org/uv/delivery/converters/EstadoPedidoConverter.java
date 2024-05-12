/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.dtos.TipoEstadoDTO;
import org.uv.delivery.models.EstadoPedido;

/**
 *
 * @author juan
 */
@Component
public class EstadoPedidoConverter implements ConverterNuevo<EstadoPedido, TipoEstadoDTO>{

    @Override
    public EstadoPedido dtotoEntity(TipoEstadoDTO dto) {
        EstadoPedido estadoPedido = new EstadoPedido();
        estadoPedido.setDescripcion(dto.getDescripcion());
        return estadoPedido;
    }

    @Override
    public List<EstadoPedido> dtoListtoEntityList(List<TipoEstadoDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
