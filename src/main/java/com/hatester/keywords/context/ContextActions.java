package com.hatester.keywords.context;

import com.hatester.drivers.DriverManager;
import com.hatester.keywords.wait.WaitActions;
import com.hatester.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static com.hatester.config.FrameworkConfig.getPoolTime;
import static com.hatester.config.FrameworkConfig.getWaitTimeout;

public class ContextActions {
    // --- FRAME ACTIONS ---
    @Step("Switched to iframe {0} with seconds {1}")
    public static void switchToFrame(By by, int seconds) {
        try {
            WebDriverWait wait = WaitActions.getWait(seconds);
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
            LogUtils.info("Switched to iframe: " + by + " with seconds " + seconds);
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for switch to Frame with " + seconds + "s : " + by.toString());
            Assert.fail("Timeout waiting for switch to Frame with " + seconds + "s : " + by.toString());
        }
    }

    @Step("Switched to iframe: {0}")
    public static void switchToFrame(By by) {
        switchToFrame(by, getWaitTimeout());
    }

    @Step("Switched back to default content")
    public static void switchToDefaultContent() {
        try {
            DriverManager.getDriver().switchTo().defaultContent();
            LogUtils.info("Switched back to default content");
        } catch (Exception e) {
            LogUtils.error("FAILED. Cannot switch to default content: " + e.getMessage());
            Assert.fail("FAILED. Cannot switch to default content.");
        }
    }

    @Step("Switched back to parent frame")
    public static void switchToParentFrame() {
        try {
            DriverManager.getDriver().switchTo().parentFrame();
            LogUtils.info("Switched back to parent frame");
        } catch (Exception e) {
            LogUtils.error("FAILED. Cannot switch to parent frame: " + e.getMessage());
            Assert.fail("FAILED. Cannot switch to parent frame.");
        }
    }

    // --- ALERT ACTIONS ---
    @Step("Accepted alert")
    public static void acceptAlert() {
        try {
            WebDriverWait wait = WaitActions.getWait(getWaitTimeout());
            wait.until(ExpectedConditions.alertIsPresent()).accept();
            LogUtils.info("Accepted alert");
        } catch (Exception e) {
            LogUtils.error("Alert not present to accept");
            Assert.fail("Alert not present to accept");
        }
    }

    @Step("Dismissed alert")
    public static void dismissAlert() {
        try {
            WebDriverWait wait = WaitActions.getWait(getWaitTimeout());
            wait.until(ExpectedConditions.alertIsPresent()).dismiss();
            LogUtils.info("Dismissed alert");
        } catch (Exception e) {
            LogUtils.error("Alert not present to dismissed");
            Assert.fail("Alert not present to dismissed");
        }
    }
}
