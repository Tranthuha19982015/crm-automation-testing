package com.hatester.commons;

import com.hatester.drivers.DriverManager;
import com.hatester.enums.BrowserType;
import com.hatester.helpers.PropertiesHelper;
import com.hatester.listeners.TestListener;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import static com.hatester.config.FrameworkConfig.*;

@Listeners(TestListener.class)
public class BaseTest {
    public SoftAssert softAssert;

    @BeforeSuite
    public void setupLoadProperties() {
        PropertiesHelper.loadAllFiles();
    }

    @Parameters("browser")
    @BeforeMethod
    public void setupDriver(@Optional("chrome") String browserParam) {
        BrowserType browserType;
        if (getBrowser() != null) {
            browserType = getBrowser();
        } else {
            try {
                browserType = BrowserType.valueOf(browserParam.toUpperCase().trim());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid browser parameter: " + browserParam);
            }
        }

        DriverManager.setDriver(browserType);
        softAssert = new SoftAssert();
    }

    @AfterMethod
    public void tearDown() {
        if (DriverManager.getDriver() != null) {
            DriverManager.quitDriver();
        }
    }
}
