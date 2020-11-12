package com.gavilan.modeloproductos.productos.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "producto_valores_propiedad")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ValorPropiedadProducto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String value;
    private Integer disponibilidad;
}
