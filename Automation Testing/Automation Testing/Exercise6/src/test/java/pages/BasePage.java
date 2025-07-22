package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Chờ element hiển thị
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Click an toàn
    protected void click(By locator) {
        waitForVisibility(locator).click();
    }

    // Nhập text an toàn
    protected void type(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    // Lấy text an toàn
    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    // Điều hướng đến URL
    public void navigateTo(String url) {
        driver.get(url);
    }

    // Kiểm tra element có hiển thị không
    protected boolean isElementVisible(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    // Upload file
    protected void uploadFile(By locator, String filePath) {
        driver.findElement(locator).sendKeys(filePath);
    }
} 