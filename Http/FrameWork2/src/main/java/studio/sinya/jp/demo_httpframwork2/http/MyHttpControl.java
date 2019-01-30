package studio.sinya.jp.demo_httpframwork2.http;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.ByteArrayBuffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import studio.sinya.jp.demo_httpframwork2.utils.LogUtils;

/**
 * Created by SinyaKoizumi on 2015/9/21.
 */
public class MyHttpControl {

    public static String mException;
    public static int mStatusCode;

    /**
     * 使用apache的HttpClient创建一个HttpClient
     * 并封装好了client的一些基本参数
     *
     * @return
     */
    public static HttpClient gethttpClient() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setContentCharset(params, "UTF-8");
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);

        return new DefaultHttpClient(params);
    }

    /**
     * 获取HttpGet请求服务器之后返回的字符串数据
     *
     * @param urlStr     请求服务器的URL
     * @param isCompress 是否需要压缩
     * @return GET请求服务器之后返回的结果（字符串）
     */
    public static String httpGet(String urlStr, boolean isCompress) {
        HttpClient httpClient = gethttpClient();
        LogUtils.I("Sinya", "HttpGet URL:\n" + urlStr);
        HttpGet httpGet = new HttpGet(urlStr);

        try {
            HttpResponse response = httpClient.execute(httpGet);
            mStatusCode = response.getStatusLine().getStatusCode();
            LogUtils.I("Sinya", "HttpGet StatusCode: " + mStatusCode);

            //获取服务器返回的数据实体
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();

            //获取服务器返回的数据中的请求头部分
            Header[] apacheHeaders = response.getAllHeaders();

            if (apacheHeaders != null && apacheHeaders.length > 0) {
                int i = 0;
                for (Header header : apacheHeaders) {
                    if (header.getValue().equals("gzip")) {
                        LogUtils.I("Sinya", "HttpGet Header Value[" + i + "] = " + header.getValue());
                        i++;
                        inputStream = new GZIPInputStream(inputStream);
                    }
                }
            }

            if (isCompress) {
                inputStream = new GZIPInputStream(inputStream);
            }

            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(1024);

            byte[] bytes = new byte[2048];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                byteArrayBuffer.append(bytes, 0, len);
            }

            //获取服务器返回的数据（字符串形式）
            String result = new String(byteArrayBuffer.toByteArray());
            LogUtils.I("Sinya", "HttpGet Result:\n" + result);
            return result;

        } catch (ClientProtocolException e) {
            e.printStackTrace();
            LogUtils.E("Sinya", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.E("Sinya", e.toString());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return null;
    }


    /**
     * 通过一个MyHttpResult类封装服务器返回的结果String字符串以及响应状态码
     *
     * @param urlStr 请求服务器的URL
     * @return MyHttpResult对象
     */
    public static MyHttpResult httpGet(String urlStr) {

        MyHttpResult httpResult = new MyHttpResult();
        HttpClient apacheHttpClient = gethttpClient();

        HttpGet apacheHttpGet = new HttpGet(urlStr);

        //将请求的URL打印出来 以便追踪
        LogUtils.I("Sinya", "HttpGet URL:\n" + urlStr);

        try {
            HttpResponse apacheResponse = apacheHttpClient.execute(apacheHttpGet);
            httpResult.statusCode = apacheResponse.getStatusLine().getStatusCode();

            LogUtils.I("Sinya", "HttpGet StatusCode: " + httpResult.statusCode);

            HttpEntity apacheEntity = apacheResponse.getEntity();

            InputStream inputStream = apacheEntity.getContent();

            Header[] apacheHeaders = apacheResponse.getAllHeaders();

            if (apacheHeaders != null && apacheHeaders.length > 0) {
                int i = 0;
                for (Header header : apacheHeaders) {
                    if (header.getValue().equals("gzip")) {
                        LogUtils.I("Sinya", "HttpGet Header Value[" + i + "] = " + header.getValue());
                        i++;
                        inputStream = new GZIPInputStream(inputStream);
                    }
                }
            }

            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(1024);
            byte[] buffer = new byte[2048];
            int len;

            while (true) {
                len = inputStream.read(buffer);
                if (len < 0) {
                    break;
                }
                byteArrayBuffer.append(buffer, 0, len);
            }

            httpResult.result = new String(byteArrayBuffer.toByteArray());
            //打印服务器返回的字符串
            LogUtils.I("Sinya", "HttpGet Result:\n" + httpResult.result);

            return httpResult;

        } catch (Exception e) {
            LogUtils.E("Sinya", e.toString());
        } finally {
            apacheHttpClient.getConnectionManager().shutdown();
        }

        return httpResult;
    }

    /**
     * @param urlStr      请求服务器的URL
     * @param postContent 需要通过post提交请求的String数据
     * @return 返回MyHttpResult对象 内部封装了服务器返回的字符串 和 请求状态码
     */
    public static MyHttpResult httpPost(String urlStr, String postContent) {
        MyHttpResult myHttpResult = new MyHttpResult();
        HttpClient apacheHttpClient = gethttpClient();

        LogUtils.I("Sinya", "HttpPost URL:\n" + urlStr);
        HttpPost apacheHttpPost = new HttpPost(urlStr);

        try {
            if (postContent != null) {
                LogUtils.I("Sinya", "HttpPost Content: " + postContent);
            }
            StringEntity stringEntity = new StringEntity(postContent, "UTF-8");
            stringEntity.setContentType("application/json");
            apacheHttpPost.setEntity(stringEntity);

            //发送POST请求
            HttpResponse apacheResponse = apacheHttpClient.execute(apacheHttpPost);
            //获取响应码
            myHttpResult.statusCode = apacheResponse.getStatusLine().getStatusCode();
            LogUtils.I("Sinya", "HttpPost StatusCode: " + myHttpResult.statusCode);
            //获取响应实体
            HttpEntity apacheEntity = apacheResponse.getEntity();

            InputStream inputStream = apacheEntity.getContent();

            //提取响应实体中的数据头
            Header[] headers = apacheResponse.getAllHeaders();
            if (headers != null && headers.length > 0) {
                int i = 0;
                for (Header header : headers) {
                    if (header.getValue().equals("gzip")) {
                        LogUtils.I("Sinya", "HttpPost Header Value[" + i + "] = " + header.getValue());
                        i++;
                        inputStream = new GZIPInputStream(inputStream);
                    }
                }
            }

            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(2048);
            byte[] buffer = new byte[2048];
            int len;

            while (true) {
                len = inputStream.read(buffer);
                if (len < 0) {
                    break;
                }
                byteArrayBuffer.append(buffer, 0, len);
            }

            //从字节流中把响应的字符串数据提取出来
            myHttpResult.result = new String(byteArrayBuffer.toByteArray());
            LogUtils.I("Sinya", "HttpPost Result:\n" + myHttpResult.result);
            return myHttpResult;

        } catch (Exception e) {
            LogUtils.E("Sinya", e.toString());
        } finally {
            apacheHttpClient.getConnectionManager().shutdown();
        }
        return myHttpResult;
    }

    /**
     * @param urlStr      请求服务器的URL
     * @param postContent byte[] 类型的 需要Post提交的数据
     * @return MyHttpResult对象
     */
    public static MyHttpResult httpPost(String urlStr, byte[] postContent) {
        MyHttpResult myHttpResult = new MyHttpResult();
        HttpClient apacheHttpClient = gethttpClient();
        HttpPost apacheHttpPost = new HttpPost(urlStr);
        LogUtils.I("Sinya", "HttpPost URL:\n" + urlStr);

        try {
            if (postContent != null) {
                LogUtils.I("Sinya", "HttpPost Content: " + postContent.toString());
            }

            ByteArrayEntity byteArrayEntity = new ByteArrayEntity(postContent);
            byteArrayEntity.setContentType("application/json");

            //如果需要提交额外定义的Header信息，通过addHeader() 方法以键值对的形式加入
            //apacheHttpPost.addHeader("key", "value");
            apacheHttpPost.setEntity(byteArrayEntity);

            //发送POST请求
            HttpResponse apacheResponse = apacheHttpClient.execute(apacheHttpPost);

            //获取响应码
            myHttpResult.statusCode = apacheResponse.getStatusLine().getStatusCode();
            LogUtils.I("Sinya", "HttpPost StatusCode: " + myHttpResult.statusCode);

            //获取响应实体
            HttpEntity apacheEntity = apacheResponse.getEntity();

            InputStream inputStream = apacheEntity.getContent();

            //提取响应实体中的数据头
            Header[] headers = apacheResponse.getAllHeaders();
            if (headers != null && headers.length > 0) {
                int i = 0;
                for (Header header : headers) {
                    if (header.getValue().equals("gzip")) {
                        LogUtils.I("Sinya", "HttpPost Header Value[" + i + "] = " + header.getValue());
                        inputStream = new GZIPInputStream(inputStream);
                        i++;
                    }
                }
            }

            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(2048);
            byte[] buffer = new byte[2048];
            int len;

            while (true) {
                len = inputStream.read(buffer);
                if (len < 0) {
                    break;
                }
                byteArrayBuffer.append(buffer, 0, len);
            }

            //从字节流中把响应的字符串数据提取出来
            myHttpResult.result = new String(byteArrayBuffer.toByteArray());
            LogUtils.I("Sinya", "HttpPost Result:\n" + myHttpResult.result);
            return myHttpResult;

        } catch (Exception e) {
            LogUtils.E("Sinya", e.toString());
        } finally {
            apacheHttpClient.getConnectionManager().shutdown();
        }
        return myHttpResult;
    }

    public static boolean httpDown(String urlStr, String filePath) {
        HttpClient apacheHttpClient = gethttpClient();

        LogUtils.I("Sinya", "HttpDown URL:\n" + urlStr);
        HttpGet apacheHttpGet = new HttpGet(urlStr);

        File file = null;
        try {
            file = new File(filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            HttpResponse apacheHttpResponse = apacheHttpClient.execute(apacheHttpGet);

            int statusCode = apacheHttpResponse.getStatusLine().getStatusCode();
            LogUtils.I("Sinya", "HttpPost StatusCode: " + statusCode);

            HttpEntity apacheEntity = apacheHttpResponse.getEntity();

            InputStream inputStream = apacheEntity.getContent();

            byte[] buffer = new byte[10240];
            if (statusCode == 200) {
                while (true) {
                    int len = inputStream.read(buffer);
                    if (len < 0) {
                        break;
                    }
                    fileOutputStream.write(buffer, 0, len);
                }

                fileOutputStream.flush();
                fileOutputStream.close();
                return true;
            }

        } catch (Exception e) {
            LogUtils.E("Sinya", e.toString());
            //出现下载错误时很可能出现的错误文件，删除处理
            if (file != null) {
                file.delete();
            }
        } finally {
            apacheHttpClient.getConnectionManager().shutdown();
        }
        return false;
    }

    public static MyHttpResult httpUpLoad(String urlStr, String filePath) {
        MyHttpResult myHttpResult = new MyHttpResult();
        HttpClient apacheHttpClient = gethttpClient();

        LogUtils.I("Sinya", "HttpPost URL:\n" + urlStr);
        HttpPost apacheHttpPost = new HttpPost(urlStr);

        try {
            File file = new File(filePath);
            if (file.exists() == false || file.length() == 0) {
                return null;
            }

            FileEntity fileEntity = new FileEntity(file, "binary/octet-stream");

            //如果需要提交额外定义的Header信息，通过addHeader() 方法以键值对的形式加入
            //apacheHttpPost.addHeader("key", "value");
            apacheHttpPost.setEntity(fileEntity);

            HttpResponse apacheResponse = apacheHttpClient.execute(apacheHttpPost);

            myHttpResult.statusCode = apacheResponse.getStatusLine().getStatusCode();
            LogUtils.I("Sinya", "HttpPost StatusCode: " + myHttpResult.statusCode);

            HttpEntity apacheEntity = apacheResponse.getEntity();

            InputStream inputStream = apacheEntity.getContent();
            ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer(2048);
            byte[] buffer = new byte[2048];
            int len;

            while (true) {
                len = inputStream.read(buffer);
                if (len < 0) {
                    break;
                }
                byteArrayBuffer.append(buffer, 0, len);
            }
            myHttpResult.result = new String(byteArrayBuffer.toByteArray());
            LogUtils.I("Sinya", "HttpPost Result:\n" + myHttpResult.result);
            return myHttpResult;
        } catch (Exception e) {
            LogUtils.E("Sinya", e.toString());
        } finally {
            apacheHttpClient.getConnectionManager().shutdown();
        }
        return myHttpResult;
    }

    public static class MyHttpResult {
        public String result;
        public int statusCode;
    }

}

