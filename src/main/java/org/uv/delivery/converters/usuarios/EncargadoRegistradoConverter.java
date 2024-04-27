/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterRegistrado;
import org.uv.delivery.dtos.usuarios.EncargadoRegistradoDTO;
import org.uv.delivery.models.usuario.Encargado;
import static org.uv.delivery.validations.Validation.datetoSring;

/**
 *
 * @author juan
 */
@Component
public class EncargadoRegistradoConverter implements ConverterRegistrado<Encargado, EncargadoRegistradoDTO>{

    @Override
    public EncargadoRegistradoDTO entitytoDTO(Encargado entity) {
        EncargadoRegistradoDTO encargado = new EncargadoRegistradoDTO();
        encargado.setAntecedentes(entity.getAntecedentes());
        encargado.setCurp(entity.getCurp());
        encargado.setDireccion(entity.getDireccion());
        encargado.setEmail(entity.getEmail());
        encargado.setFechaNacimiento(datetoSring(entity.getFechaNacimiento()));
        encargado.setGenero(entity.getGenero());
        encargado.setIdUsuario(entity.getId());
        encargado.setIne(entity.getIne());
        encargado.setNombre(entity.getNombre());
        encargado.setTelefono(entity.getTelefono());
        encargado.setUrlFoto(entity.getURLFoto());
        return encargado;
    }

    @Override
    public List<EncargadoRegistradoDTO> entityListtoDTOList(List<Encargado> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
