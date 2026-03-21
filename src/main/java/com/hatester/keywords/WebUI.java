package com.hatester.keywords;

import com.hatester.drivers.DriverManager;
import com.hatester.enums.MatchType;
import com.hatester.reports.AllureManager;
import com.hatester.utils.DataUtil;
import com.hatester.utils.LogUtils;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.hatester.config.FrameworkConfig.*;

public class WebUI {
    //-------------------------------WAIT-------------------------------
    public static void sleep(double second) {
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static WebDriverWait getWait(int seconds) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds),
                Duration.ofMillis(getPoolTime()));
        /**
         * Duy trì việc chờ đợi ngay cả khi gặp lỗi StaleElementReferenceException.
         * Lỗi này xảy ra khi Element đã tìm thấy nhưng bị thay đổi/vẽ lại (render) bởi JavaScript ngay lúc tương tác.
         * .ignoring giúp Selenium bỏ qua lỗi này và tự động tìm lại (Retry) phần tử cho đến khi hết Timeout.
         =====> GIÚP script không bị "văng lỗi" vô lý khi trang web load nhanh/chậm hoặc cập nhật dữ liệu ngầm (AJAX).
         */
        wait.ignoring(StaleElementReferenceException.class);
        return wait;
    }

    public static WebElement waitForElementVisible(By by, int seconds) {
        WebElement element = null;
        try {
            WebDriverWait wait = getWait(seconds);
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            highlightElement(by);
            return element;
        } catch (Exception e) {
            LogUtils.error("Timeout waiting for the element Visible with " + seconds + "s : " + by);
            Assert.fail("Timeout waiting for the element Visible with " + seconds + "s : " + by);
        }
        return element;
    }

    public static WebElement waitForElementVisible(By by) {
        return waitForElementVisible(by, getWaitTimeout());
    }

    public static WebElement waitForElementToBeClickable(By by, int seconds) {
        WebElement element = null;
        try {
            WebDriverWait wait = getWait(seconds);
            element = wait.until(ExpectedConditions.elementToBeClickable(by));
            highlightElement(by);
            return element;
        } catch (Exception e) {
            LogUtils.error("Timeout waiting for the element to be clickable with " + seconds + "(s) : " + by);
            Assert.fail("Timeout waiting for the element to be clickable with " + seconds + "(s) : " + by);
        }
        return element;
    }

    public static WebElement waitForElementToBeClickable(By by) {
        return waitForElementToBeClickable(by, getWaitTimeout());
    }

    public static WebElement waitForElementPresent(By by, int seconds) {
        WebElement element = null;
        try {
            WebDriverWait wait = getWait(seconds);
            element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
            highlightElement(by);
            return element;
        } catch (Exception e) {
            LogUtils.error("Element not exist with " + seconds + "(s) : " + by);
            Assert.fail("Element not exist with " + seconds + "(s) : " + by);
        }
        return element;
    }

    public static WebElement waitForElementPresent(By by) {
        return waitForElementPresent(by, getWaitTimeout());
    }

    public static void waitForElementNotVisible(By by, int seconds) {
        try {
            WebDriverWait wait = getWait(seconds);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (Exception e) {
            LogUtils.error("Timeout waiting for the element not visible with " + seconds + "(s) : " + by);
            Assert.fail("Timeout waiting for the element not visible with " + seconds + "(s) : " + by);
        }
    }

    public static void waitForElementNotVisible(By by) {
        waitForElementNotVisible(by, getWaitTimeout());
    }

    public static void waitForPageLoaded(int seconds) {
        try {
            WebDriverWait wait = getWait(seconds);
            wait.until(webDriver -> {
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                // Ép kiểu kết quả về String rồi mới so sánh với "complete"
                return String.valueOf(js.executeScript("return document.readyState")).equals("complete");
            });
        } catch (Exception e) {
            LogUtils.error("Timeout waiting for page loaded: " + e.getMessage());
            Assert.fail("Timeout waiting for page loaded");
        }
    }

    public static void waitForPageLoaded() {
        waitForPageLoaded(getPageLoadTimeout());
    }

    public static void waitForDomStable(int seconds) {
        try {
            WebDriverWait wait = getWait(seconds);
            wait.until(driver -> {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                String dom1 = js.executeScript("return document.getElementsByTagName('*').length").toString();
                sleep(0.2);
                String dom2 = js.executeScript("return document.getElementsByTagName('*').length").toString();
                return dom1.equals(dom2);
            });
        } catch (Exception e) {
            LogUtils.error("Timeout waiting for DOM stable");
            Assert.fail("Timeout waiting for DOM stable");
        }
    }

    public static void waitForDomStable() {
        waitForDomStable(getWaitTimeout());
    }

    //-------------------------------BROWSER-------------------------------
    @Step("Navigate to URL: {0}")
    public static void openURL(String url) {
        try {
            DriverManager.getDriver().get(url);
            waitForPageLoaded();
            sleep(getSleepTime());
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
            sleep(getSleepTime());
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
        try {
            DriverManager.getDriver().navigate().refresh();
            waitForPageLoaded();
            LogUtils.info("Refreshed the page");
        } catch (Exception e) {
            LogUtils.error("Failed to refresh the page: " + e.getMessage());
        }
    }

    //-------------------------------CONTEXT: IFRAME, ALERT, POPUP-WINDOW-------------------------------
    // --- FRAME ACTIONS ---
    @Step("Switched to iframe {0} with seconds {1}")
    public static void switchToFrame(By by, int seconds) {
        try {
            WebDriverWait wait = getWait(seconds);
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
            WebDriverWait wait = getWait(getWaitTimeout());
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
            WebDriverWait wait = getWait(getWaitTimeout());
            wait.until(ExpectedConditions.alertIsPresent()).dismiss();
            LogUtils.info("Dismissed alert");
        } catch (Exception e) {
            LogUtils.error("Alert not present to dismissed");
            Assert.fail("Alert not present to dismissed");
        }
    }

    //-------------------------------ELEMENT-------------------------------
    public static WebElement getWebElement(By by) {
        // Tận dụng WaitActions để tránh NoSuchElementException
        return waitForElementPresent(by);
    }

    public static List<WebElement> getWebElements(By by) {
        return DriverManager.getDriver().findElements(by);
    }

    public static void highlightElement(By by, String color) {
        String script = "arguments[0].style.border='3px solid " + color + "';";
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript(script, DriverManager.getDriver().findElement(by));
    }

    public static void highlightElement(By by) {
        highlightElement(by, "red");
    }

    @Step("Get text of element: {0}")
    public static String getElementText(By by) {
        sleep(getSleepTime());
        LogUtils.info("Get text of element: " + by);
        String text = waitForElementVisible(by).getText();
        LogUtils.info("==> TEXT: " + text);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
        AllureManager.saveTextLog("==> TEXT: " + text);
        return text;
    }

    @Step("Get attribute {1} of element {0}")
    public static String getElementAttribute(By by, String attributeName) {
        sleep(getSleepTime());
        LogUtils.info("Get attribute of element: " + by);
        String value = waitForElementVisible(by).getAttribute(attributeName);
        LogUtils.info("==> Attribute value: " + value);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
        AllureManager.saveTextLog("==> Attribute value: " + value);
        return value;
    }

    @Step("Get CSS value {1} of element {0}")
    public static String getElementCssValue(By by, String cssPropertyName) {
        sleep(getSleepTime());
        LogUtils.info("Get CSS value " + cssPropertyName + " of element " + by);
        String value = waitForElementVisible(by).getCssValue(cssPropertyName);
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
            WebElement element = getWebElement(by);
            boolean isSelected = element.isSelected();
            LogUtils.info("Checkbox " + by + " selected status: " + isSelected);
            return isSelected;
        } catch (Exception e) {
            return false;
        }
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

    @Step("Click on element: {0}")
    public static void clickJS(By by) {
        getJS().executeScript("arguments[0].click();", waitForElementPresent(by));
        LogUtils.info("Click on element " + by);
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
        waitForElementVisible(by, seconds).clear();
        LogUtils.info("Clear text of element " + by + " with " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Clear text of element: {0}")
    public static void clearTextByKeyboard(By by) {
        sleep(getSleepTime());
        // Nhấn Ctrl + A rồi nhấn Backspace để xóa sạch
        waitForElementVisible(by).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        LogUtils.info("Clear text of element: " + by);
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
        LogUtils.info("Set text " + text + " on element " + by + " with " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set key on element: {0}")
    public static void setKey(By by, Keys key) {
        sleep(getSleepTime());
        waitForElementVisible(by).sendKeys(key);
        LogUtils.info("Set key on element: " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set key on element: {0} with timeout {2} seconds")
    public static void setKey(By by, Keys key, int seconds) {
        sleep(getSleepTime());
        waitForElementVisible(by, seconds).sendKeys(key);
        LogUtils.info("Set key on element: " + by + "with: " + seconds + "(s)");
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    @Step("Set text and key {1} on element {0} ")
    public static void setTextAndKey(By by, String text, Keys key) {
        sleep(getSleepTime());
        waitForElementVisible(by).sendKeys(text, key);
        LogUtils.info("Set text and key " + text + " then " + key.name() + " on element " + by);
        if (isScreenshotAllSteps()) {
            AllureManager.saveScreenshotPNG();
        }
    }

    //-------------------------------SCROLL-------------------------------
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

    //-------------------------------ACTIONS CLASS-------------------------------
    private static Actions getActions() {
        return new Actions(DriverManager.getDriver());
    }

    public static void hoverElement(By by) {
        try {
            getActions().moveToElement(getWebElement(by)).perform();
            LogUtils.info("Hover on element: " + by);

        } catch (Exception e) {
            LogUtils.error("Failed to hover on element: " + by + ". Error: " + e.getMessage());
            Assert.fail("Failed to hover on element: " + by);
        }
    }

    public static void moveToOffset(int X, int Y) {
        try {
            getActions().moveByOffset(X, Y).build().perform();
            LogUtils.info("Move mouse by offset: " + X + "," + Y);
        } catch (Exception e) {
            LogUtils.error("Failed to move mouse by offset. Error: " + e.getMessage());
            Assert.fail("Failed to move mouse by offset.");
        }
    }

    public static void dragAndDrop(By fromElement, By toElement) {
        try {
            getActions().dragAndDrop(getWebElement(fromElement), getWebElement(toElement)).perform();
            LogUtils.info("Drag " + fromElement + " and drop to " + toElement);
        } catch (Exception e) {
            LogUtils.error("Failed to drag and drop: " + e.getMessage());
            Assert.fail("Failed to drag and drop.");
        }
    }

    public static void dragAndDropElement(By fromElement, By toElement) {
        try {
            getActions().clickAndHold(getWebElement(fromElement))
                    .moveToElement(getWebElement(toElement))
                    .release(getWebElement(toElement))
                    .build().perform();
            LogUtils.info("Drag " + fromElement + " and drop to " + toElement);
        } catch (Exception e) {
            LogUtils.error("Failed to drag and drop: " + e.getMessage());
            Assert.fail("Failed to drag and drop.");
        }
    }

    public static void dragAndDropOffset(By fromElement, int X, int Y) {
        try {
            getActions().clickAndHold(getWebElement(fromElement))
                    .pause(Duration.ofSeconds(1))
                    .moveByOffset(X, Y)
                    .release()
                    .build().perform();
            LogUtils.info("Drag " + fromElement + " to offset: " + X + "," + Y);
        } catch (Exception e) {
            LogUtils.error("Failed to drag and drop offset: " + e.getMessage());
            Assert.fail("Failed to drag and drop offset.");
        }
    }

    public static void clickByActions(By by) {
        try {
            getActions().moveToElement(getWebElement(by)).click().build().perform();
            LogUtils.info("Click to element by Actions: " + by);
        } catch (Exception e) {
            LogUtils.error("Failed to click by Actions: " + e.getMessage());
            Assert.fail("Failed to click by Actions.");
        }
    }

    public static void sendTextByAction(By by, String text) {
        try {
            // Click -> End (focus chuột vào cuối input) -> Nhập text
            getActions().click(getWebElement(by)).sendKeys(Keys.END)
                    .sendKeys(text)
                    .build().perform();
            LogUtils.info("Set text '" + text + "' to element: " + by);
        } catch (Exception e) {
            LogUtils.error("Failed to send text by Action: " + e.getMessage());
            Assert.fail("Failed to send text by Action.");
        }
    }

    //-------------------------------ROBOT CLASS-------------------------------
    private static Robot getRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            LogUtils.error("Failed to initialize AWT Robot: " + e.getMessage());
            Assert.fail("Failed to initialize AWT Robot.");
            return null;
        }
    }

    public static void pressENTER() {
        try {
            Robot robot = getRobot();
            if (robot != null) {
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                LogUtils.info("Pressed ENTER key");
            }
        } catch (Exception e) {
            Assert.fail("FAILED. Cannot press ENTER: " + e.getMessage());
        }
    }

    public static void pressESC() {
        try {
            Robot robot = getRobot();
            if (robot != null) {
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
                LogUtils.info("Pressed ESC key");
            }
        } catch (Exception e) {
            Assert.fail("FAILED. Cannot press ESC: " + e.getMessage());
        }
    }

    public static void pressF11() {
        try {
            Robot robot = getRobot();
            if (robot != null) {
                robot.keyPress(KeyEvent.VK_F11);
                robot.keyRelease(KeyEvent.VK_F11);
                LogUtils.info("Pressed F11 key");
            }
        } catch (Exception e) {
            Assert.fail("FAILED. Cannot press F11: " + e.getMessage());
        }
    }

    public static void copyFilePathToClipboard(String filePath) {
        try {
            StringSelection str = new StringSelection(filePath);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
            LogUtils.info("Copied to clipboard: " + filePath);
        } catch (Exception e) {
            LogUtils.error("FAILED to copy to clipboard: " + e.getMessage());
            Assert.fail("FAILED to copy to clipboard: " + e.getMessage());
        }
    }

    public static void pasteFromClipboard(int seconds, int delayTime) {
        Robot robot = getRobot();
        if (robot == null) return;
        try {
            sleep(seconds);
            robot.setAutoDelay(delayTime);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);

            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            LogUtils.info("Pasted from clipboard and pressed ENTER");
        } catch (Exception e) {
            // Đảm bảo thả phím Control nếu chẳng may bị lỗi lúc đang đè phím
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);

            LogUtils.error("FAILED. Cannot paste from clipboard: " + e.getMessage());
            Assert.fail("FAILED. Cannot paste from clipboard: " + e.getMessage());
        }
    }

    //-------------------------------VERIFY-------------------------------
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

    //-------------------------------FORM-------------------------------
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
        sleep(0.3);
        sendTextByAction(inputSearchDropdown, " ");
        waitForElementToBeClickable(optionDropdown, 20);
        clickElement(optionDropdown);
    }

    //Function<String, By>: là Java Function, Nhận vào 1 giá trị kiểu String → Trả ra 1 giá trị kiểu By
    public static void selectMultiDropdownToggleIfChanged(By dropdown, List<String> expected, String attribute, Function<String, By> optionByText) {
        //Không truyền dữ liệu → không làm gì
        if (expected == null || expected.isEmpty()) {
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
        for (int i = listRemoveIcon.size() - 1; i >= 0; i--) {
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

    public static void uploadMultiFileBySendkeys(By openAttachLocator, Function<Integer, By> addMoreIconLocator,
                                                 Function<Integer, By> fileInputLocator, List<String> files, String baseUploadPath) {
        if (files == null || files.isEmpty()) {
            return;
        }

        if (openAttachLocator != null) {
            clickElement(openAttachLocator);
        }

        // Click icon Add more files nếu có nhiều hơn 1 file
        if (files.size() > 1 && addMoreIconLocator != null) {
            for (int i = 0; i < files.size() - 1; i++) {
                clickElement(addMoreIconLocator.apply(0));
            }
        }

        // Set file path
        for (int i = 0; i < files.size(); i++) {
            String fullPath = baseUploadPath + files.get(i);
            setText(fileInputLocator.apply(i), fullPath);
        }
    }

    public static void setSlider(By inputHidden, By slider, String expected) {
        if (StringUtils.isBlank(expected)) {
            return;
        }

        try {
            int percent = Integer.parseInt(expected.trim());
            setSliderValue(inputHidden, slider, percent);
        } catch (NumberFormatException e) {
            LogUtils.error("Invalid slider value: " + expected);
            throw e;
        }
    }

    //table
    @Step("Verify column [{4}] with expected value '{3}' using condition '{5}'")
    public static void verifyTableColumnValues(By rowLocator, By cellLocatorTemplate, int columnIndex,
                                               String expectedValue, String columnName, MatchType matchType) {
        LogUtils.info("Verify column [" + columnName + "] with expected value '" + expectedValue + "' using condition '" + matchType + "'");

        //Xác định số dòng của table sau khi search
        List<WebElement> rows = getWebElements(rowLocator);
        int totalRows = rows.size();

        LogUtils.info("Total rows found in table: " + totalRows);
        Assert.assertTrue(totalRows > 0, "Table has no data");

        //Duyệt từng dòng
        for (int i = 1; i <= totalRows; i++) {
            String rawXpath = cellLocatorTemplate.toString().replace("By.xpath: ", "");
            By cellLocator = By.xpath(String.format(rawXpath, i, columnIndex));

            WebElement cell = getWebElement(cellLocator);
            scrollToElementAtBottom(cell);

            String actualValue = getElementText(cellLocator).trim();

            LogUtils.info("Row " + i + " | Expected: " + expectedValue + " | Actual: " + actualValue);
            AllureManager.saveTextLog("Row " + i + " | Expected: " + expectedValue + " | Actual: " + actualValue);

            //Switch expression (Java 14+) /// Dấu -> thay thế hoàn toàn break
            //Trả về một giá trị và gán trực tiếp vào result
            boolean result = switch (matchType) {
                case CONTAINS -> actualValue.toUpperCase().contains(expectedValue.toUpperCase());
                case EQUALS -> actualValue.equalsIgnoreCase(expectedValue);
            };

            Assert.assertTrue(result, "Row " + i + " | Column [" + columnName + "] | Expected [" + expectedValue + "] not matched.");
        }
    }

    @Step("Verify all rows contain search value '{1}'")
    public static void verifyAllRowsContainSearchValue(By rowLocator, String searchValue) {
        List<WebElement> rows = getWebElements(rowLocator);
        int totalRows = rows.size();

        LogUtils.info("Total rows found: " + totalRows);
        Assert.assertTrue(totalRows > 0, "Table has no data after search");

        for (int i = 0; i < totalRows; i++) {
            WebElement row = rows.get(i);
            scrollToElementAtBottom(row);

            String rowText = row.getText().trim();

            LogUtils.info("Row " + (i + 1) + " | Row Text: " + rowText);
            AllureManager.saveTextLog("Row " + (i + 1) + " | Row Text: " + rowText);

            boolean result = rowText.toUpperCase().contains(searchValue.toUpperCase());
            Assert.assertTrue(result, "Row " + (i + 1) + " does not contain search value: " + searchValue);
        }
    }
}