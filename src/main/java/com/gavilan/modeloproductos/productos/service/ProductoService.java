package com.gavilan.modeloproductos.productos.service;

import com.gavilan.modeloproductos.productos.model.Producto;
import com.gavilan.modeloproductos.productos.model.PropiedadProducto;
import com.gavilan.modeloproductos.productos.model.Sku;
import com.gavilan.modeloproductos.productos.model.ValorPropiedadProducto;

import java.util.List;

public interface ProductoService {

    Producto crearProducto(Producto producto);

    List<Producto> listarProductos();

    List<Sku> obtenerSkusProducto(Long productoId);

    Producto verProducto(Long productoId);

    PropiedadProducto crearPropiedadProducto(PropiedadProducto propiedad);

    PropiedadProducto crearPropiedadProducto(Long productoId, PropiedadProducto propiedad);

    List<PropiedadProducto> listarPropiedadesProducto();

    List<PropiedadProducto> listarPropiedadesProducto(Long productoId);

    void asignarPropiedadAProducto(Long productoId, Long propiedadId);

    PropiedadProducto crearValorPropiedad(Long propiedadId, ValorPropiedadProducto valor);
}
