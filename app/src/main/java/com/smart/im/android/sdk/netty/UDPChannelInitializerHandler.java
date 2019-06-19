package com.smart.im.android.sdk.netty;

import com.smart.im.android.sdk.core.ClientCoreHander;
import com.smart.im.protocal.proto.MessageProtocalEntity;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @date : 2019-06-18 14:34
 * @author: lichen
 * @email : 1960003945@qq.com
 * @description :
 */
public class UDPChannelInitializerHandler extends ChannelInitializer<Channel> {

    private ClientCoreHander clientCoreHandler;

    public UDPChannelInitializerHandler(ClientCoreHander clientCoreHandler) {
        this.clientCoreHandler = clientCoreHandler;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        // netty提供的自定义长度解码器，解决TCP拆包/粘包问题
//        pipeline.addLast("frameEncoder", new LengthFieldPrepender(2));
//        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535,
//                0, 2, 0, 2));

        // 增加protobuf编解码支持
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new ProtobufDecoder(MessageProtocalEntity.Protocal.getDefaultInstance()));

//        连接超时
//        pipeline.addLast(new ReadTimeoutHandler(ConfigEntity.SESION_RECYCLER_EXPIRE));

        // 接收消息处理handler
        pipeline.addLast(UDPChannelInboundHandler.class.getSimpleName(), new UDPChannelInboundHandler(clientCoreHandler));
    }
}
