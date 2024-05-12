/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.converters.producto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.uv.delivery.converters.ConverterRegistrado;
import org.uv.delivery.dtos.producto.ProductoRegistradoDTO;
import org.uv.delivery.models.Categoria;
import org.uv.delivery.models.Producto;

/**
 *
 * @author juan
 */
@Component
public class ProductoRegistradoConverter implements ConverterRegistrado<Producto, ProductoRegistradoDTO>{

    @Override
    public ProductoRegistradoDTO entitytoDTO(Producto entity) {
        ProductoRegistradoDTO producto = new ProductoRegistradoDTO();
        producto.setDescripcion(entity.getDescripcion());
        producto.setDescuento(entity.getDescuento());
        producto.setIdProducto(entity.getIdProducto());
        producto.setImagen(entity.getImagen());
        producto.setNombre(entity.getNombre());
        producto.setPrecio(entity.getPrecio());
        producto.setStock(entity.getStock());
        producto.setIdTienda(entity.getTienda().getIdTienda());
        producto.setCategorias(entity.getCategorias());
        return producto;
    }

    @Override
    public List<ProductoRegistradoDTO> entityListtoDTOList(List<Producto> entityList) {
        return entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());
    }
    
}
