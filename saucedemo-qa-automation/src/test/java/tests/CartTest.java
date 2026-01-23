package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.ProductPage;
import pages.CartPage;
import utils.Users;
import utils.PriorityTag;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartTest extends BaseTest {
    private LoginPage loginPage;
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeEach
    void setupPages() {
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test
    @Order(15)
    @DisplayName("CA-015 – Adicionar produto ao carrinho")
    void CA_015_adicionarProdutoSucesso() {
        startTest("CA-015 | Adicionar Produto | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Verificar adição de item e atualização da badge.");

        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);
        productPage.adicionarQualquerProdutoAoCarrinho();

        assertEquals("1", cartPage.getQuantidadeBadge());
        test.pass("Produto adicionado e badge validada com sucesso.");
    }

    @Test
    @Order(16)
    @DisplayName("CA-016 – Remover produto do carrinho")
    void CA_016_removerProdutoCarrinho() {
        startTest("CA-016 | Remover Produto | MÉDIA");
        PriorityTag.media(test);
        PriorityTag.cenario(test, "Validar exclusão de item da lista do carrinho.");

        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);
        productPage.adicionarQualquerProdutoAoCarrinho();
        cartPage.acessarCarrinho();
        cartPage.removerProduto();

        assertTrue(cartPage.isCarrinhoVazio());
        test.pass("Item removido e carrinho validado como vazio.");
    }

    @Test
    @Order(17)
    @DisplayName("CA-017 – Persistência após Refresh")
    void CA_017_persistenciaCarrinhoRefresh() {
        startTest("CA-017 | Persistência (Refresh) | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Validar se o estado do carrinho resiste ao refresh (F5).");

        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);
        productPage.adicionarQualquerProdutoAoCarrinho();
        String nomeAntes = productPage.getNomePrimeiroProduto();

        driver.navigate().refresh();
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
        }

        assertEquals("1", cartPage.getQuantidadeBadge());
        cartPage.acessarCarrinho();
        assertTrue(cartPage.validarItemPresente(nomeAntes));
        test.pass("Estado do carrinho mantido após atualização da página.");
    }

    @Test
    @Order(18)
    @DisplayName("CA-018 – Bug ao Adicionar (Error User)")
    void CA_018_erroAdicionarErrorUser() {
        startTest("CA-018 | Bug ao Adicionar (Error User) | MÉDIA");
        PriorityTag.media(test);
        PriorityTag.bugDetectado(test, "Detectar falha funcional na adição de itens com o perfil 'error_user'.");

        loginPage.login(Users.getUsername(Users.UserType.ERROR), Users.PASSWORD);
        productPage.adicionarQualquerProdutoAoCarrinho();

        if (cartPage.getQuantidadeBadge().equals("0")) {

            PriorityTag.bugDetectado(test, "O sistema não processou a adição do item para o 'error_user'.");
        } else {
            test.pass("Produto adicionado conforme esperado (bug não detectado).");
        }
    }
}