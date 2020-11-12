package com.gavilan.modeloproductos.productos.controller;

import com.gavilan.modeloproductos.productos.model.Producto;
import com.gavilan.modeloproductos.productos.model.Sku;
import com.gavilan.modeloproductos.productos.repository.ProductoRepository;
import com.gavilan.modeloproductos.productos.repository.SkuRepository;
import com.gavilan.modeloproductos.productos.service.SkuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TestController {

    private final ProductoRepository productoRepository;
    private final SkuRepository skuRepository;
    private final SkuService skuService;

    @GetMapping("/test/productos")
    public ResponseEntity<List<Producto>> listarProductos() {
        return new ResponseEntity<>(productoRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/test/skus")
    public ResponseEntity<List<Sku>> listarSkus() {
        return new ResponseEntity<>(skuRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/test/generar/{productoId}")
    public ResponseEntity<Integer> generarSkus(@PathVariable Long productoId) {
        Integer numero = skuService.generarSkusDeProducto(productoId);
        return new ResponseEntity<>(numero, HttpStatus.OK);
    }

}
