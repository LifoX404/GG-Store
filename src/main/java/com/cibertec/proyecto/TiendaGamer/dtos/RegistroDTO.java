package com.cibertec.proyecto.TiendaGamer.dtos;

import lombok.Data;

@Data

public class RegistroDTO {
    private String nombreUsuario;
    private String password;
    private String nombreCompleto;
    private String email;
    private String telefono;
    private String direccion;
}