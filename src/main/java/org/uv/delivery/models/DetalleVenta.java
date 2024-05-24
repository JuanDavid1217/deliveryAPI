/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.models;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author juan
 */
@Entity
@Table(name="detalle_venta")
public class DetalleVenta implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="detalle_venta_id_detalle_seq")
    @SequenceGenerator(name="detalle_venta_id_detalle_seq", sequenceName="detalle_venta_id_detalle_seq", initialValue=1, allocationSize=1)
    @Column(name="id_detalle")
    private long idDetalle;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_venta")
    private Venta venta;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="producto")
    private Producto producto;
    @Column()
    private int cantidad;
    @Column()
    private BigDecimal precio;
    @Column()
    private double decuento;

    public long getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(long idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        this.precio = producto.getPrecio();
        this.decuento = producto.getDescuento();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public double getDecuento() {
        return decuento;
    }
    
    public BigDecimal calculateSub(){
        BigDecimal sub=this.precio.multiply(new BigDecimal(this.cantidad));
        BigDecimal discount=sub.multiply(new BigDecimal(this.decuento));
        discount = discount.divide(new BigDecimal(100));
        return null;
    }
}
