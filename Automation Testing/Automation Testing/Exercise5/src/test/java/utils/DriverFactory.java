package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    public static WebDriver createDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // Prefs to block ads and popups
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("profile.managed_default_content_settings.images", 2); // Block images to reduce ad loading

        options.setExperimentalOption("prefs", prefs);

        // Arguments to improve stability and block ads
        options.addArguments(
                "--incognito",                    // Private browsing
                "--disable-notifications",       // Disable notifications
                "--disable-popup-blocking",      // Disable popup blocking (ironically helps with some elements)
                "--disable-infobars",           // Disable info bars
                "--disable-extensions",         // Disable extensions
                "--disable-dev-shm-usage",      // Overcome limited resource problems
                "--no-sandbox",                 // Bypass OS security model
                "--disable-gpu",                // Disable GPU acceleration
                "--remote-allow-origins=*",     // Allow remote origins
                "--disable-blink-features=AutomationControlled", // Hide automation flags
                "--disable-web-security",       // Disable web security for testing
                "--disable-features=VizDisplayCompositor" // Disable compositor
        );

        // Add ad blocker extension (optional - if you have uBlock Origin extension file)
        // options.addArguments("--load-extension=/path/to/ublock-origin");

        // Set user agent to avoid detection
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36");

        WebDriver driver = new ChromeDriver(options);

        // Maximize window to ensure elements are visible
        driver.manage().window().maximize();

        return driver;
    }

    public static WebDriver createHeadlessDriver() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // All the same options as above
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("profile.managed_default_content_settings.images", 2);

        options.setExperimentalOption("prefs", prefs);

        options.addArguments(
                "--headless",                   // Run in headless mode
                "--disable-notifications",
                "--disable-popup-blocking",
                "--disable-infobars",
                "--disable-extensions",
                "--disable-dev-shm-usage",
                "--no-sandbox",
                "--disable-gpu",
                "--remote-allow-origins=*",
                "--disable-blink-features=AutomationControlled",
                "--disable-web-security",
                "--disable-features=VizDisplayCompositor",
                "--window-size=1920,1080"      // Set window size for headless
        );

        return new ChromeDriver(options);
    }
}