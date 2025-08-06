package com.cibertec.proyecto.TiendaGamer.controllers;

import com.cibertec.proyecto.TiendaGamer.dtos.CarritoSessionDTO;
import com.cibertec.proyecto.TiendaGamer.models.Cliente;
import com.cibertec.proyecto.TiendaGamer.models.Producto;
import com.cibertec.proyecto.TiendaGamer.models.Usuario;
import com.cibertec.proyecto.TiendaGamer.service.AuthService;
import com.cibertec.proyecto.TiendaGamer.service.CarritoService;
import com.cibertec.proyecto.TiendaGamer.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private CarritoService carritoService;

    private ProductoService productoService;

    private AuthService authService;

    public CarritoController(CarritoService carritoService,
                             ProductoService productoService,
                             AuthService authService) {
        this.carritoService = carritoService;
        this.productoService = productoService;
        this.authService = authService;
    }

    @GetMapping(value = {"", "/",})
    public String verCarrito(HttpSession session, Model model) {
        try {
            CarritoSessionDTO carrito = carritoService.obtenerCarritoDeSession(session);
            model.addAttribute("carrito", carrito);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "carrito/carrito";
    }

    @PostMapping("/agregar")
    public String agregarAlCarrito(@RequestParam int cantidad, @RequestParam UUID productoId, HttpSession session) {
        Producto producto = productoService.findById(productoId).orElseThrow();
        try {
            carritoService.agregarProductoAlCarrito(session, producto, cantidad);
        }catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "redirect:/carrito/";
    }

    @PostMapping("/eliminar")
    public String eliminarDelCarrito(@RequestParam UUID codigoProducto, HttpSession session) {
        try {
            carritoService.eliminarProductoDelCarrito(session, codigoProducto);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/carrito/";
    }

    @PostMapping("/procesar")
    public String procesarCarrito(HttpSession session,
                                  @RequestParam(defaultValue = "Efectivo") String metodoPago,
                                  @RequestParam(defaultValue = "") String observaciones,
                                  Model model) {
        if (session.getAttribute("username") == null) {
            return "redirect:/auth/login";
        }

        CarritoSessionDTO carrito = carritoService.obtenerCarritoDeSession(session);
        if (carrito.getItems().isEmpty()) {
            model.addAttribute("error", "El carrito está vacío.");
            return "carrito/carrito";
        }

        String userId = session.getAttribute("username").toString();
        Usuario user = authService.getUsuarioByUsername(userId);
        Cliente cliente = authService.getClienteByUsuario(user);

        try {
            carritoService.procesarOrden(session, cliente,
                    cliente.getDireccionCliente(),
                    cliente.getTelfCliente(),
                    metodoPago,
                    observaciones);

            // Redirige directamente al GET que muestra las órdenes
            return "redirect:/ordenes/";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error?error=true";
        }
    }



}
