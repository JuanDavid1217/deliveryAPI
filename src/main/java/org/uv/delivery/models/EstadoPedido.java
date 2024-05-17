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
@Table(name="estado_pedido")
public class EstadoPedido implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="estado_pedido_id_estado_seq")
    @SequenceGenerator(name="estado_pedido_id_estado_seq", sequenceName="estado_pedido_id_estado_seq", initialValue=1, allocationSize=1)
    @Column(name="id_estado")
    private long idEstado;
    @Column(name="estado_pedido")
    private String descripcion;

    public long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(long idEstado) {
        this.idEstado = idEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
