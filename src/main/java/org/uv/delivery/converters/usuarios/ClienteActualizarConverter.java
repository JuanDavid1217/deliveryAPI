/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterNuevo;
import org.uv.delivery.dtos.usuarios.ClienteActualizarDTO;
import org.uv.delivery.models.Genero;
import org.uv.delivery.models.usuario.Cliente;
import static org.uv.delivery.validations.Validation.stringtoDate;

/**
 *
 * @author juan
 */
@Component
public class ClienteActualizarConverter implements ConverterNuevo<Cliente, ClienteActualizarDTO>{

    @Override
    public Cliente dtotoEntity(ClienteActualizarDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setEmail(dto.getEmail());
        cliente.setFechaNacimiento(new java.sql.Date(stringtoDate(dto.getFechaNacimiento()).getTime()));
        Genero genero = new Genero();
        genero.setIdGenero(dto.getIdGenero());
        cliente.setGenero(genero);
        cliente.setNombre(dto.getNombre());
        cliente.setTelefono(dto.getTelefono());
        cliente.setUrlFoto(dto.getUrlFoto());
        return cliente;
    }

    @Override
    public List<Cliente> dtoListtoEntityList(List<ClienteActualizarDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
