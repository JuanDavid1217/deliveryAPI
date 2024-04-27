/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uv.delivery.dtos.DireccionNuevaDTO;
import org.uv.delivery.models.Direccion;
import org.uv.delivery.services.DireccionService;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/direccion")
public class DireccionController {
    private final DireccionService direccionService;
    public DireccionController(DireccionService direccionService){
        this.direccionService=direccionService;
    }
    
    @PutMapping("/actualizarPorUsuario/{id}")
    public ResponseEntity<Void> updateByUser(@RequestBody DireccionNuevaDTO direccionNueva, @PathVariable("id") long id){
        Direccion direccion = direccionService.update(direccionNueva, id);
        if (direccion!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
