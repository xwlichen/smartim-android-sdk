package com.smart.im.android.sdk.core;

import com.smart.im.android.sdk.ClientCoreWrapper;
import com.smart.im.android.sdk.utils.LogUtils;
import com.smart.im.protocal.proto.MessageProtocalEntity;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * @date : 2019-06-17 17:29
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class ClientCoreHander {

    public static final String TAG = ClientCoreHander.class.getSimpleName();


    private ClientCoreWrapper clientCoreWrapper;


    public ClientCoreHander(ClientCoreWrapper clientCoreWrapper) {
        this.clientCoreWrapper = clientCoreWrapper;
    }


    public ClientCoreWrapper getClientCoreWrapper() {
        return clientCoreWrapper;
    }


    public void msgRecevied(MessageProtocalEntity.Protocal protocal){
        if (protocal==null){
            return;
        }


    }

    public void sessionCreated(Channel session) throws Exception {
        LogUtils.d(TAG, session.remoteAddress() + "的会话已建立");
    }

    public void sessionClosed(ChannelHandlerContext ctx) throws Exception {
        LogUtils.e(TAG, "会话已关闭了");
        if (ctx != null) {
            Channel channel = ctx.channel();
            if (channel != null) {
                channel.close();
            }
            ctx.close();
        }
        clientCoreWrapper.resetConnect(false);

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LogUtils.e(TAG, "此客户端的Channel抛出了exceptionCaught，原因是：" + cause.getMessage() + "，可以提前close掉了哦！");
        if (ctx != null) {
            Channel channel = ctx.channel();
            if (channel != null) {
                channel.close();
            }
            ctx.close();
        }

        clientCoreWrapper.resetConnect(false);

    }

}
