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
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.tienda.TiendaActualizarConverter;
import org.uv.delivery.converters.tienda.TiendaNuevaConverter;
import org.uv.delivery.converters.tienda.TiendaRegistradaConverter;
import org.uv.delivery.dtos.tienda.TiendaDTO;
import org.uv.delivery.dtos.tienda.TiendaNuevaDTO;
import org.uv.delivery.dtos.tienda.TiendaRegistradaDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.Direccion;
import org.uv.delivery.models.Tienda;
import org.uv.delivery.models.usuario.Encargado;
import org.uv.delivery.repository.DireccionRepository;
import org.uv.delivery.repository.EncargadoRepository;
import org.uv.delivery.repository.TiendaRepository;
import static org.uv.delivery.validations.Validation.telefonoValidation;

/**
 *
 * @author juan
 */
@Service
public class TiendaService {
    
    private final TiendaNuevaConverter tiendaNuevaConverter;
    private final TiendaRegistradaConverter tiendaRegistradaConverter;
    private final EncargadoRepository encargadoRepository;
    private final TiendaRepository tiendaRepository;
    private final TiendaActualizarConverter tiendaActualizarConverter;
    private final DireccionRepository direccionRepository;
    
    @Value("${message.general.inautorizado}")
    private String acceso;
    @Value("${message.usuarioService.telefono}")
    private String telefono;
    @Value("${message.tiendaService.tiendaNoRegistrada}")
    private String tiendaNoRegistrada;
    
    public TiendaService(TiendaNuevaConverter tiendaNuevaConverter,
        TiendaRegistradaConverter tiendaRegistradaConverter,
        EncargadoRepository encargadoRepository, TiendaRepository tiendaRepository,
        TiendaActualizarConverter tiendaActualizarConverter,
        DireccionRepository direccionRepository){
        this.tiendaNuevaConverter = tiendaNuevaConverter;
        this.tiendaRegistradaConverter = tiendaRegistradaConverter;
        this.encargadoRepository = encargadoRepository;
        this.tiendaRepository = tiendaRepository;
        this.tiendaActualizarConverter = tiendaActualizarConverter;
        this.direccionRepository = direccionRepository;
    }
    
    @Transactional
    public TiendaRegistradaDTO save(long idEncargado, TiendaNuevaDTO tiendaNueva){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Encargado> encargadoOptional = encargadoRepository.findById(idEncargado);
        if(!encargadoOptional.isEmpty()){
            if (email.equals(encargadoOptional.get().getEmail())){
                if (encargadoOptional.get().getTienda().isEmpty()){
                    if (telefonoValidation(tiendaNueva.getTelefono())){
                        Tienda tienda =tiendaNuevaConverter.dtotoEntity(tiendaNueva);
                        Direccion direccion = direccionRepository.save(tienda.getDireccion());
                        tienda.setDireccion(direccion);
                        tienda.setEncargado(encargadoOptional.get());
                        tienda = tiendaRepository.save(tienda);
                        return tiendaRegistradaConverter.entitytoDTO(tienda);
                    }else{
                        throw new Exceptions(telefono, HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions("El usuario: "+idEncargado+" ya cuenta con una tienda registrada.", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
     
    public TiendaRegistradaDTO update(long idEncargado, TiendaDTO tiendaActualizar){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Encargado> encargadoOptional = encargadoRepository.findById(idEncargado);
        if(!encargadoOptional.isEmpty()){
            if (email.equals(encargadoOptional.get().getEmail())){
                if (!encargadoOptional.get().getTienda().isEmpty()){
                    if (telefonoValidation(tiendaActualizar.getTelefono())){
                        Tienda tiendaRegistrada = encargadoOptional.get().getTienda().get(0);
                        Tienda tienda = tiendaActualizarConverter.dtotoEntity(tiendaActualizar);
                        tienda.setIdTienda(tiendaRegistrada.getIdTienda());
                        tienda.setDireccion(tiendaRegistrada.getDireccion());
                        tienda.setEncargado(tiendaRegistrada.getEncargado());
                        tienda = tiendaRepository.save(tienda);
                        return tiendaRegistradaConverter.entitytoDTO(tienda);
                    }else{
                        throw new Exceptions(telefono, HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions(tiendaNoRegistrada, HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean delete(long idEncargado){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Encargado> encargadoOptional = encargadoRepository.findById(idEncargado);
        if(!encargadoOptional.isEmpty()){
            if (email.equals(encargadoOptional.get().getEmail())){
                if (!encargadoOptional.get().getTienda().isEmpty()){
                    tiendaRepository.delete(encargadoOptional.get().getTienda().get(0));
                    return true;
                }else{
                    throw new Exceptions(tiendaNoRegistrada, HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public TiendaRegistradaDTO findById(long idEncargado){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Encargado> encargadoOptional = encargadoRepository.findById(idEncargado);
        if(!encargadoOptional.isEmpty()){
            if (email.equals(encargadoOptional.get().getEmail())){
                if (!encargadoOptional.get().getTienda().isEmpty()){ 
                    return tiendaRegistradaConverter.entitytoDTO(encargadoOptional.get().getTienda().get(0));
                }else{
                    throw new Exceptions(tiendaNoRegistrada, HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public List<TiendaRegistradaDTO> findAll(){
        List<Tienda> tiendas= tiendaRepository.findAll();
        return tiendaRegistradaConverter.entityListtoDTOList(tiendas);
    }
}
