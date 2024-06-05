/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.models.usuario;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.uv.delivery.models.Venta;

/**
 *
 * @author juan
 */
@Entity
@Table(name="usuario")
public class Cliente extends UsuarioBase implements Serializable{
    @OneToMany(mappedBy="cliente", cascade={CascadeType.REMOVE, CascadeType.MERGE}, fetch=FetchType.LAZY)
    private List<Venta> ventas;
    
    public List<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(List<Venta> ventas) {
        this.ventas = ventas;
    }
    
    
}
