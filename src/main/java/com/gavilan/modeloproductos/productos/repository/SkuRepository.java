package com.gavilan.modeloproductos.productos.repository;

import com.gavilan.modeloproductos.productos.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {

}
