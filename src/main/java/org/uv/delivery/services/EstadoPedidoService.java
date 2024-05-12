/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.EstadoPedidoConverter;
import org.uv.delivery.dtos.TipoEstadoDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.EstadoPedido;
import org.uv.delivery.repository.EstadoPedidoRepository;

/**
 *
 * @author juan
 */
@Service
public class EstadoPedidoService {
    private final EstadoPedidoConverter estadoPedidoConverter;
    private final EstadoPedidoRepository estadoPedidoRepository;
    
    public EstadoPedidoService(EstadoPedidoConverter estadoPedidoConverter,
            EstadoPedidoRepository estadoPedidoRepository){
        this.estadoPedidoConverter = estadoPedidoConverter;
        this.estadoPedidoRepository = estadoPedidoRepository;
    }
    
    public String message(String descripcion){
        return "El Estado de Pago: "+descripcion+" ya se encuentra registrado.";
    }
    
    public EstadoPedido save(TipoEstadoDTO estadoPedidoNuevo){
        EstadoPedido estadoPedido = estadoPedidoRepository.findByDescripcion(estadoPedidoNuevo.getDescripcion());
        if (estadoPedido == null){
            estadoPedido = estadoPedidoConverter.dtotoEntity(estadoPedidoNuevo);
            estadoPedido = estadoPedidoRepository.save(estadoPedido);
            return estadoPedido;
        }else{
             throw new Exceptions(message(estadoPedidoNuevo.getDescripcion()), HttpStatus.CONFLICT);
        }
    }
    
    public EstadoPedido update(long id, TipoEstadoDTO estadoPedidoNuevo){
        Optional<EstadoPedido> optionalEstadoPedido = estadoPedidoRepository.findById(id);
        if(!optionalEstadoPedido.isEmpty()){
            EstadoPedido estadoPedidoTemp = estadoPedidoRepository.findByDescripcion(estadoPedidoNuevo.getDescripcion());
            if(estadoPedidoTemp == null||estadoPedidoTemp.getIdEstado()==id){
                EstadoPedido estadoPedido = estadoPedidoConverter.dtotoEntity(estadoPedidoNuevo);
                estadoPedido.setIdEstado(optionalEstadoPedido.get().getIdEstado());
                estadoPedido = estadoPedidoRepository.save(estadoPedido);
                return estadoPedido;
            }else{
                throw new Exceptions(message(estadoPedidoNuevo.getDescripcion()), HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean delete(long id){
        Optional<EstadoPedido> optionalEstadoPedido = estadoPedidoRepository.findById(id);
        if(!optionalEstadoPedido.isEmpty()){
            try{
                estadoPedidoRepository.delete(optionalEstadoPedido.get());
                return true;
            }catch(Exception ex){
                throw new Exceptions("El estado: "+optionalEstadoPedido.get().getDescripcion()+" no se puede eliminar ya que presenta relaciones.", HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public EstadoPedido findById(long id){
        Optional<EstadoPedido> optionalEstadoPedido = estadoPedidoRepository.findById(id);
        if(!optionalEstadoPedido.isEmpty()){
            return optionalEstadoPedido.get();
        }else{
            return null;
        }
    }
    
    public List<EstadoPedido> findAll(){
        return estadoPedidoRepository.findAll();
    }
}
