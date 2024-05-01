/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.tienda;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterRegistrado;
import org.uv.delivery.dtos.tienda.TiendaRegistradaDTO;
import org.uv.delivery.models.Tienda;

/**
 *
 * @author juan
 */
@Component
public class TiendaRegistradaConverter implements ConverterRegistrado<Tienda, TiendaRegistradaDTO>{

    @Override
    public TiendaRegistradaDTO entitytoDTO(Tienda entity) {
        TiendaRegistradaDTO tienda = new TiendaRegistradaDTO();
        tienda.setCalificacion(entity.getCalificacion());
        tienda.setDescripcion(entity.getDescripcion());
        tienda.setDireccion(entity.getDireccion());
        tienda.setEmail(entity.getEmail());
        tienda.setHorarios(entity.getHorarios());
        tienda.setIdEncargado(entity.getEncargado().getId());
        tienda.setIdTienda(entity.getIdTienda());
        tienda.setNombre(entity.getNombre());
        tienda.setTelefono(entity.getTelefono());
        return tienda;
    }

    @Override
    public List<TiendaRegistradaDTO> entityListtoDTOList(List<Tienda> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
