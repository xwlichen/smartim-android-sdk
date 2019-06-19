package com.smart.im.android.sdk.interf;

import com.smart.im.android.sdk.entity.ConfigEntity;
import com.smart.im.android.sdk.listener.OnConnectListener;
import com.smart.im.android.sdk.listener.OnEventListener;
import com.smart.im.android.sdk.listener.OnQosListener;
import com.smart.im.protocal.entity.ErrorResponse;
import com.smart.im.protocal.proto.MessageProtocalEntity;

/**
 * @date : 2019-06-11 14:31
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface IClientCore {


    /**
     * client 初始化
     *
     * @param config            配置信息
     * @param onEventListener   事件消息回调
     * @param onConnectListener 连接消息回调
     */
    void init(ConfigEntity config, OnEventListener onEventListener, OnConnectListener onConnectListener);


    /**
     * client 初始化
     *
     * @param config            配置信息
     * @param onEventListener   事件消息回调
     * @param onConnectListener 连接消息回调
     * @param onQosListener     心跳消息回调
     */
    void init(ConfigEntity config, OnEventListener onEventListener, OnConnectListener onConnectListener, OnQosListener onQosListener);


    /**
     * 重连
     */
    void reConnect();

    /**
     * 重连（具体实现）
     *
     * @param isFirst 是否是首次连接
     */
    void resetConnect(boolean isFirst);


    /**
     * 是否连接
     *
     * @return true-连接  false-未连接
     */
    boolean isConnect();

    /**
     * 设置netty的心跳handler配置
     */
    void configureKeepAliveHandler();


    /**
     * 发送消息
     *
     * @param protocal 自定义消息协议
     * @return code
     */
    int sendMsg(MessageProtocalEntity.Protocal protocal);


    /**
     * 发送消息
     *
     * @param protocal             自定义消息协议
     * @param isJoinTimeoutManager 是否加入发送超时管理器
     * @return code
     */
    int sendMsg(MessageProtocalEntity.Protocal protocal, boolean isJoinTimeoutManager);


    /**
     * 接受的信息
     *
     * @param protocal
     */
    void receivedMsg(MessageProtocalEntity.Protocal protocal);


    /**
     * 根据错误code 在应用层进行相应处理
     *
     * @param error ERRORCODE
     */
    void onErrorHandle(ErrorResponse error);


    /**
     * 释放资源，关闭连接
     */
    void release();

}
