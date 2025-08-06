package com.cibertec.proyecto.TiendaGamer.service;

import com.cibertec.proyecto.TiendaGamer.models.Cliente;
import com.cibertec.proyecto.TiendaGamer.models.Usuario;
import com.cibertec.proyecto.TiendaGamer.repository.IClienteRepository;
import com.cibertec.proyecto.TiendaGamer.repository.IUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    IUsuarioRepository usuarioRepository;

    IClienteRepository clienteRepository;

    public AuthService(IUsuarioRepository usuarioRepository,
                       IClienteRepository clienteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
    }

    public boolean login(String username, String password) {
        return usuarioRepository.findByNombreUsuario(username)
                .map(usuario -> usuario.getPasswordUsuario().equals(password) && usuario.getStatus().equals(true))
                .orElse(false);
    }

    public boolean isAdmin(String username) {
        return usuarioRepository.findByNombreUsuario(username)
                .map(usuario -> "ADMIN".equals(usuario.getRolUsuario()))
                .orElse(false);
    }

    public boolean userExists(String username) {
        return usuarioRepository.findByNombreUsuario(username).isPresent();
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public void editarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Optional<Cliente> getClienteByCodigoCliente(UUID codigo) {
        return clienteRepository.findByCodigoCliente(codigo);
    }


    public String getUserRole(String username) {
        return usuarioRepository.findByNombreUsuario(username)
                .map(usuario -> usuario.getRolUsuario())
                .orElse(null);
    }

    public Cliente getClienteByUsuario(Usuario username) {
        return clienteRepository.findByUsuario(username).orElse(null);
    }

    public Usuario getUsuarioByUsername(String username) {
        return usuarioRepository.findByNombreUsuario(username).orElse(null);
    }

    @Transactional
    public void setStatusFalse(UUID codigo) {
        clienteRepository.setStatusFalse(codigo);
    }


}
