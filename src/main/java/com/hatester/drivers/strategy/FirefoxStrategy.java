package com.hatester.drivers.strategy;

import com.hatester.config.FrameworkConfig;
import com.hatester.drivers.DriverManager;
import com.hatester.utils.LogUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class FirefoxStrategy extends AbstractBrowserStrategy {
    @Override
    public WebDriver createDriver() {
        FirefoxOptions options = new FirefoxOptions();

        if (isHeadless()) {
            options.addArguments("-headless");
        }

        // Disable Firefox popup
        options.addPreference("signon.rememberSignons", false);
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("browser.formfill.enable", false);

        WebDriver driver = new FirefoxDriver(options);
        configureWindow(driver);
        return driver;
    }
}
