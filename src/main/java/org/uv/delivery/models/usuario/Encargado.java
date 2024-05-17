/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.models.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.uv.delivery.models.Tienda;

/**
 *
 * @author juan
 */
@Entity
@Table(name="encargado")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Encargado extends Trabajador implements Serializable{
    @OneToMany(mappedBy="encargado", cascade={CascadeType.REMOVE, CascadeType.MERGE}, fetch=FetchType.LAZY)
    private List<Tienda> tiendas;

    public List<Tienda> getTienda() {
        return tiendas;
    }

    public void setTienda(List<Tienda> tiendas) {
        this.tiendas = tiendas;
    }
    
    
}
