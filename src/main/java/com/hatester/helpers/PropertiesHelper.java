package com.hatester.helpers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class PropertiesHelper {

    private static Properties properties;
    private static String linkFile;
    private static String relPropertiesFilePathDefault = "src/test/resources/configs/config.properties";

    public static Properties loadAllFiles() {
        List<String> files = new LinkedList<>();
        files.add("src/test/resources/configs/config.properties");

        try {
            properties = new Properties();
            for (String f : files) {
                linkFile = SystemHelper.getCurrentDir() + f;
                try (FileInputStream fis = new FileInputStream(linkFile)) {
                    Properties tempProp = new Properties();
                    tempProp.load(fis);
                    properties.putAll(tempProp);
                }
            }
            //gán lại gtrị default nếu có nhiều file properties
            linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePathDefault;
            return properties;
        } catch (IOException ioe) {
            System.out.println("Cannot load properties file: " + linkFile);
            throw new RuntimeException(ioe);
        }
    }

    public static void setFile(String relPropertiesFilePath) {
        Properties temp = new Properties();
        linkFile = SystemHelper.getCurrentDir() + relPropertiesFilePath;
        try (FileInputStream fis = new FileInputStream(linkFile)) {
            temp.load(fis);
        } catch (Exception e) {
            System.out.println("Cannot load properties file: " + linkFile);
            throw new RuntimeException(e);
        }
        properties = temp;
    }

    public static void setDefaultFile() {
        setFile(relPropertiesFilePathDefault);
    }

    public static String getValue(String key) {
        if (properties == null) {
            setDefaultFile();
        }
        // Lấy giá trị từ file đã Set
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Key not found in properties: " + key);
        }
        return value;
    }

    //synchronized: Để ngăn chặn nhiều thread cùng lúc ghi vào file .properties
    // => Chỉ cho phép 1 thread chạy setValue() tại một thời điểm
    public static synchronized void setValue(String key, String value) {
        if (properties == null) {
            setDefaultFile();
        }
        properties.setProperty(key, value);
        try (FileOutputStream out = new FileOutputStream(linkFile)) {
            properties.store(out, null);
        } catch (Exception e) {
            System.out.println("Cannot write property file: " + linkFile);
            throw new RuntimeException(e);
        }
    }
}