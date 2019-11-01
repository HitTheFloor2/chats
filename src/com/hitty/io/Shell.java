package com.hitty.io;

import com.hitty.MPacket;
import com.hitty.echo.EchoServer;
import com.hitty.status.Status;

import java.util.Scanner;

public class Shell implements Runnable{
/*
* Shell是用于交互的线程
* Shell接受用户输入，并且将其打包成MPacket发送
* 当消息传入时也会从Shell反馈给用户
*
* Shell用户分为各种视图：
* chats > : 命令视图，可以进行命令输入
* (xxx) > : 聊天视图，输入的字符串默认传递给xxx，在聊天视图使用命令需要
*
* */

    public Shell(){}
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("chats > ");
            String str = sc.nextLine();
            if(str.length() == 0){continue;}



        }
    }
    public void command(String s){
        if(s.equals("show fellow")){

        }

    }
    public void Log(){


    }
    public void reply(String string){
        /*
        * 由外部ServerHandler或Server调用
        *
        * */
        System.out.println(string);
    }
}
