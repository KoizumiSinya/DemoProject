package studio.sinya.jp.demo_httpframwork.http.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * author : Sinya Koizumi
 * createDate ：2015/9/14 9:29
 * editor：
 * updateDate：2015/9/14 9:29
 */
public class IOUtils {
    public static String readFromFile(String path) {
        ByteArrayOutputStream out = null;
        FileInputStream in = null;

        try {
            File file = new File(path);
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream(1024);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }

            out.flush();

            return new String(out.toByteArray());
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (in != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
