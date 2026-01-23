package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class ProductPage extends BasePage {

    private By itensProdutos = By.className("inventory_item");
    private By seletorOrdenacao = By.className("product_sort_container");
    private By precosProdutos = By.className("inventory_item_price");
    private By nomesProdutos = By.className("inventory_item_name");
    private By imagensProdutos = By.cssSelector(".inventory_item_img img");


    private By botaoAdicionarAoCarrinho = By.cssSelector("button[id^='add-to-cart']");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    public int obterQuantidadeProdutos() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(itensProdutos));
        return driver.findElements(itensProdutos).size();
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

    public boolean verificarNomeOrdenadoAparaZ() {
        List<WebElement> listaNomes = driver.findElements(nomesProdutos);
        for (int i = 0; i < listaNomes.size() - 1; i++) {
            String atual = listaNomes.get(i).getText();
            String proximo = listaNomes.get(i + 1).getText();
            if (atual.compareToIgnoreCase(proximo) > 0) return false;
        }
        return true;
    }

    public boolean verificarImagensEstaoCorretas() {
        List<WebElement> imagens = driver.findElements(imagensProdutos);
        for (WebElement img : imagens) {
            if (img.getAttribute("src").contains("sl-404")) {
                return false;
            }
        }
        return true;
    }


    public String getNomePrimeiroProduto() {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(nomesProdutos))
                .get(0).getText();
    }


    public void adicionarQualquerProdutoAoCarrinho() {
        wait.until(ExpectedConditions.elementToBeClickable(botaoAdicionarAoCarrinho)).click();
    }
}

