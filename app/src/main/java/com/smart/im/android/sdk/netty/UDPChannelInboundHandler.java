package com.smart.im.android.sdk.netty;

import com.smart.im.android.sdk.core.ClientCoreHander;
import com.smart.im.android.sdk.utils.LogUtils;
import com.smart.im.protocal.proto.MessageProtocalEntity;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

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
        MessageProtocalEntity.Protocal protocal=(MessageProtocalEntity.Protocal)msg;
        clientCoreHandler.msgRecevied(protocal);
    }
}
