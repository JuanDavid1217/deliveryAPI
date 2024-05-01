/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.tienda;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterNuevo;
import org.uv.delivery.dtos.tienda.TiendaDTO;
import org.uv.delivery.models.Tienda;

/**
 *
 * @author juan
 */
@Component
public class TiendaActualizarConverter implements ConverterNuevo<Tienda, TiendaDTO>{

    @Override
    public Tienda dtotoEntity(TiendaDTO dto) {
        Tienda tienda = new Tienda();
        tienda.setCalificacion(dto.getCalificacion());
        tienda.setDescripcion(dto.getDescripcion());
        tienda.setEmail(dto.getEmail());
        tienda.setHorarios(dto.getHorarios());
        tienda.setNombre(dto.getNombre());
        tienda.setTelefono(dto.getTelefono());
        return tienda;
    }

    @Override
    public List<Tienda> dtoListtoEntityList(List<TiendaDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
