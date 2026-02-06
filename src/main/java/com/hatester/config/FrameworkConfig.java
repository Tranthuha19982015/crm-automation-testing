package com.hatester.config;

import com.hatester.helpers.PropertiesHelper;

//Runtime config loaded from config.properties in @BeforeSuite
public class FrameworkConfig {
    public static String getExcelDataFilePath() {
        return PropertiesHelper.getValue("EXCEL_DATA_FILE_PATH");
    }

    public static String getUploadFilePath() {
        return PropertiesHelper.getValue("UPLOAD_FILE_PATH");
    }

    public static String getBrowser() {
        return PropertiesHelper.getValue("BROWSER");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(PropertiesHelper.getValue("HEADLESS"));
    }

    public static String getWindowSize() {
        return PropertiesHelper.getValue("WINDOW_SIZE");
    }

    public static String getURL() {
        return PropertiesHelper.getValue("URL");
    }

    public static String getEmail() {
        return PropertiesHelper.getValue("EMAIL");
    }

    public static String getPassword() {
        return PropertiesHelper.getValue("PASSWORD");
    }

    public static String getAdminDashboardUrl() {
        return PropertiesHelper.getValue("ADMIN_DASHBOARD_URL");
    }

    public static String getScreenshotPath() {
        return PropertiesHelper.getValue("SCREENSHOT_PATH");
    }

    public static String getVideoRecordPath() {
        return PropertiesHelper.getValue("VIDEO_RECORD_PATH");
    }

    public static boolean isScreenshotAllSteps() {
        return Boolean.parseBoolean(PropertiesHelper.getValue("SCREENSHOT_ALL_STEPS"));
    }

    public static boolean isScreenshotPassed() {
        return Boolean.parseBoolean(PropertiesHelper.getValue("SCREENSHOT_PASSED"));
    }

    public static boolean isScreenshotFailed() {
        return Boolean.parseBoolean(PropertiesHelper.getValue("SCREENSHOT_FAILED"));
    }

    public static boolean isScreenshotSkipped() {
        return Boolean.parseBoolean(PropertiesHelper.getValue("SCREENSHOT_SKIPPED"));
    }

    public static boolean isVideoRecord() {
        return Boolean.parseBoolean(PropertiesHelper.getValue("VIDEO_RECORD"));
    }

    public static int getWaitTimeout() {
        return Integer.parseInt(PropertiesHelper.getValue("WAIT_TIMEOUT"));
    }

    public static int getPoolTime() {
        return Integer.parseInt(PropertiesHelper.getValue("POOL_TIME"));
    }

    public static double getSleepTime() {
        return Double.parseDouble(PropertiesHelper.getValue("SLEEP_TIME"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(PropertiesHelper.getValue("PAGE_LOAD_TIMEOUT"));
    }
}
