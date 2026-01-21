package tests;

import base.BaseTest;
import org.junit.jupiter.api.*;
import pages.LoginPage;
import utils.PriorityTag;
import utils.Users;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    void setupEach() {
        loginPage = new LoginPage(driver);
    }

    @Test
    @Order(1)
    void LG_001_sucesso() {
        startTest("LG-001 | Sucesso | ALTA");
        PriorityTag.alta(test);
        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);

        boolean logado = driver.getCurrentUrl().contains("inventory.html");
        if (logado) {
            test.pass("Login realizado com sucesso para usuário padrão.");
        } else {
            test.fail("Falha ao acessar a página de produtos.");
        }
        assertTrue(logado);
    }

    @Test
    @Order(2)
    void LG_002_senha_invalida() {
        startTest("LG-002 | Senha Inválida | ALTA");
        PriorityTag.alta(test);
        loginPage.login(Users.getUsername(Users.UserType.STANDARD), "errada");

        String erro = loginPage.getErrorMessage();
        if (erro.contains("do not match")) {
            test.pass("Mensagem de erro validada corretamente: " + erro);
        } else {
            test.fail("Mensagem de erro incorreta ou não exibida.");
        }
        assertTrue(erro.contains("do not match"));
    }

    @Test
    @Order(3)
    void LG_003_usuario_inexistente() {
        startTest("LG-003 | Usuário Inexistente | ALTA");
        PriorityTag.alta(test);
        loginPage.login("nao_existente", Users.PASSWORD);

        assertTrue(loginPage.getErrorMessage().contains("do not match"));
        test.pass("O sistema negou o acesso para usuário inexistente.");
    }

    @Test
    @Order(4)
    void LG_004_bloqueado() {
        startTest("LG-004 | Bloqueado | ALTA");
        PriorityTag.alta(test);
        loginPage.login(Users.getUsername(Users.UserType.LOCKED), Users.PASSWORD);

        String erro = loginPage.getErrorMessage();
        if (erro.contains("locked out")) {
            test.info("Comportamento esperado: Usuário bloqueado não teve acesso.");
            test.pass("Bloqueio de segurança validado com sucesso.");
        } else {
            test.warning("CUIDADO: O usuário bloqueado conseguiu logar ou a mensagem mudou.");
            test.fail("Falha na validação de usuário bloqueado.");
        }
        assertTrue(erro.contains("locked out"));
    }

    @Test
    @Order(5)
    void LG_005_performance() {
        startTest("LG-005 | Performance | MÉDIA");
        PriorityTag.media(test);

        long inicio = System.currentTimeMillis();
        loginPage.login(Users.getUsername(Users.UserType.PERFORMANCE), Users.PASSWORD);
        long fim = System.currentTimeMillis();

        long tempo = (fim - inicio) / 1000;
        test.info("Tempo de login: " + tempo + " segundos.");

        if (tempo >= 5) {
            test.warning("DETECÇÃO DE LENTIDÃO: O login demorou mais que 5 segundos como esperado.");
            test.pass("Bug de performance detectado com sucesso.");
        }
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    @Order(6)
    void LG_006_error_user() {
        startTest("LG-006 | Error User | ALTA");
        PriorityTag.alta(test);
        loginPage.login(Users.getUsername(Users.UserType.ERROR), Users.PASSWORD);

        if (driver.getCurrentUrl().contains("inventory.html")) {
            test.warning("DETECÇÃO DE BUG: Logado com 'error_user'. Funções internas podem falhar.");
            test.pass("Login técnico realizado.");
        }
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    @Order(7)
    void LG_007_visual_user() {
        startTest("LG-007 | Visual User | MÉDIA");
        PriorityTag.media(test);
        loginPage.login(Users.getUsername(Users.UserType.VISUAL), Users.PASSWORD);

        if (driver.getCurrentUrl().contains("inventory.html")) {
            test.warning("DETECÇÃO DE BUG VISUAL: Elementos de interface podem estar corrompidos.");
            test.pass("Acesso garantido para auditoria visual.");
        }
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    @Order(8)
    void LG_008_user_vazio() {
        startTest("LG-008 | User Vazio | ALTA");
        PriorityTag.alta(test);
        loginPage.login("", Users.PASSWORD);

        assertTrue(loginPage.getErrorMessage().contains("Username is required"));
        test.pass("Validação de campo obrigatório (Username) OK.");
    }

    @Test
    @Order(9)
    void LG_009_senha_vazia() {
        startTest("LG-009 | Senha Vazia | ALTA");
        PriorityTag.alta(test);
        loginPage.login(Users.getUsername(Users.UserType.STANDARD), "");

        assertTrue(loginPage.getErrorMessage().contains("Password is required"));
        test.pass("Validação de campo obrigatório (Password) OK.");
    }
}