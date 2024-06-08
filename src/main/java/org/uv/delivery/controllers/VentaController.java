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
        if (venta!=null){
            URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(venta.getIdVenta()).toUri();
        
            return ResponseEntity.created(ubication).body(venta);
        }else{
            return ResponseEntity.notFound().build();
        }
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
    
    @GetMapping("/comprasPorCliente/{idVenta}")
    public ResponseEntity<VentaRegistradaDTO> ofClientefindById(@PathVariable("idVenta") long idVenta){
        VentaRegistradaDTO venta = ventaService.ofClientefindById(idVenta);
        if (venta!=null){
            return ResponseEntity.ok(venta);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/allComprasPorCliente/{idCliente}")
    public ResponseEntity<List<VentaRegistradaDTO>> findAllByCliente(@PathVariable("idCliente") long idCliente){
        List<VentaRegistradaDTO> ventas = ventaService.findAllByCliente(idCliente);
        if(ventas!=null){
            return ResponseEntity.ok(ventas);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/entregasPorRepartidor/{idVenta}")
    public ResponseEntity<VentaRegistradaDTO> ofRepartidorfindById(@PathVariable("idVenta") long idVenta){
        VentaRegistradaDTO venta = ventaService.ofRepartidorfindById(idVenta);
        if (venta!=null){
            return ResponseEntity.ok(venta);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/allEntregasPorRepartidor/{idRepartidor}")
    public ResponseEntity<List<VentaRegistradaDTO>> findAllByRepartidor(@PathVariable("idRepartidor") long idRepartidor){
        List<VentaRegistradaDTO> ventas = ventaService.findAllByRepartidor(idRepartidor);
        if(ventas!=null){
            return ResponseEntity.ok(ventas);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/tipoPago/{idVenta}/{idTipoPago}")
    public ResponseEntity<Void> changeTipoPago(@PathVariable("idVenta") long idVenta,
            @PathVariable("idTipoPago") long idTipoPago){
        boolean response = ventaService.changeTipoPago(idVenta, idTipoPago);
        if(response){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/estadoPago/{idVenta}/{idEstadoPago}")
    public ResponseEntity<Void> changeEstadoPago(@PathVariable("idVenta") long idVenta,
            @PathVariable("idEstadoPago") long idEstadoPago){
        boolean response = ventaService.changeEstadoPago(idVenta, idEstadoPago);
        if(response){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/estadoPedido/{idVenta}/{idEstadoPedido}")
    public ResponseEntity<Void> changeEstadoPedido(@PathVariable("idVenta") long idVenta,
            @PathVariable("idEstadoPedido") long idEstadoPedido){
        boolean response = ventaService.changeEstadoPedido(idVenta, idEstadoPedido);
        if(response){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/repartidor/{idVenta}/{idRepartidor}")
    public ResponseEntity<Void> changeRepartidor(@PathVariable("idVenta") long idVenta,
            @PathVariable("idRepartidor") long idRepartidor){
        boolean response = ventaService.changeRepartidor(idVenta, idRepartidor);
        if(response){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
