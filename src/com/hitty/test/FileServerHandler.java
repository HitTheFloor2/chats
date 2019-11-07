package com.hitty.test;


import com.alibaba.fastjson.JSON;
import com.hitty.MPacket;
import com.hitty.util.Constants;
import com.hitty.util.MUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;

import java.io.File;
import java.io.RandomAccessFile;

public class FileServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush("HELLO: Type the path of the file to retrieve.\n");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //System.out.println((String) msg);
        ByteBuf send;
        //byte[] req = "hello\n23333\n353sfsegs fgdfg\n".getBytes();
        byte[] data = MUtil.read(new File("D:\\test.txt"));
        String s = new String(data);
        MPacket mPacket = new MPacket(Constants.filelist_reply,s);

        mPacket.no = 0;
        byte[] req = JSON.toJSONBytes(mPacket);
        ByteBuf msg1 = Unpooled.buffer(req.length);
        System.out.println(req);
        msg1.writeBytes(req);
        ctx.writeAndFlush(msg);
        //System.out.println("transfer!");
//        ctx.write("OK: " + raf.length() + '\n');
//        if (ctx.pipeline().get(SslHandler.class) == null) {
//            // SSL not enabled - can use zero-copy file transfer.
//            ctx.write(new DefaultFileRegion(raf.getChannel(), 0, length));
//        } else {
//            // SSL enabled - cannot use zero-copy file transfer.
//            ctx.write(new ChunkedFile(raf));
//        }
//        ctx.writeAndFlush("\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();

        if (ctx.channel().isActive()) {
            ctx.writeAndFlush("ERR: " +
                    cause.getClass().getSimpleName() + ": " +
                    cause.getMessage() + '\n').addListener(ChannelFutureListener.CLOSE);
        }
    }
}