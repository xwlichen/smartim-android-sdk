package com.smart.im.android.sdk;

/**
 * @date : 2019/1/23 上午10:55
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description : Android端sdk
 */
public class ClientCoreSDK {

    public static boolean DEBUG = true;
    private static ClientCoreSDK instance;


    public static ClientCoreSDK getInstance() {
        if (instance == null) {
            instance = new ClientCoreSDK();
        }
        return instance;
    }


    public static void setDebug(boolean debug) {
        ClientCoreSDK.DEBUG = debug;
    }

    public ClientCoreSDK isDebug(boolean debug) {
        DEBUG = debug;
        return this;
    }
}
