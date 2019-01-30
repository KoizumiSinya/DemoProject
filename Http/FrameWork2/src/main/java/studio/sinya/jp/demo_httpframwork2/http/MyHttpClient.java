package studio.sinya.jp.demo_httpframwork2.http;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import studio.sinya.jp.demo_httpframwork2.MyApplication;
import studio.sinya.jp.demo_httpframwork2.R;
import studio.sinya.jp.demo_httpframwork2.http.MyHttpControl.MyHttpResult;
import studio.sinya.jp.demo_httpframwork2.utils.LogUtils;

/**
 * Created by SinyaKoizumi on 2015/9/21.
 */
public class MyHttpClient {

    public static void request(final Handler handler, final int what, final MyHttpConfig httpConfig, String... param) {

        StringBuilder httpURLBuilder = new StringBuilder();

        //是否是完整的URL
        if (httpConfig.isComplete) {

            httpURLBuilder.append(httpConfig.api);

        } else {
        /*
         * 根据 isComplete 的布尔值判断，如果httpConfig的api地址不是完整的URL路径
         *
         *  ①在这里可以通过StringBuilder  自定义提取http地址的前半部分
         * 例如
         * UserConfig user = UserConfig.getInstance();
         *
         * ②在这里可以通过httpSb增加用户的sessionKey,继续拼接http地址前半部分
         *    例如：
         *    httpSb = new StringBuilder(user.getAddress + "/" + user.getID);
         *
         *      if(user.hasSessionKey){
         *            httpSb.append("/" + user.sesionKey);
         *      }
         */
        }

        int i = 0;
        String url;
        String content = null;

        //③根据请求方式的不同，拆解可变参数param，定义URL后面跟随的参数
        switch (httpConfig.RequestMethod) {

            case MyHttpConfig.GET:

                //④解析param可变参数(URL地址后面是否跟随的请求参数)，并通过StringBuider拼接（如果没有，这一步循环会跳过）
                for (; i < param.length; i++) {
                    httpURLBuilder.append('/');
                    String params = param[i];

                    try {
                        //⑤通过URLEncoder 对这个params数据进行UTF-8编码后 再次赋值
                        params = URLEncoder.encode(param[i], "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        //如果转码失败 也要将其按照原有的未知格式赋值
                        params = param[i];
                        e.printStackTrace();
                    }

                    //拼接在URL后面
                    httpURLBuilder.append(param);
                }

                //⑦根据需要从param中 拿到特定的元素 打印观察 以便追踪
                if (param.length > 0) {
                    //content = param[?];
                    //LogUtils.I("Sinya", content);
                }
                break;

            case MyHttpConfig.POST:

                break;

            case MyHttpConfig.DELETE:
                break;

            case MyHttpConfig.PUL:
                break;

            default:
                break;
        }

        if (httpConfig.hasToken) {
            // 生成Token值并拼接到Url后面
            String token = ""; // createToken(); //至于如何计算token值，就根据需求写createToken()方法即可
            if (!TextUtils.isEmpty(token)) {
                httpURLBuilder.append("/").append(token);
            }
        }

        //至此，得到最终完整的url请求地址（例如：http://主体服务器地址 + user信息 / API支点 /各种请求附加参数/ 可变参数params / token 等）
        //如果不需要拼接，则一开始的api就是完整的请求地址
        url = httpURLBuilder.toString();

        // 打印不为空的params的数据
        if (content != null) {
            LogUtils.I("Sinya", "content: " + content);
        }

        //根据URL 和 content 开启请求线程
        new MyHttpClient().new MyHttpThread(url, content) {

            @Override
            public void run() {

                MyHttpResult myHttpResult = new MyHttpResult();

                switch (httpConfig.RequestMethod) {

                    case MyHttpConfig.GET:
                        myHttpResult = MyHttpControl.httpGet(mUrl);
                        break;

                    case MyHttpConfig.POST:
                        break;

                    case MyHttpConfig.DELETE:
                        break;

                    case MyHttpConfig.PUL:
                        break;

                    default:
                        break;
                }

                parseData(myHttpResult, handler, what, httpConfig.parseClass, httpConfig.isDecrypt);

            }
        }.start();

    }

    /**
     * 解析MyHttpControl请求服务器之后返回的String数据
     *
     * @param myHttpResult MyHttpResult 对象
     * @param handler      Handler
     * @param what         Handler what
     * @param clazz        用来接收解析数据的基类class
     * @param isDecrypt    是否需要解密
     */
    private static void parseData(MyHttpResult myHttpResult, Handler handler, int what, Class<?> clazz, boolean isDecrypt) {

        Message msg = new Message();
        msg.what = what;

        //服务器返回的数据为空
        if (myHttpResult.result == null) {
            msg.obj = null;
            if (handler != null) {
                handler.sendMessage(msg);
                MyHttpControl.mException = "nullpointer";
                showNetError();
            }

            //服务器响应码不为200 请求失败
        } else if (myHttpResult.statusCode != 200) {

            msg.obj = null;
            if (handler != null) {
                handler.sendMessage(msg);
                MyHttpControl.mException = "response";
                showNetError();
            }


        } else if (myHttpResult.statusCode == 200) {

            //返回的数据需要解密
            if (isDecrypt) {

            }

            //有封装用来解析数据的class基类
            if (clazz != null) {

                Gson gson = new Gson();
                try {
                    //把Gson解析的结果绑在message上，由传递进来的handler发送出去
                    msg.obj = gson.fromJson(myHttpResult.result, clazz);
                    msg.arg1 = myHttpResult.statusCode;
                } catch (Exception e) {
                    LogUtils.I("Sinya", e.toString());
                }

                if (handler != null) {
                    handler.sendMessage(msg);
                }

            } else {
                msg.obj = myHttpResult.result;
                msg.arg1 = myHttpResult.statusCode;

                if (handler != null) {
                    handler.sendMessage(msg);
                }
            }
        }
    }

    /**
     * 用来匹配错误信息，然后通过showErrorMessage方法弹出错误消息在界面上
     * 提供显示的错误信息：
     * 联网超时、服务器无数据返回、服务器拒绝请求、服务器应答错误。
     */
    private static void showNetError() {
        int res_id = 0;

        String exception = MyHttpControl.mException.toLowerCase();

        if (exception.contains("timeout")) {
            res_id = R.string.neterr_timeout;
        } else if (exception.contains("nullpointer")) {
            res_id = R.string.neterr_nullpt;
        } else if (exception.contains("refused")) {
            res_id = R.string.neterr_refused;
        } else if (exception.contains("response")) {
            res_id = R.string.neterr_response;
        } else {
            res_id = R.string.neterr_error;
            LogUtils.I("Sinya", MyHttpControl.mException);
        }

        showErrorMessage(res_id);
    }

    /**
     * 发送封装好的错误
     *
     * @param resId
     */
    private static void showErrorMessage(int resId) {
        Message msg = new Message();
        msg.obj = MyApplication.mContext.getResources().getString(resId);

        //在这里可以判定什么样的错误情况下 加入message.arg1 = -1
        if (false) {
            msg.arg1 = -1;
        }

        MyHttpClient.mHandler.sendMessage(msg);
    }

    /**
     * MyHttpClient封装的handler。
     * 封装Toast： 弹出错误日志信息
     */
    public static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            String message = (String) msg.obj;

            if (!TextUtils.isEmpty(message) && !"没有查询到数据".equals(message)) {
                Toast.makeText(MyApplication.mContext, (String) msg.obj, Toast.LENGTH_SHORT).show();
            }

            if (msg.arg1 == -1) {
                try {
                    //在这里可以处理某些特定意义的error，比如:是否需要强制用户退出登录 还是结束整个程序
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };


    /**
     * 执行请求网络的线程
     */
    public class MyHttpThread extends Thread {
        protected String mUrl;
        protected String mContent;

        /**
         * 自定义MyThread类，继承Thread。
         *
         * @param url     url地址
         * @param content 数据字符串
         */
        public MyHttpThread(String url, String content) {
            mUrl = url;
            mContent = content;
        }
    }

}
