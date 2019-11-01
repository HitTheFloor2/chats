package com.hitty;

public class MPacket {
    public String funOp;
    public String content;
    public MPacket(String funOp,String content){
        this.content = content;
        this.funOp = funOp;
    }
    public void showInfo(){
        System.out.println("funOp:"+funOp);
        System.out.println("content:"+content);
    }
}
