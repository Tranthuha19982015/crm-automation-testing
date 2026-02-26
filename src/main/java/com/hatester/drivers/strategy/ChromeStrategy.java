package com.hatester.drivers.strategy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.hatester.config.FrameworkConfig.*;

public class ChromeStrategy implements BrowserStrategy {
    @Override
    public WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();
        // Tắt popup lưu mật khẩu
        options.addArguments("--disable-save-password-bubble");
        // Tắt cảnh báo password bị leak
        options.addArguments("--disable-features=PasswordLeakDetection");
        // Tắt các notification khác của Chrome
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");

        if (isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=" + getWindowSize());
        }

        return new ChromeDriver(options);
    }
}
