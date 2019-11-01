package com.hitty;


import com.hitty.echo.EchoServer;
import com.hitty.io.Shell;
import com.hitty.status.Status;

import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws Exception {

        //Status status = new Status();
        Status.localSocketAddress = new InetSocketAddress("192.168.0.100",8080);
        EchoServer echoServer = new EchoServer(8080);
        Shell shell = new Shell();
        Status.shell =  shell;

        new Thread((Runnable) echoServer).start();
        new Thread((Runnable) shell).start();
        System.out.println("start");


    }
}
