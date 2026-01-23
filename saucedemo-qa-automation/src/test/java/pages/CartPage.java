package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class CartPage extends BasePage {


    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");
    private By cartItem = By.className("cart_item");
    private By removeItemButton = By.cssSelector("button[id^='remove']");
    private By checkoutButton = By.id("checkout");
    private By continueShoppingButton = By.id("continue-shopping");
    private By cartItemName = By.className("inventory_item_name");

    public CartPage(WebDriver driver) {
        super(driver);
    }



    public void acessarCarrinho() {
        wait.until(ExpectedConditions.elementToBeClickable(cartLink)).click();
    }

    public void removerProduto() {
        wait.until(ExpectedConditions.elementToBeClickable(removeItemButton)).click();
    }

    public void clicarCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }



    public String getQuantidadeBadge() {
        List<WebElement> badge = driver.findElements(cartBadge);
        return badge.isEmpty() ? "0" : badge.get(0).getText();
    }

    public boolean isBadgeExibida() {
        return !driver.findElements(cartBadge).isEmpty();
    }

    public int getQuantidadeItensNoCarrinho() {
        return driver.findElements(cartItem).size();
    }

    public boolean isCarrinhoVazio() {
        return driver.findElements(cartItem).isEmpty();
    }

    public String getNomePrimeiroItem() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemName)).getText();
    }


    public boolean validarItemPresente(String nomeProduto) {
        try {

            wait.until(ExpectedConditions.presenceOfElementLocated(cartItemName));
            List<WebElement> itens = driver.findElements(cartItemName);
            for (WebElement item : itens) {
                if (item.getText().equalsIgnoreCase(nomeProduto)) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}