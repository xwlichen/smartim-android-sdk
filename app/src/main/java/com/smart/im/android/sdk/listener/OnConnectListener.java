package com.smart.im.android.sdk.listener;

/**
 * @date : 2019-06-11 14:55
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface OnConnectListener {


    /**
     * 连接状态（服务器连接、登陆验证等）
     * @param code 以ProtocalFactory 的code为标准
     */
    void onConnectStatus(int code);



}
