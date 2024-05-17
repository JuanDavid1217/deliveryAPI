/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.usuarios.ClienteActualizarConverter;
import org.uv.delivery.converters.usuarios.ClienteNuevoConverter;
import org.uv.delivery.converters.usuarios.ClienteRegistradoConverter;
import org.uv.delivery.dtos.usuarios.ClienteActualizarDTO;
import org.uv.delivery.dtos.usuarios.ClienteNuevoDTO;
import org.uv.delivery.dtos.usuarios.ClienteRegistradoDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.Direccion;
import org.uv.delivery.models.usuario.Cliente;
import org.uv.delivery.models.usuario.UsuarioBase;
import org.uv.delivery.repository.DireccionRepository;
import org.uv.delivery.repository.UsuarioRepository;
import org.uv.delivery.repository.ClienteRepository;
import org.uv.delivery.repository.GeneroRepository;
import static org.uv.delivery.validations.Validation.dateValidation;
import static org.uv.delivery.validations.Validation.telefonoValidation;

/**
 *
 * @author juan
 */
@Service
public class ClienteService {
    private final ClienteNuevoConverter clienteNuevoConverter;
    private final ClienteRegistradoConverter clienteRegistradoConverter;
    private final ClienteActualizarConverter clienteActualizarConverter;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final DireccionRepository direccionRepository;
    private final GeneroRepository generoRepository;
    private final PasswordEncoder pe;
    
    @Value("${message.general.inautorizado}")
    private String acceso;
    @Value("${message.usuarioService.telefono}")
    private String telefono;
    @Value("${message.usuarioService.genero}")
    private String genero;
    @Value("${message.usuarioService.fecha}")
    private String fechaInvalida;
    @Value("${message.usuarioService.email}")
    private String emailRegistrado;
    
    public ClienteService(ClienteNuevoConverter clienteNuevoConverter,
            ClienteRegistradoConverter clienteRegistradoConverter,
            ClienteActualizarConverter clienteActualizarConverter,
            DireccionRepository direccionRepository,
            ClienteRepository clienteRepository,
            GeneroRepository generoRepository,
            UsuarioRepository usuarioRepository, PasswordEncoder pe){
        this.clienteNuevoConverter=clienteNuevoConverter;
        this.clienteRegistradoConverter=clienteRegistradoConverter;
        this.clienteRepository=clienteRepository;
        this.usuarioRepository=usuarioRepository;
        this.clienteActualizarConverter=clienteActualizarConverter;
        this.direccionRepository=direccionRepository;
        this.generoRepository = generoRepository;
        this.pe=pe;
    }
    
    @Transactional
    public ClienteRegistradoDTO save(ClienteNuevoDTO clienteNuevoDTO){
        UsuarioBase usuario=usuarioRepository.findByEmail(clienteNuevoDTO.getEmail());
        if (usuario==null){
            String fecha=dateValidation(clienteNuevoDTO.getFechaNacimiento());
            if (fecha!=null && !fecha.equals("Invalid Date.")){
                if(!generoRepository.findById(clienteNuevoDTO.getIdGenero()).isEmpty()){
                    if(telefonoValidation(clienteNuevoDTO.getTelefono())){
                        clienteNuevoDTO.setFechaNacimiento(fecha);
                        Cliente cliente=clienteNuevoConverter.dtotoEntity(clienteNuevoDTO);
                        String password=pe.encode(cliente.getPassword());
                        cliente.setPassword(password);
                        Direccion direccion = direccionRepository.save(cliente.getDireccion());
                        cliente.setDireccion(direccion);
                        cliente=clienteRepository.save(cliente);
                        return clienteRegistradoConverter.entitytoDTO(cliente);
                    }else{
                        throw new Exceptions(telefono, HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions(genero, HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(fechaInvalida, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public ClienteRegistradoDTO update(ClienteActualizarDTO clienteActualizarDTO, long id){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if (!optionalCliente.isEmpty()){
            if (email.equals(optionalCliente.get().getEmail())){
                UsuarioBase usuario=usuarioRepository.findByEmail(clienteActualizarDTO.getEmail());
                if (usuario==null || (usuario!=null && usuario.getId()==optionalCliente.get().getId())){
                    String fecha=dateValidation(clienteActualizarDTO.getFechaNacimiento());
                    if (fecha!=null && !fecha.equals("Invalid Date.")){
                        clienteActualizarDTO.setFechaNacimiento(fecha);
                        if(!generoRepository.findById(clienteActualizarDTO.getIdGenero()).isEmpty()){
                            if(telefonoValidation(clienteActualizarDTO.getTelefono())){
                                Cliente clienteTemp=clienteActualizarConverter.dtotoEntity(clienteActualizarDTO);
                                clienteTemp.setDireccion(optionalCliente.get().getDireccion());
                                clienteTemp.setId(optionalCliente.get().getId());
                                clienteTemp.setPassword(optionalCliente.get().getPassword());
                                clienteTemp=clienteRepository.save(clienteTemp);
                                return clienteRegistradoConverter.entitytoDTO(clienteTemp);
                            }else{
                                throw new Exceptions(telefono, HttpStatus.CONFLICT);
                            }
                        }else{
                            throw new Exceptions(genero, HttpStatus.CONFLICT);
                        }
                    }else{
                        throw new Exceptions(fechaInvalida, HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions(emailRegistrado, HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean delete(long id){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if (!optionalCliente.isEmpty()){
            if (email.equals(optionalCliente.get().getEmail())){
                clienteRepository.delete(optionalCliente.get());
                return true;
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public ClienteRegistradoDTO findById(long id){
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if (!optionalCliente.isEmpty()){
            return clienteRegistradoConverter.entitytoDTO(optionalCliente.get());
        }else{
            return null;
        }
    }
    
    public List<ClienteRegistradoDTO> findAll(){
        List<Cliente> clientes=clienteRepository.findAll();
        return clienteRegistradoConverter.entityListtoDTOList(clientes);
    }
}
