/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.GeneroConverter;
import org.uv.delivery.dtos.GeneroNuevoDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.Genero;
import org.uv.delivery.repository.GeneroRepository;

/**
 *
 * @author juan
 */
@Service
public class GeneroServices {
    private final GeneroRepository generoRepository;
    private final GeneroConverter generoConverter;
    
    public GeneroServices(GeneroRepository generoRepository, GeneroConverter generoConverter){
        this.generoRepository=generoRepository;
        this.generoConverter=generoConverter;
    }
    
    public Genero saveGenero(GeneroNuevoDTO generoNuevoDTO){
        Genero genero =  generoRepository.findByDescripcion(generoNuevoDTO.getDescripcion());
        if(genero==null){
            genero=generoConverter.dtotoEntity(generoNuevoDTO);
            genero=generoRepository.save(genero);
            return genero;
        }else{
            return null;
        }
    }
    
    public boolean deleteGenero(long id){
        Optional<Genero> optionalGenero = generoRepository.findById(id);
        if(!optionalGenero.isEmpty()){
            Genero genero = optionalGenero.get();
            try{
                generoRepository.delete(genero);
                return true;
            }catch(Exception e){
                throw new Exceptions("El genero: "+optionalGenero.get().getDescripcion()+" no se puede eliminar ya que presenta relaciones.", HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public Genero updateGenero(GeneroNuevoDTO generoNuevoDTO, long id){
        Optional<Genero> optionalGenero = generoRepository.findById(id);
        if(!optionalGenero.isEmpty()){
            Genero genero = generoRepository.findByDescripcion(generoNuevoDTO.getDescripcion());
            if(genero==null || (genero!=null && genero.getIdGenero()==optionalGenero.get().getIdGenero())){
                genero = optionalGenero.get();
                genero.setDescripcion(generoNuevoDTO.getDescripcion());
                genero=generoRepository.save(genero);
            }else{
                throw new Exceptions("El genero "+generoNuevoDTO.getDescripcion()+" ya se encuentra registrado.", HttpStatus.CONFLICT);
            }
            return genero;
        }else{
            return null;
        }
    }
    
    public Genero findById(long id){
        Optional<Genero> optionalGenero = generoRepository.findById(id);
        if(!optionalGenero.isEmpty()){
            return optionalGenero.get();
        }else{
            return null;
        }
    }
    
    public List<Genero> findAll(){
        return generoRepository.findAll();
    }
}
