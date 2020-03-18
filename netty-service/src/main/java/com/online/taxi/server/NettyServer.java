package com.online.taxi.server;

import com.online.taxi.consts.Const;
import com.online.taxi.handler.DispatchHandler;
import com.online.taxi.proto.MessageProto;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Slf4j
public class NettyServer {

    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    private String host;
    private int port;
    @Autowired
    private DispatchHandler dispatchHandler;

    private NettyServer() {
    }

    private static final class LazyHodler {
        private static final NettyServer ins = new NettyServer();
    }

    public static final NettyServer ins() {
        return LazyHodler.ins;
    }

    public void init() {
        host = Const.SERVER_HOST;
        port = Const.SERVER_PORT;
        logger.info("server host:{} port:{}", host, port);
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
    }

    public void start() {
        init();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                    ch.pipeline().addLast(new ProtobufDecoder(MessageProto.RequestProto.getDefaultInstance()));
                    ch.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                    ch.pipeline().addLast(new ProtobufEncoder());
                    ch.pipeline().addLast(dispatchHandler);
                }
            });
            setOption(b);
            ChannelFuture f = b.bind(host, port).sync();
            logger.info("netty server start");
            // 等待socket关闭
            f.channel().closeFuture().sync();
            logger.info("netty server stop");
        } catch (Exception ex) {
            logger.error("start error:" + ex.getMessage(), ex);
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private void setOption(ServerBootstrap bootstrap) {
        bootstrap.option(ChannelOption.SO_BACKLOG, 2000).option(ChannelOption.SO_SNDBUF, 128 * 1024).option(ChannelOption.SO_RCVBUF, 128 * 1024).option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_REUSEADDR, true).childOption(ChannelOption.SO_KEEPALIVE, true);
    }

    @PreDestroy
    public void stop() {
        log.info("关闭服务器....");
        //优雅退出
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
