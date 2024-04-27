/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.uv.delivery.dtos.usuarios;

import org.uv.delivery.dtos.VehiculoNuevoDTO;

/**
 *
 * @author juan
 */
public class RepartidorNuevoDTO extends TrabajadorNuevoDTO{
    private String licencia;
    private VehiculoNuevoDTO vehiculo;

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }

    public VehiculoNuevoDTO getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(VehiculoNuevoDTO vehiculo) {
        this.vehiculo = vehiculo;
    }
    
    
}
