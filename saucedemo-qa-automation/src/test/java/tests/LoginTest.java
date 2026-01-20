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
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    @Order(2)
    void LG_002_senha_invalida() {
        startTest("LG-002 | Senha Inválida | ALTA");
        PriorityTag.alta(test);
        loginPage.login(Users.getUsername(Users.UserType.STANDARD), "errada");
        assertTrue(loginPage.getErrorMessage().contains("do not match"));
    }

    @Test
    @Order(3)
    void LG_003_usuario_inexistente() {
        startTest("LG-003 | Usuário Inexistente | ALTA");
        PriorityTag.alta(test);
        loginPage.login("nao_existente", Users.PASSWORD);
        assertTrue(loginPage.getErrorMessage().contains("do not match"));
    }

    @Test
    @Order(4)
    void LG_004_bloqueado() {
        startTest("LG-004 | Bloqueado | ALTA");
        PriorityTag.alta(test);
        loginPage.login(Users.getUsername(Users.UserType.LOCKED), Users.PASSWORD);
        assertTrue(loginPage.getErrorMessage().contains("locked out"));
    }

    @Test
    @Order(5)
    void LG_005_performance() {
        startTest("LG-005 | Performance | MÉDIA");
        PriorityTag.media(test);
        loginPage.login(Users.getUsername(Users.UserType.PERFORMANCE), Users.PASSWORD);
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    @Order(6)
    void LG_006_error_user() {
        startTest("LG-006 | Error User | ALTA");
        PriorityTag.alta(test);
        loginPage.login(Users.getUsername(Users.UserType.ERROR), Users.PASSWORD);
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    @Order(7)
    void LG_007_visual_user() {
        startTest("LG-007 | Visual User | MÉDIA");
        PriorityTag.media(test);
        loginPage.login(Users.getUsername(Users.UserType.VISUAL), Users.PASSWORD);
        assertTrue(driver.getCurrentUrl().contains("inventory.html"));
    }

    @Test
    @Order(8)
    void LG_008_user_vazio() {
        startTest("LG-008 | User Vazio | ALTA");
        PriorityTag.alta(test);
        loginPage.login("", Users.PASSWORD);
        assertTrue(loginPage.getErrorMessage().contains("Username is required"));
    }

    @Test
    @Order(9)
    void LG_009_senha_vazia() {
        startTest("LG-009 | Senha Vazia | ALTA");
        PriorityTag.alta(test);
        loginPage.login(Users.getUsername(Users.UserType.STANDARD), "");
        assertTrue(loginPage.getErrorMessage().contains("Password is required"));
    }
}