package com.hitty.test;

import com.alibaba.fastjson.JSON;
import com.hitty.MPacket;
import com.hitty.util.MUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.FileInputStream;
import java.nio.ByteBuffer;

public class FileClientHandler extends ChannelInboundHandlerAdapter {
    public FileClientHandler(){

    }
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext){
        System.out.println("active!");
        ByteBuf msg;
        byte[] req = "hello\n23333\n353sfsegs fgdfg\n".getBytes();
        msg = Unpooled.buffer(req.length);
        msg.writeBytes(req);
        channelHandlerContext.writeAndFlush(msg);
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("channelRead!");
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        MPacket mPacket = JSON.parseObject(req,MPacket.class);
        byte[] data = mPacket.content.getBytes();
        MUtil.byte2File(data,"D:\\testfile","test2.txt");


    }
}
