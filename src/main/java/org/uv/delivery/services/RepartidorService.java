/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.http.HttpStatus;
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
import org.uv.delivery.models.usuario.Usuario;
import org.uv.delivery.repository.DireccionRepository;
import org.uv.delivery.repository.GeneroRepository;
import org.uv.delivery.repository.RepartidorRepository;
import org.uv.delivery.repository.UsuarioRepository;
import org.uv.delivery.repository.VehiculoRepository;
import org.uv.delivery.security.JWTUtils;
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
    private final JWTUtils jwtUtils;
    
    public RepartidorService(RepartidorNuevoConverter repartidorNuevoConverter,
            RepartidorRegistradoConverter repartidorRegistradoConverter,
            RepartidorActualizarConverter repartidorActualizarConverter,
            DireccionRepository direccionRepository,
            RepartidorRepository repartidorRepository,
            GeneroRepository generoRepository, VehiculoRepository vehiculoRepository,
            VehiculoConverter vehiculoConverter,
            UsuarioRepository usuarioRepository, PasswordEncoder pe,
            JWTUtils jwtUtils){
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
        this.jwtUtils = jwtUtils;
    }
    
    @Transactional
    public RepartidorRegistradoDTO save(RepartidorNuevoDTO repartidorNuevoDTO){
        Usuario usuario=usuarioRepository.findByEmail(repartidorNuevoDTO.getEmail());
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
                        RepartidorRegistradoDTO repartidorRegistrado = repartidorRegistradoConverter.entitytoDTO(repartidor);
                        return repartidorRegistrado;
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
    
    public RepartidorRegistradoDTO update(RepartidorActualizarDTO repartidorActualizarDTO, long id){
        Optional<Repartidor> optionalRepartidor = repartidorRepository.findById(id);
        if (!optionalRepartidor.isEmpty()){
            Usuario usuario=usuarioRepository.findByEmail(repartidorActualizarDTO.getEmail());
            if (usuario.getId()==optionalRepartidor.get().getId()){
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
                            RepartidorRegistradoDTO repartidorDTO = repartidorRegistradoConverter.entitytoDTO(repartidorTemp);
                            return repartidorDTO;
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
            return null;
        }
    }
    
    public boolean delete(long id){
        Optional<Repartidor> optionalRepartidor = repartidorRepository.findById(id);
        if (!optionalRepartidor.isEmpty()){
            repartidorRepository.delete(optionalRepartidor.get());
            return true;
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
        Optional<Repartidor> optionalRepartidor = repartidorRepository.findById(id);
        if (!optionalRepartidor.isEmpty()){
            Vehiculo vehiculo = vehiculoConverter.dtotoEntity(vehiculoNuevo);
            vehiculo.setIdVehiculo(optionalRepartidor.get().getVehiculo().getIdVehiculo());
            return vehiculoRepository.save(vehiculo);
        }else{
            return null;
        }
    }
}
