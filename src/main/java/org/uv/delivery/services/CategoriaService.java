/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.CategoriaConverter;
import org.uv.delivery.dtos.CategoriaNuevaDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.Categoria;
import org.uv.delivery.repository.CategoriaRepository;

/**
 *
 * @author juan
 */
@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaConverter categoriaConverter;
    
    @Value("${message.categoriaService.categoria}")
    private String categoriaRegistrada;
    
    public CategoriaService(CategoriaRepository categoriaRepository,
            CategoriaConverter categoriaConverter){
        this.categoriaConverter = categoriaConverter;
        this.categoriaRepository = categoriaRepository;
    }
    
    public Categoria save(CategoriaNuevaDTO categoriaNueva){
        Categoria categoria = categoriaRepository.findByDescripcion(categoriaNueva.getDescripcion());
        if (categoria == null){
            categoria = categoriaConverter.dtotoEntity(categoriaNueva);
            categoria = categoriaRepository.save(categoria);
            return categoria;
        }else{
            throw new Exceptions(categoriaRegistrada, HttpStatus.CONFLICT);        
        }
    }
    
    public Categoria update(CategoriaNuevaDTO categoriaNueva, long id){
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        if (!optionalCategoria.isEmpty()){
            Categoria categoria = categoriaRepository.findByDescripcion(categoriaNueva.getDescripcion());
            if (categoria == null || (categoria!=null && categoria.getIdCategoria()==optionalCategoria.get().getIdCategoria())){
                categoria = categoriaConverter.dtotoEntity(categoriaNueva);
                categoria.setIdCategoria(optionalCategoria.get().getIdCategoria());
                categoria = categoriaRepository.save(categoria);
                return categoria;
            }else{
                throw new Exceptions(categoriaRegistrada, HttpStatus.CONFLICT);
            }
        }else{
            return null;        
        }
    }
    
    public boolean delete(long id){
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        if (!optionalCategoria.isEmpty()){
            try{
                categoriaRepository.delete(optionalCategoria.get());
                return true;
            }catch(Exception e){
                throw new Exceptions("La categoria: "+optionalCategoria.get().getDescripcion()+" no puede ser Eliminada, ya que presenta RELACION.", HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public Categoria findById(long id){
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        if (!optionalCategoria.isEmpty()){
            return optionalCategoria.get();
        }else{
            return null;
        }
    }
    
    public List<Categoria> findAll(){
        return categoriaRepository.findAll();
    }
}
