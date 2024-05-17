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
import org.uv.delivery.converters.VehiculoConverter;
import org.uv.delivery.converters.usuarios.RepartidorActualizarConverter;
import org.uv.delivery.converters.usuarios.RepartidorNuevoConverter;
import org.uv.delivery.converters.usuarios.RepartidorRegistradoConverter;
import org.uv.delivery.dtos.VehiculoNuevoDTO;
import org.uv.delivery.dtos.usuarios.RepartidorActualizarDTO;
import org.uv.delivery.dtos.usuarios.RepartidorNuevoDTO;
import org.uv.delivery.dtos.usuarios.RepartidorRegistradoDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.Direccion;
import org.uv.delivery.models.Vehiculo;
import org.uv.delivery.models.usuario.Repartidor;
import org.uv.delivery.models.usuario.UsuarioBase;
import org.uv.delivery.repository.DireccionRepository;
import org.uv.delivery.repository.GeneroRepository;
import org.uv.delivery.repository.RepartidorRepository;
import org.uv.delivery.repository.UsuarioRepository;
import org.uv.delivery.repository.VehiculoRepository;
import static org.uv.delivery.validations.Validation.dateValidation;
import static org.uv.delivery.validations.Validation.telefonoValidation;

/**
 *
 * @author juan
 */
@Service
public class RepartidorService {
    private final RepartidorNuevoConverter repartidorNuevoConverter;
    private final RepartidorRegistradoConverter repartidorRegistradoConverter;
    private final RepartidorActualizarConverter repartidorActualizarConverter;
    private final UsuarioRepository usuarioRepository;
    private final RepartidorRepository repartidorRepository;
    private final DireccionRepository direccionRepository;
    private final GeneroRepository generoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final VehiculoConverter vehiculoConverter;
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
    
    public RepartidorService(RepartidorNuevoConverter repartidorNuevoConverter,
            RepartidorRegistradoConverter repartidorRegistradoConverter,
            RepartidorActualizarConverter repartidorActualizarConverter,
            DireccionRepository direccionRepository,
            RepartidorRepository repartidorRepository,
            GeneroRepository generoRepository, VehiculoRepository vehiculoRepository,
            VehiculoConverter vehiculoConverter,
            UsuarioRepository usuarioRepository, PasswordEncoder pe){
        this.repartidorNuevoConverter = repartidorNuevoConverter;
        this.repartidorRegistradoConverter = repartidorRegistradoConverter;
        this.repartidorRepository = repartidorRepository;
        this.usuarioRepository = usuarioRepository;
        this.repartidorActualizarConverter = repartidorActualizarConverter;
        this.direccionRepository = direccionRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.vehiculoConverter = vehiculoConverter;
        this.generoRepository = generoRepository;
        this.pe = pe;
    }
    
    @Transactional
    public RepartidorRegistradoDTO save(RepartidorNuevoDTO repartidorNuevoDTO){
        UsuarioBase usuario=usuarioRepository.findByEmail(repartidorNuevoDTO.getEmail());
        if (usuario==null){
            String fecha=dateValidation(repartidorNuevoDTO.getFechaNacimiento());
            if (fecha!=null && !fecha.equals("Invalid Date.")){
                if(!generoRepository.findById(repartidorNuevoDTO.getIdGenero()).isEmpty()){
                    if(telefonoValidation(repartidorNuevoDTO.getTelefono())){
                        repartidorNuevoDTO.setFechaNacimiento(fecha);
                        Repartidor repartidor = repartidorNuevoConverter.dtotoEntity(repartidorNuevoDTO);
                        String password=pe.encode(repartidor.getPassword());
                        repartidor.setPassword(password);
                        Direccion direccion = direccionRepository.save(repartidor.getDireccion());
                        repartidor.setDireccion(direccion);
                        Vehiculo vehiculo = vehiculoRepository.save(repartidor.getVehiculo());
                        repartidor.setVehiculo(vehiculo);
                        repartidor = repartidorRepository.save(repartidor);
                        return repartidorRegistradoConverter.entitytoDTO(repartidor);
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
    
    public RepartidorRegistradoDTO update(RepartidorActualizarDTO repartidorActualizarDTO, long id){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Repartidor> optionalRepartidor = repartidorRepository.findById(id);
        if (!optionalRepartidor.isEmpty()){
            if(email.equals(optionalRepartidor.get().getEmail())){
                UsuarioBase usuario=usuarioRepository.findByEmail(repartidorActualizarDTO.getEmail());
                if (usuario==null || (usuario!=null && usuario.getId()==optionalRepartidor.get().getId())){
                    String fecha=dateValidation(repartidorActualizarDTO.getFechaNacimiento());
                    if (fecha!=null && !fecha.equals("Invalid Date.")){
                        repartidorActualizarDTO.setFechaNacimiento(fecha);
                        if(!generoRepository.findById(repartidorActualizarDTO.getIdGenero()).isEmpty()){
                            if(telefonoValidation(repartidorActualizarDTO.getTelefono())){
                                Repartidor repartidorTemp = repartidorActualizarConverter.dtotoEntity(repartidorActualizarDTO);
                                repartidorTemp.setDireccion(optionalRepartidor.get().getDireccion());
                                repartidorTemp.setId(optionalRepartidor.get().getId());
                                repartidorTemp.setPassword(optionalRepartidor.get().getPassword());
                                repartidorTemp.setVehiculo(optionalRepartidor.get().getVehiculo());
                                repartidorTemp = repartidorRepository.save(repartidorTemp);
                                return repartidorRegistradoConverter.entitytoDTO(repartidorTemp);
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
        Optional<Repartidor> optionalRepartidor = repartidorRepository.findById(id);
        if (!optionalRepartidor.isEmpty()){
            if (email.equals(optionalRepartidor.get().getEmail())){
                repartidorRepository.delete(optionalRepartidor.get());
                return true;
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public RepartidorRegistradoDTO findById(long id){
        Optional<Repartidor> optionalRepartidor = repartidorRepository.findById(id);
        if (!optionalRepartidor.isEmpty()){
            return repartidorRegistradoConverter.entitytoDTO(optionalRepartidor.get());
        }else{
            return null;
        }
    }
    
    public List<RepartidorRegistradoDTO> findAll(){
        List<Repartidor> repartidores = repartidorRepository.findAll();
        return repartidorRegistradoConverter.entityListtoDTOList(repartidores);
    }
    
    public Vehiculo updateVehiculo(long id, VehiculoNuevoDTO vehiculoNuevo){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Repartidor> optionalRepartidor = repartidorRepository.findById(id);
        if (!optionalRepartidor.isEmpty()){
            if (email.equals(optionalRepartidor.get().getEmail())){
                Vehiculo vehiculo = vehiculoConverter.dtotoEntity(vehiculoNuevo);
                vehiculo.setIdVehiculo(optionalRepartidor.get().getVehiculo().getIdVehiculo());
                return vehiculoRepository.save(vehiculo);
            }else{
                throw new Exceptions("Acceso Inautorizado", HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
}
