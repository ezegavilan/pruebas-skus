package com.gavilan.modeloproductos.bootstrap;

import com.gavilan.modeloproductos.productos.model.Producto;
import com.gavilan.modeloproductos.productos.model.PropiedadProducto;
import com.gavilan.modeloproductos.productos.model.Sku;
import com.gavilan.modeloproductos.productos.model.ValorPropiedadProducto;
import com.gavilan.modeloproductos.productos.repository.ProductoRepository;
import com.gavilan.modeloproductos.productos.repository.ValorPropiedadProductoRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Component
@AllArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {


    private final ProductoRepository productoRepository;
    private final ValorPropiedadProductoRepository valorPropiedadProductoRepository;

    @Transactional
    @Override
    public void run(String... args) {
        if (productoRepository.findAll().size() != 0) {
            return;
        }
        Producto remeraX = Producto.builder()
                .nombre("Remera X")
                .precio(800.00)
                .properties(new ArrayList<>())
                .skus(new ArrayList<>())
                .build();

        PropiedadProducto talle = PropiedadProducto.builder().property("Talle").values(new ArrayList<>()).build();
        PropiedadProducto color = PropiedadProducto.builder().property("Color").values(new ArrayList<>()).build();

        talle.getValues().add(ValorPropiedadProducto.builder().value("XL").disponibilidad(25).build());
        talle.getValues().add(ValorPropiedadProducto.builder().value("S").disponibilidad(20).build());

        color.getValues().add(ValorPropiedadProducto.builder().value("Roja").disponibilidad(15).build());
        color.getValues().add(ValorPropiedadProducto.builder().value("Negra").disponibilidad(10).build());

        remeraX.getProperties().add(talle);
        remeraX.getProperties().add(color);

        remeraX.setDefaultSku(Sku.builder()
                .nombre("Remera X")
                .descripcion("Remera Negra Comun")
                .precio(remeraX.getPrecio())
                .disponibilidad(50)
                .defaultProducto(remeraX)
                .valorPropiedadesProducto(new ArrayList<>())
                .producto(null).build());

        productoRepository.save(remeraX);

        /*
        Producto remeraY = Producto.builder()
                .nombre("Remera Y")
                .precio(850.00)
                .properties(new ArrayList<>()).build();

        remeraY.getProperties().add(color);
        productoRepository.save(remeraY);

         */

        // Simular agregar un Sku de producto que tenga color y talle.
        // Ejemplo: Sku de Remera X --> Color: Roja XL.
        Sku remeraXRojaXl = Sku.builder()
                .nombre("Remera X Roja XL")
                .descripcion("Remera X de color roja con talle xl")
                .precio(850.00)
                .disponibilidad(55)
                .valorPropiedadesProducto(new ArrayList<>())
                .defaultProducto(null)
                .valores(valorPropiedadProductoRepository.getOne(3L).getValue()
                        + "/" + valorPropiedadProductoRepository.getOne(1L).getValue())
                .producto(remeraX).build();
        remeraXRojaXl.getValorPropiedadesProducto().add(valorPropiedadProductoRepository.getOne(3L));
        remeraXRojaXl.getValorPropiedadesProducto().add(valorPropiedadProductoRepository.getOne(1L));
        remeraX.getSkus().add(remeraXRojaXl);
        productoRepository.save(remeraX);

        // Simular agregar un Sku de un producto que solo tenga color.
        // Ejemplo: Sku de Remera x --> Color: Negra
        Sku remeraXNegra = Sku.builder()
                .nombre("Remera X Negra")
                .descripcion("Remera X de color negra con talle único")
                .precio(850.00)
                .disponibilidad(0)
                .valorPropiedadesProducto(new ArrayList<>())
                .defaultProducto(null)
                .valores(valorPropiedadProductoRepository.getOne(4L).getValue())
                .producto(remeraX).build();
        remeraXNegra.getValorPropiedadesProducto().add(valorPropiedadProductoRepository.getOne(4L));
        remeraX.getSkus().add(remeraXNegra);
        productoRepository.save(remeraX);
    }
}
