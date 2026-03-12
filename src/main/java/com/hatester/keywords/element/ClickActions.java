package com.hatester.keywords.element;

import com.hatester.drivers.DriverManager;
import com.hatester.keywords.wait.WaitActions;
import com.hatester.reports.AllureManager;
import com.hatester.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import static com.hatester.config.FrameworkConfig.getSleepTime;
import static com.hatester.config.FrameworkConfig.isScreenshotAllSteps;

public class ClickActions {
    @Step("Click on element: {0}")
    public static void clickElement(By by) {
        WaitActions.sleep(getSleepTime());
        WaitActions.waitForElementToBeClickable(by).click();
        LogUtils.info("Click on element " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Click on element {0} with timeout {1} seconds")
    public static void clickElement(By by, int seconds) {
        WaitActions.sleep(getSleepTime());
        WaitActions.waitForElementToBeClickable(by, seconds).click();
        LogUtils.info("Click on element " + by + "with " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Click on element: {0}")
    public static void clickJS(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].click();", WaitActions.waitForElementPresent(by));
        LogUtils.info("Click on element " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }
}
