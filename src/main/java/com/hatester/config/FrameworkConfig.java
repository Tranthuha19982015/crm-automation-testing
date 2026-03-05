package com.hatester.config;

import com.hatester.enums.BrowserType;
import com.hatester.helpers.PropertiesHelper;
import com.hatester.utils.LogUtils;

//Runtime config loaded from config.properties in @BeforeSuite
public class FrameworkConfig {
    //Chặn kế thừa vì là utility class toàn static
    private FrameworkConfig() {
        throw new UnsupportedOperationException("Utility class");
    }

    private static String getRaw(String key) {
        return PropertiesHelper.getValue(key);
    }

    private static String getRequired(String key) {
        String value = getRaw(key);
        if (value == null || value.isBlank()) {
            throw new RuntimeException("Config key '" + key + "' is missing or empty");
        }
        return value.trim();
    }

    private static String getOptional(String key) {
        String value = getRaw(key);
        return (value == null || value.isBlank()) ? null : value.trim();
    }

    private static boolean getBoolean(String key) {
        String value = getRequired(key);
        if (!"true".equalsIgnoreCase(value) && !"false".equalsIgnoreCase(value)) {
            throw new RuntimeException("Invalid boolean value for key '" + key + "': " + value);
        }
        return Boolean.parseBoolean(value);
    }

    private static int getInt(String key) {
        try {
            return Integer.parseInt(getRequired(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid int value for key '" + key + "'", e);
        }
    }

    private static double getDouble(String key) {
        try {
            return Double.parseDouble(getRequired(key));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid double value for key '" + key + "'", e);
        }
    }

    public static String getExcelDataFilePath() {
        return getRequired("EXCEL_DATA_FILE_PATH");
    }

    public static String getUploadFilePath() {
        return getRequired("UPLOAD_FILE_PATH");
    }

    public static BrowserType getBrowser() {
        String raw = getOptional("BROWSER");

        if (raw == null) {
            return null; // cho phép null để dùng param TestNG
        }

        try {
            //Chuyển giá trị truyền vào từ config: String thành Enum (có in hoa và bỏ khoảng trắng đầu cuối chuỗi)
            return BrowserType.valueOf(raw.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid BROWSER config: " + raw);
        }
    }

    public static boolean isHeadless() {
        return getBoolean("HEADLESS");
    }

    public static String getWindowSize() {
        return getRequired("WINDOW_SIZE");
    }

    public static String getURL() {
        return getRequired("URL");
    }

    public static String getEmail() {
        return getRequired("EMAIL");
    }

    public static String getPassword() {
        return getRequired("PASSWORD");
    }

    public static String getAdminDashboardUrl() {
        return getRequired("ADMIN_DASHBOARD_URL");
    }

    public static String getScreenshotPath() {
        return getRequired("SCREENSHOT_PATH");
    }

    public static String getVideoRecordPath() {
        return getRequired("VIDEO_RECORD_PATH");
    }

    public static boolean isScreenshotAllSteps() {
        return getBoolean("SCREENSHOT_ALL_STEPS");
    }

    public static boolean isScreenshotPassed() {
        return getBoolean("SCREENSHOT_PASSED");
    }

    public static boolean isScreenshotFailed() {
        return getBoolean("SCREENSHOT_FAILED");
    }

    public static boolean isScreenshotSkipped() {
        return getBoolean("SCREENSHOT_SKIPPED");
    }

    public static boolean isScreenshotBroken() {
        return getBoolean("SCREENSHOT_BROKEN");
    }

    public static boolean isVideoRecord() {
        return getBoolean("VIDEO_RECORD");
    }

    public static int getWaitTimeout() {
        return getInt("WAIT_TIMEOUT");
    }

    public static int getPoolTime() {
        return getInt("POOL_TIME");
    }

    public static double getSleepTime() {
        return getDouble("SLEEP_TIME");
    }

    public static int getPageLoadTimeout() {
        return getInt("PAGE_LOAD_TIMEOUT");
    }
}
