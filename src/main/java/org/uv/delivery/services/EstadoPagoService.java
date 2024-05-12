/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.EstadoPagoConverter;
import org.uv.delivery.dtos.TipoEstadoDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.EstadoPago;
import org.uv.delivery.repository.EstadoPagoRepository;

/**
 *
 * @author juan
 */
@Service
public class EstadoPagoService {
    private final EstadoPagoConverter estadoPagoConverter;
    private final EstadoPagoRepository estadoPagoRepository;
    
    public EstadoPagoService(EstadoPagoConverter estadoPagoConverter,
            EstadoPagoRepository estadoPagoRepository){
        this.estadoPagoConverter = estadoPagoConverter;
        this.estadoPagoRepository = estadoPagoRepository;
    }
    
    public String message(String descripcion){
        return "El Estado de Pago: "+descripcion+" ya se encuentra registrado.";
    }
    
    public EstadoPago save(TipoEstadoDTO estadoPagoNuevo){
        EstadoPago estadoPago = estadoPagoRepository.findByDescripcion(estadoPagoNuevo.getDescripcion());
        if (estadoPago == null){
            estadoPago = estadoPagoConverter.dtotoEntity(estadoPagoNuevo);
            estadoPago = estadoPagoRepository.save(estadoPago);
            return estadoPago;
        }else{
             throw new Exceptions(message(estadoPagoNuevo.getDescripcion()), HttpStatus.CONFLICT);
        }
    }
    
    public EstadoPago update(long id, TipoEstadoDTO estadoPagoNuevo){
        Optional<EstadoPago> optionalEstadoPago = estadoPagoRepository.findById(id);
        if(!optionalEstadoPago.isEmpty()){
            EstadoPago estadoPagoTemp = estadoPagoRepository.findByDescripcion(estadoPagoNuevo.getDescripcion());
            if(estadoPagoTemp == null||estadoPagoTemp.getIdEstado()==id){
                EstadoPago estadoPago = estadoPagoConverter.dtotoEntity(estadoPagoNuevo);
                estadoPago.setIdEstado(optionalEstadoPago.get().getIdEstado());
                estadoPago = estadoPagoRepository.save(estadoPago);
                return estadoPago;
            }else{
                throw new Exceptions(message(estadoPagoNuevo.getDescripcion()), HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean delete(long id){
        Optional<EstadoPago> optionalEstadoPago = estadoPagoRepository.findById(id);
        if(!optionalEstadoPago.isEmpty()){
            try{
                estadoPagoRepository.delete(optionalEstadoPago.get());
                return true;
            }catch(Exception ex){
                throw new Exceptions("El estado: "+optionalEstadoPago.get().getDescripcion()+" no se puede eliminar ya que presenta relaciones.", HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public EstadoPago findById(long id){
        Optional<EstadoPago> optionalEstadoPago = estadoPagoRepository.findById(id);
        if(!optionalEstadoPago.isEmpty()){
            return optionalEstadoPago.get();
        }else{
            return null;
        }
    }
    
    public List<EstadoPago> findAll(){
        return estadoPagoRepository.findAll();
    }
}
