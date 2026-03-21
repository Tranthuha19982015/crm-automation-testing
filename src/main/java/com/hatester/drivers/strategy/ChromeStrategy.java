package com.hatester.drivers.strategy;

import com.hatester.config.FrameworkConfig;
import com.hatester.drivers.DriverManager;
import com.hatester.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class ChromeStrategy extends AbstractBrowserStrategy {
    @Override
    public WebDriver createDriver() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        if (isHeadless()) {
            options.addArguments("--headless=new");
        }
        WebDriver driver = new ChromeDriver(options);
        configureWindow(driver);
        return driver;
    }
}
