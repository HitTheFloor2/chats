package com.hitty.echo;


import com.alibaba.fastjson.JSONArray;
import com.hitty.MPacket;
import com.hitty.io.Shell;
import com.hitty.status.Status;
import com.hitty.util.Constants;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.Constant;

import java.net.InetSocketAddress;

/*
* EchoServer使用UDP协议工作
* 用于发现局域网中同样启动chats的进程，将其记录在Status的fellow中
* 在于fellow聊天时若通过UDP，则也使用EchoServer
*
*
* */
public class EchoServer implements Runnable{
    private int port;
    public Channel ch;

    public EchoServerHandler echoServerHandler;
    public InetSocketAddress castingInetSocketAddress;
    public EchoServer(int port) {
        this.echoServerHandler = new EchoServerHandler();
        this.port = port;
        castingInetSocketAddress = new InetSocketAddress("255.255.255.255",port);
    }

    public void write2Casting(String content){
        MPacket mPacket = new MPacket(Status.localSocketAddress,castingInetSocketAddress, Constants.casting,Status.localSocketAddress.getAddress().getHostAddress());
        Object object = JSONArray.toJSON(mPacket);
        String s = object.toString();
        //System.out.println(s);
        try {
            ch.writeAndFlush(
                    new DatagramPacket(Unpooled.copiedBuffer(s,CharsetUtil.UTF_8), castingInetSocketAddress)).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void write2fellow(InetSocketAddress inetSocketAddress){
        MPacket mPacket = new MPacket(Status.localSocketAddress,inetSocketAddress, Constants.casting,"");
        Object object = JSONArray.toJSON(mPacket);
        String s = object.toString();
        //System.out.println(s);
        try {
            ch.writeAndFlush(
                    new DatagramPacket(Unpooled.copiedBuffer(s,CharsetUtil.UTF_8), inetSocketAddress)).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void run()  {
        try {
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            //广播使用的是UDP数据包，使用NioDatagramChannel来初始化
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)//支持广播
                    .handler(this.echoServerHandler);//ChineseProverbServerHandler是业务处理类
            ch = b.bind(port).sync().channel();
            while(true){
                this.write2Casting("");
                this.write2fellow(new InetSocketAddress("192.168.0.255",8080));

                if(!ch.closeFuture().await(5000)){
                    //System.out.println("echo no response");
                }
                Thread.sleep(2000);
            }


        } catch (Exception e){
            e.printStackTrace();

        }
    }


}
