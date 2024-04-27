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
import org.uv.delivery.converters.VehiculoConverter;
import org.uv.delivery.dtos.usuarios.RepartidorNuevoDTO;
import org.uv.delivery.models.usuario.Repartidor;
import org.uv.delivery.repository.GeneroRepository;
import static org.uv.delivery.validations.Validation.stringtoDate;

/**
 *
 * @author juan
 */
@Component
public class RepartidorNuevoConverter implements ConverterNuevo<Repartidor, RepartidorNuevoDTO>{
    private final DireccionConverter direccionConverter;
    private final GeneroRepository generoRepository;
    private final VehiculoConverter vehiculoConverter;
    
    public RepartidorNuevoConverter(DireccionConverter direccionConverter,
            GeneroRepository generoRepository, VehiculoConverter vehiculoConverter){
        this.direccionConverter=direccionConverter;
        this.generoRepository=generoRepository;
        this.vehiculoConverter=vehiculoConverter;
    }

    @Override
    public Repartidor dtotoEntity(RepartidorNuevoDTO dto) {
        Repartidor repartidor = new Repartidor();
        repartidor.setAntecedentes(dto.getAntecedentes());
        repartidor.setCurp(dto.getCurp());
        repartidor.setDireccion(direccionConverter.dtotoEntity(dto.getDireccion()));
        repartidor.setEmail(dto.getEmail());
        repartidor.setFechaNacimiento(new java.sql.Date(stringtoDate(dto.getFechaNacimiento()).getTime()));
        repartidor.setGenero(generoRepository.getById(dto.getIdGenero()));
        repartidor.setIne(dto.getIne());
        repartidor.setLicencia(dto.getLicencia());
        repartidor.setNombre(dto.getNombre());
        repartidor.setPassword(dto.getPassword());
        repartidor.setTelefono(dto.getTelefono());
        repartidor.setURLFoto(dto.getUrlFoto());
        repartidor.setVehiculo(vehiculoConverter.dtotoEntity(dto.getVehiculo()));
        return repartidor;
    }

    @Override
    public List<Repartidor> dtoListtoEntityList(List<RepartidorNuevoDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
}
