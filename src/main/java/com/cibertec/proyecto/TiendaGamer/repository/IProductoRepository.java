package com.cibertec.proyecto.TiendaGamer.repository;

import com.cibertec.proyecto.TiendaGamer.models.Producto;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProductoRepository extends JpaRepository<Producto, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE Producto p SET p.status = false WHERE p.codigoProducto = :codigo")
    void setStatusFalse(@Param("codigo") UUID codigo);

    @Query("SELECT p FROM Producto p WHERE p.status = true")
    List<Producto> findAllActive();
}
