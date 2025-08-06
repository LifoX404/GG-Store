package com.cibertec.proyecto.TiendaGamer.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CarritoItemDTO {

    private UUID codigoProducto;
    private String nombreProducto;
    private Double precioUnitario;
    private int cantidadItem;
    private Double subtotal;

    public CarritoItemDTO(){
        this.cantidadItem=1;
        this.subtotal=0.0;
    }

    public void calcularSubtotal() {
        this.subtotal = this.precioUnitario * this.cantidadItem;
    }
}
