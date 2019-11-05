package com.hitty;


import com.hitty.server.EchoServer;
import com.hitty.io.Shell;
import com.hitty.server.FileServer;
import com.hitty.status.Status;

import java.net.InetSocketAddress;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) throws Exception {

        //Status status = new Status();
        String host = args[0];
        System.out.println("local IP: "+host);


        Status.localSocketAddress = new InetSocketAddress(host,8080);
        Status.fellows = new HashSet<>();
        Status.fellows.add(Status.localSocketAddress);

        EchoServer echoServer = new EchoServer(8080);
        FileServer fileServer = new FileServer(8081);
        Shell shell = new Shell();
        Status.shell =  shell;
        Status.echoServer = echoServer;
        Status.fileServer = fileServer;
        new Thread((Runnable) echoServer).start();
        new Thread((Runnable) fileServer).start();
        new Thread((Runnable) shell).start();
        System.out.println("start");


    }
}
