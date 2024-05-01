/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.usuarios.EncargadoActualizarConverter;
import org.uv.delivery.converters.usuarios.EncargadoNuevoConverter;
import org.uv.delivery.converters.usuarios.EncargadoRegistradoConverter;
import org.uv.delivery.dtos.usuarios.EncargadoActualizarDTO;
import org.uv.delivery.dtos.usuarios.EncargadoNuevoDTO;
import org.uv.delivery.dtos.usuarios.EncargadoRegistradoDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.Direccion;
import org.uv.delivery.models.usuario.Encargado;
import org.uv.delivery.models.usuario.Usuario;
import org.uv.delivery.repository.DireccionRepository;
import org.uv.delivery.repository.EncargadoRepository;
import org.uv.delivery.repository.GeneroRepository;
import org.uv.delivery.repository.UsuarioRepository;
import org.uv.delivery.security.JWTUtils;
import static org.uv.delivery.validations.Validation.dateValidation;
import static org.uv.delivery.validations.Validation.telefonoValidation;

/**
 *
 * @author juan
 */
@Service
public class EncargadoService {
    private final EncargadoNuevoConverter encargadoNuevoConverter;
    private final EncargadoRegistradoConverter encargadoRegistradoConverter;
    private final EncargadoActualizarConverter encargadoActualizarConverter;
    private final UsuarioRepository usuarioRepository;
    private final EncargadoRepository encargadoRepository;
    private final DireccionRepository direccionRepository;
    private final GeneroRepository generoRepository;
    private final PasswordEncoder pe;
    private final JWTUtils jwtUtils;
    
    public EncargadoService(EncargadoNuevoConverter encargadoNuevoConverter,
            EncargadoRegistradoConverter encargadoRegistradoConverter,
            EncargadoActualizarConverter encargadoActualizarConverter,
            DireccionRepository direccionRepository,
            EncargadoRepository encargadoRepository,
            GeneroRepository generoRepository,
            UsuarioRepository usuarioRepository, PasswordEncoder pe,
            JWTUtils jwtUtils){
        this.encargadoNuevoConverter = encargadoNuevoConverter;
        this.encargadoRegistradoConverter = encargadoRegistradoConverter;
        this.encargadoRepository = encargadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.encargadoActualizarConverter = encargadoActualizarConverter;
        this.direccionRepository=direccionRepository;
        this.generoRepository = generoRepository;
        this.pe=pe;
        this.jwtUtils=jwtUtils;
    }
    
    @Transactional
    public EncargadoRegistradoDTO save(EncargadoNuevoDTO encargadoNuevoDTO){
        Usuario usuario=usuarioRepository.findByEmail(encargadoNuevoDTO.getEmail());
        if (usuario==null){
            String fecha=dateValidation(encargadoNuevoDTO.getFechaNacimiento());
            if (fecha!=null && !fecha.equals("Invalid Date.")){
                if(!generoRepository.findById(encargadoNuevoDTO.getIdGenero()).isEmpty()){
                    if(telefonoValidation(encargadoNuevoDTO.getTelefono())){
                        encargadoNuevoDTO.setFechaNacimiento(fecha);
                        Encargado encargado = encargadoNuevoConverter.dtotoEntity(encargadoNuevoDTO);
                        String password=pe.encode(encargado.getPassword());
                        encargado.setPassword(password);
                        Direccion direccion = direccionRepository.save(encargado.getDireccion());
                        encargado.setDireccion(direccion);
                        encargado=encargadoRepository.save(encargado);
                        EncargadoRegistradoDTO encargadoRegistrado = encargadoRegistradoConverter.entitytoDTO(encargado);
                        return encargadoRegistrado;
                    }else{
                        throw new Exceptions("Número de teléfono invalido..", HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions("El genero seleccionado no existe.", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("Fecha Invalida.", HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public EncargadoRegistradoDTO update(EncargadoActualizarDTO encargadoActualizarDTO, long id){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Encargado> optionalEncargado = encargadoRepository.findById(id);
        if (!optionalEncargado.isEmpty()){
            if (email.equals(optionalEncargado.get().getEmail())){
                Usuario usuario=usuarioRepository.findByEmail(encargadoActualizarDTO.getEmail());
                if (usuario.getId()==optionalEncargado.get().getId()){
                    String fecha=dateValidation(encargadoActualizarDTO.getFechaNacimiento());
                    if (fecha!=null && !fecha.equals("Invalid Date.")){
                        encargadoActualizarDTO.setFechaNacimiento(fecha);
                        if(!generoRepository.findById(encargadoActualizarDTO.getIdGenero()).isEmpty()){
                            if(telefonoValidation(encargadoActualizarDTO.getTelefono())){
                                Encargado encargadoTemp = encargadoActualizarConverter.dtotoEntity(encargadoActualizarDTO);
                                encargadoTemp.setDireccion(optionalEncargado.get().getDireccion());
                                encargadoTemp.setId(optionalEncargado.get().getId());
                                encargadoTemp.setPassword(optionalEncargado.get().getPassword());
                                encargadoTemp = encargadoRepository.save(encargadoTemp);
                                EncargadoRegistradoDTO encargadoDTO = encargadoRegistradoConverter.entitytoDTO(encargadoTemp);
                                return encargadoDTO;
                            }else{
                                throw new Exceptions("Número de teléfono invalido..", HttpStatus.CONFLICT);
                            }
                        }else{
                            throw new Exceptions("El genero seleccionado no existe.", HttpStatus.CONFLICT);
                        }
                    }else{
                        throw new Exceptions("Fecha Invalida.", HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions("El email ingresado ya se encuentra registrado en otra cuenta.", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("Acceso Inautorizado", HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean delete(long id){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Encargado> optionalEncargado = encargadoRepository.findById(id);
        if (!optionalEncargado.isEmpty()){
            if(email.equals(optionalEncargado.get().getEmail())){
                encargadoRepository.delete(optionalEncargado.get());
                return true;
            }else{
                throw new Exceptions("Acceso Inautorizado", HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public EncargadoRegistradoDTO findById(long id){
        Optional<Encargado> optionalEncargado = encargadoRepository.findById(id);
        if (!optionalEncargado.isEmpty()){
            return encargadoRegistradoConverter.entitytoDTO(optionalEncargado.get());
        }else{
            return null;
        }
    }
    
    public List<EncargadoRegistradoDTO> findAll(){
        List<Encargado> encargado = encargadoRepository.findAll();
        return encargadoRegistradoConverter.entityListtoDTOList(encargado);
    }
}
