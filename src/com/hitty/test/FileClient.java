package com.hitty.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class FileClient {
    public void connet(String ip,int port){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline()
//                    .addLast(new LineBasedFrameDecoder(1024))
//                                            .addLast(new StringDecoder())
                                            .addLast(new FileClientHandler());
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(ip,port).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();

        }finally {
            eventLoopGroup.shutdownGracefully();
        }

    }
    public static void main(String[] args) {
        //new FileServer().run();
        new FileClient().connet("127.0.0.1",8080);
    }
}
