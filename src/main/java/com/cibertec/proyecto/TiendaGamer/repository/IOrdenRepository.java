package com.cibertec.proyecto.TiendaGamer.repository;

import com.cibertec.proyecto.TiendaGamer.models.Orden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IOrdenRepository extends JpaRepository<Orden, UUID> {

    List<Orden> findAllByOrderByFechaOrdenDesc();

    @Query("SELECT o FROM Orden o WHERE o.cliente.codigoCliente = :codigoCliente")
    List<Orden> findOrdenesByClienteId(@Param("codigoCliente") UUID codigoCliente);

    Optional<Orden> findById(UUID id);

}
