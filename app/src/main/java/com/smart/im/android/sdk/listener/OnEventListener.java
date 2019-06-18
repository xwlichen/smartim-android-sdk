package com.smart.im.android.sdk.listener;

import com.smart.im.protocal.proto.MessageProtocalEntity;

/**
 * @date : 2019-06-11 14:55
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public interface OnEventListener {

    /**
     * 分发消息
     * @param protocal
     */
    void dispatchMsg(MessageProtocalEntity.Protocal protocal);


    /**
     * 检查是否有网络
     */
    boolean isNetworkAvailable();
}
