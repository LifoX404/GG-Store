package com.cibertec.proyecto.TiendaGamer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "tb_productos")
@Getter
@Setter
public class Producto {
    @Id
    @Column(name = "codigo_producto")
    private UUID codigoProducto;

    @Column(name = "nombre_producto", nullable = false, length = 100)
    private String nombreProducto;

    @Column(name = "descripcion_producto", nullable = false, length = 1000)
    private String descripcionProducto;

    @Column(name = "precio_producto", nullable = false)
    private double precioProducto;

    @Column(name = "stock_producto")
    private int stockProducto = 0;

    @Column(name = "imagen_producto", length = 1000)
    private String imagenProducto;

    @Column(name = "status", columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean status = true;

    @ManyToOne
    @JoinColumn(name = "codigo_categoria", referencedColumnName = "codigo_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "codigo_marca", referencedColumnName = "codigo_marca")
    private Marca marca;

    public Producto() {
        this.codigoProducto = UUID.randomUUID();
    }
}