package com.smart.im.android.sdk.entity;

/**
 * @date : 2018/11/29 下午3:55
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :配置实体类
 */
public class SIMConfig {
    public static String APP_KEY = null;
    public static String SEVER_IP = "";
    public static int SERVER_PORT;
    public static int LOCAL_PORT;

    //默认重连一个周期失败间隔时长
    public static final int DEFAULT_RECONNECT_INTERVAL = 3 * 1000;
    //默认心时间跳间隔
    public static int DEFAULT_KEEPALIVE_INTERVAL = 0;
    //默认连接超时
    public static int DEFAULT_CONNECT_TIMEOUT = 0;

    public static void setSenseMode(SenseMode mode) {
        switch (mode) {
            case MODE_3S: {
                DEFAULT_KEEPALIVE_INTERVAL = 3000;// 3s
                DEFAULT_CONNECT_TIMEOUT = 3000 * 3 + 1000;// 10s
                break;
            }
            case MODE_10S:
                DEFAULT_KEEPALIVE_INTERVAL = 10000;// 10s
                DEFAULT_CONNECT_TIMEOUT = 10000 * 2 + 1000;// 21s
                break;
            case MODE_30S:
                DEFAULT_KEEPALIVE_INTERVAL = 30000;// 30s
                DEFAULT_CONNECT_TIMEOUT = 30000 * 2 + 1000;// 61s
                break;
            case MODE_60S:
                DEFAULT_KEEPALIVE_INTERVAL = 60000;// 60s
                DEFAULT_CONNECT_TIMEOUT = 60000 * 2 + 1000;// 121s
                break;
            case MODE_120S:
                DEFAULT_KEEPALIVE_INTERVAL = 120000;// 120s
                DEFAULT_CONNECT_TIMEOUT = 120000 * 2 + 1000;// 241s
                break;
            default:
                break;
        }

        if (DEFAULT_KEEPALIVE_INTERVAL > 0) {
//            KeepAliveDaemon.DEFAULT_KEEPALIVE_INTERVAL = DEFAULT_KEEPALIVE_INTERVAL;
        }
        if (DEFAULT_CONNECT_TIMEOUT > 0) {
//            KeepAliveDaemon.NETWORK_CONNECTION_TIME_OUT = DEFAULT_CONNECT_TIMEOUT;
        }
    }

    public enum SenseMode {
        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为3秒；<br>
         * * 10秒后未收到服务端心跳反馈即认为连接已断开（相当于连续3 个心跳间隔后仍未收到服务端反馈）。
         */
        MODE_3S,

        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为10秒；<br>
         * * 21秒后未收到服务端心跳反馈即认为连接已断开（相当于连续2 个心跳间隔后仍未收到服务端反馈）。
         */
        MODE_10S,

        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为30秒；<br>
         * * 61秒后未收到服务端心跳反馈即认为连接已断开（相当于连续2 个心跳间隔后仍未收到服务端反馈）。
         */
        MODE_30S,

        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为60秒；<br>
         * * 121秒后未收到服务端心跳反馈即认为连接已断开（相当于连续2 个心跳间隔后仍未收到服务端反馈）。
         */
        MODE_60S,

        /**
         * 此模式下：<br>
         * * KeepAlive心跳问隔为120秒；<br>
         * * 241秒后未收到服务端心跳反馈即认为连接已断开（相当于连续2 个心跳间隔后仍未收到服务端反馈）。
         */
        MODE_120S
    }
}
