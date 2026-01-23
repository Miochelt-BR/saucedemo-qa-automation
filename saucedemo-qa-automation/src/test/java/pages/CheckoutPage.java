package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;

    private By campoNome = By.id("first-name");
    private By campoSobrenome = By.id("last-name");
    private By campoCep = By.id("postal-code");
    private By botaoContinue = By.id("continue");
    private By botaoCancel = By.id("cancel");
    private By botaoFinish = By.id("finish");
    private By mensagemErro = By.cssSelector("[data-test='error']");
    private By mensagemSucesso = By.className("complete-header");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void preencherDados(String nome, String sobrenome, String cep) {
        driver.findElement(campoNome).sendKeys(nome);
        driver.findElement(campoSobrenome).sendKeys(sobrenome);
        driver.findElement(campoCep).sendKeys(cep);
    }

    public void clicarContinuar() { driver.findElement(botaoContinue).click(); }
    public void clicarCancelar() { driver.findElement(botaoCancel).click(); }
    public void clicarFinalizar() { driver.findElement(botaoFinish).click(); }

    public String obterMensagemErro() { return driver.findElement(mensagemErro).getText(); }
    public String obterMensagemSucesso() { return driver.findElement(mensagemSucesso).getText(); }
}