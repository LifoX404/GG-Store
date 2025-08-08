package com.cibertec.proyecto.TiendaGamer.service;

import com.cibertec.proyecto.TiendaGamer.dtos.CarritoItemDTO;
import com.cibertec.proyecto.TiendaGamer.dtos.CarritoSessionDTO;
import com.cibertec.proyecto.TiendaGamer.models.Cliente;
import com.cibertec.proyecto.TiendaGamer.models.Orden;
import com.cibertec.proyecto.TiendaGamer.models.OrdenItem;
import com.cibertec.proyecto.TiendaGamer.models.Producto;
import com.cibertec.proyecto.TiendaGamer.repository.IOrdenRepository;
import com.cibertec.proyecto.TiendaGamer.repository.IProductoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CarritoService {

    private static final String CARRITO_SESSION_KEY = "carrito";

    @Autowired
    private IOrdenRepository ordenRepository;

    @Autowired
    private IProductoRepository productoRepository;

    public CarritoSessionDTO obtenerCarritoDeSession(HttpSession session) {
        CarritoSessionDTO carrito = (CarritoSessionDTO) session.getAttribute(CARRITO_SESSION_KEY);
        if (carrito == null) {
            carrito = new CarritoSessionDTO();
            session.setAttribute(CARRITO_SESSION_KEY, carrito);
        }
        return carrito;
    }

    public void agregarProductoAlCarrito(HttpSession session, Producto producto, int cantidad) {
        CarritoSessionDTO carrito = obtenerCarritoDeSession(session);
        carrito.agregarItem(producto, cantidad);
        session.setAttribute(CARRITO_SESSION_KEY, carrito);
    }

    public void eliminarProductoDelCarrito(HttpSession session, UUID codigoProducto) {
        CarritoSessionDTO carrito = obtenerCarritoDeSession(session);
        carrito.eliminarItem(codigoProducto);
        session.setAttribute(CARRITO_SESSION_KEY, carrito);
    }

    public void actualizarCantidadProducto(HttpSession session, UUID codigoProducto,
                                           int nuevaCantidad) {
        CarritoSessionDTO carrito = obtenerCarritoDeSession(session);
        carrito.actualizarCantidad(codigoProducto, nuevaCantidad);
        session.setAttribute(CARRITO_SESSION_KEY, carrito);
    }

    @Transactional
    public Orden procesarOrden(HttpSession session,
                               Cliente cliente,
                               String direccionEntrega,
                               String telefonoEntrega,
                               String metodoPago,
                               String observaciones) {

        CarritoSessionDTO carrito = obtenerCarritoDeSession(session);



        if (carrito.getItems().isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        // Crear la orden
        Orden orden = new Orden();
        orden.setCliente(cliente);
        orden.setTotalOrden(carrito.getTotal());
        orden.setDireccionEntrega(direccionEntrega);
        orden.setTelefonoEntrega(telefonoEntrega);
        orden.setMetodoPago(metodoPago);
        orden.setObservaciones(observaciones);

        // Crear los items de la orden
        List<OrdenItem> ordenItems = new ArrayList<>();
        for (CarritoItemDTO itemCarrito : carrito.getItems()) {
            Producto producto = productoRepository.findById(itemCarrito.getCodigoProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemCarrito.getCodigoProducto()));

            OrdenItem ordenItem = new OrdenItem();
            ordenItem.setOrden(orden);
            ordenItem.setProducto(producto);
            ordenItem.setCantidadItem(itemCarrito.getCantidadItem());
            ordenItem.setPrecioUnitario(itemCarrito.getPrecioUnitario());
            ordenItem.calcularSubtotal();

            ordenItems.add(ordenItem);
        }

        orden.setOrdenItems(ordenItems);

        // Guardar la orden
        Orden ordenGuardada = ordenRepository.save(orden);

        // Limpiar el carrito de la sesión
        carrito.limpiarCarrito();
        session.setAttribute(CARRITO_SESSION_KEY, carrito);

        return ordenGuardada;
    }

    public void limpiarCarrito(HttpSession session) {
        CarritoSessionDTO carrito = obtenerCarritoDeSession(session);
        carrito.limpiarCarrito();
        session.setAttribute(CARRITO_SESSION_KEY, carrito);
    }
}