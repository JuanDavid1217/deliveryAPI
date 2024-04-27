/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.DireccionConverter;
import org.uv.delivery.dtos.DireccionNuevaDTO;
import org.uv.delivery.models.Direccion;
import org.uv.delivery.models.usuario.Usuario;
import org.uv.delivery.repository.DireccionRepository;
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
    
    public DireccionService(DireccionRepository direccionRepository,
            DireccionConverter direccionConverter, UsuarioRepository usuarioRepository){
        this.direccionConverter=direccionConverter;
        this.direccionRepository=direccionRepository;
        this.usuarioRepository=usuarioRepository;
    }
    
    public Direccion update(DireccionNuevaDTO direccionNueva, long idUsuario){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(idUsuario);
        if(!optionalUsuario.isEmpty()){
            Direccion direccion = direccionConverter.dtotoEntity(direccionNueva);
            direccion.setIdDireccion(optionalUsuario.get().getDireccion().getIdDireccion());
            return direccionRepository.save(direccion);
        }else{
            return null;
        }
    }
}
