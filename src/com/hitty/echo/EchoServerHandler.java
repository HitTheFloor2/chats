package com.hitty.echo;

import com.alibaba.fastjson.JSONArray;
import com.hitty.MPacket;
import com.hitty.status.Status;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


public class EchoServerHandler  extends
        SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {

        //System.out.println(datagramPacket.content().toString(CharsetUtil.UTF_8));
        MPacket p = JSONArray.parseObject(datagramPacket.content().toString(CharsetUtil.UTF_8),MPacket.class);

        p.showInfo();
        if(p.content.equals("casting")){


        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
