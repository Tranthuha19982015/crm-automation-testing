package com.hatester.keywords.verify;

import com.hatester.reports.AllureManager;
import com.hatester.utils.LogUtils;
import org.testng.Assert;

import java.util.Objects;

import static com.hatester.keywords.wait.WaitActions.waitForPageLoaded;

public class VerifyActions {
    public static boolean verifyEquals(Object actual, Object expected) {
        LogUtils.info("Verify equals: " + actual + " and " + expected);
        AllureManager.saveTextLog("Verify equals: " + actual + " and " + expected);
        return Objects.equals(actual, expected);
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        try {
            LogUtils.info("Assert Equals | Actual: " + actual + " - Expected: " + expected);
            AllureManager.saveTextLog("Assert Equals | Actual: " + actual + " - Expected: " + expected);
            Assert.assertEquals(actual, expected, message);
        } catch (AssertionError e) {
            // Chụp ảnh ngay khi Assert fail để biết giao diện lúc đó thế nào
            AllureManager.saveScreenshotPNG();
            LogUtils.error("Assert Equals Failed: " + message);
            throw e; // Quăng lại lỗi để TestNG đánh dấu fail test case
        }
    }

    public static boolean verifyContains(String actual, String expected) {
        if (actual == null || expected == null) return false;
        LogUtils.info("Verify contains: " + actual + " and " + expected);
        AllureManager.saveTextLog("Verify contains: " + actual + " and " + expected);
        return actual.contains(expected);
    }

    public static void assertContains(String actual, String expected, String message) {
        try {
            LogUtils.info("Assert Contains | Actual: " + actual + " - Expected: " + expected);
            AllureManager.saveTextLog("Assert Contains | Actual: " + actual + " - Expected: " + expected);
            if (actual == null || !actual.contains(expected)) {
                Assert.fail(message + " (Actual: " + actual + " does not contain Expected: " + expected + ")");
            }
        }catch (AssertionError e) {
            AllureManager.saveScreenshotPNG();
            LogUtils.error("Assert Contains Failed: " + message);
            throw e;
        }
    }

    public static void assertTrue(boolean condition, String message) {
        try {
            LogUtils.info("Assert True | Condition: " + condition);
            AllureManager.saveTextLog("Assert True | Condition: " + condition);
            Assert.assertTrue(condition, message);
        } catch (AssertionError e) {
            AllureManager.saveScreenshotPNG();
            throw e;
        }
    }
}
