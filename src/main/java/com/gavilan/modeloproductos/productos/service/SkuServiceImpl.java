package com.gavilan.modeloproductos.productos.service;

import com.gavilan.modeloproductos.productos.model.Producto;
import com.gavilan.modeloproductos.productos.model.Sku;
import com.gavilan.modeloproductos.productos.model.ValorPropiedadProducto;
import com.gavilan.modeloproductos.productos.repository.ProductoRepository;
import com.gavilan.modeloproductos.productos.repository.SkuRepository;
import com.gavilan.modeloproductos.productos.repository.ValorPropiedadProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SkuServiceImpl implements SkuService {

    private final SkuRepository skuRepository;
    private final ProductoRepository productoRepository;
    private final ValorPropiedadProductoRepository valorRepository;

    @Transactional
    @Override
    public Sku crearSkuProducto(Long productoId, Sku sku) {
        Producto producto = this.getProducto(productoId);

        Sku nuevoSku = Sku.builder()
                .nombre(sku.getNombre())
                .descripcion(sku.getDescripcion())
                .precio(sku.getPrecio())
                .disponibilidad(sku.getDisponibilidad())
                .defaultProducto(null)
                .producto(producto)
                .valores("").build();


        String values = "";
        List<ValorPropiedadProducto> valoresReales = new ArrayList<>();
        for (ValorPropiedadProducto value: sku.getValorPropiedadesProducto()) {
            ValorPropiedadProducto currentValorReal = this.encontrarValor(value.getId());
            valoresReales.add(currentValorReal);

            values = values.concat(currentValorReal.getValue().concat("/"));
        }

        nuevoSku.setValorPropiedadesProducto(valoresReales);
        log.info(values);
        nuevoSku.setValores(values);

        //Pendiente(si es necesario): Validar que los valores pertenecen a las propiedades
        // del producto.
        return skuRepository.save(nuevoSku);
    }

    private Producto getProducto(Long productoId) {
        return productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("No existe el producto"));
    }

    private ValorPropiedadProducto encontrarValor(Long valorId) {
        return valorRepository.findById(valorId)
                .orElseThrow(() -> new RuntimeException("No such Value"));
    }
}
