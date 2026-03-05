package com.hatester.drivers.strategy;

import com.hatester.config.FrameworkConfig;
import com.hatester.drivers.DriverManager;
import com.hatester.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeStrategy extends AbstractBrowserStrategy {
    @Override
    public WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();

        // Disable Chrome popup
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");

        if (isHeadless()) {
            options.addArguments("--headless=new");
        }
        WebDriver driver = new ChromeDriver(options);
        configureWindow(driver);
        return driver;
    }
}
