package com.hitty.echo;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hitty.MPacket;
import com.hitty.MUtil;
import com.hitty.io.Shell;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.util.CharsetUtil;

import java.awt.image.PackedColorModel;
import java.net.InetSocketAddress;

public class EchoServer implements Runnable{
    private int port;
    public Channel ch;
    public Shell shell;
    public EchoServerHandler echoServerHandler;
    public EchoServer(int port) {
        this.echoServerHandler = new EchoServerHandler();
        this.port = port;
    }

    public void write2Casting(String content){
        MPacket mPacket = new MPacket("chat",content);
        Object object = JSONArray.toJSON(mPacket);
        String s = object.toString();
        System.out.println(s);
        //DatagramPacket datagramPacket = new DatagramPacket();
        try {
            ch.writeAndFlush(
                    new DatagramPacket(Unpooled.copiedBuffer(s,CharsetUtil.UTF_8),
                            new InetSocketAddress("255.255.255.255",port))).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void run()  {

        try {
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            //由于我们用的是UDP协议，所以要用NioDatagramChannel来创建
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)//支持广播
                    .handler(this.echoServerHandler);//ChineseProverbServerHandler是业务处理类
            ch = b.bind(port).sync().channel();
            while(true){
                MPacket mPacket = new MPacket("casting","");
                String s = MUtil.Object2JSONString(mPacket);
                //DatagramPacket datagramPacket = new DatagramPacket();
                ch.writeAndFlush(
                        new DatagramPacket(Unpooled.copiedBuffer(s,CharsetUtil.UTF_8),
                                new InetSocketAddress("255.255.255.255",port))).sync();
                //System.out.println("send echo to broadcasting");
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
