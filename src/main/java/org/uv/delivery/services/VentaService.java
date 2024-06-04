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
import org.uv.delivery.models.Producto;
import org.uv.delivery.models.Venta;
import org.uv.delivery.models.usuario.Cliente;
import org.uv.delivery.repository.ClienteRepository;
import org.uv.delivery.repository.EstadoPagoRepository;
import org.uv.delivery.repository.EstadoPedidoRepository;
import org.uv.delivery.repository.ProductoRepository;
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
    
    public VentaService(VentaRepository ventaRepository,
            VentaNuevaConverter ventaNuevaConverter,
            VentaRegistradaConverter ventaRegistradaConverter,
            ClienteRepository clienteRepository,
            EstadoPedidoRepository estadoPedidoRepository,
            EstadoPagoRepository estadoPagoRepository,
            TipoPagoRepository tipoPagoRepository, ProductoRepository productoRepository){
        this.ventaRepository = ventaRepository;
        this.ventaNuevaConverter = ventaNuevaConverter;
        this.ventaRegistradaConverter = ventaRegistradaConverter;
        this.clienteRepository = clienteRepository;
        this.estadoPedidoRepository = estadoPedidoRepository;
        this.estadoPagoRepository = estadoPagoRepository;
        this.tipoPagoRepository = tipoPagoRepository;
        this.productoRepository = productoRepository;
    }
    
    @Transactional
    public VentaRegistradaDTO save(VentaNuevaDTO ventaNueva){
        Optional<Cliente> optionalCliente = clienteRepository.findById(ventaNueva.getIdCliente());
        if(!optionalCliente.isEmpty()){
            String email=SecurityContextHolder.getContext().getAuthentication().getName();
            if(optionalCliente.get().getEmail().equals(email)){
                String fecha=dateValidation(ventaNueva.getFechaCreacion());
                if (fecha!=null && !fecha.equals("Invalid Date.")){
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
                String fecha=dateValidation(ventaNueva.getFechaCreacion());
                if (fecha!=null && !fecha.equals("Invalid Date.")){
                    if (estadoPedidoRepository.existsById(ventaNueva.getIdEstadoPedido())){
                        if(estadoPagoRepository.existsById(ventaNueva.getIdEstadoPago())){
                            if(tipoPagoRepository.existsById(ventaNueva.getIdTipoPago())){
                                for(DetalleVentaNuevoDTO detalle:ventaNueva.getDetalles()){
                                    if(!productoRepository.existsById(detalle.getIdProducto())){
                                        throw new Exceptions(productoNoEncontrado, HttpStatus.CONFLICT);
                                    }
                                }
                                List<DetalleVenta> detalles = optionalVenta.get().getDetalles();
                                for(DetalleVenta detalle:detalles){
                                    try{
                                        Producto producto = detalle.getProducto();
                                        producto.setStock(producto.getStock()+detalle.getCantidad());
                                        productoRepository.save(producto);
                                    }catch(Exception e){
                                        throw new Exceptions(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                                    }
                                }
                                Venta venta = ventaNuevaConverter.dtotoEntity(ventaNueva);
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
    
    public VentaRegistradaDTO findById(long idVenta){
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
    
}
