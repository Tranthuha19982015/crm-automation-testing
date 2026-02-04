package com.hatester.keywords;

import com.hatester.drivers.DriverManager;
import com.hatester.reports.AllureManager;
import com.hatester.utils.DataUtil;
import com.hatester.utils.LogUtils;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hatester.config.FrameworkConfig.*;

public class WebUI {
    public static void logConsole(Object message) {
        LogUtils.info(message);
    }

    public static void highlightElement(By by) {
        String script = "arguments[0].style.border='3px solid red';";
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript(script, getWebElement(by));
    }

    public static void highlightElement(By by, String color) {
        String script = "arguments[0].style.border='3px solid " + color + "';";
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript(script, getWebElement(by));
    }

    public static void sleep(double second) {
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static WebElement waitForElementVisible(By by) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getWaitTimeout()), Duration.ofMillis(getPoolTime()));
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
        return element;
    }

    public static WebElement waitForElementVisible(By by, int seconds) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds), Duration.ofMillis(getPoolTime()));
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element Visible with " + seconds + "s : " + by);
            Assert.fail("Timeout waiting for the element Visible with " + seconds + "s : " + by);
        }
        return element;
    }

    public static WebElement waitForElementToBeClickable(By by) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getWaitTimeout()), Duration.ofMillis(getPoolTime()));
            element = wait.until(ExpectedConditions.elementToBeClickable(by));
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element to be clickable. " + by.toString());
            Assert.fail("Timeout waiting for the element to be clickable. " + by.toString());
        }
        return element;
    }

    public static WebElement waitForElementToBeClickable(By by, int seconds) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds), Duration.ofMillis(getPoolTime()));
            element = wait.until(ExpectedConditions.elementToBeClickable(by));
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element to be clickable with " + seconds + "(s) : " + by);
            Assert.fail("Timeout waiting for the element to be clickable with " + seconds + "(s) : " + by);
        }
        return element;
    }

    public static WebElement waitForElementPresent(By by) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getWaitTimeout()), Duration.ofMillis(getPoolTime()));
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            LogUtils.error("Element not exist " + by.toString());
            Assert.fail("Element not exist " + by.toString());
        }
        return element;
    }

    public static WebElement waitForElementPresent(By by, int seconds) {
        WebElement element = null;
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds), Duration.ofMillis(getPoolTime()));
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            highlightElement(by);
            return element;
        } catch (Throwable error) {
            LogUtils.error("Element not exist with " + seconds + "(s) : " + by);
            Assert.fail("Element not exist with " + seconds + "(s) : " + by);
        }
        return element;
    }

    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getPageLoadTimeout()), Duration.ofMillis(getPoolTime()));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) DriverManager.getDriver())
                .executeScript("return document.readyState")
                .toString().equals("complete");

        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        if (!jsReady) {
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("FAILED. Timeout waiting for page load.");
            }
        }
    }

    public static void waitForElementNotVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getWaitTimeout()), Duration.ofMillis(getPoolTime()));
            wait.until(ExpectedConditions.invisibilityOf(getWebElement(by)));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for the element Not Visible " + by.toString());
            Assert.fail("Timeout waiting for the element Not Visible " + by.toString());
        }
    }

    public static void waitForSearchResult(By by) {
        try {
            int oldCount = getWebElements(by).size();
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getWaitTimeout()), Duration.ofMillis(getPoolTime()));
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(by, oldCount));
        } catch (Throwable error) {
            LogUtils.error("Timeout while waiting for search results. " + by.toString());
            Assert.fail("Timeout while waiting for search results. " + by.toString());
        }
    }

    public static void switchToFrame(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(getWaitTimeout()), Duration.ofMillis(getPoolTime()));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
            LogUtils.info("Switched to iframe: " + by);
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for Switch To Frame. " + by.toString());
            Assert.fail("Timeout waiting for Switch To Frame. " + by.toString());
        }
    }

    public static void switchToFrame(By by, int seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds), Duration.ofMillis(getPoolTime()));
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(by));
            LogUtils.info("Switched to iframe: " + by);
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for switch to Frame with " + seconds + "s : " + by.toString());
            Assert.fail("Timeout waiting for switch to Frame with " + seconds + "s : " + by.toString());
        }
    }

    public static void switchToDefaultContent() {
        DriverManager.getDriver().switchTo().defaultContent();
        LogUtils.info("Switched back to default content");
    }

    public static void switchToParentFrame() {
        DriverManager.getDriver().switchTo().parentFrame();
        LogUtils.info("Switched back to parent frame");
    }

    public static void acceptAlert() {
        DriverManager.getDriver().switchTo().alert().accept();
        LogUtils.info("Accepted alert");
    }

    public static void dismissAlert() {
        DriverManager.getDriver().switchTo().alert().dismiss();
        LogUtils.info("Dismissed alert");
    }

    public static Boolean checkElementExist(By by) {
        List<WebElement> listElement = getWebElements(by);

        if (listElement.size() > 0) {
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
                WebElement element = getWebElement(by);
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
        WebElement element = getWebElement(by);
        if (element.isSelected()) {
            LogUtils.info("Element is selected");
            return true;
        } else {
            LogUtils.info("Element is not selected");
            return false;
        }
    }

    public static WebElement getWebElement(By by) {
        return DriverManager.getDriver().findElement(by);
    }

    public static List<WebElement> getWebElements(By by) {
        return DriverManager.getDriver().findElements(by);
    }

    public static void refreshPage() {
        DriverManager.getDriver().navigate().refresh();
    }

    @Step("Navigate to URL: {0}")
    public static void openURL(String url) {
        DriverManager.getDriver().get(url);
        waitForPageLoaded();
        sleep(getSleepTime());
        LogUtils.info("Navigate to URL: " + url);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step
    public static String getCurrentURL() {
        sleep(getSleepTime());
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        LogUtils.info("Current URL: " + currentUrl);
        AllureManager.saveTextLog("Current URL: " + currentUrl);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
        return currentUrl;
    }

    @Step("Click on element: {0}")
    public static void clickElement(By by) {
        sleep(getSleepTime());
        waitForElementToBeClickable(by).click();
        LogUtils.info("Click on element " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Click on element {0} with timeout {1} seconds")
    public static void clickElement(By by, int seconds) {
        sleep(getSleepTime());
        waitForElementToBeClickable(by, seconds).click();
        LogUtils.info("Click on element " + by + "with " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Clear text of element: {0}")
    public static void clearElementText(By by) {
        sleep(getSleepTime());
        waitForElementVisible(by).clear();
        LogUtils.info("Clear text of element: " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Clear text of element {0} with timeout {1} seconds")
    public static void clearElementText(By by, int seconds) {
        sleep(getSleepTime());
        waitForElementVisible(by).clear();
        LogUtils.info("Clear text of element " + by + "with " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set text {1} on element {0}")
    public static void setText(By by, String text) {
        sleep(getSleepTime());
        waitForElementVisible(by).sendKeys(text);
        LogUtils.info("Set text " + text + " on element " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set text {1} on element {0} with timeout {2} seconds")
    public static void setText(By by, String text, int seconds) {
        sleep(getSleepTime());
        waitForElementVisible(by, seconds).sendKeys(text);
        LogUtils.info("Set text " + text + " on element " + by + "with " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set key on element: {0}")
    public static void setKey(By by, Keys key) {
        waitForElementVisible(by).sendKeys(key);
        LogUtils.info("Set key on element: " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set key on element: {0} with timeout {2} seconds")
    public static void setKey(By by, Keys key, int seconds) {
        waitForElementVisible(by).sendKeys(key);
        LogUtils.info("Set key on element: " + by + "with: " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set text and key {1} on element {0} ")
    public static void setTextAndKey(By by, String text, Keys key) {
        waitForPageLoaded();
        getWebElement(by).sendKeys(text, key);
        LogUtils.info("Set text and key " + text + " on element " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set text and key {1} on element {0} with timeout {3} seconds")
    public static void setTextAndKey(By by, String text, Keys key, int seconds) {
        waitForPageLoaded();
        getWebElement(by).sendKeys(text, key);
        LogUtils.info("Set text and key " + text + " on element " + by + "with " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Get text of element: {0}")
    public static String getElementText(By by) {
        waitForElementVisible(by);
        sleep(getSleepTime());
        LogUtils.info("Get text of element: " + by);
        String text = getWebElement(by).getText();
        LogUtils.info("==> TEXT: " + text);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
        AllureManager.saveTextLog("==> TEXT: " + text);
        return text;
    }

    @Step("Get attribute {1} of element {0}")
    public static String getElementAttribute(By by, String attributeName) {
        waitForElementVisible(by);
        LogUtils.info("Get attribute of element: " + by);
        String value = getWebElement(by).getAttribute(attributeName);
        LogUtils.info("==> Attribute value: " + value);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
        AllureManager.saveTextLog("==> Attribute value: " + value);
        return value;
    }

    @Step("Get CSS value {1} of element {0}")
    public static String getElementCssValue(By by, String cssPropertyName) {
        waitForElementVisible(by);
        LogUtils.info("Get CSS value " + cssPropertyName + " of element " + by);
        String value = getWebElement(by).getCssValue(cssPropertyName);
        LogUtils.info("==> CSS value: " + value);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
        AllureManager.saveTextLog("==> CSS value: " + value);
        return value;
    }

    public static void scrollToElement(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", waitForElementVisible(by));
    }

    public static void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public static void scrollToElementAtTop(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", waitForElementVisible(by));
    }

    public static void scrollToElementAtBottom(By by) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", waitForElementVisible(by));
    }

    public static void scrollToElementAtTop(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToElementAtBottom(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    public static void scrollToPosition(int X, int Y) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollTo(" + X + "," + Y + ");");
    }

    @Step("Set slider value to: {2)%")
    public static void setSliderValue(By hiddenInputBy, By sliderHandleBy, int percent) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        WebElement hiddenInput = getWebElement(hiddenInputBy);
        WebElement sliderHandle = getWebElement(sliderHandleBy);

        js.executeScript(
                "arguments[0].value = arguments[2];" +
                        "arguments[1].style.left = arguments[2] + '%';" +
                        "arguments[0].dispatchEvent(new Event('change'));",
                hiddenInput,
                sliderHandle,
                percent
        );

        LogUtils.info("Set slider value to: " + percent + "%");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
        AllureManager.saveTextLog("Set slider value to: " + percent + "%");
    }

    public static boolean moveToElement(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).release().build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean moveToOffset(int X, int Y) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveByOffset(X, Y).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean hoverElement(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean mouseHover(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).perform();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean dragAndDrop(By fromElement, By toElement) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean dragAndDropElement(By fromElement, By toElement) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.clickAndHold(getWebElement(fromElement)).moveToElement(getWebElement(toElement)).release(getWebElement(toElement)).build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean dragAndDropOffset(By fromElement, int X, int Y) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.clickAndHold(getWebElement(fromElement)).pause(1).moveByOffset(X, Y).release().build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static boolean clickToElementByActions(By by) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.moveToElement(getWebElement(by)).click().build().perform();
            return true;
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
            return false;
        }
    }

    public static void sendTextByAction(By by, String text) {
        try {
            Actions action = new Actions(DriverManager.getDriver());
            action.click(getWebElement(by)).sendKeys(Keys.END).sendKeys(text).build().perform();
        } catch (Exception e) {
            LogUtils.info(e.getMessage());
        }
    }

    public static boolean pressENTER() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean pressESC() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_ESCAPE);
            robot.keyRelease(KeyEvent.VK_ESCAPE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean pressF11() {
        try {
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_F11);
            robot.keyRelease(KeyEvent.VK_F11);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void copyFilePathToClipboard(String file) {
        StringSelection str = new StringSelection(file);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
    }

    public static void pasteFromClipboard(int seconds, int delayTime) {
        try {
            sleep(seconds);
            Robot robot = new Robot();
            robot.setAutoDelay(delayTime);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);

            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyEquals(Object actual, Object expected) {
        waitForPageLoaded();
        LogUtils.info("Verify equals: " + actual + " and " + expected);
        AllureManager.saveTextLog("Verify equals: " + actual + " and " + expected);
        boolean check = actual.equals(expected);
        return check;
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        waitForPageLoaded();
        LogUtils.info("Assert equals: " + actual + " and " + expected);
        AllureManager.saveTextLog("Assert equals: " + actual + " and " + expected);
        Assert.assertEquals(actual, expected, message);
    }

    public static boolean verifyContains(String actual, String expected) {
        waitForPageLoaded();
        LogUtils.info("Verify contains: " + actual + " and " + expected);
        AllureManager.saveTextLog("Verify contains: " + actual + " and " + expected);
        boolean check = actual.contains(expected);
        return check;
    }

    public static void assertContains(String actual, String expected, String message) {
        waitForPageLoaded();
        LogUtils.info("Assert contains: " + actual + " and " + expected);
        AllureManager.saveTextLog("Assert contains: " + actual + " and " + expected);
        boolean check = actual.contains(expected);
        Assert.assertTrue(check, message);
    }

    //handle element
    public static void setTextIfChanged(By by, String expected, String attribute) {
        // Excel không có cột thì bỏ qua trường này
        if (expected == null) {
            return;
        }

        String actual = getElementAttribute(by, attribute);

        // Excel có cột nhưng ô để trống → clear
        if (expected.isEmpty()) {
            if (!actual.isEmpty()) {
                clearElementText(by);
            }
            return;
        }

        if (expected.equals(actual)) {
            return;
        }

        clearElementText(by);
        setText(by, expected);
    }

    public static void selectDropdownIfChanged(By dropdown, String expected, String attribute, By optionDropdown) {
        if (StringUtils.isBlank(expected)) {
            return;
        }

        String actual = getElementAttribute(dropdown, attribute);
        if (expected.equals(actual)) {
            return;
        }

        clickElement(dropdown);
        clickElement(optionDropdown);
    }

    public static void selectSearchableDropdownIfChanged(By dropdown, String expected, String attribute, By inputSearchDropdown, By optionDropdown) {
        if (StringUtils.isBlank(expected)) {
            return;
        }

        String actual = getElementAttribute(dropdown, attribute);
        if (expected.equals(actual)) {
            return;
        }

        clickElement(dropdown);
        setText(inputSearchDropdown, expected);
        sleep(0.5);
        sendTextByAction(inputSearchDropdown, " ");
        clickElement(optionDropdown);
    }

    //Function<String, By>: là Java Function, Nhận vào 1 giá trị kiểu String → Trả ra 1 giá trị kiểu By
    public static void selectMultiDropdownToggleIfChanged(By dropdown, List<String> expected, String attribute, Function<String, By> optionByText) {
        //Không truyền dữ liệu → không làm gì
        if (expected.size() <= 0 || expected == null) {
            return;
        }

        //Lấy giá trị đang selected trên UI
        List<String> current;

        String raw = getElementAttribute(dropdown, attribute);

        if (raw == null || raw.isBlank() || raw.equalsIgnoreCase("Nothing selected")) {
            current = Collections.emptyList();
        } else {
            current = DataUtil.parseList(raw);
        }

        //chuyển list thành set để bỏ qua thứ tự khi duyệt for
        Set<String> expectedSet = expected.stream().map(String::trim).collect(Collectors.toSet());
        Set<String> currentSet = current.stream().map(String::trim).collect(Collectors.toSet());

        //Mở dropdown
        clickElement(dropdown);

        //Xóa giá trị trên UI nếu không có trong excel
        for (String value : currentSet) {
            if (!expectedSet.contains(value)) {
                clickElement(optionByText.apply(value));
            }
        }

        //Add giá trị thiếu (giữ thứ tự Excel)
        for (String value : expected) {
            if (!currentSet.contains(value)) {
                clickElement(optionByText.apply(value));
            }
        }

        //Đóng dropdown
        clickElement(dropdown);
    }

    //Multi-value input dạng chip / token / pill  (VD: tags, labels,...)
    public static void setMultiChipInput(By input, List<String> expected, By chipRemoveIcon, By blurTarget) {
        // 1. Excel không có cột cần thao tác → bỏ qua
        if (expected == null) {
            return;
        }

        // 2. Xoá toàn bộ thẻ hiện tại trên UI (nếu có)
        List<WebElement> listRemoveIcon = getWebElements(chipRemoveIcon);
        while (!listRemoveIcon.isEmpty()) {
            listRemoveIcon.get(0).click();
            listRemoveIcon = getWebElements(chipRemoveIcon);
        }

        // 3. Excel có cột cần thao tác nhưng ô để trống → chỉ xoá, không add
        if (expected.isEmpty()) {
            return;
        }

        // 4. Add lại thẻ theo Excel
        for (String member : expected) {
            setTextAndKey(input, member, Keys.ENTER);
        }

        clickElement(blurTarget);
    }

    public static void setTextInIframeIfChanged(By iframe, By iframeEditor, String expected) {
        if (expected == null) {
            return;
        }

        switchToFrame(iframe);

        try {
            String actual = getElementText(iframeEditor);

            if (expected.isEmpty()) {
                if (!actual.isEmpty()) {
                    clearElementText(iframeEditor);
                }
                return;
            }

            if (expected.equals(actual)) {
                return;
            }

            clearElementText(iframeEditor);
            setText(iframeEditor, expected);
        } finally {
            switchToDefaultContent();
        }
    }

    public static void setCheckboxIfChanged(By checkbox, boolean expected, By label) {
        boolean actual = isCheckboxSelected(checkbox);
        if (actual == expected) {
            return;
        }

        clickElement(label);
    }
}