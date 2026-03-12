package com.hatester.keywords.wait;

import com.hatester.drivers.DriverManager;
import com.hatester.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static com.hatester.config.FrameworkConfig.*;
import static com.hatester.config.FrameworkConfig.getPoolTime;
import static com.hatester.config.FrameworkConfig.getWaitTimeout;
import static com.hatester.keywords.element.ElementActions.*;

public class WaitActions {
    public static void sleep(double second) {
        try {
            Thread.sleep((long) (1000 * second));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static WebDriverWait getWait(int seconds) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds), Duration.ofMillis(getPoolTime()));

        /**
         * Duy trì việc chờ đợi ngay cả khi gặp lỗi StaleElementReferenceException.
         * Lỗi này xảy ra khi Element đã tìm thấy nhưng bị thay đổi/vẽ lại (render) bởi JavaScript ngay lúc tương tác.
         * .ignoring giúp Selenium bỏ qua lỗi này và tự động tìm lại (Retry) phần tử cho đến khi hết Timeout.
         =====> GIÚP script không bị "văng lỗi" vô lý khi trang web load nhanh/chậm hoặc cập nhật dữ liệu ngầm (AJAX).
         */
        wait.ignoring(StaleElementReferenceException.class);
        return wait;
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
}
