/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterRegistrado;
import org.uv.delivery.dtos.usuarios.RepartidorRegistradoDTO;
import org.uv.delivery.models.usuario.Repartidor;
import static org.uv.delivery.validations.Validation.datetoSring;

/**
 *
 * @author juan
 */
@Component
public class RepartidorRegistradoConverter implements ConverterRegistrado<Repartidor, RepartidorRegistradoDTO>{

    @Override
    public RepartidorRegistradoDTO entitytoDTO(Repartidor entity) {
        RepartidorRegistradoDTO repartidor = new RepartidorRegistradoDTO();
        repartidor.setAntecedentes(entity.getAntecedentes());
        repartidor.setCurp(entity.getCurp());
        repartidor.setDireccion(entity.getDireccion());
        repartidor.setEmail(entity.getEmail());
        repartidor.setFechaNacimiento(datetoSring(entity.getFechaNacimiento()));
        repartidor.setGenero(entity.getGenero());
        repartidor.setIdUsuario(entity.getId());
        repartidor.setIne(entity.getIne());
        repartidor.setLicencia(entity.getLicencia());
        repartidor.setNombre(entity.getNombre());
        repartidor.setTelefono(entity.getTelefono());
        repartidor.setVehiculo(entity.getVehiculo());
        return repartidor;
    }

    @Override
    public List<RepartidorRegistradoDTO> entityListtoDTOList(List<Repartidor> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
