package com.hatester.keywords.element;

import com.hatester.drivers.DriverManager;
import com.hatester.keywords.wait.WaitActions;
import com.hatester.reports.AllureManager;
import com.hatester.utils.LogUtils;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static com.hatester.config.FrameworkConfig.*;
import static com.hatester.config.FrameworkConfig.getPoolTime;

public class ElementActions {
    public static WebElement getWebElement(By by) {
        // Tận dụng WaitActions để tránh NoSuchElementException
        return WaitActions.waitForElementPresent(by);
    }

    public static List<WebElement> getWebElements(By by) {
        return DriverManager.getDriver().findElements(by);
    }

    public static void highlightElement(By by) {
        highlightElement(by, "red");
    }

    public static void highlightElement(By by, String color) {
        String script = "arguments[0].style.border='3px solid " + color + "';";
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript(script, DriverManager.getDriver().findElement(by));
    }

    @Step("Get text of element: {0}")
    public static String getElementText(By by) {
        WaitActions.sleep(getSleepTime());
        LogUtils.info("Get text of element: " + by);
        String text = WaitActions.waitForElementVisible(by).getText();
        LogUtils.info("==> TEXT: " + text);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
        AllureManager.saveTextLog("==> TEXT: " + text);
        return text;
    }

    @Step("Get attribute {1} of element {0}")
    public static String getElementAttribute(By by, String attributeName) {
        WaitActions.sleep(getSleepTime());
        LogUtils.info("Get attribute of element: " + by);
        String value = WaitActions.waitForElementVisible(by).getAttribute(attributeName);
        LogUtils.info("==> Attribute value: " + value);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
        AllureManager.saveTextLog("==> Attribute value: " + value);
        return value;
    }

    @Step("Get CSS value {1} of element {0}")
    public static String getElementCssValue(By by, String cssPropertyName) {
        WaitActions.sleep(getSleepTime());
        LogUtils.info("Get CSS value " + cssPropertyName + " of element " + by);
        String value = WaitActions.waitForElementVisible(by).getCssValue(cssPropertyName);
        LogUtils.info("==> CSS value: " + value);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
        AllureManager.saveTextLog("==> CSS value: " + value);
        return value;
    }

    @Step("Set slider value to: {2}%")
    public static void setSliderValue(By hiddenInputBy, By sliderHandleBy, int percent) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        WebElement hiddenInput = getWebElement(hiddenInputBy);
        WebElement sliderHandle = getWebElement(sliderHandleBy);

        // 1. Clamp theo min/max nếu có
        String minAttr = hiddenInput.getAttribute("min");
        String maxAttr = hiddenInput.getAttribute("max");

        int min = StringUtils.isNotBlank(minAttr) ? Integer.parseInt(minAttr) : 0;
        int max = StringUtils.isNotBlank(maxAttr) ? Integer.parseInt(maxAttr) : 100;

        percent = Math.max(min, Math.min(percent, max));  //ép giá trị vào khoảng hợp lệ [min,max]

        // 2. Check current value (QUAN TRỌNG khi EDIT)
        String currentValue = hiddenInput.getAttribute("value");
        if (String.valueOf(percent).equals(currentValue)) {
            LogUtils.info("Slider already at value: " + percent + "%. Skip update.");
            return;
        }

        // 3. Set value + trigger đầy đủ event
        js.executeScript(
                "arguments[0].value = arguments[2];" +
                        "arguments[1].style.left = arguments[2] + '%';" +
                        "arguments[0].dispatchEvent(new Event('input'));" +
                        "arguments[0].dispatchEvent(new Event('change'));",
                hiddenInput,
                sliderHandle,
                percent
        );

        LogUtils.info("Set slider value to: " + percent + "%");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    public static Boolean checkElementExist(By by) {
        List<WebElement> listElement = getWebElements(by);

        if (!listElement.isEmpty()) {
            LogUtils.info("checkElementExist: " + true + " --- " + by);
            return true;
        } else {
            LogUtils.info("checkElementExist: " + false + " --- " + by);
            return false;
        }
    }

    public static boolean checkElementExist(By by, int maxRetries, int waitTimeMillis) {
        int retryCount = 0;

        while (retryCount < maxRetries) {
            try {
                WebElement element = DriverManager.getDriver().findElement(by);
                if (element != null) {
                    LogUtils.info("Element was found on attempt " + (retryCount + 1));
                    return true;
                }
            } catch (NoSuchElementException e) {
                LogUtils.warn("Element not found. Retry attempt " + (retryCount + 1));
                retryCount++;
                try {
                    Thread.sleep(waitTimeMillis);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
        }
        LogUtils.info("Failed to find element after " + maxRetries + " attempts.");
        return false;
    }

    public static boolean isCheckboxSelected(By by) {
        try {
            WebElement element = ElementActions.getWebElement(by);
            boolean isSelected = element.isSelected();
            LogUtils.info("Checkbox " + by + " selected status: " + isSelected);
            return isSelected;
        } catch (Exception e) {
            return false;
        }
    }
}
