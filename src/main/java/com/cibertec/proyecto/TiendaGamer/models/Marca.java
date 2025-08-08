package com.cibertec.proyecto.TiendaGamer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "tb_marcas")
@Getter
@Setter
public class Marca {
    @Id
    @Column(name = "codigo_marca")
    private UUID codigoMarca;

    @Column(name = "nombre_marca", nullable = false, length = 100)
    private String nombreMarca;

    @Column(name = "email_marca", length = 100)
    private String emailMarca;

    @Column(name = "telf_marca", length = 9)
    private String telfMarca;

    @Column(name = "imagen_marca", length = 1000)
    private String imagenMarca;

    @Column(name = "status" , columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean status = true;

    public Marca() {
        this.codigoMarca = UUID.randomUUID();
    }
}