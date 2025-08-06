package com.cibertec.proyecto.TiendaGamer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_clientes")
@Getter
@Setter
public class Cliente {
    @Id
    @Column(name = "codigo_cliente")
    private UUID codigoCliente;

    @Column(name = "nombre_cliente", nullable = false, length = 100)
    private String nombreCliente;

    @Column(name = "email_cliente", length = 100)
    private String emailCliente;

    @Column(name = "telf_cliente", length = 9)
    private String telfCliente;

    @Column(name = "direccion_cliente", length = 100)
    private String direccionCliente;

    @Column(name = "fec_reg_cliente")
    private LocalDate fecRegCliente = LocalDate.now();

    @Column(name = "status" , columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean status = true;

    // Relación OneToOne con Usuario
    @OneToOne
    @JoinColumn(name = "codigo_usuario", referencedColumnName = "codigo_usuario")
    private Usuario usuario;

    // Relación con Órdenes (eliminamos la relación con Carritos)
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orden> ordenes;

    public Cliente() {
        this.codigoCliente = UUID.randomUUID();
    }
}