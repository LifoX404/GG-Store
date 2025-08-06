package com.cibertec.proyecto.TiendaGamer.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_ordenes")
@Getter
@Setter
public class Orden {
    @Id
    @Column(name = "codigo_orden")
    private UUID codigoOrden;

    @ManyToOne
    @JoinColumn(name = "codigo_cliente", referencedColumnName = "codigo_cliente")
    public Cliente cliente;

    @Column(name = "fecha_orden")
    private LocalDateTime fechaOrden = LocalDateTime.now();

    @Column(name = "total_orden", nullable = false)
    private Double totalOrden;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_orden", length = 20)
    private EstadoOrden estadoOrden = EstadoOrden.PENDIENTE;

    @Column(name = "direccion_entrega", length = 200)
    private String direccionEntrega;

    @Column(name = "telefono_entrega", length = 9)
    private String telefonoEntrega;

    @Column(name = "metodo_pago", length = 50)
    private String metodoPago;

    @Column(name = "observaciones", length = 500)
    private String observaciones;

    @Column(name = "status" , columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean status = true;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrdenItem> ordenItems;

    public Orden() {
        this.codigoOrden = UUID.randomUUID();
    }

    public enum EstadoOrden {
        PENDIENTE,
        CONFIRMADA,
        PROCESANDO,
        ENVIADO,
        ENTREGADO,
        CANCELADO
    }
}