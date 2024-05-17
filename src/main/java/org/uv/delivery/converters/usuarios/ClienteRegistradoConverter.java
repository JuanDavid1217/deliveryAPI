/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.usuarios;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterRegistrado;
import org.uv.delivery.dtos.usuarios.ClienteRegistradoDTO;
import org.uv.delivery.models.usuario.Cliente;
import static org.uv.delivery.validations.Validation.datetoSring;

/**
 *
 * @author juan
 */
@Component
public class ClienteRegistradoConverter implements ConverterRegistrado<Cliente, ClienteRegistradoDTO>{

    @Override
    public ClienteRegistradoDTO entitytoDTO(Cliente entity) {
        ClienteRegistradoDTO cliente = new ClienteRegistradoDTO();
        cliente.setIdUsuario(entity.getId());
        cliente.setDireccion(entity.getDireccion());
        cliente.setEmail(entity.getEmail());
        cliente.setFechaNacimiento(datetoSring(entity.getFechaNacimiento()));
        cliente.setGenero(entity.getGenero());
        cliente.setNombre(entity.getNombre());
        cliente.setTelefono(entity.getTelefono());
        cliente.setUrlFoto(entity.getUrlFoto());
        return cliente;
    }

    @Override
    public List<ClienteRegistradoDTO> entityListtoDTOList(List<Cliente> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
