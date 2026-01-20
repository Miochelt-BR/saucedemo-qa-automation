package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class InventoryPage extends BasePage {

    private By itensProdutos = By.className("inventory_item");
    private By botaoAdicionarPrimeiro = By.xpath("(//button[text()='Add to cart'])[1]");
    private By iconeCarrinho = By.className("shopping_cart_badge");
    private By seletorOrdenacao = By.className("product_sort_container");
    private By precosProdutos = By.className("inventory_item_price");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public int obterQuantidadeProdutos() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(itensProdutos));
        return driver.findElements(itensProdutos).size();
    }

    public void adicionarPrimeiroProdutoAoCarrinho() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoAdicionarPrimeiro)).click();
    }

    public String obterTextoCarrinho() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(iconeCarrinho)).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void selecionarOrdenacao(String texto) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(seletorOrdenacao));
        new Select(dropdown).selectByVisibleText(texto);
    }

    public boolean verificarPrecoOrdenadoMenorParaMaior() {
        List<WebElement> listaPrecos = driver.findElements(precosProdutos);
        for (int i = 0; i < listaPrecos.size() - 1; i++) {
            double atual = Double.parseDouble(listaPrecos.get(i).getText().replace("$", ""));
            double proximo = Double.parseDouble(listaPrecos.get(i + 1).getText().replace("$", ""));
            if (atual > proximo) return false;
        }
        return true;
    }
}