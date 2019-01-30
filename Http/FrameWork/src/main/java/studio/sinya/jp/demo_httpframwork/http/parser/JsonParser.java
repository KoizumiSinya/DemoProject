package studio.sinya.jp.demo_httpframwork.http.parser;

import com.google.gson.Gson;

import java.lang.reflect.Type;

import studio.sinya.jp.demo_httpframwork.http.utils.FileUtils;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/14 9:36
 * editor：
 * updateDate：2015/9/14 9:36
 */
public class JsonParser {

    public static Gson gson = new Gson();

    public static <T> T deserializeByJson(String data, Type type) {
        if (FileUtils.isValidate(data)) {
            return gson.fromJson(data, type);
        }
        return null;
    }

    public static <T> T deserializeByJson(String data, Class<T> clazz) {
        if (FileUtils.isValidate(data)) {
            return gson.fromJson(data, clazz);
        }
        return null;
    }

    public static <T>  String serializeToJson(T t) {
        if (t == null) {
            return "";
        }

        return gson.toJson(t);
    }
}
