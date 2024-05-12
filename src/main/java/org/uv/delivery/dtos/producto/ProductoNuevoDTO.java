/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.dtos.producto;

import java.util.List;

/**
 *
 * @author juan
 */
public class ProductoNuevoDTO extends ProductoDTO{
    private long idTienda;
    private List<Long> categoriasId;

    public long getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(long idTienda) {
        this.idTienda = idTienda;
    }

    public List<Long> getCategoriasId() {
        return categoriasId;
    }

    public void setCategoriasId(List<Long> categoriasId) {
        this.categoriasId = categoriasId;
    }
    
    
}
