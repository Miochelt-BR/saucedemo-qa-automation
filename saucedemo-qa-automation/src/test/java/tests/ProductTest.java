package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import pages.ProductPage;
import utils.Users;
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
    void PR_010_visualizar_lista() {
        iniciarTeste("PR-010 | Visualizar Lista | ALTA");
        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);

        assertTrue(inventoryPage.obterQuantidadeProdutos() > 0);
        test.pass("Lista de produtos carregada com sucesso.");
    }

    @Test
    @Order(11)
    void PR_011_ordenar_por_preco() {
        iniciarTeste("PR-011 | Ordenar Preço | ALTA");
        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);

        inventoryPage.selecionarOrdenacao("Price (low to high)");
        assertTrue(inventoryPage.verificarPrecoOrdenadoMenorParaMaior());
        test.pass("Ordenação por preço (menor para maior) validada.");
    }

    @Test
    @Order(12)
    void PR_012_ordenar_por_nome() {
        iniciarTeste("PR-012 | Ordenar Nome | ALTA");
        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);

        inventoryPage.selecionarOrdenacao("Name (A to Z)");
        assertTrue(inventoryPage.verificarNomeOrdenadoAparaZ());
        test.pass("Ordenação alfabética validada.");
    }

    @Test
    @Order(13)
    void PR_013_imagem_quebrada() {
        iniciarTeste("PR-013 | Imagem Quebrada | ALTA");
        loginPage.login("problem_user", Users.PASSWORD);

        // Verificação das imagens
        boolean imagensEstaoOk = inventoryPage.verificarImagensEstaoCorretas();

        if (!imagensEstaoOk) {
            // Log detalhado informando que um comportamento anômalo foi detectado
            test.warning("DETECÇÃO DE BUG: Foram encontrados links de imagem quebrados (404) na página de produtos.");
            test.info("Usuário utilizado: problem_user");
            test.pass("A automação validou com sucesso a presença do erro visual esperado.");
        } else {
            // Se as imagens estiverem OK para o problem_user, algo mudou no sistema (o bug sumiu)
            test.fail("FALHA: O bug esperado (imagens quebradas) não foi detectado para o 'problem_user'.");
            fail("Comportamento de erro não encontrado no problem_user.");
        }
    }


    @Test
    @Order(14)
    void PR_014_verificar_lentidao() {
        iniciarTeste("PR-014 | Lentidão | ALTA");
        long inicio = System.currentTimeMillis();

        loginPage.login("performance_glitch_user", Users.PASSWORD);
        inventoryPage.obterQuantidadeProdutos();

        long fim = System.currentTimeMillis();
        long duracao = (fim - inicio) / 1000;

        test.info("Tempo de carregamento: " + duracao + " segundos.");
        assertTrue(duracao >= 5, "O delay de performance não foi detectado!");
        test.pass("Lentidão detectada e tratada com sucesso.");
    }
}