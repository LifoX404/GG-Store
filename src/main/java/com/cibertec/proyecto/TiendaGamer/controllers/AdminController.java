package com.cibertec.proyecto.TiendaGamer.controllers;

import com.cibertec.proyecto.TiendaGamer.models.Cliente;
import com.cibertec.proyecto.TiendaGamer.models.Orden;
import com.cibertec.proyecto.TiendaGamer.models.Producto;
import com.cibertec.proyecto.TiendaGamer.service.AuthService;
import com.cibertec.proyecto.TiendaGamer.service.OrdenService;
import com.cibertec.proyecto.TiendaGamer.service.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    AuthService authService;

    ProductoService productoService;

    OrdenService ordenService;


    public AdminController(AuthService authService,
                           ProductoService productoService,
                           OrdenService ordenService) {
        this.authService = authService;
        this.productoService = productoService;
        this.ordenService = ordenService;
    }

    @GetMapping(value = {"", "index", "home", "inicio", "/"})
    public String admin() {
        return "admin/dashboard";
    }

    @GetMapping("/clientes")
    public String getClientes(HttpSession session, Model model) {
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {

            return "redirect:/auth/login";
        }
        try{
            List<Cliente> clientes = authService.getAllClientes();
            model.addAttribute("clientes", clientes);
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/error";
        }
        return "admin/clientes";
    }

    @GetMapping("clientes/editar/{codigo}")
    public String editarCliente(@PathVariable UUID codigo, Model model, HttpSession session) {
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        try {
            Cliente cliente = authService.getClienteByCodigoCliente(codigo).orElse(null);
            if (cliente != null) {
                model.addAttribute("cliente", cliente);

            } else {
                return "redirect:/error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "admin/editarcliente";
    }

    @PostMapping("/clientes/editar")
    public String editarCliente(Cliente cliente, HttpSession session) {
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {

            return "redirect:/auth/login";
        }
        try {
            authService.editarCliente(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/admin/clientes";
    }

    @PostMapping("/clientes/eliminar/{codigo}")
    public String eliminarCliente(@PathVariable UUID codigo, HttpSession session) {
        System.out.println("Llegó al método DELETE con código: " + codigo);
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        try {
            authService.setStatusFalse(codigo);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/admin/clientes";
    }

    @GetMapping("/productos")
    public String listarProductos(Producto prod, HttpSession session, Model model) {
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        try {
            List<Producto> lista = productoService.findAll();
            model.addAttribute("productos", lista);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "admin/productos";
    }

    @GetMapping("productos/editar/{codigo}")
    public String editarProducto(@PathVariable UUID codigo, Model model, HttpSession session) {
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        try {
            Producto producto = productoService.findById(codigo).orElse(null);
            if (producto != null) {
                model.addAttribute("producto", producto);
            } else {
                return "redirect:/error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "admin/editarproducto";
    }

    @PostMapping("productos/editar")
    public String editarProducto(Producto producto, HttpSession session) {
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        try {
            productoService.save(producto);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/admin/productos";
    }

    @PostMapping("productos/eliminar/{codigo}")
    public String eliminarProducto(@PathVariable UUID codigo, HttpSession session) {
        System.out.println("Llegó al método DELETE con código: " + codigo);
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        try {
            productoService.setStatusFalse(codigo);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/admin/productos";
    }

    @GetMapping("/productos/nuevo")
    public String nuevoProducto(Model model,HttpSession session) {
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        model.addAttribute("producto", new Producto());
        return "admin/createproducto";
    }

    @PostMapping("/productos/create")
    public String crearProducto(Producto producto, HttpSession session) {
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        try {
            productoService.save(producto);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/admin/productos";
    }

    @GetMapping("/ordenes")
    public String listarOrdenes(HttpSession session, Model model) {
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        try {
            List<Orden> ordenes = ordenService.listarOrdenesPorFecha();
            model.addAttribute("ordenes", ordenes);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "admin/ordenes";
    }


    @GetMapping("/ordenes/detalles/{codigo}")
    @Transactional(readOnly = true)
    public String detallesOrden(@PathVariable UUID codigo, Model model, HttpSession session) {
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        try {
            Orden orden = ordenService.listarOrdenPorId(codigo).orElse(null);

            if (orden != null) {
                orden.getOrdenItems().size();
                model.addAttribute("orden", orden);
            } else {
                return "redirect:/error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "admin/detallesorden";
    }

    @GetMapping("/ordenes/editar/{codigo}")
    public String editarOrden(@PathVariable UUID codigo, Model model, HttpSession session){
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        try {
            Orden orden = ordenService.listarOrdenPorId(codigo).orElse(null);
            if (orden != null) {
                model.addAttribute("orden", orden);
            } else {
                return "redirect:/error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "admin/editarorden";
    }

    @PostMapping("/ordenes/editar")
    public String editarOrden(Orden orden, HttpSession session){
        if (session.getAttribute("username") == null || !"ADMIN".equals(session.getAttribute("userRol"))) {
            return "redirect:/auth/login";
        }
        try {
            ordenService.actualizarOrden(orden);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
        return "redirect:/admin/ordenes";
    }






}
