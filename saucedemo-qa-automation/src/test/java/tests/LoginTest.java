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
    @DisplayName("LG-001 – Login com sucesso")
    void LG_001_sucesso() {
        startTest("LG-001 | Sucesso | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Verificar acesso ao sistema com usuário padrão (standard_user).");

        loginPage.login(Users.getUsername(Users.UserType.STANDARD), Users.PASSWORD);

        boolean logado = driver.getCurrentUrl().contains("inventory.html");
        assertTrue(logado);
        test.pass("Login realizado com sucesso e redirecionamento validado.");
    }

    @Test
    @Order(2)
    @DisplayName("LG-002 – Validação de senha inválida")
    void LG_002_senha_invalida() {
        startTest("LG-002 | Senha Inválida | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Validar bloqueio de acesso com senha incorreta.");

        loginPage.login(Users.getUsername(Users.UserType.STANDARD), "errada");

        String erro = loginPage.getErrorMessage();
        assertTrue(erro.contains("do not match"));
        test.pass("Mensagem de erro validada corretamente.");
    }

    @Test
    @Order(3)
    @DisplayName("LG-003 – Usuário inexistente")
    void LG_003_usuario_inexistente() {
        startTest("LG-003 | Usuário Inexistente | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Tentar login com credenciais que não constam na base.");

        loginPage.login("nao_existente", Users.PASSWORD);

        assertTrue(loginPage.getErrorMessage().contains("do not match"));
        test.pass("O sistema negou o acesso corretamente.");
    }

    @Test
    @Order(4)
    @DisplayName("LG-004 – Usuário bloqueado")
    void LG_004_bloqueado() {
        startTest("LG-004 | Bloqueado | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Validar se usuários 'locked_out_user' são impedidos de entrar.");

        loginPage.login(Users.getUsername(Users.UserType.LOCKED), Users.PASSWORD);

        String erro = loginPage.getErrorMessage();
        assertTrue(erro.contains("locked out"));
        test.pass("Bloqueio de segurança validado com sucesso.");
    }

    @Test
    @Order(5)
    @DisplayName("LG-005 – Performance Glitch User")
    void LG_005_performance() {
        startTest("LG-005 | Performance | MÉDIA");
        PriorityTag.media(test);
        PriorityTag.cenario(test, "Detectar atraso excessivo no carregamento do login.");

        long inicio = System.currentTimeMillis();
        loginPage.login(Users.getUsername(Users.UserType.PERFORMANCE), Users.PASSWORD);
        long fim = System.currentTimeMillis();

        long tempo = (fim - inicio) / 1000;
        test.info("Tempo registrado: " + tempo + " segundos.");

        if (tempo >= 5) {
            PriorityTag.bugDetectado(test, "Lentidão detectada (Login acima de 5s).");
        } else {
            test.pass("Login realizado dentro do tempo aceitável.");
        }
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    @Order(6)
    @DisplayName("LG-006 – Error User")
    void LG_006_error_user() {
        startTest("LG-006 | Error User | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Validar comportamento do perfil 'error_user'.");

        loginPage.login(Users.getUsername(Users.UserType.ERROR), Users.PASSWORD);

        if (driver.getCurrentUrl().contains("inventory.html")) {
            PriorityTag.bugDetectado(test, "Logado com 'error_user'. Falhas funcionais esperadas.");
        }
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    @Order(7)
    @DisplayName("LG-007 – Visual User")
    void LG_007_visual_user() {
        startTest("LG-007 | Visual User | MÉDIA");
        PriorityTag.media(test);
        PriorityTag.cenario(test, "Identificar anomalias visuais com 'visual_user'.");

        loginPage.login(Users.getUsername(Users.UserType.VISUAL), Users.PASSWORD);

        if (driver.getCurrentUrl().contains("inventory.html")) {
            PriorityTag.bugDetectado(test, "Interface pode apresentar elementos corrompidos.");
        }
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    @Order(8)
    @DisplayName("LG-008 – Campo Username vazio")
    void LG_008_user_vazio() {
        startTest("LG-008 | User Vazio | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Tentar login sem preencher o usuário.");

        loginPage.login("", Users.PASSWORD);

        assertTrue(loginPage.getErrorMessage().contains("Username is required"));
        test.pass("Validação de campo obrigatório OK.");
    }

    @Test
    @Order(9)
    @DisplayName("LG-009 – Campo Senha vazio")
    void LG_009_senha_vazia() {
        startTest("LG-009 | Senha Vazia | ALTA");
        PriorityTag.alta(test);
        PriorityTag.cenario(test, "Tentar login sem preencher a senha.");

        loginPage.login(Users.getUsername(Users.UserType.STANDARD), "");

        assertTrue(loginPage.getErrorMessage().contains("Password is required"));
        test.pass("Validação de campo obrigatório OK.");
    }
}