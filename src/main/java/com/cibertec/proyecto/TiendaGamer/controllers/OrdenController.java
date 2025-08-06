package com.cibertec.proyecto.TiendaGamer.controllers;

import com.cibertec.proyecto.TiendaGamer.models.Cliente;
import com.cibertec.proyecto.TiendaGamer.models.Orden;
import com.cibertec.proyecto.TiendaGamer.service.AuthService;
import com.cibertec.proyecto.TiendaGamer.service.JasperReportsService;
import com.cibertec.proyecto.TiendaGamer.service.OrdenService;
import jakarta.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/ordenes")
public class OrdenController {

    OrdenService ordenService;

    AuthService authService;

    JasperReportsService jasperReportsService;

    public OrdenController(OrdenService ordenService,
                           AuthService authService,
                           JasperReportsService jasperReportsService) {
        this.ordenService = ordenService;
        this.authService = authService;
        this.jasperReportsService = jasperReportsService;
    }


    @GetMapping(value = {"", "/",})
    public String listarOrdenes(HttpSession session, Model model) {
        Object username = session.getAttribute("username");
        if (username == null) {
            return "redirect:/auth/login";
        }
        Cliente cliente = authService.getClienteByUsuario(authService.getUsuarioByUsername(username.toString()));
        List<Orden> ordenes = ordenService.listarOrdenesPorCliente(cliente.getCodigoCliente());


        model.addAttribute("ordenes", ordenes);
        return "home/ordenes/ordenes";
    }

    @GetMapping("/{codigo}/base64")
    public ResponseEntity<?> getCharacterPdf(@PathVariable UUID codigo) throws JRException {
        Orden orden = ordenService.findById(codigo).orElse(null);

        try {
            Map<String, Object> parameters = jasperReportsService.getStringObjectMap(orden);
            byte[] reporte = jasperReportsService.generatePdfReport("factura", parameters,orden);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment ; filename=reporte" + codigo + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(reporte);
        } catch (JRException e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Collections.singletonMap("error", "No se pudo realizar el reporte"));

        }
    }

}
