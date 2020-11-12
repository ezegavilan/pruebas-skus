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
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Marca marca;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "productos_propiedades", joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_propiedad_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"producto_id", "producto_propiedad_id"})}
    )
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<PropiedadProducto> properties;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "default_sku_id")
    @JsonIgnoreProperties(value = {"producto", "defaultProducto" ,"hibernateLazyInitializer", "handler"}, allowSetters = true)
    private Sku defaultSku;
    @OneToMany(fetch = FetchType.LAZY, targetEntity = Sku.class,
            mappedBy = "producto", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"producto", "defaultProducto","hibernateLazyInitializer", "handler"}, allowSetters = true)
    private List<Sku> skus;

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", marca=" + marca +
                ", properties=" + properties +
                ", defaultSkuId=" + defaultSku.getId() +
                '}';
    }
}
