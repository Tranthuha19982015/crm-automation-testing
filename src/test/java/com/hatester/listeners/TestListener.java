package com.hatester.listeners;

import com.hatester.helpers.*;
import com.hatester.utils.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static com.hatester.config.FrameworkConfig.*;

public class TestListener implements ITestListener {

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    @Override
    public void onStart(ITestContext result) {
        LogUtils.info("Test suite " + result.getName() + " started at: " + result.getStartDate());
    }

    @Override
    public void onFinish(ITestContext result) {
        LogUtils.info("Test suite " + result.getName() + " finished at: " + result.getEndDate());
    }

    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("Test Started: " + result.getName());

        if (isVideoRecord()) {
            CaptureHelper.startRecord(result.getName());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("Test case " + result.getName() + " is passed.");

        if (isVideoRecord()) {
            CaptureHelper.stopRecord();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.error("Test case " + result.getName() + " is failed.");

        LogUtils.error("==> Reason: " + result.getThrowable());
        CaptureHelper.takeScreenshot(result.getName() + "_" + SystemHelper.getDateTimeNow());

        if (isVideoRecord()) {
            CaptureHelper.stopRecord();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("Test case " + result.getName() + " is skipped.");

        if (isVideoRecord()) {
            CaptureHelper.stopRecord();
        }
    }
}