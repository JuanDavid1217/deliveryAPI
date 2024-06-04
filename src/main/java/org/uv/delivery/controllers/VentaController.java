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
import org.uv.delivery.dtos.ventas.VentaNuevaDTO;
import org.uv.delivery.dtos.ventas.VentaRegistradaDTO;
import org.uv.delivery.models.Venta;
import org.uv.delivery.services.VentaService;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/ventas")
public class VentaController {
    private final VentaService ventaService;
    
    public VentaController(VentaService ventaService){
        this.ventaService = ventaService;
    }
    
    @PostMapping()
    public ResponseEntity<VentaRegistradaDTO> save(@RequestBody VentaNuevaDTO ventaNueva){
        VentaRegistradaDTO venta = ventaService.save(ventaNueva);
        URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(venta.getIdVenta()).toUri();
        
            return ResponseEntity.created(ubication).body(venta);
    }
    
    @PutMapping("/{idVenta}")
    public ResponseEntity<Void> update(@PathVariable("idVenta") long idVenta, @RequestBody VentaNuevaDTO ventaNueva){
        VentaRegistradaDTO venta = ventaService.update(idVenta, ventaNueva);
        if (venta!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{idVenta}")
    public ResponseEntity<Void> delete(@PathVariable("idVenta") long idVenta){
        boolean response = ventaService.delete(idVenta);
        if (response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{idVenta}")
    public ResponseEntity<VentaRegistradaDTO> findById(@PathVariable("idVenta") long idVenta){
        VentaRegistradaDTO venta = ventaService.findById(idVenta);
        if (venta!=null){
            return ResponseEntity.ok(venta);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/comprasPorCliente/{idCliente}")
    public ResponseEntity<List<VentaRegistradaDTO>> findAllByCliente(@PathVariable("idCliente") long idCliente){
        List<VentaRegistradaDTO> ventas = ventaService.findAllByCliente(idCliente);
        return ResponseEntity.ok(ventas);
    }
}
