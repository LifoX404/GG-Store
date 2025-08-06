package com.cibertec.proyecto.TiendaGamer.service;

import com.cibertec.proyecto.TiendaGamer.models.Producto;
import com.cibertec.proyecto.TiendaGamer.repository.IProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductoService {

    IProductoRepository productoRepository;

    public ProductoService(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    public Optional<Producto> findById(UUID id) {
        return productoRepository.findById(id);
    }

    public List<Producto> findAllActive() {
        return productoRepository.findAllActive();
    }

    public void save(Producto producto) {
        productoRepository.save(producto);
    }

    public void deleteById(UUID id) {
        productoRepository.deleteById(id);
    }
    @Transactional
    public void setStatusFalse(UUID codigo) {
        productoRepository.setStatusFalse(codigo);
    }

    @Transactional
    public void reactivarProducto(UUID codigo) {
        Producto producto = productoRepository.findById(codigo)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        producto.setStatus(true);
        productoRepository.save(producto);
    }
}
