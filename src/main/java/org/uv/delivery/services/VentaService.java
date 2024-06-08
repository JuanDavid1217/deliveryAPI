/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.uv.delivery.converters.ventas.VentaNuevaConverter;
import org.uv.delivery.converters.ventas.VentaRegistradaConverter;
import org.uv.delivery.dtos.ventas.DetalleVentaNuevoDTO;
import org.uv.delivery.dtos.ventas.VentaNuevaDTO;
import org.uv.delivery.dtos.ventas.VentaRegistradaDTO;
import org.uv.delivery.exceptions.Exceptions;
import org.uv.delivery.models.DetalleVenta;
import org.uv.delivery.models.EstadoPago;
import org.uv.delivery.models.EstadoPedido;
import org.uv.delivery.models.Producto;
import org.uv.delivery.models.TipoPago;
import org.uv.delivery.models.Venta;
import org.uv.delivery.models.usuario.Cliente;
import org.uv.delivery.models.usuario.Repartidor;
import org.uv.delivery.repository.ClienteRepository;
import org.uv.delivery.repository.DetalleVentaRepository;
import org.uv.delivery.repository.EstadoPagoRepository;
import org.uv.delivery.repository.EstadoPedidoRepository;
import org.uv.delivery.repository.ProductoRepository;
import org.uv.delivery.repository.RepartidorRepository;
import org.uv.delivery.repository.TipoPagoRepository;
import org.uv.delivery.repository.VentaRepository;
import static org.uv.delivery.validations.Validation.dateValidation;

/**
 *
 * @author juan
 */
@Service
public class VentaService {
    private final VentaRepository ventaRepository;
    private final VentaNuevaConverter ventaNuevaConverter;
    private final VentaRegistradaConverter ventaRegistradaConverter;
    private final ClienteRepository clienteRepository;
    private final EstadoPedidoRepository estadoPedidoRepository;
    private final EstadoPagoRepository estadoPagoRepository;
    private final TipoPagoRepository tipoPagoRepository;
    private final ProductoRepository productoRepository;
    private final RepartidorRepository repartidorRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    
    @Value("${message.general.inautorizado}")
    private String acceso;
    @Value("${message.usuarioService.fecha}")
    private String fechaInvalida;
    @Value("${message.ventaService.producto}")
    private String productoNoEncontrado;
    @Value("${message.ventaService.estadoPedido}")
    private String estadoPedidoNoEncontrado;
    @Value("${message.ventaService.estadoPago}")
    private String estadoPagoNoEncontrado;
    @Value("${message.ventaService.tipoPago}")
    private String tipoPagoNoEncontrado;
    @Value("${message.ventaService.repartidor}")
    private String repartidorNoEncontrado;
    
    public VentaService(VentaRepository ventaRepository,
            VentaNuevaConverter ventaNuevaConverter,
            VentaRegistradaConverter ventaRegistradaConverter,
            ClienteRepository clienteRepository,
            EstadoPedidoRepository estadoPedidoRepository,
            EstadoPagoRepository estadoPagoRepository,
            TipoPagoRepository tipoPagoRepository, ProductoRepository productoRepository,
            RepartidorRepository repartidorRepository,
            DetalleVentaRepository detalleVentaRepository){
        this.ventaRepository = ventaRepository;
        this.ventaNuevaConverter = ventaNuevaConverter;
        this.ventaRegistradaConverter = ventaRegistradaConverter;
        this.clienteRepository = clienteRepository;
        this.estadoPedidoRepository = estadoPedidoRepository;
        this.estadoPagoRepository = estadoPagoRepository;
        this.tipoPagoRepository = tipoPagoRepository;
        this.productoRepository = productoRepository;
        this.repartidorRepository = repartidorRepository;
        this.detalleVentaRepository = detalleVentaRepository;
    }
    
