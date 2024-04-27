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
@Table(name="vehiculos")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vehiculo implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="vehiculos_id_vehiculo_seq")
    @SequenceGenerator(name="vehiculos_id_vehiculo_seq", sequenceName="vehiculos_id_vehiculo_seq", initialValue=1, allocationSize=1)
    @Column(name="id_vahiculo")
    private long idVehiculo;
    @Column()
    private String placa;
    @Column()
    private String marca;
    @Column()
    private String tipo;
    @Column()
    private String modelo;
    @Column()
    private String repube;
    @Column()
    private String niv;

    public long getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(long idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getRepube() {
        return repube;
    }

    public void setRepube(String repube) {
        this.repube = repube;
    }

    public String getNiv() {
        return niv;
    }

    public void setNiv(String niv) {
        this.niv = niv;
    }
    
    
}
