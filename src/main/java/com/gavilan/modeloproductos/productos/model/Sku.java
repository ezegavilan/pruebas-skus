package com.gavilan.modeloproductos.productos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sku implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer disponibilidad;
    private String valores;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "sku_valores_propiedades_producto", joinColumns = @JoinColumn(name = "sku_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_valores_propiedad_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"sku_id", "producto_valores_propiedad_id"})})
    private List<ValorPropiedadProducto> valorPropiedadesProducto;
    @OneToOne(targetEntity = Producto.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "default_producto_id")
    private Producto defaultProducto;
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Producto.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties(value = {"defaultSku", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private Producto producto;

    @Override
    public String toString() {
        return "Sku{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", disponibilidad=" + disponibilidad +
                ", valorPropiedadesProducto=" + valorPropiedadesProducto +
                '}';
    }
}
