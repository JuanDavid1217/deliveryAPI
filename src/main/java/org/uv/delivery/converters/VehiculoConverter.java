/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.dtos.VehiculoNuevoDTO;
import org.uv.delivery.models.Vehiculo;

/**
 *
 * @author juan
 */
@Component
public class VehiculoConverter implements ConverterNuevo<Vehiculo, VehiculoNuevoDTO>{

    @Override
    public Vehiculo dtotoEntity(VehiculoNuevoDTO dto) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setNiv(dto.getNiv());
        vehiculo.setPlaca(dto.getPlaca());
        vehiculo.setRepube(dto.getRepube());
        vehiculo.setTipo(dto.getTipo());
        return vehiculo;
    }


    @Override
    public List<Vehiculo> dtoListtoEntityList(List<VehiculoNuevoDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }

    
}
