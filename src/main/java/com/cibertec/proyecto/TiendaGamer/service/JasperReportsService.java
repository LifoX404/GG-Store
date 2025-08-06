package com.cibertec.proyecto.TiendaGamer.service;

import com.cibertec.proyecto.TiendaGamer.models.Orden;
import com.cibertec.proyecto.TiendaGamer.models.OrdenItem;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportsService {


    public byte[] generatePdfReport(String reportName, Map<String, Object> parameters, Orden orden) throws JRException {
        // Cargar el archivo del reporte
        InputStream reportStream = getClass().getResourceAsStream("/reports/" + reportName + ".jasper");
        if (reportStream == null) {
            System.out.println("ERROR: No se encontró el archivo en la ruta: /reports/" + reportName + ".jasper");
            return null;
        }

        // Cargar el logo como InputStream
        InputStream logoStream = getClass().getResourceAsStream("/images/TechPlusLogo.svg");
        if (logoStream == null) {
            System.out.println("ERROR: No se encontró el logo en la ruta: /images/TechPlusLogo.svg");
        } else {
            parameters.put("image", logoStream);
        }

        // Cargar el reporte
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);

        List<OrdenItem> items = orden.getOrdenItems();
        JRBeanCollectionDataSource itemsDataSource = new JRBeanCollectionDataSource(items);
        parameters.put("itemsData", itemsDataSource);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }


    public Map<String, Object> getStringObjectMap(Orden orden) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("codigo", orden.getCodigoOrden());
        parameters.put("fecha", orden.getFechaOrden().toString());
        parameters.put("total", orden.getTotalOrden());
        parameters.put("direccion", orden.getDireccionEntrega());
        parameters.put("telefono", orden.getTelefonoEntrega());
        parameters.put("formaPago", orden.getMetodoPago());
        parameters.put("observaciones", orden.getObservaciones());
        parameters.put("nombreCliente", orden.getCliente().getNombreCliente());
        parameters.put("emailCliente", orden.getCliente().getEmailCliente());
        parameters.put("image", "/images/TechPlusLogo.svg");
        return parameters;
    }
}
