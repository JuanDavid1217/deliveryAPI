/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name="genero")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Genero implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="genero_id_genero_seq")
    @SequenceGenerator(name="genero_id_genero_seq", sequenceName="genero_id_genero_seq", initialValue=1, allocationSize=1)
    @Column(name="id_genero")
    private long idGenero;
    @Column(name="genero")
    private String descripcion;

    public long getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(long idGenero) {
        this.idGenero = idGenero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
