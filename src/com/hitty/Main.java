package com.hitty;


import com.hitty.echo.EchoServer;
import com.hitty.io.Shell;
import com.hitty.status.Status;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {

        //Status status = new Status();
        String host = args[0];
        System.out.println("local IP: "+host);


        Status.localSocketAddress = new InetSocketAddress(host,8080);
        Status.fellows = new HashSet<>();
        Status.fellows.add(Status.localSocketAddress);

        EchoServer echoServer = new EchoServer(8080);
        Shell shell = new Shell();
        Status.shell =  shell;
        Status.echoServer = echoServer;
        new Thread((Runnable) echoServer).start();
        new Thread((Runnable) shell).start();
        System.out.println("start");


    }
}
