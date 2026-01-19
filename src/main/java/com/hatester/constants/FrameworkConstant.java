package com.hatester.constants;

import com.hatester.helpers.PropertiesHelper;

public class FrameworkConstant {
    public static final String BROWSER = PropertiesHelper.getValue("BROWSER");
    public static final String HEADLESS = PropertiesHelper.getValue("HEADLESS");
    public static final String WINDOW_SIZE = PropertiesHelper.getValue("WINDOW_SIZE");
    public static final String URL = PropertiesHelper.getValue("URL");
    public static final String EMAIL = PropertiesHelper.getValue("EMAIL");
    public static final String PASSWORD = PropertiesHelper.getValue("PASSWORD");

    public static final String SCREENSHOT_PATH = PropertiesHelper.getValue("SCREENSHOT_PATH");
    public static final String VIDEO_RECORD_PATH = PropertiesHelper.getValue("VIDEO_RECORD_PATH");

    public static final String SCREENSHOT_ALL_STEPS = PropertiesHelper.getValue("SCREENSHOT_ALL_STEPS");
    public static final String SCREENSHOT_PASSED = PropertiesHelper.getValue("SCREENSHOT_PASSED");
    public static final String SCREENSHOT_FAILED = PropertiesHelper.getValue("SCREENSHOT_FAILED");
    public static final String SCREENSHOT_SKIPPED = PropertiesHelper.getValue("SCREENSHOT_SKIPPED");
    public static final String VIDEO_RECORD =  PropertiesHelper.getValue("VIDEO_RECORD");

    public static final int WAIT_TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("WAIT_TIMEOUT"));
    public static final int POOL_TIME = Integer.parseInt(PropertiesHelper.getValue("POOL_TIME"));
    public static final double SLEEP_TIME = Double.parseDouble(PropertiesHelper.getValue("SLEEP_TIME"));
    public static final int PAGE_LOAD_TIMEOUT = Integer.parseInt(PropertiesHelper.getValue("PAGE_LOAD_TIMEOUT"));
}
