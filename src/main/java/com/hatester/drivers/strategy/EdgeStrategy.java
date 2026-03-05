package com.hatester.drivers.strategy;

import com.hatester.config.FrameworkConfig;
import com.hatester.drivers.DriverManager;
import com.hatester.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import static com.hatester.config.FrameworkConfig.isHeadless;

public class EdgeStrategy extends AbstractBrowserStrategy {
    @Override
    public WebDriver createDriver() {
        EdgeOptions options = new EdgeOptions();

        if (isHeadless()) {
            options.addArguments("--headless=new");
        }

        WebDriver driver = new EdgeDriver(options);
        configureWindow(driver);
        return driver;
    }
}
