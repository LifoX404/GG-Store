package com.cibertec.proyecto.TiendaGamer.service;

import com.cibertec.proyecto.TiendaGamer.dtos.RegistroDTO;
import com.cibertec.proyecto.TiendaGamer.models.Cliente;
import com.cibertec.proyecto.TiendaGamer.models.Usuario;
import com.cibertec.proyecto.TiendaGamer.repository.IClienteRepository;
import com.cibertec.proyecto.TiendaGamer.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class RegistroService {

    private final IUsuarioRepository usuarioRepository;

    private final IClienteRepository clienteRepository;

    public RegistroService(IUsuarioRepository usuarioRepository, IClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public Usuario registrarUsuarioCliente(RegistroDTO registroDTO) {
        if (usuarioRepository.existsByNombreUsuario(registroDTO.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        // Crear Usuario
        Usuario usuario = new Usuario();
        usuario.setCodigoUsuario(UUID.randomUUID());
        usuario.setNombreUsuario(registroDTO.getNombreUsuario());
        usuario.setPasswordUsuario(registroDTO.getPassword());
        usuario.setRolUsuario("CLIENT");
        usuario.setStatus(true);

        // Guardar Usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // Crear Cliente asociado (solo si es CLIENT)
        if ("CLIENT".equals(usuario.getRolUsuario())) {
            Cliente cliente = new Cliente();
            cliente.setCodigoCliente(UUID.randomUUID());
            cliente.setUsuario(usuarioGuardado);
            cliente.setNombreCliente(registroDTO.getNombreCompleto());
            cliente.setEmailCliente(registroDTO.getEmail());
            cliente.setTelfCliente(registroDTO.getTelefono());
            cliente.setDireccionCliente(registroDTO.getDireccion());
            cliente.setFecRegCliente(LocalDate.now());
            cliente.setStatus(true);

            // Guardar Cliente
            Cliente clienteGuardado = clienteRepository.save(cliente);

            // Asociar cliente al usuario
            usuarioGuardado.setCliente(clienteGuardado);
        }

        return usuarioGuardado;
    }
}