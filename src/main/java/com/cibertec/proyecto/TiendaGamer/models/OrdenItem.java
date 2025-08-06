package com.cibertec.proyecto.TiendaGamer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "tb_orden_items")
@Getter
@Setter
public class OrdenItem {
    @Id
    @Column(name = "codigo_orden_item")
    private UUID codigoOrdenItem;

    @ManyToOne
    @JoinColumn(name = "codigo_orden", referencedColumnName = "codigo_orden")
    private Orden orden;

    @ManyToOne
    @JoinColumn(name = "codigo_producto", referencedColumnName = "codigo_producto")
    private Producto producto;

    @Column(name = "cantidad_item", nullable = false)
    private int cantidadItem;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal;

    @Column(name = "status" , columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean status = true;

    public OrdenItem() {
        this.codigoOrdenItem = UUID.randomUUID();
    }

    public void calcularSubtotal() {
        this.subtotal = this.cantidadItem * this.precioUnitario;
    }
}