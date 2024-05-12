/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.producto.ProductoNuevoConverter;
import org.uv.delivery.converters.producto.ProductoRegistradoConverter;
import org.uv.delivery.dtos.producto.ProductoNuevoDTO;
import org.uv.delivery.dtos.producto.ProductoRegistradoDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.Producto;
import org.uv.delivery.models.Tienda;
import org.uv.delivery.repository.CategoriaRepository;
import org.uv.delivery.repository.ProductoRepository;
import org.uv.delivery.repository.TiendaRepository;

/**
 *
 * @author juan
 */
@Service
public class ProductoService {
    private final ProductoNuevoConverter productoNuevoConverter;
    private final ProductoRegistradoConverter productoRegistradoConverter;
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final TiendaRepository tiendaRepository;
    
    @Value("${message.general.inautorizado}")
    private String acceso;
    
    public ProductoService(ProductoNuevoConverter productoNuevoConverter,
            ProductoRegistradoConverter productoRegistradoConverter,
            ProductoRepository productoRepository, CategoriaRepository categoriaRepository,
            TiendaRepository tiendaRepository){
        this.productoNuevoConverter = productoNuevoConverter;
        this.productoRegistradoConverter = productoRegistradoConverter;
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.tiendaRepository = tiendaRepository;
    }
    
    public String categoriaNotFound(long id){
        return "La categoria con id: "+id+" no existe.";
    }
    
    public ProductoRegistradoDTO save(ProductoNuevoDTO productoNuevo){
        Optional<Tienda> tienda = tiendaRepository.findById(productoNuevo.getIdTienda());
        String email=SecurityContextHolder.getContext().getAuthentication().getName();
        if(!tienda.isEmpty()){
            if (tienda.get().getEncargado().getEmail().equals(email)){
                for(long id:productoNuevo.getCategoriasId()){
                    if(categoriaRepository.findById(id).isEmpty()){
                        throw new Exceptions(categoriaNotFound(id), HttpStatus.CONFLICT);
                    }
                }
                Producto producto = productoNuevoConverter.dtotoEntity(productoNuevo);
                producto = productoRepository.save(producto);
                return productoRegistradoConverter.entitytoDTO(producto);
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null; //Tienda no encontrada
        }
    }
    
    public ProductoRegistradoDTO update(long id, ProductoNuevoDTO productoNuevo){
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if(!optionalProducto.isEmpty()){
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            if (optionalProducto.get().getTienda().getEncargado().getEmail().equals(email)){
                for(long categoriaId:productoNuevo.getCategoriasId()){
                    if(categoriaRepository.findById(categoriaId).isEmpty()){
                        throw new Exceptions(categoriaNotFound(categoriaId), HttpStatus.CONFLICT);
                    }
                }
                Producto producto = productoNuevoConverter.dtotoEntity(productoNuevo);
                producto.setIdProducto(id);
                producto.setTienda(optionalProducto.get().getTienda());
                producto = productoRepository.save(producto);
                return productoRegistradoConverter.entitytoDTO(producto);
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null; //Producto no encontrado
        }
    }
    
    public boolean delete(long id){
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if(!optionalProducto.isEmpty()){
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            if (optionalProducto.get().getTienda().getEncargado().getEmail().equals(email)){
                productoRepository.delete(optionalProducto.get());
                return true;
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public ProductoRegistradoDTO findById(long id){
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if(!optionalProducto.isEmpty()){
            return productoRegistradoConverter.entitytoDTO(optionalProducto.get());
        }else{
            return null;
        }
    }
    
    public List<ProductoRegistradoDTO> findAll(){
        List<Producto> productos = productoRepository.findAll();
        return productoRegistradoConverter.entityListtoDTOList(productos);
    }
}
