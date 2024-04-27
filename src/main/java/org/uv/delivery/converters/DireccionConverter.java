/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.dtos.DireccionNuevaDTO;
import org.uv.delivery.models.Direccion;

/**
 *
 * @author juan
 */
@Component
public class DireccionConverter implements ConverterNuevo<Direccion, DireccionNuevaDTO>{

    @Override
    public Direccion dtotoEntity(DireccionNuevaDTO dto) {
        Direccion direccion=new Direccion();
        direccion.setCalle1(dto.getCalle1());
        direccion.setCalle2(dto.getCalle2());
        direccion.setCalle3(dto.getCalle3());
        direccion.setColonia(dto.getColonia());
        direccion.setCp(dto.getCp());
        direccion.setEstado(dto.getEstado());
        direccion.setMunicipio(dto.getMunicipio());
        direccion.setNumeroExterior(dto.getNumeroExterior());
        direccion.setNumeroInterior(dto.getNumeroInterior());
        direccion.setReferencia(dto.getReferencia());
        return direccion;
    }

    @Override
    public List<Direccion> dtoListtoEntityList(List<DireccionNuevaDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }

    
}
