package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.InventoryPage;
import utils.PriorityTag;
import utils.Users;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeEach
    void prepararTeste() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);

        // 🔑 Login obrigatório para acessar produtos
        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);
    }

    @Test
    @Order(1)
    void PR_001_validar_lista_produtos() {
        iniciarTeste("PR-001 | Listagem de Produtos | ALTA");
        PriorityTag.alta(test);

        int qtd = inventoryPage.obterQuantidadeProdutos();
        assertTrue(qtd > 0, "A lista de produtos está vazia!");
        test.pass("Produtos carregados: " + qtd);
    }

    @Test
    @Order(2)
    void PR_002_adicionar_ao_carrinho() {
        iniciarTeste("PR-002 | Carrinho | ALTA");
        PriorityTag.alta(test);

        inventoryPage.adicionarPrimeiroProdutoAoCarrinho();
        assertEquals("1", inventoryPage.obterTextoCarrinho());
        test.pass("Produto adicionado com sucesso.");
    }

    @Test
    @Order(3)
    void PR_003_ordenacao_preco() {
        iniciarTeste("PR-003 | Ordenação | MÉDIA");
        PriorityTag.media(test);

        inventoryPage.selecionarOrdenacao("Price (low to high)");
        assertTrue(inventoryPage.verificarPrecoOrdenadoMenorParaMaior());
        test.pass("Ordenação por preço validada.");
    }
}