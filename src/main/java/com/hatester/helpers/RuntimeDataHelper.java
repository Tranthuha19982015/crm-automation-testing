package com.hatester.helpers;

import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RuntimeDataHelper {

    private static final String FILE_PATH = "target/runtime-data/data.json";

    //Đọc file data.json và trả về dữ liệu dưới dạng JSONObject
    private static JSONObject load() {
        try {
            File file = new File(FILE_PATH);

            //Nếu file chưa tồn tại → trả về JSON rỗng {}
            if (!file.exists()) {
                return new JSONObject();
            }

            //Đọc toàn bộ file thành String
            String content = Files.readString(file.toPath());

            //Convert String thành JSONObject
            return new JSONObject(content);
        } catch (Exception e) {
            throw new RuntimeException("Cannot load runtime data", e);
        }
    }

    private static void save(JSONObject json) {
        try {
            //tạo folder nếu chưa tồn tại
            Files.createDirectories(Paths.get("target/runtime-data"));

            //ghi file
            Files.writeString(
                    Paths.get(FILE_PATH),
                    json.toString(4) //Lùi đầu dòng 4 ký tự khoảng trắng
            );
        } catch (Exception e) {
            throw new RuntimeException("Cannot save runtime data", e);
        }
    }

    public static void set(String key, String value) {
        JSONObject json = load();  //load dữ liệu cũ có trong file
        json.put(key, value);      //thêm/cập nhật key
        save(json);                //Lưu lại file
    }

    //đọc dữ liệu theo key
    public static String get(String key) {
        return load().getString(key);
    }
}