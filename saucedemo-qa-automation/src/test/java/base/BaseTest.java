package base;

import com.aventstack.extentreports.ExtentTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import utils.AutomationMenu; // Importando seu novo menu estilizado
import utils.DriverFactory;
import utils.ExtentManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentTest test;

    static {

        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        Logger.getLogger("io.netty").setLevel(Level.OFF);
        Logger.getLogger("org.apache.hc").setLevel(Level.OFF);

        System.setProperty("webdriver.chrome.silentOutput", "true");
        System.setProperty("webdriver.chrome.args", "--log-level=3");


        AutomationMenu.exibirBoasVindas();
    }

    @BeforeEach
    public void setup() {
        driver = DriverFactory.getDriver();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterEach
    public void tearDown() {
        if (ExtentManager.getInstance() != null) {
            ExtentManager.getInstance().flush();
        }
        DriverFactory.quitDriver();
    }

    protected void startTest(String testName) {
        test = ExtentManager.getInstance().createTest(testName);
    }
}