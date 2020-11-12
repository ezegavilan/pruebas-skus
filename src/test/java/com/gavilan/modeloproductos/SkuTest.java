package com.gavilan.modeloproductos;

import com.gavilan.modeloproductos.productos.model.Producto;
import com.gavilan.modeloproductos.productos.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Slf4j
public class SkuTest {
    @Autowired
    ProductoRepository productoRepository;

    List<Producto> productos;
    Producto remeraX;

    @BeforeEach
    void setUp() {
        productos = productoRepository.findAll();
        remeraX = productoRepository.getOne(1L);
    }

    @Transactional
    @Test
    void findAllProductos() {
        log.info(productos.toString());
    }

    @Transactional
    @Test
    void getSkusFromProduct() {
        log.info(remeraX.getNombre());
        log.info(String.valueOf(remeraX.getSkus()));
    }
}
