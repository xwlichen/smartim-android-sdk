package com.smart.im.android.sdk.core;

import android.util.Log;

import com.smart.im.android.sdk.utils.LogUtils;
import com.smart.im.protocal.proto.ProtocalEntity;

import java.util.logging.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @date : 2019-06-17 17:30
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class UDPChannelInboundHandler extends ChannelInboundHandlerAdapter
{
    private static final String  TAG = UDPChannelInboundHandler.class.getSimpleName();

    private ClientCoreHander clientCoreHandler = null;

    public UDPChannelInboundHandler(ClientCoreHander clientCoreHandler)
    {
        this.clientCoreHandler = clientCoreHandler;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
        try{
            clientCoreHandler.exceptionCaught(ctx, e);
        }catch (Exception e2){
            LogUtils.e(TAG,e.getMessage().toString());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        clientCoreHandler.sessionCreated(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        clientCoreHandler.sessionClosed(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx,  Object msg) throws Exception {
        ProtocalEntity.Protocal protocal=(ProtocalEntity.Protocal)msg;
        clientCoreHandler.msgRecevied(protocal);
    }
}
