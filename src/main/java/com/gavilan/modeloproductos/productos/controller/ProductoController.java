package com.gavilan.modeloproductos.productos.controller;

import com.gavilan.modeloproductos.productos.model.Producto;
import com.gavilan.modeloproductos.productos.model.PropiedadProducto;
import com.gavilan.modeloproductos.productos.model.ValorPropiedadProducto;
import com.gavilan.modeloproductos.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/productos")
    public ResponseEntity<?> crearNuevoProducto(@RequestBody Producto producto) {
        Map<String, Object> response = new HashMap<>();
        Producto nuevoProducto;

        try {
            nuevoProducto = productoService.crearProducto(producto);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @GetMapping("/productos")
    public ResponseEntity<List<Producto>> listarProductos() {
        return new ResponseEntity<>(productoService.listarProductos(), HttpStatus.OK);
    }

    @GetMapping("/productos/{productoId}")
    public ResponseEntity<Producto> verProducto(@PathVariable Long productoId) {
        return new ResponseEntity<>(productoService.verProducto(productoId), HttpStatus.OK);
    }

    @PostMapping("/productos/propiedades")
    public ResponseEntity<?> crearNuevaPropiedad(@RequestBody PropiedadProducto propiedad) {
        Map<String, Object> response = new HashMap<>();
        PropiedadProducto propiedadNueva;

        try {
            propiedadNueva = productoService.crearPropiedadProducto(propiedad);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("propiedad", propiedadNueva);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/productos/{productoId}/propiedades")
    public ResponseEntity<?> crearNuevaPropiedad(@PathVariable Long productoId,
                                                 @RequestBody PropiedadProducto propiedadProducto) {

        Map<String, Object> response = new HashMap<>();
        PropiedadProducto nuevaPropiedad;

        try {
            nuevaPropiedad = productoService.crearPropiedadProducto(productoId, propiedadProducto);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("propiedad", nuevaPropiedad);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/productos/{productoId}/propiedades/{propiedadId}/asignar")
    public ResponseEntity<?> asignarPropiedadAProducto(@PathVariable Long productoId,
                                                       @PathVariable Long propiedadId) {

        Map<String, Object> response = new HashMap<>();

        try {
            productoService.asignarPropiedadAProducto(productoId, propiedadId);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "Propiedad asignada con éxito al producto");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/productos/propiedades")
    public ResponseEntity<List<PropiedadProducto>> listarPropiedades() {
        return new ResponseEntity<>(productoService.listarPropiedadesProducto(), HttpStatus.OK);
    }

    @GetMapping("/productos/{productoId}/propiedades")
    public ResponseEntity<List<PropiedadProducto>> listarPropiedades(@PathVariable Long productoId) {
        return new ResponseEntity<>(productoService.listarPropiedadesProducto(productoId), HttpStatus.OK);
    }

    @PostMapping("/productos/propiedades/{propiedadId}/valores")
    public ResponseEntity<?> crearValorAPropiedad(@PathVariable Long propiedadId,
                                                  @RequestBody ValorPropiedadProducto valor) {

        Map<String, Object> response = new HashMap<>();
        PropiedadProducto propertyActualizada;

        try {
            propertyActualizada = productoService.crearValorPropiedad(propiedadId, valor);
        } catch (DataAccessException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("property", propertyActualizada);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
