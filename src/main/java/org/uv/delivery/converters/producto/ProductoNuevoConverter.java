/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.producto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterNuevo;
import org.uv.delivery.dtos.producto.ProductoNuevoDTO;
import org.uv.delivery.models.Categoria;
import org.uv.delivery.models.Producto;
import org.uv.delivery.models.Tienda;

/**
 *
 * @author juan
 */
@Component
public class ProductoNuevoConverter implements ConverterNuevo<Producto, ProductoNuevoDTO>{

    @Override
    public Producto dtotoEntity(ProductoNuevoDTO dto) {
        Producto producto = new Producto();
        producto.setDescripcion(dto.getDescripcion());
        producto.setDescuento(dto.getDescuento());
        producto.setImagen(dto.getImagen());
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        Tienda tienda = new Tienda();
        tienda.setIdTienda(dto.getIdTienda());
        producto.setTienda(tienda);
        List<Categoria> categorias = new ArrayList<>();
        Categoria categoria = new Categoria();
        for (long id:dto.getCategoriasId()){
            categoria.setIdCategoria(id);
            categorias.add(categoria);
        }
        producto.setCategorias(categorias);
        return producto;
    }

    @Override
    public List<Producto> dtoListtoEntityList(List<ProductoNuevoDTO> dtoList) {
        return dtoList.stream().map(this::dtotoEntity).collect(Collectors.toList());
    }
    
}
