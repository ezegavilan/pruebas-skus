package com.gavilan.modeloproductos.productos.repository;

import com.gavilan.modeloproductos.productos.model.PropiedadProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropiedadRepository extends JpaRepository<PropiedadProducto, Long> {

    Optional<PropiedadProducto> findByProperty(String propertyName);

}
