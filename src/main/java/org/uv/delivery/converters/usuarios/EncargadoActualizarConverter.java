/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterNuevo;
import org.uv.delivery.dtos.usuarios.EncargadoActualizarDTO;
import org.uv.delivery.models.Genero;
import org.uv.delivery.models.usuario.Encargado;
import static org.uv.delivery.validations.Validation.stringtoDate;

/**
 *
 * @author juan
 */
@Component
public class EncargadoActualizarConverter implements ConverterNuevo<Encargado, EncargadoActualizarDTO>{

    @Override
    public Encargado dtotoEntity(EncargadoActualizarDTO dto) {
        Encargado encargado = new Encargado();
        encargado.setAntecedentes(dto.getAntecedentes());
        encargado.setCurp(dto.getCurp());
        encargado.setEmail(dto.getEmail());
        encargado.setFechaNacimiento(new java.sql.Date(stringtoDate(dto.getFechaNacimiento()).getTime()));
        Genero genero = new Genero();
        genero.setIdGenero(dto.getIdGenero());
        encargado.setGenero(genero);
        encargado.setIne(dto.getIne());
        encargado.setNombre(dto.getNombre());
        encargado.setTelefono(dto.getTelefono());
        return encargado;
    }

    @Override
    public List<Encargado> dtoListtoEntityList(List<EncargadoActualizarDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
