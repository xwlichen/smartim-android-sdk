package com.smart.im.android.sdk;

import com.smart.im.android.sdk.interf.IClientCore;
import com.smart.im.android.sdk.manager.ExecutorServiceFactory;
import com.smart.im.protocal.proto.ProtocalEntity;

/**
 * @date : 2019-06-18 16:03
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public abstract class ClientCoreWrapper implements IClientCore {

    public ExecutorServiceFactory loopGroup = null;// 线程池工厂


    /**
     * 获取线程池管理
     *
     * @return obj
     */
    public abstract ExecutorServiceFactory getLoopGroup();


    /**
     * 创建登陆信息
     * @return protocal
     */
    public abstract ProtocalEntity.Protocal createLoginMsg();

    /**
     * 创建心跳信息
     * @return protocal
     */
    public abstract ProtocalEntity.Protocal createKeepAliveMsg();
}
