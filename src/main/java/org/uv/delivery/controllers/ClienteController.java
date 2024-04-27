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
import org.uv.delivery.dtos.usuarios.ClienteActualizarDTO;
import org.uv.delivery.dtos.usuarios.ClienteNuevoDTO;
import org.uv.delivery.dtos.usuarios.ClienteRegistradoDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.services.ClienteService;

/**
 *
 * @author juan
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;
    
    public ClienteController(ClienteService clienteService){
        this.clienteService=clienteService;
    }
    
    @PostMapping("/crearCuenta")
    public ResponseEntity<ClienteRegistradoDTO> save(@RequestBody ClienteNuevoDTO clienteNuevo){
       ClienteRegistradoDTO cliente = clienteService.save(clienteNuevo);
       if(cliente!=null){
           URI ubication = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(cliente.getIdUsuario()).toUri();
        
            return ResponseEntity.created(ubication).body(cliente);
       }else{
           throw new Exceptions("El email ingresado ya se encuentra registrado en otra cuenta.", HttpStatus.CONFLICT);
       }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") long id, @RequestBody ClienteActualizarDTO clienteActulizar){
        ClienteRegistradoDTO cliente=clienteService.update(clienteActulizar, id);
        if (cliente!=null){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id){
        boolean response = clienteService.delete(id);
        if(response){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClienteRegistradoDTO> findById(@PathVariable("id") long id){
        ClienteRegistradoDTO cliente = clienteService.findById(id);
        if (cliente!=null){
            return ResponseEntity.ok(cliente);
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping()
    public ResponseEntity<List<ClienteRegistradoDTO>> findAll(){
        List<ClienteRegistradoDTO> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }
}
