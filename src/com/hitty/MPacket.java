package com.hitty;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class MPacket {
    /*
    *
    *
    *
    * */
    public String funOp;
    public String content;
    public InetSocketAddress src,dst;
    public MPacket(InetSocketAddress src,InetSocketAddress dst,String funOp,String content){
        this.src = src;
        this.dst = dst;
        this.content = content;
        this.funOp = funOp;
    }
    public MPacket(String funOp,String content){
        this.funOp = funOp;
        this.content = content;
        this.src = null;
        this.dst = null;
    }
    public void showInfo(){
        System.out.println("*******************");
        System.out.println("src:"+src.toString());
        System.out.println("dst:"+dst.toString());
        System.out.println("funOp:"+funOp);
        System.out.println("content:"+content);
    }
}
