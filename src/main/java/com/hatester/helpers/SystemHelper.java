package com.hatester.helpers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SystemHelper {
    public static String getCurrentDir() {
        String currentDir = System.getProperty("user.dir") + File.separator;
        return currentDir;
    }

    public static String getDateTimeSimple() {
        return new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
    }

    public static String getDateTimeNow() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = now.format(dateTimeFormatter)
                .replace("-", "")
                .replace(" ", "_")
                .replace(":", "");
        return formattedDate;
    }
}
