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
        Optional<Encargado> encargadoOptional = encargadoRepository.findById(idEncargado);
        if(!encargadoOptional.isEmpty()){
            if (encargadoOptional.get().getTienda().isEmpty()){
                if (telefonoValidation(tiendaNueva.getTelefono())){
                    Tienda tienda =tiendaNuevaConverter.dtotoEntity(tiendaNueva);
                    Direccion direccion = direccionRepository.save(tienda.getDireccion());
                    tienda.setDireccion(direccion);
                    tienda.setEncargado(encargadoOptional.get());
                    tienda = tiendaRepository.save(tienda);
                    return tiendaRegistradaConverter.entitytoDTO(tienda);
                }else{
                    throw new Exceptions("Número de teléfono invalido..", HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions("El usuario: "+idEncargado+" ya cuenta con una tienda registrada.", HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
     
    public TiendaRegistradaDTO update(long idTienda, TiendaDTO tiendaActualizar){
        Optional<Tienda> tiendaOptional = tiendaRepository.findById(idTienda);
        if(!tiendaOptional.isEmpty()){
            if (telefonoValidation(tiendaActualizar.getTelefono())){
                Tienda tienda = tiendaActualizarConverter.dtotoEntity(tiendaActualizar);
                tienda.setIdTienda(tiendaOptional.get().getIdTienda());
                tienda.setDireccion(tiendaOptional.get().getDireccion());
                tienda.setEncargado(tiendaOptional.get().getEncargado());
                tienda = tiendaRepository.save(tienda);
                return tiendaRegistradaConverter.entitytoDTO(tienda);
            }else{
                throw new Exceptions("Número de teléfono invalido..", HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean delete(long idTienda){
        Optional<Tienda> tiendaOptional = tiendaRepository.findById(idTienda);
        if(!tiendaOptional.isEmpty()){
            tiendaRepository.delete(tiendaOptional.get());
            return true;
        }else{
            return false;
        }
    }
    
    public TiendaRegistradaDTO findById(long idTienda){
        Optional<Tienda> tiendaOptional = tiendaRepository.findById(idTienda);
        if(!tiendaOptional.isEmpty()){
            return tiendaRegistradaConverter.entitytoDTO(tiendaOptional.get());
        }else{
            return null;
        }
    }
    
    public List<TiendaRegistradaDTO> findAll(){
        List<Tienda> tiendas= tiendaRepository.findAll();
        return tiendaRegistradaConverter.entityListtoDTOList(tiendas);
    }
}
