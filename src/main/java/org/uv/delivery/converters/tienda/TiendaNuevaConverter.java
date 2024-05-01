/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.tienda;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterNuevo;
import org.uv.delivery.converters.DireccionConverter;
import org.uv.delivery.dtos.tienda.TiendaNuevaDTO;
import org.uv.delivery.models.Tienda;

/**
 *
 * @author juan
 */
@Component
public class TiendaNuevaConverter implements ConverterNuevo<Tienda, TiendaNuevaDTO>{
    
    private final DireccionConverter direccionConverter;
    
    public TiendaNuevaConverter(DireccionConverter direccionConverter){
        this.direccionConverter=direccionConverter;
    } 
    
    @Override
    public Tienda dtotoEntity(TiendaNuevaDTO dto) {
        Tienda tienda = new Tienda();
        tienda.setCalificacion(dto.getCalificacion());
        tienda.setDescripcion(dto.getDescripcion());
        tienda.setDireccion(direccionConverter.dtotoEntity(dto.getDireccion()));
        tienda.setEmail(dto.getEmail());
        tienda.setHorarios(dto.getHorarios());
        tienda.setNombre(dto.getNombre());
        tienda.setTelefono(dto.getTelefono());
        return tienda;
    }

    @Override
    public List<Tienda> dtoListtoEntityList(List<TiendaNuevaDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
