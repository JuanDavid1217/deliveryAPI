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
@Table(name="direccion")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Direccion implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="direccion_id_direccion_seq")
    @SequenceGenerator(name="direccion_id_direccion_seq", sequenceName="direccion_id_direccion_seq", initialValue=1, allocationSize=1)
    @Column(name="id_direccion")
    private long idDireccion;
    @Column()
    private int cp;
    @Column()
    private String estado;
    @Column()
    private String municipio;
    @Column()
    private String colonia;
    @Column()
    private String calle1;
    @Column()
    private String calle2;
    @Column()
    private String calle3;
    @Column()
    private String referencia;
    @Column(name="nomeroext")
    private String numeroExterior;
    @Column(name="nomeroint")
    private String numeroInterior;

    public long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCalle1() {
        return calle1;
    }

    public void setCalle1(String calle1) {
        this.calle1 = calle1;
    }

    public String getCalle2() {
        return calle2;
    }

    public void setCalle2(String calle2) {
        this.calle2 = calle2;
    }

    public String getCalle3() {
        return calle3;
    }

    public void setCalle3(String calle3) {
        this.calle3 = calle3;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    
    
    
}
