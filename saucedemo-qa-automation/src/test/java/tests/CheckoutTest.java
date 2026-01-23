package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.*;
import utils.PriorityTag;
import utils.Users;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckoutTest extends BaseTest {


    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeEach
    void prepararCenario() {

        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);


        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);
        productPage.adicionarQualquerProdutoAoCarrinho();
        cartPage.acessarCarrinho();
        driver.findElement(org.openqa.selenium.By.id("checkout")).click();
    }

    @Test
    @Order(19)
    @DisplayName("CK-019 — Checkout completo válido")
    void CK_019_checkoutSucesso() {
        startTest("CK-019 | Checkout Completo | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Finalizar uma compra com todos os dados obrigatórios preenchidos corretamente.");

        checkoutPage.preencherDados("João", "Teste", "12345-678");
        checkoutPage.clicarContinuar();
        checkoutPage.clicarFinalizar();

        assertEquals("Thank you for your order!", checkoutPage.obterMensagemSucesso());
        test.pass("Compra finalizada com sucesso e mensagem de agradecimento validada.");
    }

    @Test
    @Order(20)
    @DisplayName("CK-020 — Campos obrigatórios vazios")
    void CK_020_validarCamposVazios() {
        startTest("CK-020 | Campos Vazios | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Tentar avançar no checkout sem preencher as informações do cliente.");

        checkoutPage.clicarContinuar();

        String erro = checkoutPage.obterMensagemErro();
        assertTrue(erro.contains("First Name is required"));
        test.pass("O sistema bloqueou o avanço e exibiu a mensagem de erro correta.");
    }

    @Test
    @Order(21)
    @DisplayName("CK-021 — Dados inválidos (Anomalia)")
    void CK_021_dadosInvalidos() {
        startTest("CK-021 | Dados Inválidos | MÉDIA");
        PriorityTag.media(test);
        PriorityTag.cenario(test, "Inserir caracteres especiais e números para validar a máscara dos campos.");

        checkoutPage.preencherDados("123", "!@#$", "abcde");
        checkoutPage.clicarContinuar();


        if (driver.getCurrentUrl().contains("checkout-step-two")) {
            PriorityTag.bugDetectado(test, "O sistema não possui validação de tipo de dado (máscara) nos campos de checkout.");
        } else {
            test.pass("O sistema barrou a entrada de dados formatados incorretamente.");
        }
    }

    @Test
    @Order(22)
    @DisplayName("CK-022 — Cancelar checkout")
    void CK_022_cancelarCheckout() {
        startTest("CK-022 | Cancelar Checkout | MÉDIA");
        PriorityTag.media(test);
        PriorityTag.cenario(test, "Verificar se ao clicar em Cancelar, o utilizador retorna ao carrinho com os itens preservados.");

        checkoutPage.clicarCancelar();

        boolean noCarrinho = driver.getCurrentUrl().contains("cart.html");
        assertTrue(noCarrinho);
        test.pass("Cancelamento realizado com sucesso. O utilizador retornou ao carrinho.");
    }
}