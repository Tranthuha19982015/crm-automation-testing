package com.hatester.drivers;

import com.hatester.config.FrameworkConfig;
import com.hatester.drivers.strategy.BrowserStrategy;
import com.hatester.drivers.strategy.ChromeStrategy;
import com.hatester.drivers.strategy.EdgeStrategy;
import com.hatester.drivers.strategy.FirefoxStrategy;
import com.hatester.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    public static WebDriver createDriver() {
        String browser = FrameworkConfig.getBrowser().trim().toLowerCase();

        BrowserStrategy strategy;
        switch (browser) {
            case "chrome":
                LogUtils.info("Launching Chrome browser...");
                strategy = new ChromeStrategy();
                break;
            case "firefox":
                LogUtils.info("Launching Firefox browser...");
                strategy = new FirefoxStrategy();
                break;
            case "edge":
                LogUtils.info("Launching Edge browser...");
                strategy = new EdgeStrategy();
                break;
            default:
                LogUtils.info("Browser: " + browser + " is invalid, Launching Chrome as browser of choice...");
                strategy = new ChromeStrategy();
        }
        return strategy.createDriver();
    }
}
