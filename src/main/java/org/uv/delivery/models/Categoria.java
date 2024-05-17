/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author juan
 */
@Entity
@Table(name="categoria")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Categoria implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="categorias_id_categoria_seq")
    @SequenceGenerator(name="categorias_id_categoria_seq", sequenceName="categorias_id_categoria_seq", initialValue=1, allocationSize=1)
    @Column(name="id_categoria")
    private long idCategoria;
    @Column(name="categoria")
    private String descripcion;

    public long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
