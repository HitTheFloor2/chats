package com.hitty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

/**
 * Server that accept the path of a file an echo back its content.
 */
public final class FileServer {

    static final boolean SSL = System.getProperty("ssl") != null;
    // Use the same default port with the telnet example so that we can use the telnet client example to access it.
    static final int PORT = 8080;
    public void connect(){


    }
    public void run(){

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    //.handler(new LoggingHandler(LogLevel.ERROR))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
//                            p.addLast(new LineBasedFrameDecoder(1024));
//                            p.addLast(new StringDecoder());
                            p.addLast(new FileServerHandler());
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind("127.0.0.1",PORT).sync();
            Channel channel = f.channel();


            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        new FileServer().run();
        //new FileClient().connet("127.0.0.1",8080);
    }
}
