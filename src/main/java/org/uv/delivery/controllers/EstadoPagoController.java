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
import org.uv.delivery.dtos.TipoEstadoDTO;
import org.uv.delivery.models.EstadoPago;
import org.uv.delivery.services.EstadoPagoService;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/estadoPago")
public class EstadoPagoController {
    private final EstadoPagoService estadoPagoService;
    
    public EstadoPagoController(EstadoPagoService estadoPagoService){
        this.estadoPagoService = estadoPagoService;
    }
    
    @PostMapping()
    public ResponseEntity<EstadoPago> save(@RequestBody TipoEstadoDTO estadoPagoNuevoDTO){
        EstadoPago estadoPago = estadoPagoService.save(estadoPagoNuevoDTO);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(estadoPago.getIdEstado()).toUri();

        return ResponseEntity.created(ubication).body(estadoPago);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id, @RequestBody TipoEstadoDTO estadoPagoNuevoDTO){
        EstadoPago estadoPago = estadoPagoService.update(id, estadoPagoNuevoDTO);
        if (estadoPago!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        boolean borrado = estadoPagoService.delete(id);
        if (borrado){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<EstadoPago>> findAll(){
        List<EstadoPago> estados = estadoPagoService.findAll();
        return ResponseEntity.ok(estados);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EstadoPago> findById(@PathVariable("id") long id){
        EstadoPago estadoPago = estadoPagoService.findById(id);
        if (estadoPago!=null){
            return ResponseEntity.ok(estadoPago);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
