package com.hatester.keywords.scroll;

import com.hatester.drivers.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static com.hatester.keywords.wait.WaitActions.waitForElementVisible;

public class ScrollActions {
    private static JavascriptExecutor getJS() {
        return (JavascriptExecutor) DriverManager.getDriver();
    }

    public static void scrollToElement(By by) {
        getJS().executeScript("arguments[0].scrollIntoView(false);", waitForElementVisible(by));
    }

    public static void scrollToElementAtTop(By by) {
        getJS().executeScript("arguments[0].scrollIntoView(true);", waitForElementVisible(by));
    }

    public static void scrollToElementAtBottom(By by) {
        getJS().executeScript("arguments[0].scrollIntoView(false);", waitForElementVisible(by));
    }

    public static void scrollToElementAtTop(WebElement element) {
        getJS().executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToElementAtBottom(WebElement element) {
        getJS().executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public static void scrollToPosition(int X, int Y) {
        getJS().executeScript("window.scrollTo(" + X + "," + Y + ");");
    }
}
