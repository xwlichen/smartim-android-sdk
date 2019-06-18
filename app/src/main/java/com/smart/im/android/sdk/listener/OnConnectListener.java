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
     * @param code  1-连接成功（ConfigEntity.CONNECT_STATE_SUCCESSFUL） 需要发送登陆相关信息
     */
    void onConnectStatus(int code);



}
