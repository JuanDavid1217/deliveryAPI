/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.models;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author juan
 */
@Entity
@Table(name="tipo_pago")
public class TipoPago implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tipo_pago_id_tipo_seq")
    @SequenceGenerator(name="tipo_pago_id_tipo_seq", sequenceName="tipo_pago_id_tipo_seq", initialValue=1, allocationSize=1)
    @Column(name="id_tipo")
    private long idTipo;
    @Column(name="tipo_pago")
    private String descripcion;

    public long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(long idTipo) {
        this.idTipo = idTipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
