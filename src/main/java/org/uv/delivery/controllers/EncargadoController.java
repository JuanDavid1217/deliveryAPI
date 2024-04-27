/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.controllers;

import java.net.URI;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.uv.delivery.dtos.usuarios.EncargadoActualizarDTO;
import org.uv.delivery.dtos.usuarios.EncargadoNuevoDTO;
import org.uv.delivery.dtos.usuarios.EncargadoRegistradoDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.services.EncargadoService;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/encargados")
public class EncargadoController {
    private final EncargadoService encargadoService;
    
    public EncargadoController(EncargadoService encargadoService){
        this.encargadoService=encargadoService;
    }
    
    @PostMapping("/crearCuenta")
    public ResponseEntity<EncargadoRegistradoDTO> save(@RequestBody EncargadoNuevoDTO encargadoNuevo){
       EncargadoRegistradoDTO encargado = encargadoService.save(encargadoNuevo);
       if(encargado!=null){
           URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(encargado.getIdUsuario()).toUri();
        
            return ResponseEntity.created(ubication).body(encargado);
       }else{
           throw new Exceptions("El email ingresado ya se encuentra registrado en otra cuenta.", HttpStatus.CONFLICT);
       }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id, @RequestBody EncargadoActualizarDTO encargadoActulizar){
        EncargadoRegistradoDTO encargado = encargadoService.update(encargadoActulizar, id);
        if (encargado!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        boolean response = encargadoService.delete(id);
        if(response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EncargadoRegistradoDTO> findById(@PathVariable("id") long id){
        EncargadoRegistradoDTO encargado = encargadoService.findById(id);
        if (encargado!=null){
            return ResponseEntity.ok(encargado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<EncargadoRegistradoDTO>> findAll(){
        List<EncargadoRegistradoDTO> encargados = encargadoService.findAll();
        return ResponseEntity.ok(encargados);
    }
}
