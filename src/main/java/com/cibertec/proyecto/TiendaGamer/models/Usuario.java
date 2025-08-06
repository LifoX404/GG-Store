package com.cibertec.proyecto.TiendaGamer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "tb_usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @Column(name = "codigo_usuario")
    private UUID codigoUsuario;

    @Column(name = "nombre_usuario", nullable = false, length = 20, unique = true)
    private String nombreUsuario;

    @Column(name = "password_usuario", nullable = false, length = 255)
    private String passwordUsuario;

    @Column(name = "rol_usuario", length = 10)
    private String rolUsuario = "CLIENT";

    @Column(name = "status" , columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean status = true;

    // Relación OneToOne con Cliente
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cliente cliente;

    public Usuario(String username, String password, String role, boolean status) {
        this.codigoUsuario = UUID.randomUUID();
        this.nombreUsuario = username;
        this.passwordUsuario = password;
        this.rolUsuario = role;
        this.status = status;
    }
}