    @Transactional
    public VentaRegistradaDTO save(VentaNuevaDTO ventaNueva){
        Optional<Cliente> optionalCliente = clienteRepository.findById(ventaNueva.getIdCliente());
        if(!optionalCliente.isEmpty()){
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            if(optionalCliente.get().getEmail().equals(email)){
                Optional<Repartidor> optionalRepartidor = repartidorRepository.findById(ventaNueva.getIdRepartidor());
                if(!optionalRepartidor.isEmpty()){
                    String fecha=dateValidation(ventaNueva.getFechaCreacion());
                    if (fecha!=null && !fecha.equals("Invalid Date.")){
                        ventaNueva.setFechaCreacion(fecha);
                        if (estadoPedidoRepository.existsById(ventaNueva.getIdEstadoPedido())){
                            if(estadoPagoRepository.existsById(ventaNueva.getIdEstadoPago())){
                                if(tipoPagoRepository.existsById(ventaNueva.getIdTipoPago())){
                                    for(DetalleVentaNuevoDTO detalle:ventaNueva.getDetalles()){
                                        if(!productoRepository.existsById(detalle.getIdProducto())){
                                            throw new Exceptions(productoNoEncontrado, HttpStatus.CONFLICT);
                                        }
                                    }
                                    Venta venta = ventaNuevaConverter.dtotoEntity(ventaNueva);
                                    List<DetalleVenta> detalles = venta.getDetalles();
                                    venta = ventaRepository.save(venta);
                                    for(DetalleVenta detalle:detalles){
                                        detalle.setVenta(venta);
                                        Producto producto = detalle.getProducto();
                                        if (producto.getStock()>=detalle.getCantidad()){
                                            producto.setStock(producto.getStock()-detalle.getCantidad());
                                            productoRepository.save(producto);
                                        }else{
                                            throw new Exceptions("Cantidad insuficiente del producto: "+producto.getIdProducto(), HttpStatus.CONFLICT);
                                        }
                                    }
                                    venta.setDetalles(detalles);
                                    venta= ventaRepository.save(venta);
                                    return ventaRegistradaConverter.entitytoDTO(venta);
                                }else{
                                    throw new Exceptions(tipoPagoNoEncontrado, HttpStatus.CONFLICT);
                                }
                            }else{
                                throw new Exceptions(estadoPagoNoEncontrado, HttpStatus.CONFLICT);
                            }
                        }else{
                            throw new Exceptions(estadoPedidoNoEncontrado, HttpStatus.CONFLICT);
                        }
                    }else{
                        throw new Exceptions(fechaInvalida, HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions(repartidorNoEncontrado, HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    @Transactional
    public VentaRegistradaDTO update(long idVenta, VentaNuevaDTO ventaNueva){
        Optional<Venta> optionalVenta = ventaRepository.findById(idVenta);
        if(!optionalVenta.isEmpty()){
            Cliente cliente = optionalVenta.get().getCliente();
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            if(cliente.getEmail().equals(email)){
                Optional<Repartidor> optionalRepartidor = repartidorRepository.findById(ventaNueva.getIdRepartidor());
                if(!optionalRepartidor.isEmpty()){
                    String fecha=dateValidation(ventaNueva.getFechaCreacion());
                    if (fecha!=null && !fecha.equals("Invalid Date.")){
                        ventaNueva.setFechaCreacion(fecha);
                        if (estadoPedidoRepository.existsById(ventaNueva.getIdEstadoPedido())){
                            if(estadoPagoRepository.existsById(ventaNueva.getIdEstadoPago())){
                                if(tipoPagoRepository.existsById(ventaNueva.getIdTipoPago())){
                                    for(DetalleVentaNuevoDTO detalle:ventaNueva.getDetalles()){
                                        if(!productoRepository.existsById(detalle.getIdProducto())){
                                            throw new Exceptions(productoNoEncontrado, HttpStatus.CONFLICT);
                                        }
                                    }
                                    List<DetalleVenta> detalles = optionalVenta.get().getDetalles();
                                    Venta venta = ventaNuevaConverter.dtotoEntity(ventaNueva);
                                    List<DetalleVenta> detalles2 = venta.getDetalles();
                                    for(int i=0; i<detalles.size(); i++){
                                        try{
                                            Producto producto = detalles.get(i).getProducto();
                                            producto.setStock(producto.getStock()+detalles.get(i).getCantidad());
                                            productoRepository.save(producto);
                                        }catch(Exception e){
                                            throw new Exceptions(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                                        }
                                        if(i<detalles2.size()){
                                            detalles2.get(i).setIdDetalle(detalles.get(i).getIdDetalle());
                                        }else{
                                            detalleVentaRepository.delete(detalles.get(i));
                                        }
                                    }
                                    venta.setDetalles(detalles2);
                                    venta.setIdVenta(optionalVenta.get().getIdVenta());
                                    for(DetalleVenta detalle:venta.getDetalles()){
                                        detalle.setVenta(venta);
                                        Producto producto = detalle.getProducto();
                                        if (producto.getStock()>=detalle.getCantidad()){
                                            producto.setStock(producto.getStock()-detalle.getCantidad());
                                            productoRepository.save(producto);
                                        }else{
                                            throw new Exceptions("Cantidad insuficiente del producto: "+producto.getIdProducto(), HttpStatus.CONFLICT);
                                        }
                                    }
                                    venta = ventaRepository.save(venta);
                                    return ventaRegistradaConverter.entitytoDTO(venta); 
                                }else{
                                    throw new Exceptions(tipoPagoNoEncontrado, HttpStatus.CONFLICT);
                                }
                            }else{
                                throw new Exceptions(estadoPagoNoEncontrado, HttpStatus.CONFLICT);
                            }
                        }else{
                            throw new Exceptions(estadoPedidoNoEncontrado, HttpStatus.CONFLICT);
                        }
                    }else{
                        throw new Exceptions(fechaInvalida, HttpStatus.CONFLICT);
                    }
                }else{
                    throw new Exceptions(repartidorNoEncontrado, HttpStatus.CONFLICT);
                }
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean delete(long idVenta){
        Optional<Venta> optionalVenta = ventaRepository.findById(idVenta);
        if(!optionalVenta.isEmpty()){
            Cliente cliente = optionalVenta.get().getCliente();
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            if(cliente.getEmail().equals(email)){
                ventaRepository.delete(optionalVenta.get());
                return true;
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return false;
        }
    }
    
    public VentaRegistradaDTO ofClientefindById(long idVenta){
        Optional<Venta> optionalVenta = ventaRepository.findById(idVenta);
        if(!optionalVenta.isEmpty()){
            Cliente cliente = optionalVenta.get().getCliente();
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            if(cliente.getEmail().equals(email)){
                return ventaRegistradaConverter.entitytoDTO(optionalVenta.get());
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public List<VentaRegistradaDTO> findAllByCliente(long idCliente){
        Optional<Cliente> optionalCliente = clienteRepository.findById(idCliente);
        if(!optionalCliente.isEmpty()){
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            if(optionalCliente.get().getEmail().equals(email)){
                return ventaRegistradaConverter.entityListtoDTOList(optionalCliente.get().getVentas());
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public VentaRegistradaDTO ofRepartidorfindById(long idVenta){
        Optional<Venta> optionalVenta = ventaRepository.findById(idVenta);
        if(!optionalVenta.isEmpty()){
            Repartidor repartidor = optionalVenta.get().getRepartidor();
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            if(repartidor.getEmail().equals(email)){
                return ventaRegistradaConverter.entitytoDTO(optionalVenta.get());
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public List<VentaRegistradaDTO> findAllByRepartidor(long idRepartidor){
        Optional<Repartidor> optionalRepartidor = repartidorRepository.findById(idRepartidor);
        if(!optionalRepartidor.isEmpty()){
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            if(optionalRepartidor.get().getEmail().equals(email)){
                return ventaRegistradaConverter.entityListtoDTOList(optionalRepartidor.get().getVentas());
            }else{
                throw new Exceptions(acceso, HttpStatus.CONFLICT);
            }
        }else{
            return null;
        }
    }
    
    public boolean changeTipoPago(long idVenta, long idTipoPago){
        boolean flag = false;
        Optional<Venta> optionalVenta = ventaRepository.findById(idVenta);
        if(!optionalVenta.isEmpty()){
            Optional<TipoPago> optionalTipoPago = tipoPagoRepository.findById(idTipoPago);
            if(!optionalTipoPago.isEmpty()){
                Venta venta = optionalVenta.get();
                venta.setTipoPago(optionalTipoPago.get());
                ventaRepository.save(venta);
                flag = true;
            }else{
                throw new Exceptions(tipoPagoNoEncontrado, HttpStatus.CONFLICT);
            }
        }
        return flag;
    }
    
    public boolean changeEstadoPago(long idVenta, long idEstadoPago){
        boolean flag = false;
        Optional<Venta> optionalVenta = ventaRepository.findById(idVenta);
        if(!optionalVenta.isEmpty()){
            Optional<EstadoPago> optionalEstadoPago = estadoPagoRepository.findById(idEstadoPago);
            if(!optionalEstadoPago.isEmpty()){
                Venta venta = optionalVenta.get();
                venta.setEstadoPago(optionalEstadoPago.get());
                ventaRepository.save(venta);
                flag = true;
            }else{
                throw new Exceptions(estadoPagoNoEncontrado, HttpStatus.CONFLICT);
            }
        }
        return flag;
    }
    
    public boolean changeEstadoPedido(long idVenta, long idEstadoPedido){
        boolean flag = false;
        Optional<Venta> optionalVenta = ventaRepository.findById(idVenta);
        if(!optionalVenta.isEmpty()){
            Optional<EstadoPedido> optionalEstadoPedido = estadoPedidoRepository.findById(idEstadoPedido);
            if(!optionalEstadoPedido.isEmpty()){
                Venta venta = optionalVenta.get();
                venta.setEstadoPedido(optionalEstadoPedido.get());
                ventaRepository.save(venta);
                flag = true;
            }else{
                throw new Exceptions(estadoPedidoNoEncontrado, HttpStatus.CONFLICT);
            }
        }
        return flag;
    }
    
    public boolean changeRepartidor(long idVenta, long idRepartidor){
        boolean flag = false;
        Optional<Venta> optionalVenta = ventaRepository.findById(idVenta);
        if(!optionalVenta.isEmpty()){
            Venta venta = optionalVenta.get();
            if(venta.getEstadoPedido().getIdEstado()==1){
                Optional<Repartidor> optionalRepartidor = repartidorRepository.findById(idRepartidor);
                if(!optionalRepartidor.isEmpty()){
                    venta.setRepartidor(optionalRepartidor.get());
                    ventaRepository.save(venta);
                    flag = true;
                }else{
                    throw new Exceptions(repartidorNoEncontrado, HttpStatus.CONFLICT);
                }
            }else{
                String message;
                if(venta.getEstadoPedido().getIdEstado()==2){
                    message="se encuentra en camino.";
                }else{
                    message="fue entregado.";
                }
                throw new Exceptions("El pedido ya "+message, HttpStatus.CONFLICT);
            }
        }
        return flag;
    }
}
