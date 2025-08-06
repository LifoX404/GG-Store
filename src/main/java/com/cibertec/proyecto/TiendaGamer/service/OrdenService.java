package com.cibertec.proyecto.TiendaGamer.service;

import com.cibertec.proyecto.TiendaGamer.models.Cliente;
import com.cibertec.proyecto.TiendaGamer.models.Orden;
import com.cibertec.proyecto.TiendaGamer.models.Producto;
import com.cibertec.proyecto.TiendaGamer.repository.IClienteRepository;
import com.cibertec.proyecto.TiendaGamer.repository.IOrdenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdenService {

    IOrdenRepository ordenRepository;
    IClienteRepository clienteRepository;

    public OrdenService(IOrdenRepository ordenRepository,
                        IClienteRepository clienteRepository) {
        this.ordenRepository = ordenRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Orden> listarOrdenesPorCliente(UUID codigoCliente) {
        return ordenRepository.findOrdenesByClienteId(codigoCliente);
    }

    public List<Orden> listarOrdenesPorFecha() {
        return ordenRepository.findAllByOrderByFechaOrdenDesc();
    }

    public Optional<Orden> listarOrdenPorId(UUID id){
        return ordenRepository.findById(id);
    }

    public void save(Orden orden){
        ordenRepository.save(orden);
    }

    public void actualizarOrden(Orden orden) {
        Cliente cliente = clienteRepository.findById(orden.getCliente().getCodigoCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        orden.setCliente(cliente);

        ordenRepository.save(orden);
    }

    public Optional<Orden> findById(UUID id) {
        return ordenRepository.findById(id);
    }


}
