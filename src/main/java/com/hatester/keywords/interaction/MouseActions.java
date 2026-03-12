package com.hatester.keywords.interaction;

import com.hatester.drivers.DriverManager;
import com.hatester.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import java.time.Duration;

import static com.hatester.keywords.element.ElementActions.getWebElement;

public class MouseActions {
    private static Actions getActions() {
        return new Actions(DriverManager.getDriver());
    }

    public static void hoverElement(By by) {
        try {
            Actions action = getActions();
            action.moveToElement(getWebElement(by)).perform();
            LogUtils.info("Hover on element: " + by);

        } catch (Exception e) {
            LogUtils.error("Failed to hover on element: " + by + ". Error: " + e.getMessage());
            Assert.fail("Failed to hover on element: " + by);
        }
    }

    public static void moveToOffset(int X, int Y) {
        try {
            Actions action = getActions();
            action.moveByOffset(X, Y).build().perform();
            LogUtils.info("Move mouse by offset: " + X + "," + Y);
        } catch (Exception e) {
            LogUtils.error("Failed to move mouse by offset. Error: " + e.getMessage());
            Assert.fail("Failed to move mouse by offset.");
        }
    }

    public static void dragAndDrop(By fromElement, By toElement) {
        try {
            Actions action = getActions();
            action.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
            LogUtils.info("Drag " + fromElement + " and drop to " + toElement);
        } catch (Exception e) {
            LogUtils.error("Failed to drag and drop: " + e.getMessage());
            Assert.fail("Failed to drag and drop.");
        }
    }

    public static void dragAndDropElement(By fromElement, By toElement) {
        try {
            Actions action = getActions();
            action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            LogUtils.info("Drag " + fromElement + " and drop to " + toElement);
        } catch (Exception e) {
            LogUtils.error("Failed to drag and drop: " + e.getMessage());
            Assert.fail("Failed to drag and drop.");
        }
    }

    public static void dragAndDropOffset(By fromElement, int X, int Y) {
        try {
            Actions action = getActions();
            action.clickAndHold(getWebElement(fromElement)).pause(Duration.ofSeconds(1)).moveByOffset(X, Y).release().build().perform();
            LogUtils.info("Drag " + fromElement + " to offset: " + X + "," + Y);
        } catch (Exception e) {
            LogUtils.error("Failed to drag and drop offset: " + e.getMessage());
            Assert.fail("Failed to drag and drop offset.");
        }
    }

    public static void clickByActions(By by) {
        try {
            Actions action = getActions();
            action.moveToElement(getWebElement(by)).click().build().perform();
            LogUtils.info("Click to element by Actions: " + by);
        } catch (Exception e) {
            LogUtils.error("Failed to click by Actions: " + e.getMessage());
            Assert.fail("Failed to click by Actions.");
        }
    }

    public static void sendTextByAction(By by, String text) {
        try {
            Actions action = getActions();
            // Click -> End (focus chuột vào cuối input) -> Nhập text
            action.click(getWebElement(by)).sendKeys(Keys.END).sendKeys(text).build().perform();
            LogUtils.info("Set text '" + text + "' to element: " + by);
        } catch (Exception e) {
            LogUtils.error("Failed to send text by Action: " + e.getMessage());
            Assert.fail("Failed to send text by Action.");
        }
    }
}
