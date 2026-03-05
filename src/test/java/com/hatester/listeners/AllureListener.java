package com.hatester.listeners;

import com.hatester.drivers.DriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.TestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;

import static com.hatester.config.FrameworkConfig.*;

public class AllureListener implements TestLifecycleListener {

    @Override
    public void beforeTestSchedule(TestResult result) {
    }

    @Override
    public void afterTestSchedule(TestResult result) {
    }

    @Override
    public void beforeTestUpdate(TestResult result) {
    }

    @Override
    public void afterTestUpdate(TestResult result) {
    }

    @Override
    public void beforeTestStart(TestResult result) {
    }

    @Override
    public void afterTestStart(TestResult result) {
    }

    @Override
    public void beforeTestStop(TestResult result) {
        Status status = result.getStatus();

        if (status == null) {
            return;  // tránh NPE khi Allure chưa set status
        }

        if (isScreenshotPassed() && Status.PASSED.equals(status)) {
            attachScreenshot(result, "Passed");
        }

        if (isScreenshotFailed() && Status.FAILED.equals(status)) {
            attachScreenshot(result, "Failed");
        }

        if (isScreenshotSkipped() && Status.SKIPPED.equals(status)) {
            attachScreenshot(result, "Skipped");
        }

        if (isScreenshotBroken() && Status.BROKEN.equals(status)) {
            attachScreenshot(result, "Broken");
        }
    }

    private void attachScreenshot(TestResult result, String type) {
        if (DriverManager.getDriver() == null) {
            return;
        }
        Allure.addAttachment(
                result.getName() + "_" + type + "_Screenshot",
                new ByteArrayInputStream(
                        ((TakesScreenshot) DriverManager.getDriver())
                                .getScreenshotAs(OutputType.BYTES)
                )
        );
    }

    @Override
    public void afterTestStop(TestResult result) {
    }

    @Override
    public void beforeTestWrite(TestResult result) {
    }

    @Override
    public void afterTestWrite(TestResult result) {
    }
}
