/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterNuevo;
import org.uv.delivery.dtos.usuarios.RepartidorActualizarDTO;
import org.uv.delivery.models.Genero;
import org.uv.delivery.models.usuario.Repartidor;
import static org.uv.delivery.validations.Validation.stringtoDate;

/**
 *
 * @author juan
 */
@Component
public class RepartidorActualizarConverter implements ConverterNuevo<Repartidor, RepartidorActualizarDTO>{

    @Override
    public Repartidor dtotoEntity(RepartidorActualizarDTO dto) {
        Repartidor repartidor = new Repartidor();
        repartidor.setAntecedentes(dto.getAntecedentes());
        repartidor.setCurp(dto.getCurp());
        repartidor.setEmail(dto.getEmail());
        repartidor.setFechaNacimiento(new java.sql.Date(stringtoDate(dto.getFechaNacimiento()).getTime()));
        Genero genero = new Genero();
        genero.setIdGenero(dto.getIdGenero());
        repartidor.setGenero(genero);
        repartidor.setIne(dto.getIne());
        repartidor.setLicencia(dto.getLicencia());
        repartidor.setNombre(dto.getNombre());
        repartidor.setTelefono(dto.getTelefono());
        repartidor.setUrlFoto(dto.getUrlFoto());
        return repartidor;
    }

    @Override
    public List<Repartidor> dtoListtoEntityList(List<RepartidorActualizarDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
