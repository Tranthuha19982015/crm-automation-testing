package com.hatester.commons;

import com.hatester.drivers.DriverFactory;
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

    @BeforeMethod
    public void setupDriver() {
        DriverManager.initDriver();

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
