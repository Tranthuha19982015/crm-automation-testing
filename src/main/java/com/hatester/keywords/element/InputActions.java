package com.hatester.keywords.element;

import com.hatester.keywords.wait.WaitActions;
import com.hatester.reports.AllureManager;
import com.hatester.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.hatester.config.FrameworkConfig.getSleepTime;
import static com.hatester.config.FrameworkConfig.isScreenshotAllSteps;

public class InputActions {
    @Step("Clear text of element: {0}")
    public static void clearElementText(By by) {
        WaitActions.sleep(getSleepTime());
        WaitActions.waitForElementVisible(by).clear();
        LogUtils.info("Clear text of element: " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Clear text of element {0} with timeout {1} seconds")
    public static void clearElementText(By by, int seconds) {
        WaitActions.sleep(getSleepTime());
        WaitActions.waitForElementVisible(by, seconds).clear();
        LogUtils.info("Clear text of element " + by + " with " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Clear text of element: {0}")
    public static void clearTextByKeyboard(By by) {
        WaitActions.sleep(getSleepTime());
        // Nhấn Ctrl + A rồi nhấn Backspace để xóa sạch
        WaitActions.waitForElementVisible(by).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        LogUtils.info("Clear text of element: " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set text {1} on element {0}")
    public static void setText(By by, String text) {
        WaitActions.sleep(getSleepTime());
        WaitActions.waitForElementVisible(by).sendKeys(text);
        LogUtils.info("Set text " + text + " on element " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set text {1} on element {0} with timeout {2} seconds")
    public static void setText(By by, String text, int seconds) {
        WaitActions.sleep(getSleepTime());
        WaitActions.waitForElementVisible(by, seconds).sendKeys(text);
        LogUtils.info("Set text " + text + " on element " + by + " with " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set key on element: {0}")
    public static void setKey(By by, Keys key) {
        WaitActions.sleep(getSleepTime());
        WaitActions.waitForElementVisible(by).sendKeys(key);
        LogUtils.info("Set key on element: " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set key on element: {0} with timeout {2} seconds")
    public static void setKey(By by, Keys key, int seconds) {
        WaitActions.sleep(getSleepTime());
        WaitActions.waitForElementVisible(by, seconds).sendKeys(key);
        LogUtils.info("Set key on element: " + by + "with: " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set text and key {1} on element {0} ")
    public static void setTextAndKey(By by, String text, Keys key) {
        WaitActions.sleep(getSleepTime());
        WaitActions.waitForElementVisible(by).sendKeys(text, key);
        LogUtils.info("Set text and key " + text + " then " + key.name() + " on element " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }
}
