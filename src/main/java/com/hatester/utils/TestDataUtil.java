package com.hatester.utils;

import com.hatester.helpers.SystemHelper;
import org.apache.commons.lang3.RandomStringUtils;

public class TestDataUtil {
    public static String generateUnique(String prefix) {
        return prefix + "_" + SystemHelper.getDateTimeNow() + "_" + RandomStringUtils.randomAlphanumeric(6);
    }
}
