package com.hatester.commons;

import com.hatester.drivers.DriverManager;
import com.hatester.helpers.PropertiesHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import static com.hatester.constants.FrameworkConstant.*;

public class BaseTest {
    public SoftAssert softAssert;

    @BeforeSuite
    public void setupLoadProperties() {
        PropertiesHelper.loadAllFiles();
    }

    @BeforeMethod
    @Parameters("brower")
    public void setupDriver(@Optional String browserName) {
        WebDriver driver;
        if (BROWSER != null && BROWSER.isBlank()) {
            browserName = BROWSER;
        }

        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                System.out.println("Launching Chrome browser...");

                ChromeOptions options = new ChromeOptions();
                // Tắt popup lưu mật khẩu
                options.addArguments("--disable-save-password-bubble");
                // Tắt cảnh báo password bị leak
                options.addArguments("--disable-features=PasswordLeakDetection");
                // Tắt các notification khác của Chrome
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-infobars");

                if (HEADLESS.equalsIgnoreCase("true")) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=" + WINDOW_SIZE);
                }

                driver = new ChromeDriver(options);
                break;
            case "firefox":
                System.out.println("Launching Firefox browser...");
                driver = new FirefoxDriver();
                break;
            case "edge":
                System.out.println("Launching Edge browser...");
                driver = new EdgeDriver();
                break;
            default:
                System.out.println("Browser: " + browserName + " is invalid, Launching Chrome as browser of choice...");
                driver = new ChromeDriver();
        }

        DriverManager.setDriver(driver);

        if (HEADLESS.equals("true")) {
            driver.manage().window().maximize();
        }

        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void tearDown() {
        if (DriverManager.getDriver() != null) {
            DriverManager.quitDriver();
        }
    }
}
