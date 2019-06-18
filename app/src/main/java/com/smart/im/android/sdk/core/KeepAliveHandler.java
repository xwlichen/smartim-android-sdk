package com.smart.im.android.sdk.core;

import com.smart.im.protocal.proto.ProtocalEntity;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @date : 2019-06-18 15:46
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class KeepAliveHandler extends ChannelInboundHandlerAdapter {

    private ClientCoreHander clientCoreHandler;

    public KeepAliveHandler(ClientCoreHander clientCoreHandler) {
        this.clientCoreHandler = clientCoreHandler;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            switch (state) {
                case READER_IDLE: {
                    // 规定时间内没收到服务端心跳包响应，进行重连操作
                    clientCoreHandler.getClientCoreWrapper().resetConnect(false);
                    break;
                }

                case WRITER_IDLE: {
                    // 规定时间内没向服务端发送心跳包，即发送一个心跳包
                    if (heartbeatTask == null) {
                        heartbeatTask = new HeartbeatTask(ctx);
                    }

                    clientCoreHandler.getClientCoreWrapper().getLoopGroup().execWorkTask(heartbeatTask);
                    break;
                }
            }
        }
    }

    private HeartbeatTask heartbeatTask;

    private class HeartbeatTask implements Runnable {

        private ChannelHandlerContext ctx;

        public HeartbeatTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if (ctx.channel().isActive()) {
                ProtocalEntity.Protocal msg = clientCoreHandler.getClientCoreWrapper().createKeepAliveMsg();
                if (msg == null) {
                    return;
                }
                System.out.println("发送心跳消息，message=" + msg + "当前心跳间隔为：" + clientCoreHandler.getClientCoreWrapper().createKeepAliveMsg() + "ms\n");
                clientCoreHandler.getClientCoreWrapper().sendMsg(msg, false);
            }
        }
    }
}