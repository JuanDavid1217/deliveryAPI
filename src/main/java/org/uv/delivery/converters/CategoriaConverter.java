/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.dtos.CategoriaNuevaDTO;
import org.uv.delivery.models.Categoria;

/**
 *
 * @author juan
 */
@Component
public class CategoriaConverter implements ConverterNuevo<Categoria, CategoriaNuevaDTO>{

    @Override
    public Categoria dtotoEntity(CategoriaNuevaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }

    @Override
    public List<Categoria> dtoListtoEntityList(List<CategoriaNuevaDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
