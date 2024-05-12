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
import org.uv.delivery.dtos.producto.ProductoNuevoDTO;
import org.uv.delivery.dtos.producto.ProductoRegistradoDTO;
import org.uv.delivery.services.ProductoService;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService productoService;
    
    public ProductoController(ProductoService productoService){
        this.productoService = productoService;
    }
    
    @PostMapping()
    public ResponseEntity<ProductoRegistradoDTO> save(@RequestBody ProductoNuevoDTO productoNuevo){
        ProductoRegistradoDTO producto = productoService.save(productoNuevo);
        if(producto != null){
            URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(producto.getIdProducto()).toUri();
        
            return ResponseEntity.created(ubication).body(producto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id, @RequestBody ProductoNuevoDTO productoNuevo){
        ProductoRegistradoDTO producto = productoService.update(id, productoNuevo);
        if(producto != null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(long id){
        boolean response = productoService.delete(id);
        if(response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductoRegistradoDTO> findById(long id){
        ProductoRegistradoDTO producto = productoService.findById(id);
        if (producto != null){
            return ResponseEntity.ok(producto);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<ProductoRegistradoDTO>> findAll(){
        List<ProductoRegistradoDTO> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }
}
