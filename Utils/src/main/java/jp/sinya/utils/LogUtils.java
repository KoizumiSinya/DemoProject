package jp.sinya.utils;

import android.text.TextUtils;

/**
 * @Author: Sinya
 * @Editor：
 * @Date: 2015年5月20日 下午11:24:31
 * @Description: Log工具类
 */
public class LogUtils {

    private static boolean DEBUG = true;

    private static final int TYPE_D = 0;
    private static final int TYPE_I = 1;
    private static final int TYPE_E = 2;
    private static final int TYPE_W = 3;

    private static final int SINGLE_LENGTH = 3000;

    public static void init(boolean debug) {
        DEBUG = debug;
    }

    public static void setDebug(boolean isOpen) {
        DEBUG = isOpen;
    }

    /**
     * @param tag 标签
     * @param msg 消息
     * @Description: 打印info级别的日志
     */
    public static void D(String tag, String msg) {
        if (DEBUG) {

            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String className = stackTrace.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);

            String content = head + msg;

            if (tag == null || "".equals(tag.trim())) {
                tag = className;
            }

            if (!TextUtils.isEmpty(content)) {
                showLogCompletion(tag, TYPE_D, content, SINGLE_LENGTH);
            }
        }
    }

    /**
     * @param tag 标签
     * @param msg 消息
     * @Description: 打印info级别的日志
     */
    public static void I(String tag, String msg) {
        if (DEBUG) {

            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String content = head + msg;

            if (tag == null || "".equals(tag.trim())) {
                String className = stackTrace.getClassName();
                className = className.substring(className.lastIndexOf(".") + 1);
                tag = className;
            }

            if (!TextUtils.isEmpty(content)) {
                showLogCompletion(tag, TYPE_I, content, SINGLE_LENGTH);
            }
        }
    }

    public static void Sinya(String string, Object... object) {
        if (DEBUG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String content = head + string + ((object != null && object.length >= 1 && object[0] != null)//
                    ? object[0].toString()//
                    : "");

            if (!TextUtils.isEmpty(content)) {
                showLogCompletion("Sinya", TYPE_I, content, SINGLE_LENGTH);
            }
        }
    }

    public static void SinyaE(String string) {
        if (DEBUG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String content = head + string;

            if (!TextUtils.isEmpty(content)) {
                showLogCompletion("Sinya", TYPE_E, content, SINGLE_LENGTH);
            }
        }
    }

    public static void SAP(String string, Object... object) {
        if (DEBUG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String content = head + string + ((object != null && object.length >= 1 && object[0] != null)//
                    ? object[0].toString()//
                    : "");

            if (!TextUtils.isEmpty(content)) {
                showLogCompletion("SAP", TYPE_I, content, SINGLE_LENGTH);
            }
        }
    }

    public static void SAPE(String string) {
        if (DEBUG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String content = head + string;

            if (!TextUtils.isEmpty(content)) {
                showLogCompletion("SAP", TYPE_E, content, SINGLE_LENGTH);
            }
        }
    }

    /**
     * @param tag 标签
     * @param msg 消息
     * @Description: 打印error级别的日志
     */
    public static void E(String tag, String msg) {
        if (DEBUG) {
            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String className = stackTrace.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);

            String content = head + msg;

            if (tag == null || "".equals(tag.trim())) {
                tag = className;
            }

            if (!TextUtils.isEmpty(content)) {
                showLogCompletion(tag, TYPE_E, content, SINGLE_LENGTH);
            }
        }
    }

    /**
     * @param tag
     * @param msg
     * @Description: 打印warning级别的日志
     */
    public static void W(String tag, String msg) {
        if (DEBUG) {

            StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
            String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
            String className = stackTrace.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);

            String content = head + msg;

            if (tag == null || "".equals(tag.trim())) {
                tag = className;
            }

            if (!TextUtils.isEmpty(content)) {
                showLogCompletion(tag, TYPE_W, content, SINGLE_LENGTH);
            }
        }
    }

    /**
     * @return
     * @Description: 获取日志头信息，包含打印日志的类全路径名，行号，方法名称。
     */
    public static String getLogAddress() {
        StackTraceElement stackTrace = new Throwable().getStackTrace()[1];
        String head = stackTrace.getClassName() + "." + stackTrace.getMethodName() + "()" + ";第" + stackTrace.getLineNumber() + "行:\n";
        return head;
    }

    private static void showLogCompletion(String tag, int type, String content, int showCount) {
        if (showCount != 0 && content.length() > showCount) {
            String show = content.substring(0, showCount);

            LogPrint(tag, type, show);

            if ((content.length() - showCount) > showCount) {//剩下的文本还是大于规定长度
                String partLog = content.substring(showCount, content.length());
                showLogCompletion(tag, type, partLog, showCount);
            } else {
                String surplusLog = content.substring(showCount, content.length());
                LogPrint(tag, type, surplusLog);
            }

        } else {
            LogPrint(tag, type, content);
        }
    }

    private static void LogPrint(String tag, int type, String content) {
        switch (type) {
            case TYPE_D:
                android.util.Log.d(tag, content);
                break;
            case TYPE_I:
                android.util.Log.i(tag, content);
                break;
            case TYPE_E:
                android.util.Log.e(tag, content);
                break;
            case TYPE_W:
                android.util.Log.w(tag, content);
                break;
            default:
                break;
        }

    }
}