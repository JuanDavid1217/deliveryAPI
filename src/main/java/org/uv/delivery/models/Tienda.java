/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.uv.delivery.models.usuario.Encargado;

/**
 *
 * @author juan
 */
@Entity
@Table(name="tiendas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tienda implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tiendas_id_tienda_seq")
    @SequenceGenerator(name="tiendas_id_tienda_seq", sequenceName="tiendas_id_tienda_seq", initialValue=1, allocationSize=1)
    @Column(name="id_tienda")
    private long idTienda;
    @Column()
    private String nombre;
    @OneToOne(cascade={CascadeType.REMOVE, CascadeType.MERGE}, fetch=FetchType.LAZY)
    @JoinColumn(name="id_direccion")
    private Direccion direccion;
    @Column()
    private String telefono;
    @Column()
    private String email;
    @Column()
    private String descripcion;
    @Column()
    private String horarios;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_encargado")
    private Encargado encargado;
    @Column()
    private double calificacion;

    public long getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(long idTienda) {
        this.idTienda = idTienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }

    public Encargado getEncargado() {
        return encargado;
    }

    public void setEncargado(Encargado encargado) {
        this.encargado = encargado;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }
    
    
}
