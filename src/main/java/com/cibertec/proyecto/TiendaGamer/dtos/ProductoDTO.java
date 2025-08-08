package com.cibertec.proyecto.TiendaGamer.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductoDTO {

    private UUID codigoProducto;

    private String nombreProducto;

    private String descripcionProducto;

    private double precioProducto;

    private int stockProducto = 0;

    private String imagenProducto;

    private Boolean status = true;
}
