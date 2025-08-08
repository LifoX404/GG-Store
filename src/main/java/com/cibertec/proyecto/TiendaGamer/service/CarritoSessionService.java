package com.cibertec.proyecto.TiendaGamer.service;

import com.cibertec.proyecto.TiendaGamer.dtos.CarritoItemDTO;
import com.cibertec.proyecto.TiendaGamer.dtos.ProductoDTO;
import org.springframework.stereotype.Service;

@Service
public class CarritoSessionService {

    public void agregarItem(ProductoDTO producto, int cantidad) {

    }

    public void eliminarItem(String codigoProducto) {
        // Implementar lógica para eliminar un producto del carrito
    }

    public void actualizarCantidad(String codigoProducto, int nuevaCantidad) {
        // Implementar lógica para actualizar la cantidad de un producto en el carrito
    }

    public void limpiarCarrito() {
        // Implementar lógica para limpiar el carrito de compras
    }

    private void calcularTotal() {
        //todo: Implementar lógica para calcular el total del carrito
    }

    public void getTotalItems() {
        //TODO: Implementar lógica para obtener el total de ítems en el carrito
    }
}
