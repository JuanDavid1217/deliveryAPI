/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterNuevo;
import org.uv.delivery.converters.DireccionConverter;
import org.uv.delivery.dtos.usuarios.EncargadoNuevoDTO;
import org.uv.delivery.models.usuario.Encargado;
import org.uv.delivery.repository.GeneroRepository;
import static org.uv.delivery.validations.Validation.stringtoDate;

/**
 *
 * @author juan
 */
@Component
public class EncargadoNuevoConverter implements ConverterNuevo<Encargado, EncargadoNuevoDTO>{
    private final DireccionConverter direccionConverter;
    private final GeneroRepository generoRepository;
    
    public EncargadoNuevoConverter(DireccionConverter direccionConverter, GeneroRepository generoRepository){
        this.direccionConverter=direccionConverter;
        this.generoRepository=generoRepository;
    }
    
    @Override
    public Encargado dtotoEntity(EncargadoNuevoDTO dto) {
        Encargado encargado = new Encargado();
        encargado.setAntecedentes(dto.getAntecedentes());
        encargado.setCurp(dto.getCurp());
        encargado.setDireccion(direccionConverter.dtotoEntity(dto.getDireccion()));
        encargado.setEmail(dto.getEmail());
        encargado.setFechaNacimiento(new java.sql.Date(stringtoDate(dto.getFechaNacimiento()).getTime()));
        encargado.setGenero(generoRepository.getById(dto.getIdGenero()));
        encargado.setIne(dto.getIne());
        encargado.setNombre(dto.getNombre());
        encargado.setPassword(dto.getPassword());
        encargado.setTelefono(dto.getTelefono());
        return encargado;
    }

    @Override
    public List<Encargado> dtoListtoEntityList(List<EncargadoNuevoDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
