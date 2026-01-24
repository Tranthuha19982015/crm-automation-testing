package com.hatester.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataUtils {
    public static List<String> parseList(String rawData) {
        //Nếu truyền vào String rỗng/null thì trả ra List rỗng
        if (rawData == null || rawData.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(rawData.split(","))
                .map(String::trim)                      //cắt khoảng trắng đầu - cuối
                .collect(Collectors.toList());          //Chuyển Stream<String> sang List<String>
    }
}