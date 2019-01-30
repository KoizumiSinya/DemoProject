package studio.sinya.jp.demo_httpframwork2.http;

import studio.sinya.jp.demo_httpframwork2.bean.Person;
import studio.sinya.jp.demo_httpframwork2.bean.PersonRsult;

/**
 * 用来配制所有的请求信息，包括：
 * 请求http的分支点、params属性、请求方式、class基类等
 * Created by SinyaKoizumi on 2015/9/21.
 */
public class MyHttpConfig {

    /**
     * api分支节点
     */
    public String api;

    /**
     * api是否是一个完整的请求地址（不需要拼接param）
     */
    public boolean isComplete;

    /**
     * http请求方式
     */
    public int RequestMethod;

    /**
     * 是否需要SessionKey
     */
    public boolean hasSessionKey;

    /**
     * 是否需要token值
     */
    public boolean hasToken;

    /**
     * 是否需要压缩
     */
    public boolean isCompress;

    /**
     * 提交的数据是否需要加密
     */
    public boolean isEncrypt;

    /**
     * 返回的数据是否需要加密
     */
    public boolean isDecrypt;

    /**
     * 包含基类Class
     */
    public Class<?> parseClass;

    public static final int GET = 1;
    public static final int POST = 2;
    public static final int DELETE = 3;
    public static final int PUL = 4;

    /**
     * @param api           URL(必须是完整的请求路径)
     * @param requestMethod 请求方式
     */
    public MyHttpConfig(String api, int requestMethod) {
        this.isComplete = true;
        this.api = api;
        this.RequestMethod = requestMethod;
    }

    /**
     * @param api           URL(必须是完整的请求路径，并非分支点)
     * @param requestMethod 请求方式
     * @param parseClass    接收解析数据的积累 class
     */
    public MyHttpConfig(String api, int requestMethod, Class<?> parseClass) {
        this.isComplete = true;
        this.api = api;
        this.RequestMethod = requestMethod;
        this.parseClass = parseClass;
    }

    /**
     * @param api           URL的连接（可以是完整的地址，也可以是分支点）
     * @param requestMethod 请求方式
     * @param isComplete    api是否是完整的地址
     * @param hasSessionKey 是否包含sessionKey
     * @param isCompress    是否需要压缩
     * @param parseClass    接收解析数据的基类class
     */
    public MyHttpConfig(String api, int requestMethod, boolean isComplete, boolean hasSessionKey, boolean isCompress, Class<?> parseClass) {
        this.api = api;
        this.parseClass = parseClass;
        this.isComplete = isComplete;
        this.hasSessionKey = hasSessionKey;
        this.isCompress = isCompress;
        this.RequestMethod = requestMethod;
    }


    // [+] 以下是模拟封装好的各个请求地址API

    public static MyHttpConfig aJson = new MyHttpConfig("http://192.168.1.3:8080/QQJY/test/a.json", GET, true, false, false, Person.class);

    public static MyHttpConfig arrayJson = new MyHttpConfig("http://192.168.1.3:8080/QQJY/test/chat_list.json", GET, true, false, false, PersonRsult.class);

    public static MyHttpConfig baidu = new MyHttpConfig("http://www.baidu.com", GET, true, false, false, null);

    public static MyHttpConfig baidu2 = new MyHttpConfig("http://www.baidu.com", GET);

    public static MyHttpConfig personGet = new MyHttpConfig("http://192.168.1.3:8080/QQJY/test/a.json", GET, Person.class);

    // [-] 以上是模拟封装好的各个请求地址API

}
