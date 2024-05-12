/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.controllers;

import java.net.URI;
import java.util.List;
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
import org.uv.delivery.dtos.CategoriaNuevaDTO;
import org.uv.delivery.models.Categoria;
import org.uv.delivery.services.CategoriaService;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;
    
    public CategoriaController(CategoriaService categoriaService){
        this.categoriaService = categoriaService;
    }
    
    @PostMapping()
    public ResponseEntity<Categoria> save(@RequestBody CategoriaNuevaDTO categoriaNueva){
        Categoria categoria = categoriaService.save(categoriaNueva);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoria.getIdCategoria()).toUri();
        
            return ResponseEntity.created(ubication).body(categoria);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id,
            @RequestBody CategoriaNuevaDTO categoriaNueva){
        Categoria categoria = categoriaService.update(categoriaNueva, id);
        if(categoria != null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        boolean response = categoriaService.delete(id);
        if (response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable("id") long id){
        Categoria categoria = categoriaService.findById(id);
        if (categoria != null){
            return ResponseEntity.ok(categoria);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<Categoria>> findAll(){
        List<Categoria> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }
    
}
