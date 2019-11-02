package com.hitty.echo;

import com.alibaba.fastjson.JSONArray;
import com.hitty.MPacket;
import com.hitty.status.Status;
import com.hitty.util.Constants;
import com.sun.org.apache.bcel.internal.classfile.Constant;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.stomp.StompSubframeAggregator;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.stream.StreamSupport;


public class EchoServerHandler  extends
        SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        //channelRead()中尽量不进行耗时操作，转交给其他线程
        MPacket p = JSONArray.parseObject(datagramPacket.content().toString(CharsetUtil.UTF_8),MPacket.class);

        if(p.funOp.equals(Constants.casting) && p.content.length() > 0){
            if(!Status.localSocketAddress.getAddress().getHostAddress().equals(p.content)){
                Status.shell.reply("receive casting from "+p.content);
                Status.fellows.add(new InetSocketAddress(p.content,8080));
            }

        }
        if(p.funOp.equals(Constants.verbose)){
            Status.shell.reply("\nfrom "+datagramPacket.sender().getAddress().getHostAddress()+" : "+p.content);
        }

    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
