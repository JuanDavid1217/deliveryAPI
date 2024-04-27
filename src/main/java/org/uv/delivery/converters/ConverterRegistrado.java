/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.uv.delivery.converters;

import java.util.List;

/**
 *
 * @author juan
 */
public interface ConverterRegistrado<T, S> {
    public S entitytoDTO(T entity);
    public List<S> entityListtoDTOList(List<T> entityList);
}