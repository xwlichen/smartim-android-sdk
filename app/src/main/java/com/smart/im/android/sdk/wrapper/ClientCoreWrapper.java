package com.smart.im.android.sdk.wrapper;

import com.smart.im.android.sdk.core.ClientCoreHander;
import com.smart.im.android.sdk.interf.IClientCore;
import com.smart.im.android.sdk.listener.OnConnectListener;
import com.smart.im.android.sdk.listener.OnEventListener;
import com.smart.im.android.sdk.listener.OnQosListener;
import com.smart.im.android.sdk.manager.ExecutorServiceFactory;
import com.smart.im.protocal.proto.MessageProtocalEntity;

import io.netty.channel.Channel;

/**
 * @date : 2019-06-18 16:03
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public abstract class ClientCoreWrapper implements IClientCore {

    protected ExecutorServiceFactory loopGroup = null;// 线程池工厂
    protected Channel channel; //通道
    protected ClientCoreHander clientCoreHander;


    protected OnEventListener onEventListener;
    protected OnConnectListener onConnectListener;
    protected OnQosListener onQosListener;


    public OnEventListener getOnEventListener() {
        return onEventListener;
    }

    public void setOnEventListener(OnEventListener onEventListener) {
        this.onEventListener = onEventListener;
    }

    public OnConnectListener getOnConnectListener() {
        return onConnectListener;
    }

    public void setOnConnectListener(OnConnectListener onConnectListener) {
        this.onConnectListener = onConnectListener;
    }

    public OnQosListener getOnQosListener() {
        return onQosListener;
    }

    public void setOnQosListener(OnQosListener onQosListener) {
        this.onQosListener = onQosListener;
    }


    /**
     * 获取线程池管理
     *
     * @return obj
     */
    public abstract ExecutorServiceFactory getLoopGroup();


    /**
     * 创建登陆信息
     *
     * @return protocal
     */
    public abstract MessageProtocalEntity.Protocal createLoginMsg();

    /**
     * 创建心跳信息
     *
     * @return protocal
     */
    public abstract MessageProtocalEntity.Protocal createKeepAliveMsg();


}
