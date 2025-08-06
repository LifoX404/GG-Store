package com.cibertec.proyecto.TiendaGamer.repository;

import com.cibertec.proyecto.TiendaGamer.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, UUID> {


    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

    boolean existsByNombreUsuario(String nombreUsuario);

    Optional<Usuario> findByCodigoUsuario(UUID codigoUsuario);
}
