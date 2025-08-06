package com.cibertec.proyecto.TiendaGamer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class InicioController {

    @GetMapping(value = {"", "index", "home", "inicio"})
    public String inicio() {
        return "home/index";
    }


}
