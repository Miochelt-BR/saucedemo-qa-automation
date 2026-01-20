package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            // 1. Definimos o formato (Ex: Dia_Mes_Ano_Hora_Minuto)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");

            // 2. Pegamos a data atual e formatamos
            String timestamp = LocalDateTime.now().format(formatter);

            // 3. Criamos o caminho do arquivo com o timestamp
            String path = "test-output/Relatorio_" + timestamp + ".html";

            ExtentSparkReporter spark = new ExtentSparkReporter(path);
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("SauceDemo Automation");
            spark.config().setReportName("Resultado dos Testes");

            extent = new ExtentReports();
            extent.attachReporter(spark);
        }
        return extent;
    }
}