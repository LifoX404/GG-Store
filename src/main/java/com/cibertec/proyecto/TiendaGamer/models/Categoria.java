package com.cibertec.proyecto.TiendaGamer.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_categorias")
@Getter
@Setter
public class Categoria {

    @Id
    @Column(name = "codigo_categoria")
    private UUID codigoCategoria;

    @Column(name = "nombre_categoria", nullable = false, length = 100)
    private String nombreCategoria;

    @Column(name = "imagen_categoria", length = 1000)
    private String imagenCategoria;

    @Column(name = "status", columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean status = true;

    public Categoria() {
        this.codigoCategoria = UUID.randomUUID();
    }

}