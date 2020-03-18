package com.online.taxi.client;

import com.google.protobuf.Message;
import com.online.taxi.proto.MessageProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @date 2018/8/21
 */
public class ClientHandler extends SimpleChannelInboundHandler<Message> {

    /**
     *
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

        System.out.println("Server say : " + msg.toString());
    }

    /**
     *
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client active ");
        MessageProto.RequestProto msg = MessageProto.RequestProto.newBuilder().build();
        ctx.writeAndFlush(msg);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client close ");
        super.channelInactive(ctx);
    }

}
