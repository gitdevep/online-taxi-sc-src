package com.online.taxi.client;

import com.online.taxi.proto.MessageProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @date 2018/8/21
 */
@Slf4j
public class NettyClient {
    protected EventLoopGroup group;

    private String host;
    private int port;
    private Channel channel;

    public NettyClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void init() {
        group = new NioEventLoopGroup();
    }

    public void start() {
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                    ch.pipeline().addLast(new ProtobufDecoder(MessageProto.RequestProto.getDefaultInstance()));
                    ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                    ch.pipeline().addLast(new ProtobufEncoder());
                    ch.pipeline().addLast(new ClientHandler());
                }
            });
            setOption(b);
            channel = b.connect(new InetSocketAddress(host, port)).sync().channel();
        } catch (Exception ex) {
            log.error("start error:" + ex.getMessage(), ex);
        }

    }

    private void setOption(Bootstrap bootstrap) {
        bootstrap.option(ChannelOption.SO_SNDBUF, 128 * 1024).option(ChannelOption.SO_RCVBUF, 128 * 1024).option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true);
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public Channel channel() {
        return channel;
    }

    public static void main(String[] args) {
        NettyClient client = new NettyClient("127.0.0.1", 80);
        client.init();
        client.start();
        Channel channel = client.channel();
        /*for (int i = 0; i < 10; i++) {
            MessageProto.RequestProto.Builder b = MessageProto.RequestProto.newBuilder();
            b.setCode(1);
            b.setMessage("message " + i);
            channel.writeAndFlush(b.build());
        }*/
    }
}
