/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.models.usuario;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.uv.delivery.models.Vehiculo;

/**
 *
 * @author juan
 */
@Entity
@Table(name="repartidores")
public class Repartidor extends Trabajador implements Serializable{
    @Column()
    private String licencia;
    @OneToOne(cascade={CascadeType.REMOVE, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="id_vehiculo", foreignKey = @ForeignKey(name = "fk_trabajador_direccion"))
    private Vehiculo vehiculo;
    
    
    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    
}
