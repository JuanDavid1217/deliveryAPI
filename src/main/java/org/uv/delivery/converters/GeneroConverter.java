/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.dtos.GeneroNuevoDTO;
import org.uv.delivery.models.Genero;

/**
 *
 * @author juan
 */
@Component
public class GeneroConverter implements ConverterNuevo<Genero, GeneroNuevoDTO>{

    @Override
    public Genero dtotoEntity(GeneroNuevoDTO dto) {
        Genero genero = new Genero();
        genero.setDescripcion(dto.getDescripcion());
        return genero;
    }


    @Override
    public List<Genero> dtoListtoEntityList(List<GeneroNuevoDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }

    
}
