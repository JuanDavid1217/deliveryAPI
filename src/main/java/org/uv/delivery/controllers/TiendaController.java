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
import org.uv.delivery.dtos.tienda.TiendaDTO;
import org.uv.delivery.dtos.tienda.TiendaNuevaDTO;
import org.uv.delivery.dtos.tienda.TiendaRegistradaDTO;
import org.uv.delivery.services.TiendaService;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/tienda")
public class TiendaController {
    private final TiendaService tiendaService;
    
    public TiendaController(TiendaService tiendaService){
        this.tiendaService = tiendaService;
    }
    
    @PostMapping("/guardarTiendaporEncargado/{id}")
    public ResponseEntity<TiendaRegistradaDTO> save(@PathVariable("id") long idEncargado, @RequestBody TiendaNuevaDTO tiendaNueva){
        TiendaRegistradaDTO tienda = tiendaService.save(idEncargado, tiendaNueva);
        if(tienda!=null){
            URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                 .buildAndExpand(tienda.getIdTienda()).toUri();

            return ResponseEntity.created(ubication).body(tienda);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long idTienda, @RequestBody TiendaDTO tiendaActualizar){
        TiendaRegistradaDTO tienda = tiendaService.update(idTienda, tiendaActualizar);
        if (tienda!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long idTienda){
        boolean response = tiendaService.delete(idTienda);
        if (response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TiendaRegistradaDTO> findById(@PathVariable("id") long idTienda){
        TiendaRegistradaDTO tienda = tiendaService.findById(idTienda);
        if (tienda!=null){
            return ResponseEntity.ok().body(tienda);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<TiendaRegistradaDTO>> findAll(){
        return ResponseEntity.ok().body(tiendaService.findAll());
    }
}
