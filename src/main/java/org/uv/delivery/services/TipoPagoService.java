/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.TipoPagoConverter;
import org.uv.delivery.dtos.TipoEstadoDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.TipoPago;
import org.uv.delivery.repository.TipoPagoRepository;

/**
 *
 * @author juan
 */
@Service
public class TipoPagoService {
    private final TipoPagoConverter tipoPagoConverter;
    private final TipoPagoRepository tipoPagoRepository;
    
    public TipoPagoService(TipoPagoConverter tipoPagoConverter,
            TipoPagoRepository tipoPagoRepository){
        this.tipoPagoConverter = tipoPagoConverter;
        this.tipoPagoRepository = tipoPagoRepository;
    }
    
    public String message(String descripcion){
        return "El Tipo de Pago: "+descripcion+" ya se encuentra registrado.";
    }
    
    public TipoPago save(TipoEstadoDTO tipoPagoNuevo){
        TipoPago tipoPago = tipoPagoRepository.findByDescripcion(tipoPagoNuevo.getDescripcion());
        if (tipoPago == null){
            tipoPago = tipoPagoConverter.dtotoEntity(tipoPagoNuevo);
            tipoPago = tipoPagoRepository.save(tipoPago);
            return tipoPago;
        }else{
             throw new Exceptions(message(tipoPagoNuevo.getDescripcion()), HttpStatus.CONFLICT);
        }
    }
    
    public TipoPago update(long id, TipoEstadoDTO tipoPagoNuevo){
        Optional<TipoPago> optionalTipoPago = tipoPagoRepository.findById(id);
        if(!optionalTipoPago.isEmpty()){
            TipoPago tipoPagoTemp = tipoPagoRepository.findByDescripcion(tipoPagoNuevo.getDescripcion());
            if(tipoPagoTemp == null||tipoPagoTemp.getIdTipo()==id){
                TipoPago tipoPago = tipoPagoConverter.dtotoEntity(tipoPagoNuevo);
                tipoPago.setIdTipo(optionalTipoPago.get().getIdTipo());
                tipoPago = tipoPagoRepository.save(tipoPago);
                return tipoPago;
            }else{
                throw new Exceptions(message(tipoPagoNuevo.getDescripcion()), HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean delete(long id){
        Optional<TipoPago> optionalTipoPago = tipoPagoRepository.findById(id);
        if(!optionalTipoPago.isEmpty()){
            try{
                tipoPagoRepository.delete(optionalTipoPago.get());
                return true;
            }catch(Exception ex){
                throw new Exceptions("El tipo de pago: "+optionalTipoPago.get().getDescripcion()+" no se puede eliminar ya que presenta relaciones.", HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public TipoPago findById(long id){
        Optional<TipoPago> optionalTipoPago = tipoPagoRepository.findById(id);
        if(!optionalTipoPago.isEmpty()){
            return optionalTipoPago.get();
        }else{
            return null;
        }
    }
    
    public List<TipoPago> findAll(){
        return tipoPagoRepository.findAll();
    }
}
