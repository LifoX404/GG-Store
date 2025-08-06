import net.sf.jasperreports.engine.JasperCompileManager;

public class ReportCompiler {
    public static void main(String[] args) {
        try {
            JasperCompileManager.compileReportToFile(
                    "src/main/resources/reports/factura.jrxml",
                    "src/main/resources/reports/factura.jasper"
            );
            System.out.println("Reporte compilado exitosamente!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}