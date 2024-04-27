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
import org.uv.delivery.dtos.VehiculoNuevoDTO;
import org.uv.delivery.dtos.usuarios.RepartidorActualizarDTO;
import org.uv.delivery.dtos.usuarios.RepartidorNuevoDTO;
import org.uv.delivery.dtos.usuarios.RepartidorRegistradoDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.Vehiculo;
import org.uv.delivery.services.RepartidorService;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/repartidores")
public class RepartidorController {
    private final RepartidorService repartidorService;
    
    public RepartidorController(RepartidorService repartidorService){
        this.repartidorService=repartidorService;
    }
    
    @PostMapping("/crearCuenta")
    public ResponseEntity<RepartidorRegistradoDTO> save(@RequestBody RepartidorNuevoDTO repartidorNuevo){
       RepartidorRegistradoDTO repartidor = repartidorService.save(repartidorNuevo);
       if(repartidor!=null){
           URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(repartidor.getIdUsuario()).toUri();
        
            return ResponseEntity.created(ubication).body(repartidor);
       }else{
           throw new Exceptions("El email ingresado ya se encuentra registrado en otra cuenta.", HttpStatus.CONFLICT);
       }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id, @RequestBody RepartidorActualizarDTO repartidorActulizar){
        RepartidorRegistradoDTO repartidor = repartidorService.update(repartidorActulizar, id);
        if (repartidor!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/actualizarVehiculoPorRepartidor/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id, @RequestBody VehiculoNuevoDTO vehiculoNuevo){
        Vehiculo vehiculo = repartidorService.updateVehiculo(id, vehiculoNuevo);
        if (vehiculo!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        boolean response = repartidorService.delete(id);
        if(response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<RepartidorRegistradoDTO> findById(@PathVariable("id") long id){
        RepartidorRegistradoDTO repartidor = repartidorService.findById(id);
        if (repartidor!=null){
            return ResponseEntity.ok(repartidor);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<RepartidorRegistradoDTO>> findAll(){
        List<RepartidorRegistradoDTO> repartidores = repartidorService.findAll();
        return ResponseEntity.ok(repartidores);
    }
}
