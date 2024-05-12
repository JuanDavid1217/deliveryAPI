/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uv.delivery.models.EstadoPedido;

/**
 *
 * @author juan
 */
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Long>{
    public EstadoPedido findByDescripcion(String descripcion);
}
