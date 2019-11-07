package com.hitty;

import com.alibaba.fastjson.JSONArray;

import java.net.InetSocketAddress;
import java.util.Scanner;

public class MPacket {
    /*
    *
    *
    *
    * */
    public int no;
    public String funOp;
    public String content;
    public InetSocketAddress src,dst;
    //public byte[] bytes;
    public MPacket(InetSocketAddress src,InetSocketAddress dst,String funOp,String content){
        this.src = src;
        this.dst = dst;
        this.content = content;
        this.funOp = funOp;
        //this.bytes = null;
    }
    public MPacket(String funOp,String content){
        this.funOp = funOp;
        this.content = content;
        this.src = null;
        this.dst = null;
        //this.bytes = null;
    }
    public String toJSONString(){
        Object object = JSONArray.toJSON(this);
        String s = object.toString();
        return s;
    }
    public void showInfo(){
        try{}catch (Exception e){
            System.out.println("*******************");
            System.out.println("src:"+src.toString());
            System.out.println("dst:"+dst.toString());
            System.out.println("funOp:"+funOp);
            System.out.println("content:"+content);


        }

        //System.out.println("bytes:"+bytes);
    }

}
