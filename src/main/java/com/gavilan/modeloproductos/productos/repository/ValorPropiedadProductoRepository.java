package com.gavilan.modeloproductos.productos.repository;

import com.gavilan.modeloproductos.productos.model.ValorPropiedadProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValorPropiedadProductoRepository extends JpaRepository<ValorPropiedadProducto, Long> {

}
