package com.smart.im.android.sdk.core;

import android.text.TextUtils;

import com.smart.im.android.sdk.entity.ConfigEntity;
import com.smart.im.android.sdk.utils.GsonUtils;
import com.smart.im.android.sdk.utils.LogUtils;
import com.smart.im.android.sdk.wrapper.ClientCoreWrapper;
import com.smart.im.protocal.entity.ErrorResponse;
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


    public void msgRecevied(MessageProtocalEntity.Protocal protocal) {
        dispathcMsg(protocal);


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


    private void dispathcMsg(MessageProtocalEntity.Protocal protocal) {
        if (protocal == null) {
            return;
        }
        switch (protocal.getHeader().getProtocalType()) {
            case CONNECT:
                break;
            case CONNACK:
                LogUtils.d(TAG, "登陆等相关验证信息验证成功");
                //添加netty心跳管理，发送心跳信息
                clientCoreWrapper.configureKeepAliveHandler();
                break;
            case PUBLISH:
                break;
            case PUBACK:
                break;
            case RECEIVE:
                break;
            case RECEACK:
                break;
            case PINGREQ:
                break;
            case PINGRESP:
                LogUtils.d("收到服务断心跳响应消息，message=" + protocal.getHeader().getId()
                        + "当前心跳间隔为：" + getClientCoreWrapper().createKeepAliveMsg() + "ms");
                break;
            case ERRORESP:
                handleError(protocal);
                break;
            case DISCONNET:
                if (clientCoreWrapper.getOnConnectListener() != null) {
                    clientCoreWrapper.getOnConnectListener().onConnectStatus(ConfigEntity.CONNECT_STATE_BROKEN);
                }
                break;
            case UNRECOGNIZED:
            default:
                break;
        }
    }


    public void handleError(MessageProtocalEntity.Protocal protocal) {
        if (protocal == null) {
            return;
        }
        if (TextUtils.isEmpty(protocal.getBody())) {
            return;
        }
        ErrorResponse errorResponse = GsonUtils.init().fromJsonObject(protocal.getBody(), ErrorResponse.class);
        if (errorResponse == null) {
            return;
        }

        clientCoreWrapper.onErrorHandle(errorResponse);
    }

}
