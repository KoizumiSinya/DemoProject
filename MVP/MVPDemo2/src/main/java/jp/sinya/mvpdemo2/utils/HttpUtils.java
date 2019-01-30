package jp.sinya.mvpdemo2.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//POST请求
public class HttpUtils {

    public static String post(String urlStr, String username, String password) throws Exception {
        StringBuffer sb = null;
        String param = "username=" + username + "&password=" + password;

        URL url = new URL(urlStr);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        // 设置参数
        httpConn.setDoOutput(true); // 需要输出
        httpConn.setDoInput(true); // 需要输入
        httpConn.setUseCaches(false); // 不允许缓存
        httpConn.setRequestMethod("POST"); // 设置POST方式连接
        // 设置请求属性
        httpConn.setRequestProperty("Charset", "UTF-8");
        // 连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        httpConn.connect();
        // 建立输入流，向指向的URL传入参数
        DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
        dos.writeBytes(param.toString());
        dos.flush();
        dos.close();
        // 获得响应状态
        int resultCode = httpConn.getResponseCode();
        sb = new StringBuffer();
        if (HttpURLConnection.HTTP_OK == resultCode) {
            String readLine = new String();
            //解析网络请求数据
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            while ((readLine = responseReader.readLine()) != null) {
                sb.append(readLine).append("\n");
            }
            responseReader.close();
            return sb.toString();
        }
        return null;
    }

    public interface OnHttpResultListener {
        public void onResult(String result);
    }
}
