package com.hatester.drivers.strategy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxStrategy implements BrowserStrategy {
    @Override
    public WebDriver createDriver() {
        return new FirefoxDriver();
    }
}
