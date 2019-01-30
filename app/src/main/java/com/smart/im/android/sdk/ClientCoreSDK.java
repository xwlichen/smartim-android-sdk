package com.smart.im.android.sdk;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.smart.im.android.sdk.receiver.NetBroadcastReceiver;

/**
 * @date : 2019/1/23 上午10:55
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description : Android端sdk
 */
public class ClientCoreSDK {

    public static boolean DEBUG = true;
    private static ClientCoreSDK instance;


    private Context context;



    public static ClientCoreSDK getInstance() {
        if (instance == null) {
            instance = new ClientCoreSDK();
        }
        return instance;
    }


    public ClientCoreSDK init(Context context) {
        if (context==null){
            throw new IllegalArgumentException("context can't be null");
        }
        if (context instanceof Application){
            this.context=context;
        }else{
            this.context=context.getApplicationContext();
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.context.registerReceiver(new NetBroadcastReceiver(), intentFilter);


        return this;
    }


    public ClientCoreSDK isDebug(boolean debug) {
        DEBUG = debug;
        return this;
    }
}
