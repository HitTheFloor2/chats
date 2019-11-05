package com.hitty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 *
 * <p>Description: netty服务端  文件传输
 * <p>
 * @author shadow
 * @date 2016年8月7日
 */
public class FileServer implements Runnable{

    int port;
    Channel ch;
    public FileServer(int port){
        this.port = port;
    }
    public void write(String s){
        ch.writeAndFlush(s, (ChannelPromise) new InetSocketAddress("127.0.0.1",8080));
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());
            // 绑定端口 同步等待成功
            ChannelFuture f = null;
            try {
                ch = b.bind("127.0.0.1",port).sync().channel();
                write("23333333333");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }finally{
            //优雅的退出 释放线程池资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    static class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
            ch.pipeline().addLast(new FileServerHandler());
        }
    }

    public static void main(String[] args){
        new Thread(new FileServer(8080)).start();
    }

}