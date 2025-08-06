package com.cibertec.proyecto.TiendaGamer.controllers;

import com.cibertec.proyecto.TiendaGamer.dtos.RegistroDTO;
import com.cibertec.proyecto.TiendaGamer.models.Usuario;
import com.cibertec.proyecto.TiendaGamer.service.AuthService;
import com.cibertec.proyecto.TiendaGamer.service.RegistroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private RegistroService registroService;

    private AuthService authService;

    public AuthController(AuthService authService, RegistroService registroService) {
        this.authService = authService;
        this.registroService = registroService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/validate")
    public String validate(@RequestParam String username, @RequestParam String password, HttpSession session) {
        if(authService.userExists(username) && username != null && !username.isEmpty()){
            try{


                if(authService.login(username, password) == true){
                    session.setAttribute("username", username);
                    session.setAttribute("userRol", authService.getUserRole(username));

                    Enumeration<String> attributeNames = session.getAttributeNames();
                    while (attributeNames.hasMoreElements()) {
                        String name = attributeNames.nextElement();
                        System.out.println(name + " = " + session.getAttribute(name));

                        if(authService.isAdmin(username)) {
                            return "redirect:/admin/index";
                        } else {
                            return "redirect:/inicio";
                        }
                    }

                }else{
                    return "redirect:/auth/login?error=true";
                }

            }
            catch(IllegalArgumentException e){

            }
        }

        return "redirect:/auth/login?error=true";

    }

    @GetMapping("/register")
    public String registro(Model model) {
        model.addAttribute("registroDTO", new RegistroDTO());
        return "auth/register";
    }

    @PostMapping("/process")
    public String registrarUsuario(@ModelAttribute RegistroDTO registroDTO) {
        try {
            if (registroDTO.getNombreUsuario() == null || registroDTO.getNombreUsuario().isEmpty() ||
                registroDTO.getPassword() == null || registroDTO.getPassword().isEmpty() ||
                registroDTO.getEmail() == null || registroDTO.getEmail().isEmpty()) {
                return "redirect:/auth/register?error=missing_fields";
            }
            Usuario usuario = registroService.registrarUsuarioCliente(registroDTO);
            if (usuario != null) {
                return "redirect:/auth/login?success=true";
            } else {
                return "redirect:/auth/registro?error=true";
            }
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
            return "redirect:/auth/registro?error=unexpected";
        }

    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }
}
