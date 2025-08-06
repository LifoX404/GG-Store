package com.cibertec.proyecto.TiendaGamer.repository;

import com.cibertec.proyecto.TiendaGamer.models.Cliente;
import com.cibertec.proyecto.TiendaGamer.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface IClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByUsuario(Usuario usuario);

    Optional<Cliente> findByCodigoCliente(UUID codigoCliente);

    @Modifying
    @Transactional
    @Query("UPDATE Cliente p SET p.status = false WHERE p.codigoCliente = :codigo")
    void setStatusFalse(@Param("codigo") UUID codigo);

}
