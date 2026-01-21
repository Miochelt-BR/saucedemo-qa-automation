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

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");

            String timestamp = LocalDateTime.now().format(formatter);

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