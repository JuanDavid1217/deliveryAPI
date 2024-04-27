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
import org.uv.delivery.models.Genero;
import org.uv.delivery.services.GeneroServices;
import org.uv.delivery.dtos.GeneroNuevoDTO;
import org.uv.delivery.exceptions.Exceptions;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/genero")
public class GeneroController {
    private final GeneroServices generoServices;
    
    public GeneroController(GeneroServices generoServices){
        this.generoServices=generoServices;
    }
    
    @PostMapping()
    public ResponseEntity<Genero> save(@RequestBody GeneroNuevoDTO generoNuevoDTO){
        Genero genero = generoServices.saveGenero(generoNuevoDTO);
        if (genero!=null){
            URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(genero.getIdGenero()).toUri();
        
            return ResponseEntity.created(ubication).body(genero);
            
        }else{
            throw new Exceptions("El genero "+generoNuevoDTO.getDescripcion()+" ya se encuentra registrado.", HttpStatus.CONFLICT);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody GeneroNuevoDTO generoNuevoDTO, @PathVariable("id") long id){
        Genero genero = generoServices.updateGenero(generoNuevoDTO, id);
        if (genero!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        boolean borrado = generoServices.deleteGenero(id);
        if (borrado){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<Genero>> findAll(){
        List<Genero> generos = generoServices.findAll();
        return ResponseEntity.ok(generos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Genero> findById(@PathVariable("id") long id){
        Genero genero = generoServices.findById(id);
        if (genero!=null){
            return ResponseEntity.ok(genero);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
