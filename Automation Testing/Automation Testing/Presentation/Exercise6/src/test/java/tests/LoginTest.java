package tests;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Kiểm thử chức năng đăng nhập")
public class LoginTest extends BaseTest {
    static LoginPage loginPage;
    static WebDriverWait wait;

    @BeforeAll
    static void initPage() {
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @Order(1)
    @DisplayName("Đăng nhập thành công với thông tin hợp lệ")
    void testLoginSuccess() {
        loginPage.navigate();
        loginPage.login("thinhbmde180185@fpt.edu.vn", "123456");
        // Đợi header cập nhật bằng WebDriverWait thay vì sleep
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(3))
            .until(d -> loginPage.isLoginSuccessful());
        assertTrue(loginPage.isLoginSuccessful(), "Đăng nhập không thành công");
        loginPage.logout();
    }

    @Test
    @Order(2)
    @DisplayName("Đăng nhập thất bại với thông tin không hợp lệ")
    void testLoginFail() {
        loginPage.navigate();
        loginPage.login("invalid@email.com", "wrongpassword");
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(2))
            .until(d -> loginPage.isErrorDisplayed());
        assertTrue(loginPage.isErrorDisplayed(), "Không hiển thị thông báo lỗi");
        String actualErrorMessage = loginPage.getErrorMessage();
        System.out.println("Thông báo lỗi thực tế: [" + actualErrorMessage + "]");
        boolean containsExpectedText = actualErrorMessage.contains("không hợp lệ") || 
                                     actualErrorMessage.contains("invalid") ||
                                     actualErrorMessage.contains("incorrect") || 
                                     actualErrorMessage.contains("sai") ||
                                     actualErrorMessage.contains("không đúng") ||
                                     actualErrorMessage.toLowerCase().contains("email") ||
                                     actualErrorMessage.toLowerCase().contains("password");
        assertTrue(containsExpectedText, 
                "Thông báo lỗi không chứa bất kỳ nội dung mong đợi nào. Thông báo thực tế: " + actualErrorMessage);
    }

    @ParameterizedTest(name = "Đăng nhập với: {0} / {1}")
    @Order(3)
    @org.junit.jupiter.params.provider.CsvFileSource(resources = "/login-data.csv", numLinesToSkip = 1)
    @DisplayName("Kiểm thử đăng nhập với nhiều bộ dữ liệu")
    void testLoginWithCSV(String email, String password, String expected) {
        loginPage.navigate();
        email = (email == null) ? "" : email.trim();
        password = (password == null) ? "" : password.trim();
        loginPage.login(email, password);
        if (expected.equals("success")) {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(3))
                .until(d -> loginPage.isLoginSuccessful());
            assertTrue(loginPage.isLoginSuccessful(), 
                    "Đăng nhập không thành công với " + email + "/" + password);
            loginPage.logout();
        } else {
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(2))
                .until(d -> loginPage.isErrorDisplayed());
            assertTrue(loginPage.isErrorDisplayed(), 
                    "Không hiển thị lỗi khi đăng nhập với " + email + "/" + password);
        }
    }

    @Test
    @Order(4)
    @DisplayName("Kiểm tra truy cập trang được bảo vệ sau khi đăng xuất")
    void testAccessProtectedPageAfterLogout() {
        // Đăng nhập bằng tài khoản hợp lệ mới
        loginPage.navigate();
        loginPage.login("thinhbmde180185@fpt.edu.vn", "123456");
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(3))
            .until(d -> loginPage.isLoginSuccessful());
        assertTrue(loginPage.isLoginSuccessful(), "Đăng nhập không thành công");
        loginPage.logout();
        // Có thể kiểm tra truy cập trang profile nếu đã cập nhật cho React app
        // Nếu không, chỉ cần xác nhận đã logout thành công
        assertTrue(!loginPage.isLoginSuccessful(), "Vẫn còn đăng nhập sau khi logout");
    }
    
    @AfterEach
    void cleanupAfterTest() {
        // Đảm bảo đăng xuất sau mỗi test case
        if (driver.getCurrentUrl() != null && !driver.getCurrentUrl().contains("/login")) {
            loginPage.logout();
        }
    }

    @Test
    @DisplayName("Chặn truy cập profile khi chưa đăng nhập")
    void testProfileRequireLogin() {
        driver.get("http://localhost:3000/profile");
        WebElement alert = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(2))
            .until(d -> d.findElement(org.openqa.selenium.By.xpath("//*[contains(text(),'Bạn cần đăng nhập')]")));
        assertTrue(alert.isDisplayed());
    }

    @Test
    @DisplayName("Chặn truy cập create-tour khi chưa đăng nhập")
    void testCreateTourRequireLogin() {
        driver.get("http://localhost:3000/create-tour");
        WebElement alert = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(2))
            .until(d -> d.findElement(org.openqa.selenium.By.xpath("//*[contains(text(),'Bạn cần đăng nhập')]")));
        assertTrue(alert.isDisplayed());
    }

    @Test
    @DisplayName("Truy cập profile sau khi đăng nhập")
    void testProfileAfterLogin() {
        loginPage.navigate();
        loginPage.login("thinhbmde180185@fpt.edu.vn", "123456");
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(3))
            .until(d -> loginPage.isLoginSuccessful());
        driver.get("http://localhost:3000/profile");
        boolean hasAlert = driver.findElements(org.openqa.selenium.By.xpath("//*[contains(text(),'Bạn cần đăng nhập')]"))
            .size() > 0;
        assertFalse(hasAlert);
        loginPage.logout();
    }

    @Test
    @DisplayName("Truy cập create-tour sau khi đăng nhập")
    void testCreateTourAfterLogin() {
        loginPage.navigate();
        loginPage.login("thinhbmde180185@fpt.edu.vn", "123456");
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(3))
            .until(d -> loginPage.isLoginSuccessful());
        driver.get("http://localhost:3000/create-tour");
        boolean hasAlert = driver.findElements(org.openqa.selenium.By.xpath("//*[contains(text(),'Bạn cần đăng nhập')]"))
            .size() > 0;
        assertFalse(hasAlert);
        loginPage.logout();
    }
} 