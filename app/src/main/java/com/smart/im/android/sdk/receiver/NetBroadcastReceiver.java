package com.smart.im.android.sdk.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.smart.im.android.sdk.ClientCoreSDK;
import com.smart.im.android.sdk.utils.LogUtils;

/**
 * @date : 2019/1/30 下午4:37
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

    private final static String TAG = NetBroadcastReceiver.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LogUtils.warn(TAG, "检测本地网络已连接上了！");
        } else {
            LogUtils.warn(TAG, "检测本地网络断开了！");

        }
    }
}
