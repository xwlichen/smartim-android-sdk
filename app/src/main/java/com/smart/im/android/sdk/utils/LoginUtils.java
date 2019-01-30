package com.smart.im.android.sdk.utils;

import android.text.TextUtils;
import android.util.Log;

import com.smart.im.android.sdk.ClientCoreSDK;


/**
 * @date : 2019/1/30 下午1:58
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class LoginUtils {

    private final static String DEFAULT_TAG = "=====SMART-IM=====";
    private final static int MAX_LENGTH = 3500;
    private final static String LINE=" ==========  ";


    /**
     * info级别日志
     *
     * @param msg 相关内容
     */
    public static void info(String msg) {
        if (!ClientCoreSDK.DEBUG || TextUtils.isEmpty(msg)) {
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
    public static void info(String sourceName, String msg) {
        if (!ClientCoreSDK.DEBUG || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.i(DEFAULT_TAG, "【" + sourceName + "】"+LINE + printLong(msg));
    }


    /**
     * warn级别日志
     *
     * @param msg 相关内容
     */
    public static void warn(String msg) {
        if (!ClientCoreSDK.DEBUG || TextUtils.isEmpty(msg)) {
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
    public static void warn(String sourceName, String msg) {
        if (!ClientCoreSDK.DEBUG || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.w(DEFAULT_TAG, "【" + sourceName + "】"+LINE + printLong(msg));
    }


    /**
     * error级别日志
     *
     * @param msg 相关内容
     */
    public static void error(String msg) {
        if (!ClientCoreSDK.DEBUG || TextUtils.isEmpty(msg)) {
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
    public static void error(String sourceName, String msg) {
        if (!ClientCoreSDK.DEBUG || TextUtils.isEmpty(msg)) {
            return;
        }
        Log.e(DEFAULT_TAG, "【" + sourceName + "】" +LINE+ printLong(msg));
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
