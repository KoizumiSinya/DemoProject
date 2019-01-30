package studio.sinya.jp.demo_httpframwork.http.utils;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

/**
 * Created by SinyaKoizumi on 2015/9/14.
 */
public class FileUtils {
    public static boolean isValidate(String content) {
        return content != null && !"".equals(content.trim());
    }

    public static boolean isValidate(ArrayList<NameValuePair> content) {
        return content != null && content.size() > 0;
    }
}
