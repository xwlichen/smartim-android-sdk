package com.smart.im.android.sdk.core;

import android.content.Context;

/**
 * @date : 2018/11/29 下午3:59
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class UDPDataSender {
    private final static String TAG = UDPDataSender.class.getSimpleName();

    private static UDPDataSender instance;
    private Context context;

    public static UDPDataSender getInstance(){
        if (instance==null){
            instance=new UDPDataSender();
        }
        return instance;
    }

}
