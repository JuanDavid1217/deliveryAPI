/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.uv.delivery.models.usuario.Cliente;
import org.uv.delivery.models.usuario.Repartidor;

/**
 *
 * @author juan
 */
@Entity
@Table(name="nota_venta")
public class Venta implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="nota_venta_n_venta_seq")
    @SequenceGenerator(name="nota_venta_n_venta_seq", sequenceName="nota_venta_n_venta_seq", initialValue=1, allocationSize=1)
    @Column(name="n_venta")
    private long idVenta;
    @Column(name="fecha_creacion")
    private Date fechaCreacion;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cliente")
    private Cliente cliente;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="estado_pago")
    private EstadoPago estadoPago;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="tipo_pago")
    private TipoPago tipoPago;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="estado_pedido")
    private EstadoPedido estadoPedido;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="repartidor")
    private Repartidor repartidor;
    @OneToMany(mappedBy="venta", fetch=FetchType.LAZY, cascade={CascadeType.REMOVE, CascadeType.MERGE})
    private List<DetalleVenta> detalles;
    @Column()
    private BigDecimal total;

    public long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
