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
import org.uv.delivery.models.TipoPago;
import org.uv.delivery.services.TipoPagoService;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/tipoPago")
public class TipoPagoController {
    private final TipoPagoService tipoPagoService;
    
    public TipoPagoController(TipoPagoService tipoPagoService){
        this.tipoPagoService = tipoPagoService;
    }
    
    @PostMapping()
    public ResponseEntity<TipoPago> save(@RequestBody TipoEstadoDTO tipoPagoNuevoDTO){
        TipoPago tipoPago = tipoPagoService.save(tipoPagoNuevoDTO);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(tipoPago.getIdTipo()).toUri();

        return ResponseEntity.created(ubication).body(tipoPago);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id, @RequestBody TipoEstadoDTO tipoPagoNuevoDTO){
        TipoPago tipoPago = tipoPagoService.update(id, tipoPagoNuevoDTO);
        if (tipoPago!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        boolean borrado = tipoPagoService.delete(id);
        if (borrado){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<TipoPago>> findAll(){
        List<TipoPago> tipos = tipoPagoService.findAll();
        return ResponseEntity.ok(tipos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TipoPago> findById(@PathVariable("id") long id){
        TipoPago tipoPago = tipoPagoService.findById(id);
        if (tipoPago!=null){
            return ResponseEntity.ok(tipoPago);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
