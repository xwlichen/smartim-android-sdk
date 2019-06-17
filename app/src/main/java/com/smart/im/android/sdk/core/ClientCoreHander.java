package com.smart.im.android.sdk.core;

import com.smart.im.android.sdk.utils.LogUtils;

import io.netty.channel.Channel;

/**
 * @date : 2019-06-17 17:29
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ClientCoreHander {

    public static final String TAG=ClientCoreHander.class.getSimpleName();





    public void exceptionCaught(Channel session, Throwable cause) throws Exception
    {
        LogUtils.e(TAG,"此客户端的Channel抛出了exceptionCaught，原因是："+cause.getMessage()+"，可以提前close掉了哦！");
        session.close();
    }

}
