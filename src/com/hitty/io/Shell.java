package com.hitty.io;

import com.hitty.echo.EchoServer;
import com.hitty.status.Status;

import java.util.Scanner;

public class Shell implements Runnable{

    public EchoServer echoServer;
    public Shell(EchoServer echoServer){
        this.echoServer = echoServer;
    }
    @Override
    public void run() {
        //创建Scanner对象
        //System.in表示标准化输出，也就是键盘输出

        System.out.println(Status.name);
        //利用hasNextXXX()判断是否还有下一输入项
        while (true) {
            System.out.print("casting > ");

            Scanner sc = new Scanner(System.in);
            String str = sc.nextLine();
            if(str.length() == 0){continue;}

            this.echoServer.write2Casting(str);

        }
    }
}
