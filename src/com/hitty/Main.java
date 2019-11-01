package com.hitty;


import com.hitty.echo.EchoServer;
import com.hitty.io.Shell;
import com.hitty.status.Status;

public class Main {

    public static void main(String[] args) throws Exception {

        //Status status = new Status();
        EchoServer echoServer = new EchoServer(8080);
        Shell shell = new Shell(echoServer);

        new Thread((Runnable) echoServer).start();
        new Thread((Runnable) shell).start();
        System.out.println("start");


    }
}
