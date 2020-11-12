package com.gavilan.modeloproductos.productos.service;

import com.gavilan.modeloproductos.productos.model.Producto;
import com.gavilan.modeloproductos.productos.model.PropiedadProducto;
import com.gavilan.modeloproductos.productos.model.Sku;
import com.gavilan.modeloproductos.productos.model.ValorPropiedadProducto;
import com.gavilan.modeloproductos.productos.repository.ProductoRepository;
import com.gavilan.modeloproductos.productos.repository.PropiedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    private final PropiedadRepository propiedadRepository;

    @Autowired
    public ProductoServiceImpl(ProductoRepository productoRepository, PropiedadRepository propiedadRepository) {
        this.productoRepository = productoRepository;
        this.propiedadRepository = propiedadRepository;
    }

    @Transactional
    @Override
    public Producto crearProducto(Producto producto) {
        Producto nuevoProducto = Producto.builder()
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .marca(null)
                .properties(new ArrayList<>())
                .skus(new ArrayList<>())
                .build();

        nuevoProducto.setDefaultSku(Sku.builder()
                .nombre(producto.getNombre())
                .precio(producto.getPrecio())
                .descripcion("")
                .valores(null)
                .producto(null)
                .defaultProducto(nuevoProducto)
                .disponibilidad(0)
                .valorPropiedadesProducto(null).build());

        return this.productoRepository.save(nuevoProducto);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Producto> listarProductos() {
        return this.productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Producto verProducto(Long productoId) {
        return this.productoRepository.findById(productoId).orElse(null);
    }

    @Transactional
    @Override
    public PropiedadProducto crearPropiedadProducto(PropiedadProducto propiedad) {
        // Agregar if preguntando si propiedad.valores es nulo, si no lo es, crear valores
        // que vienen del JSON.
        return this.propiedadRepository.save(PropiedadProducto.builder()
                .property(propiedad.getProperty())
                .values(new ArrayList<>()).build());
    }

    @Transactional
    @Override
    public PropiedadProducto crearPropiedadProducto(Long productoId, PropiedadProducto propiedad) {
        Producto producto = this.getProducto(productoId);

        PropiedadProducto nuevaPropiedad = PropiedadProducto.builder()
                .property(propiedad.getProperty())
                .values(new ArrayList<>()).build();

        producto.getProperties().add(nuevaPropiedad);
        this.productoRepository.save(producto);
        return nuevaPropiedad;
    }

    @Transactional(readOnly = true)
    @Override
    public List<PropiedadProducto> listarPropiedadesProducto() {
        return propiedadRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<PropiedadProducto> listarPropiedadesProducto(Long productoId) {
        Producto producto = this.getProducto(productoId);

        return producto.getProperties();
    }

    @Override
    public void asignarPropiedadAProducto(Long productoId, Long propiedadId) {
        Producto producto = this.getProducto(productoId);
        PropiedadProducto property = this.getPropiedad(propiedadId);

        // Validar no repetidos

        producto.getProperties().add(property);
        this.productoRepository.save(producto);
    }

    @Transactional
    @Override
    public PropiedadProducto crearValorPropiedad(Long propiedadId, ValorPropiedadProducto valor) {
        PropiedadProducto property = this.getPropiedad(propiedadId);

        ValorPropiedadProducto nuevoValue = ValorPropiedadProducto.builder()
                .value(valor.getValue()).build();

        property.getValues().add(nuevoValue);
        return this.propiedadRepository.save(property);
    }

    private Producto getProducto(Long id) {
        return this.productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el producto"));
    }

    private PropiedadProducto getPropiedad(Long id) {
        return this.propiedadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la propiedad"));
    }
}
