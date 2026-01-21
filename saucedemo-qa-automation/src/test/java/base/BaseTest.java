package base;

import com.aventstack.extentreports.ExtentTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import utils.DriverFactory;
import utils.ExtentManager;

public class BaseTest {
    protected WebDriver driver;
    protected ExtentTest test;

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