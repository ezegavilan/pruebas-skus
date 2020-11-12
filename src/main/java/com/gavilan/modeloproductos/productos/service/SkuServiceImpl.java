package com.gavilan.modeloproductos.productos.service;

import com.gavilan.modeloproductos.productos.model.Producto;
import com.gavilan.modeloproductos.productos.model.PropiedadProducto;
import com.gavilan.modeloproductos.productos.model.Sku;
import com.gavilan.modeloproductos.productos.model.ValorPropiedadProducto;
import com.gavilan.modeloproductos.productos.repository.ProductoRepository;
import com.gavilan.modeloproductos.productos.repository.SkuRepository;
import com.gavilan.modeloproductos.productos.repository.ValorPropiedadProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
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

    @Transactional
    @Override
    public void eliminarSku(Long skuId) {
        skuRepository.deleteById(skuId);
    }

    @Transactional
    @Override
    public Integer generarSkusDeProducto(Long productoId) {
        Producto producto = this.getProducto(productoId);

        if (CollectionUtils.isEmpty(producto.getProperties())) return -1;

        List<List<ValorPropiedadProducto>> allPermutations = generarPermutaciones(0, new ArrayList<>(), producto.getProperties());

        if (allPermutations.size() == 0) {
            return -2;
        }
        log.info("Total de permutaciones: " + allPermutations.size());
        log.info(String.valueOf(allPermutations));
        List<List<ValorPropiedadProducto>> permutacionesGeneradasPreviamente = new ArrayList<>();

        if (!CollectionUtils.isEmpty(producto.getSkus())) {
            for (Sku skuAdicional: producto.getSkus()) {
                if (!CollectionUtils.isEmpty(skuAdicional.getValorPropiedadesProducto())) {
                    permutacionesGeneradasPreviamente.add(skuAdicional.getValorPropiedadesProducto());
                }
            }
        }

        List<List<ValorPropiedadProducto>> permutacionesAGenerar = new ArrayList<>();
        for (List<ValorPropiedadProducto> permutacion: allPermutations) {
            boolean generadoPreviamente = false;
            for (List<ValorPropiedadProducto> permutacionGenerada: permutacionesGeneradasPreviamente) {
                if (esMismaPermutacion(permutacion, permutacionGenerada)) {
                    generadoPreviamente = true;
                    break;
                }
            }
            if (!generadoPreviamente) {
                permutacionesAGenerar.add(permutacion);
            }
        }

        log.info("Número total de permutaciones a generar: " + permutacionesAGenerar.size());
        return null;
    }

    private boolean esMismaPermutacion(List<ValorPropiedadProducto> permutacion1, List<ValorPropiedadProducto> permutacion2) {
        if (permutacion1.size() == permutacion2.size()) {
            Collection<Long> permutacion1Ids = com.gavilan.modeloproductos.utils.CollectionUtils.collect(permutacion1, input -> ((ValorPropiedadProducto) input).getId());

            Collection<Long> permutacion2Ids = com.gavilan.modeloproductos.utils.CollectionUtils.collect(permutacion2, input -> ((ValorPropiedadProducto) input).getId());

            return permutacion1Ids.contains(permutacion2Ids);
        }

        return false;
    }

    private List<List<ValorPropiedadProducto>> generarPermutaciones(int actualTypeIndex,
                                                                    List<ValorPropiedadProducto> permutacionActual,
                                                                    List<PropiedadProducto> propiedades) {

        List<List<ValorPropiedadProducto>> resultado = new ArrayList<>();
        if (actualTypeIndex == propiedades.size()) {
            resultado.add(permutacionActual);
            return resultado;
        }

        PropiedadProducto propiedadActual = propiedades.get(actualTypeIndex);
        List<ValorPropiedadProducto> valoresPermitidos = propiedadActual.getValues();
        if (propiedadActual.getValues().isEmpty()) {
            return new ArrayList<>();
        }

        for (ValorPropiedadProducto valor: valoresPermitidos) {
            List<ValorPropiedadProducto> permutacion = new ArrayList<>(permutacionActual);
            permutacion.add(valor);
            resultado.addAll(generarPermutaciones(actualTypeIndex + 1, permutacion, propiedades));

        }

        if (valoresPermitidos.size() == 0) {
            resultado.addAll(generarPermutaciones(actualTypeIndex + 1, permutacionActual, propiedades));
        }

        return resultado;
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
