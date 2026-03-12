package com.hatester.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    public static void requireNotBlank(String value, String message) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(message);
        }
    }
}
