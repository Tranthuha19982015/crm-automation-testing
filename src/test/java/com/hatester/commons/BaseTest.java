package com.hatester.commons;

import com.hatester.drivers.DriverManager;
import com.hatester.helpers.PropertiesHelper;
import com.hatester.listeners.TestListener;
import com.hatester.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import static com.hatester.config.FrameworkConfig.*;
import static com.hatester.config.FrameworkConfig.isHeadless;

@Listeners(TestListener.class)
public class BaseTest {
    public SoftAssert softAssert;

    @BeforeSuite
    public void setupLoadProperties() {
        PropertiesHelper.loadAllFiles();
    }

    @Parameters("browser")
    @BeforeMethod
    public void setupDriver(@Optional("browser") String browserName) {
        WebDriver driver;

        if (getBrowser() != null && !getBrowser().isBlank()) {
            browserName = getBrowser();
        }

        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                LogUtils.info("Launching Chrome browser...");

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

                driver = new ChromeDriver(options);
                break;
            case "firefox":
                LogUtils.info("Launching Firefox browser...");
                driver = new FirefoxDriver();
                break;
            case "edge":
                LogUtils.info("Launching Edge browser...");
                driver = new EdgeDriver();
                break;
            default:
                LogUtils.info("Browser: " + browserName + " is invalid, Launching Chrome as browser of choice...");
                driver = new ChromeDriver();
        }

        DriverManager.setDriver(driver);

        if (!isHeadless()) {
            LogUtils.info("Headless: " + isHeadless());
            DriverManager.getDriver().manage().window().maximize();
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
