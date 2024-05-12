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
import org.uv.delivery.models.EstadoPedido;
import org.uv.delivery.services.EstadoPedidoService;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/estadoPedido")
public class EstadoPedidoController {
    private final EstadoPedidoService estadoPedidoService;
    
    public EstadoPedidoController(EstadoPedidoService estadoPedidoService){
        this.estadoPedidoService = estadoPedidoService;
    }
    
    @PostMapping()
    public ResponseEntity<EstadoPedido> save(@RequestBody TipoEstadoDTO estadoPedidoNuevoDTO){
        EstadoPedido estadoPedido = estadoPedidoService.save(estadoPedidoNuevoDTO);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(estadoPedido.getIdEstado()).toUri();

        return ResponseEntity.created(ubication).body(estadoPedido);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id, @RequestBody TipoEstadoDTO estadoPedidoNuevoDTO){
        EstadoPedido estadoPedido = estadoPedidoService.update(id, estadoPedidoNuevoDTO);
        if (estadoPedido!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        boolean borrado = estadoPedidoService.delete(id);
        if (borrado){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<EstadoPedido>> findAll(){
        List<EstadoPedido> estados = estadoPedidoService.findAll();
        return ResponseEntity.ok(estados);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EstadoPedido> findById(@PathVariable("id") long id){
        EstadoPedido estadoPedido = estadoPedidoService.findById(id);
        if (estadoPedido!=null){
            return ResponseEntity.ok(estadoPedido);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
