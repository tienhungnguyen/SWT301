package tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.RegisterPage;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Kiểm thử chức năng đăng ký tài khoản")
public class RegisterTest extends BaseTest {
    static RegisterPage registerPage;
    static WebDriverWait wait;

    @BeforeAll
    static void initPage() {
        registerPage = new RegisterPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void ensureLoggedOut() {
        // Nếu đã đăng nhập thì logout để các test sau luôn thấy nút Đăng ký
        if (registerPage.isRegisterSuccessful()) {
            registerPage.clickLogout();
            registerPage.navigate(); // Reload lại trang chủ để header cập nhật lại DOM
        }
    }

    @Test
    @Order(1)
    @DisplayName("Đăng ký thành công với thông tin hợp lệ")
    void testRegisterSuccess() {
        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@gmail.com";
        registerPage.navigate();
        registerPage.openRegisterModal();
        registerPage.register("Test User", uniqueEmail, "123456789");
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(d -> registerPage.isRegisterSuccessful());
        assertTrue(registerPage.isRegisterSuccessful(), "Đăng ký không thành công");
        // Đăng xuất sau khi đăng ký thành công
        registerPage.clickLogout();
    }

    @Test
    @Order(2)
    @DisplayName("Đăng ký với email đã tồn tại")
    void testRegisterDuplicateEmail() {
        String existingEmail = "thinhbmde180185@fpt.edu.vn";
        registerPage.navigate();
        registerPage.openRegisterModal();
        registerPage.register("Test User", existingEmail, "123456789");
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(d -> registerPage.isErrorDisplayed());
        assertTrue(registerPage.isErrorDisplayed(), "Không hiển thị thông báo lỗi khi đăng ký với email đã tồn tại");
        String errorMsg = registerPage.getErrorMessage();
        System.out.println("Thông báo lỗi: " + errorMsg);
    }

    @Test
    @Order(3)
    @DisplayName("Đăng ký với mật khẩu quá ngắn")
    void testRegisterShortPassword() {
        String uniqueEmail = "shortpw" + System.currentTimeMillis() + "@gmail.com";
        registerPage.navigate();
        registerPage.openRegisterModal();
        registerPage.register("Test User", uniqueEmail, "123");
        // Kiểm tra validationMessage của trường password
        String pwValidationMsg = registerPage.getInputValidationMessage(registerPage.getPasswordField());
        System.out.println("Password validation message: " + pwValidationMsg);
        assertFalse(pwValidationMsg.isEmpty(), "Không có validation message cho mật khẩu ngắn");
    }

    @Test
    @Order(4)
    @DisplayName("Đăng ký với email không đúng định dạng")
    void testRegisterInvalidEmail() {
        String invalidEmail = "invalid-email-format";
        String uniqueName = "Test User " + System.currentTimeMillis();
        registerPage.navigate();
        registerPage.openRegisterModal();
        registerPage.register(uniqueName, invalidEmail, "123456789");
        // Kiểm tra validationMessage của trường email
        String emailValidationMsg = registerPage.getInputValidationMessage(registerPage.getEmailField());
        System.out.println("Email validation message: " + emailValidationMsg);
        assertFalse(emailValidationMsg.isEmpty(), "Không có validation message cho email sai định dạng");
    }
}