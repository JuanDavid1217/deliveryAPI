/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.DireccionConverter;
import org.uv.delivery.dtos.DireccionNuevaDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.Direccion;
import org.uv.delivery.models.Tienda;
import org.uv.delivery.models.usuario.UsuarioBase;
import org.uv.delivery.repository.DireccionRepository;
import org.uv.delivery.repository.TiendaRepository;
import org.uv.delivery.repository.UsuarioRepository;

/**
 *
 * @author juan
 */
@Service
public class DireccionService {
    private final DireccionRepository direccionRepository;
    private final DireccionConverter direccionConverter;
    private final UsuarioRepository usuarioRepository;
    private final TiendaRepository tiendaRepository;
    
    @Value("${message.general.inautorizado}")
    private String acceso;
    
    public DireccionService(DireccionRepository direccionRepository,
            DireccionConverter direccionConverter, UsuarioRepository usuarioRepository,
            TiendaRepository tiendaRepository){
        this.direccionConverter=direccionConverter;
        this.direccionRepository=direccionRepository;
        this.usuarioRepository=usuarioRepository;
        this.tiendaRepository = tiendaRepository;
    }
    
    public Direccion updateUsuarioDireccion(DireccionNuevaDTO direccionNueva, long idUsuario){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UsuarioBase> optionalUsuario = usuarioRepository.findById(idUsuario);
        if(!optionalUsuario.isEmpty()){
            if (email.equals(optionalUsuario.get().getEmail())){
               Direccion direccion = direccionConverter.dtotoEntity(direccionNueva);
                direccion.setIdDireccion(optionalUsuario.get().getDireccion().getIdDireccion());
                return direccionRepository.save(direccion);
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            } 
        }else{
            return null;
        }
    }
    
    public Direccion updateTiendaDireccion(DireccionNuevaDTO direccionNueva, long idTienda){
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Tienda> optionalTienda = tiendaRepository.findById(idTienda);
        if(!optionalTienda.isEmpty()){
            if(email.equals(optionalTienda.get().getEncargado().getEmail())){
                Direccion direccion = direccionConverter.dtotoEntity(direccionNueva);
                direccion.setIdDireccion(optionalTienda.get().getDireccion().getIdDireccion());
                return direccionRepository.save(direccion);
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
}
