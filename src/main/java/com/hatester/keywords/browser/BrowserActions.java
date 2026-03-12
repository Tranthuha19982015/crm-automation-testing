package com.hatester.keywords.browser;

import com.hatester.drivers.DriverManager;
import com.hatester.keywords.element.ElementActions;
import com.hatester.keywords.wait.WaitActions;
import com.hatester.reports.AllureManager;
import com.hatester.utils.LogUtils;
import io.qameta.allure.Step;
import org.testng.Assert;

import static com.hatester.config.FrameworkConfig.getSleepTime;
import static com.hatester.config.FrameworkConfig.isScreenshotAllSteps;

public class BrowserActions {
    @Step("Navigate to URL: {0}")
    public static void openURL(String url) {
        try {
            DriverManager.getDriver().get(url);
            WaitActions.waitForPageLoaded();
            WaitActions.sleep(getSleepTime());
            LogUtils.info("Navigate to URL: " + url);
        } catch (Exception e) {
            LogUtils.error("Failed to navigate to URL: " + url + ". Error: " + e.getMessage());
            Assert.fail("Failed to navigate to URL: " + url);
        }
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Get current URL")
    public static String getCurrentURL() {
        String currentUrl = "";
        try {
            WaitActions.sleep(getSleepTime());
            currentUrl = DriverManager.getDriver().getCurrentUrl();
            LogUtils.info("Current URL: " + currentUrl);
        } catch (Exception e) {
            LogUtils.error("FAILED. Cannot get current URL: " + e.getMessage());
            Assert.fail("FAILED. Cannot get current URL.");
        }

        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
        return currentUrl;
    }

    @Step("Refresh page")
    public static void refreshPage() {
        try{
        DriverManager.getDriver().navigate().refresh();
        WaitActions.waitForPageLoaded();
        LogUtils.info("Refreshed the page");
        } catch (Exception e) {
            LogUtils.error("Failed to refresh the page: " + e.getMessage());
        }
    }
}
