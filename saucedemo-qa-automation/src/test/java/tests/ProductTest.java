package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.ProductPage;
import utils.Users;
import utils.PriorityTag;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest extends BaseTest {

    private LoginPage loginPage;
    private ProductPage inventoryPage;

    @BeforeEach
    void prepararTeste() {
        loginPage = new LoginPage(driver);
        inventoryPage = new ProductPage(driver);
    }

    @Test
    @Order(10)
    @DisplayName("PR-010 – Visualizar lista de produtos")
    void PR_010_visualizar_lista() {
        startTest("PR-010 | Visualizar Lista | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Verificar se a vitrine de produtos carrega itens após o login.");

        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);
        assertTrue(inventoryPage.obterQuantidadeProdutos() > 0);
        test.pass("Lista de produtos carregada com sucesso.");
    }

    @Test
    @Order(11)
    @DisplayName("PR-011 – Ordenação por preço")
    void PR_011_ordenar_por_preco() {
        startTest("PR-011 | Ordenar Preço | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Validar o filtro de ordenação de preços (Low to High).");

        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);
        inventoryPage.selecionarOrdenacao("Price (low to high)");
        assertTrue(inventoryPage.verificarPrecoOrdenadoMenorParaMaior());
        test.pass("Ordenação por preço (menor para maior) validada.");
    }

    @Test
    @Order(12)
    @DisplayName("PR-012 – Ordenação alfabética")
    void PR_012_ordenar_por_nome() {
        startTest("PR-012 | Ordenar Nome | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Validar o filtro de ordenação alfabética (A to Z).");

        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);
        inventoryPage.selecionarOrdenacao("Name (A to Z)");
        assertTrue(inventoryPage.verificarNomeOrdenadoAparaZ());
        test.pass("Ordenação alfabética validada.");
    }

    @Test
    @Order(13)
    @DisplayName("PR-013 – Detecção de imagens quebradas")
    void PR_013_imagem_quebrada() {
        startTest("PR-013 | Imagem Quebrada | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Identificar falhas de carregamento de assets (imagens 404).");

        loginPage.login(Users.getUsername(Users.UserType.PROBLEM), Users.PASSWORD);
        boolean imagensEstaoOk = inventoryPage.verificarImagensEstaoCorretas();

        if (!imagensEstaoOk) {

            PriorityTag.bugDetectado(test, "Foram encontrados links de imagem quebrados na vitrine (Usuário: problem_user).");
        } else {
            test.fail("O bug esperado não se manifestou.");
            fail("Falha ao detectar imagens quebradas.");
        }
    }

    @Test
    @Order(14)
    @DisplayName("PR-014 – Teste de performance (Lentidão)")
    void PR_014_verificar_lentidao() {
        startTest("PR-014 | Lentidão | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Validar se o sistema detecta gargalos de carregamento.");

        long inicio = System.currentTimeMillis();
        loginPage.login("performance_glitch_user", Users.PASSWORD);
        inventoryPage.obterQuantidadeProdutos();
        long fim = System.currentTimeMillis();

        long duracao = (fim - inicio) / 1000;
        test.info("Tempo registrado: " + duracao + " segundos.");

        assertTrue(duracao >= 5);
        test.pass("Lentidão detectada e registrada com sucesso.");
    }
}