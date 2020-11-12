package com.gavilan.modeloproductos.productos.service;

import com.gavilan.modeloproductos.productos.model.Sku;

public interface SkuService {

    Sku crearSkuProducto(Long productoId, Sku sku);

    void eliminarSku(Long skuId);

    Integer generarSkusDeProducto(Long productoId);
}
