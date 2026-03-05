package com.hatester.drivers;

import com.hatester.config.FrameworkConfig;
import com.hatester.drivers.strategy.BrowserStrategy;
import com.hatester.drivers.strategy.ChromeStrategy;
import com.hatester.drivers.strategy.EdgeStrategy;
import com.hatester.drivers.strategy.FirefoxStrategy;
import com.hatester.enums.BrowserType;
import com.hatester.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    public static WebDriver createDriver(BrowserType browserType) {
        BrowserStrategy strategy;

        switch (browserType) {
            case CHROME:
                LogUtils.info("Launching Chrome browser...");
                strategy = new ChromeStrategy();
                break;
            case FIREFOX:
                LogUtils.info("Launching Firefox browser...");
                strategy = new FirefoxStrategy();
                break;
            case EDGE:
                LogUtils.info("Launching Edge browser...");
                strategy = new EdgeStrategy();
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + browserType);
        }
        return strategy.createDriver();
    }
}
