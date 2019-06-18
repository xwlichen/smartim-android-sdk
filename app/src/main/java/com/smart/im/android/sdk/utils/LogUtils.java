package com.smart.im.android.sdk.utils;

import android.text.TextUtils;
import android.util.Log;

import com.smart.im.android.sdk.core.ClientSDK;


/**
 * @date : 2019/1/30 下午1:58
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LogUtils {

    private final static String DEFAULT_TAG = "=====SMART-IM=====";
    private final static int MAX_LENGTH = 3500;
    private final static String LINE=" ==========  ";


    /**
     * info级别日志
     *
     * @param msg 相关内容
     */
    public static void i(String msg) {
        if (!ClientSDK.DEBUG || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.i(DEFAULT_TAG, printLong(msg));
    }


    /**
     * info级别日志
     *
     * @param sourceName 来源的名称
     * @param msg        相关内容
     */
    public static void i(String sourceName, String msg) {
        if (!ClientSDK.DEBUG || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.i(sourceName, LINE + printLong(msg));
    }



    /**
     * debug级别日志
     *
     * @param msg 相关内容
     */
    public static void d(String msg) {
        if (!ClientSDK.DEBUG || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.d(DEFAULT_TAG, printLong(msg));
    }


    /**
     * debug级别日志
     *
     * @param sourceName 来源的名称
     * @param msg        相关内容
     */
    public static void d(String sourceName, String msg) {
        if (!ClientSDK.DEBUG || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.d(sourceName, LINE + printLong(msg));
    }




    /**
     * warn级别日志
     *
     * @param msg 相关内容
     */
    public static void w(String msg) {
        if (!ClientSDK.DEBUG || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.w(DEFAULT_TAG, printLong(msg));
    }


    /**
     * warn级别日志
     *
     * @param sourceName 来源的名称
     * @param msg        相关内容
     */
    public static void w(String sourceName, String msg) {
        if (!ClientSDK.DEBUG || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.w(sourceName, LINE + printLong(msg));
    }


    /**
     * error级别日志
     *
     * @param msg 相关内容
     */
    public static void e(String msg) {
        if (!ClientSDK.DEBUG || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.e(DEFAULT_TAG, printLong(msg));
    }


    /**
     * error级别日志
     *
     * @param sourceName 来源的名称
     * @param msg        相关内容
     */
    public static void e(String sourceName, String msg) {
        if (!ClientSDK.DEBUG || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.e(sourceName, LINE+ printLong(msg));
    }


    /**
     * 打印较长的日志
     *
     * @param msg 相关内容
     */
    private static String printLong(String msg) {

        msg = msg.trim();
        int index = 0;
        int maxLength = MAX_LENGTH;
        String sub = null;
        while (index < msg.length()) {
            if (msg.length() <= index + maxLength) {
                sub = msg.substring(index);
            } else {
                sub = msg.substring(index, index + maxLength);
            }

            index += maxLength;

        }
        if (sub == null) {
            sub = "";
        }
        return sub;

    }


}
