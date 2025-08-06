package com.cibertec.proyecto.TiendaGamer.dtos;

import com.cibertec.proyecto.TiendaGamer.models.Producto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CarritoSessionDTO {

    private List<CarritoItemDTO> items;
    private Double total;

    public CarritoSessionDTO() {
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    public void agregarItem(Producto producto, int cantidad){
        UUID codigoProducto = producto.getCodigoProducto();
        CarritoItemDTO itemExistente = items.stream()
                .filter(item -> item.getCodigoProducto().equals(codigoProducto))
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setCantidadItem(itemExistente.getCantidadItem() + cantidad);
            itemExistente.calcularSubtotal();
        } else {
            CarritoItemDTO nuevoItem = new CarritoItemDTO();
            nuevoItem.setCodigoProducto(codigoProducto);
            nuevoItem.setNombreProducto(producto.getNombreProducto());
            nuevoItem.setPrecioUnitario(producto.getPrecioProducto());
            nuevoItem.setCantidadItem(cantidad);
            nuevoItem.calcularSubtotal();
            items.add(nuevoItem);
        }
        calcularTotal();
    }

    public void eliminarItem(UUID codigoProducto) {
        items.removeIf(item -> item.getCodigoProducto().equals(codigoProducto));
        calcularTotal();
    }

    public void actualizarCantidad(UUID codigoProducto, int nuevaCantidad) {
        items.stream()
                .filter(item -> item.getCodigoProducto().equals(codigoProducto))
                .findFirst()
                .ifPresent(item -> {
                    item.setCantidadItem(nuevaCantidad);
                    item.calcularSubtotal();
                });
        calcularTotal();
    }

    public void limpiarCarrito() {
        items.clear();
        total = 0.0;
    }

    private void calcularTotal() {
        this.total = items.stream()
                .mapToDouble(CarritoItemDTO::getSubtotal)
                .sum();
    }

    public int getTotalItems() {
        return items.stream()
                .mapToInt(CarritoItemDTO::getCantidadItem)
                .sum();
    }
}