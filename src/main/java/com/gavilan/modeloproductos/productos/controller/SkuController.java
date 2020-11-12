package com.gavilan.modeloproductos.productos.controller;

import com.gavilan.modeloproductos.productos.model.Sku;
import com.gavilan.modeloproductos.productos.service.SkuService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SkuController {

    private final SkuService skuService;

    @PostMapping("/productos/{productoId}/skus")
    public ResponseEntity<?> crearSkuDeProducto(@PathVariable Long productoId,
                                                @RequestBody Sku sku) {

        Map<String, Object> response = new HashMap<>();
        Sku nuevoSku;

        try {
            nuevoSku = skuService.crearSkuProducto(productoId, sku);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("sku", nuevoSku);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/sku/{skuId}")
    public ResponseEntity<String> eliminarSku(@PathVariable Long skuId) {

        try {
            skuService.eliminarSku(skuId);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
