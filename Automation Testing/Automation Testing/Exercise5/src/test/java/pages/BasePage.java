package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Wait for visibility
    protected WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait for element to be clickable
    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Click an element safely with retry mechanism
    protected void click(By locator) {
        WebElement element = waitForClickable(locator);

        // Try normal click first
        try {
            element.click();
            return;
        } catch (ElementClickInterceptedException e) {
            // If intercepted, try JavaScript click
            System.out.println("Normal click intercepted, trying JavaScript click for: " + locator);
            jsClick(locator);
        }
    }

    // JavaScript click as fallback
    protected void jsClick(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // Force click with scroll and JS
    protected void forceClick(By locator) {
        scrollToElement(locator);
        try {
            Thread.sleep(500); // Small delay after scroll
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            waitForClickable(locator).click();
        } catch (ElementClickInterceptedException e) {
            jsClick(locator);
        }
    }

    // Send keys safely
    protected void type(By locator, String text) {
        WebElement element = waitForVisibility(locator);
        element.clear();
        element.sendKeys(text);
    }

    // Get text safely
    protected String getText(By locator) {
        return waitForVisibility(locator).getText();
    }

    // Navigate to a URL
    public void navigateTo(String url) {
        driver.get(url);
    }

    // Check if element is present
    protected boolean isElementVisible(By locator) {
        try {
            return waitForVisibility(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Scroll to element
    protected void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
    }

    // Select dropdown by visible text
    protected void selectByText(By locator, String text) {
        WebElement dropdown = waitForVisibility(locator);
        Select select = new Select(dropdown);
        select.selectByVisibleText(text);
    }

    // Remove ads that might intercept clicks
    protected void removeAds() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "var ads = document.querySelectorAll('iframe[id*=\"google_ads\"], .adsbygoogle, [id*=\"ad\"]');" +
                            "for(var i = 0; i < ads.length; i++) { ads[i].style.display = 'none'; }"
            );
        } catch (Exception e) {
            // Ignore if can't remove ads
        }
    }

    // Wait for page to be ready
    protected void waitForPageReady() {
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete")
        );
    }
}