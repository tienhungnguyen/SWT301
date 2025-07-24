package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage extends BasePage {
    private final String HOME_URL = "http://localhost:3000/";
    // Locators cho React app
    private final By openRegisterModalButton = By.xpath("//nav//button[contains(text(),'Đăng ký')]");
    private final By nameField = By.cssSelector("input[placeholder='Nhập họ và tên']");
    private final By emailField = By.cssSelector("input[placeholder='example@gmail.com']");
    private final By passwordField = By.cssSelector("input[placeholder='Nhập mật khẩu']");
    private final By registerButton = By.xpath("//div[contains(@class,'modal')]//button[@type='submit' and contains(text(),'Đăng ký')]");
    private final By modalTitle = By.xpath("//div[contains(@class,'modal')]//*[contains(text(),'Đăng ký tài khoản')]");
    private final By errorMessage = By.cssSelector(".alert-danger");
    private final By userNameOnHeader = By.xpath("//nav//span[contains(@class,'me-2')]");
    private final By logoutButton = By.xpath("//button[contains(text(),'Đăng xuất')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    // Điều hướng đến trang chủ
    public void navigate() {
        navigateTo(HOME_URL);
    }

    // Mở modal đăng ký
    public void openRegisterModal() {
        click(openRegisterModalButton);
        waitForVisibility(modalTitle); // Chờ tiêu đề modal xuất hiện
        waitForVisibility(nameField);  // Chờ trường họ tên xuất hiện
    }

    // Đăng ký tài khoản mới
    public void register(String name, String email, String password) {
        if (!isElementVisible(nameField)) {
            openRegisterModal();
        }
        WebElement nameInput = waitForVisibility(nameField);
        nameInput.clear();
        nameInput.sendKeys(name);

        WebElement emailInput = waitForVisibility(emailField);
        emailInput.clear();
        emailInput.sendKeys(email);

        WebElement passwordInput = waitForVisibility(passwordField);
        passwordInput.clear();
        passwordInput.sendKeys(password);

        click(registerButton);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Kiểm tra đăng ký thành công: header có tên user và nút Đăng xuất
    public boolean isRegisterSuccessful() {
        try {
            Thread.sleep(1000);
            boolean hasUserName = isElementVisible(userNameOnHeader);
            boolean hasLogout = isElementVisible(logoutButton);
            return hasUserName && hasLogout;
        } catch (Exception e) {
            return false;
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

    public By getLogoutButton() {
        return logoutButton;
    }

    // Thêm hàm public để click logout
    public void clickLogout() {
        click(logoutButton);
    }

    public By getEmailField() { return emailField; }
    public By getPasswordField() { return passwordField; }

    public String getInputValidationMessage(By inputLocator) {
        WebElement input = waitForVisibility(inputLocator);
        return (String) ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;", input);
    }
} 