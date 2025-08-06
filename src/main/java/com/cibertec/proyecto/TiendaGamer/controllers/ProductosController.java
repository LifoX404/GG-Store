package com.cibertec.proyecto.TiendaGamer.controllers;

import com.cibertec.proyecto.TiendaGamer.dtos.ProductoDTO;
import com.cibertec.proyecto.TiendaGamer.models.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cibertec.proyecto.TiendaGamer.service.ProductoService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/productos")
public class ProductosController {

    ProductoService productoService;

    public ProductosController(ProductoService productoService) {
     this.productoService = productoService;
    }

    @GetMapping(value = {"", "/",})
    public String inicio(Model model) {

        List<Producto> lista= productoService.findAllActive();
        model.addAttribute("productos", lista);

        return "home/productos/productos";
    }

    @GetMapping("/{id}")
    public String verProducto(@PathVariable UUID id, Model model) {
        Producto producto = productoService.findById(id).orElseThrow();
        ProductoDTO dtoP = new ProductoDTO();
        dtoP.setNombreProducto(producto.getNombreProducto());
        dtoP.setCodigoProducto(id);
        dtoP.setPrecioProducto(producto.getPrecioProducto());
        dtoP.setDescripcionProducto(producto.getDescripcionProducto());
        dtoP.setImagenProducto(producto.getImagenProducto());
        dtoP.setStockProducto(producto.getStockProducto());
        model.addAttribute("producto", dtoP);
        return "home/productos/detalles";
    }
}
