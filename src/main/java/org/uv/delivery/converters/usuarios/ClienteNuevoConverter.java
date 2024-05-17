/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.usuarios;

import java.util.List;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.DireccionConverter;
import org.uv.delivery.dtos.usuarios.ClienteNuevoDTO;
import org.uv.delivery.models.usuario.Cliente;
import org.uv.delivery.repository.GeneroRepository;
import static org.uv.delivery.validations.Validation.stringtoDate;
import org.uv.delivery.converters.ConverterNuevo;

/**
 *
 * @author juan
 */
@Component
public class ClienteNuevoConverter implements ConverterNuevo<Cliente, ClienteNuevoDTO>{
    private final DireccionConverter direccionConverter;
    private final GeneroRepository generoRepository;
    
    public ClienteNuevoConverter(DireccionConverter direccionConverter, GeneroRepository generoRepository){
        this.direccionConverter=direccionConverter;
        this.generoRepository=generoRepository;
    }
    
    @Override
    public Cliente dtotoEntity(ClienteNuevoDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setDireccion(direccionConverter.dtotoEntity(dto.getDireccion()));
        cliente.setEmail(dto.getEmail());
        cliente.setFechaNacimiento(new java.sql.Date(stringtoDate(dto.getFechaNacimiento()).getTime()));
        cliente.setGenero(generoRepository.getById(dto.getIdGenero()));
        cliente.setNombre(dto.getNombre());
        cliente.setPassword(dto.getPassword());
        cliente.setTelefono(dto.getTelefono());
        cliente.setUrlFoto(dto.getUrlFoto());
    
        return cliente;
    }

    @Override
    public List<Cliente> dtoListtoEntityList(List<ClienteNuevoDTO> dtoList) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
