package com.hitty.io;

import com.hitty.status.Status;

import java.net.InetSocketAddress;
import java.util.Iterator;
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
    String view = "chats";
    String fellow = "";
    public Shell(){
        view = "chats";
        fellow = "";
    }
    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            if(view.equals("chats")){
                //System.out.print("chats > ");
                reply1("chats > ");
                String str = sc.nextLine();
                if(str.length() == 0){continue;}
                command(str);
            }
            if(view.equals("chating")){
                reply1(fellow+" > ");
                String str = sc.nextLine();
                if(str.length() == 0){continue;}
                if(str.startsWith("::")){
                    command(str.substring(2));
                }else{

                    Status.echoServer.write2fellow(new InetSocketAddress(fellow,8080),str);
                }

            }


        }
    }
    public void changeView(String view){
        synchronized (this){
            this.view = view;
            this.fellow = "";
        }

    }
    public void changeView(String view,String fellow){
        synchronized (this){
            this.view = view;
            this.fellow = fellow;
        }

    }
    public void command(String s){
        /*
        *
        * */
        if(view.equals("chats")){
            if(s.equals("quit")){
                System.exit(0);
            }
            if(s.equals("show fellow")){
                reply("Here are your fellows:");
                Iterator<InetSocketAddress> it = Status.fellows.iterator();
                while (it.hasNext()) {
                    InetSocketAddress inetSocketAddress = it.next();
                    reply(inetSocketAddress.getAddress().getHostAddress());
                }
                return;
            }
            if(s.equals("help")){

                reply("Usage:" +
                        "show fellow \t\t--display the sockets run in your gateway\n" +
                        "->[ip address] \t\t--chat to if it run a chats client\n"
                );
                return;
            }
            if(s.startsWith("->")){
                s = s.substring(2);
                try{
                    InetSocketAddress inetSocketAddress = new InetSocketAddress(s,8080);
                    if(Status.fellows.contains(inetSocketAddress)){
                        changeView("chating",s);
                        return;
                    }else{
                        reply("can not switch to chating view, please check your input!");
                    }

                }catch (Exception e){
                    reply("can not switch to chating view, please check your input!");
                    return;
                }
            }

        }
        if(view.equals("chating")){
            /*
            * 正在聊天 默认发送当前字符串给当前ip
            * */
            if(s.equals("quit")){
                changeView("chats");
            }
            if(s.startsWith("send")){
                String file_path = s.substring(4);

            }
        }


    }
    public void Log(){


    }
    public void reply(String string){
        /*
        * 由外部ServerHandler或Server调用
        *
        * */
        synchronized (this){
            System.out.println(string);
        }

    }
    public void reply1(String string){
        /*
         * 由外部ServerHandler或Server调用
         *
         * */
        synchronized (this){
            System.out.print(string);
        }

    }

}
