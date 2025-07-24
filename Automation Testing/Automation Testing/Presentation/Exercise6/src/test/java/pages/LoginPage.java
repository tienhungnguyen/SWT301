package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
    // URL của trang chủ (React app)
    private final String HOME_URL = "http://localhost:3000/";
    // Locators cho React app
    private final By openLoginModalButton = By.xpath("//nav//button[contains(text(),'Đăng nhập')]");
    private final By emailField = By.cssSelector("input[type='email']");
    private final By passwordField = By.cssSelector("input[type='password']");
    private final By loginButton = By.xpath("//div[contains(@class,'modal')]//button[@type='submit' and contains(text(),'Đăng nhập')]");
    private final By errorMessage = By.cssSelector(".alert-danger");
    private final By userNameOnHeader = By.xpath("//nav//span[contains(@class,'me-2')]");
    private final By logoutButton = By.xpath("//button[contains(text(),'Đăng xuất')]");
    
    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    // Điều hướng đến trang chủ (không tự mở modal)
    public void navigate() {
        navigateTo(HOME_URL);
    }
    
    // Đăng nhập với email và password
    public void login(String email, String password) {
        // Nếu đã đăng nhập thì logout trước
        if (isLoginSuccessful()) {
            logout();
        }
        // Nếu modal chưa mở, click nút mở modal (chỉ khi nút còn tồn tại)
        if (isElementVisible(openLoginModalButton)) {
            click(openLoginModalButton);
            waitForVisibility(emailField);
        }
        // Xóa và nhập email
        WebElement emailInput = waitForVisibility(emailField);
        emailInput.clear();
        emailInput.sendKeys(email);

        // Xóa và nhập password
        WebElement passwordInput = waitForVisibility(passwordField);
        passwordInput.clear();
        passwordInput.sendKeys(password);

        // In ra giá trị thực tế để debug
        System.out.println("Email nhập: " + emailInput.getAttribute("value"));
        System.out.println("Password nhập: " + passwordInput.getAttribute("value"));

        // Click nút đăng nhập trong modal
        click(loginButton);

        // Chờ header cập nhật hoặc thông báo lỗi xuất hiện
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    // Kiểm tra thông báo lỗi
    public boolean isErrorDisplayed() {
        return isElementVisible(errorMessage);
    }
    
    // Lấy nội dung thông báo lỗi
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    // Đăng xuất
    public void logout() {
        try {
            if (isElementVisible(logoutButton)) {
                click(logoutButton);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            // ignore
        }
    }
    
    // Kiểm tra đăng nhập thành công: header có tên user và nút Đăng xuất
    public boolean isLoginSuccessful() {
        try {
            Thread.sleep(1000);
            boolean hasUserName = isElementVisible(userNameOnHeader);
            boolean hasLogout = isElementVisible(logoutButton);
            return hasUserName && hasLogout;
        } catch (Exception e) {
            return false;
        }
    }
} 