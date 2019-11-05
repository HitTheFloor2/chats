package com.hitty;


import com.hitty.server.EchoServer;
import com.hitty.io.Shell;
import com.hitty.server.HttpFileServer;
import com.hitty.status.Status;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws Exception {
        Logger.getLogger("io.netty").setLevel(Level.OFF);
        //Status status = new Status();
        String host = args[0];
        System.out.println("local IP: "+host);


        Status.localSocketAddress = new InetSocketAddress(host,8080);
        Status.fellows = new HashSet<>();
        Status.fellows.add(Status.localSocketAddress);

        EchoServer echoServer = new EchoServer(8080);
        HttpFileServer httpFileServer = new HttpFileServer(8081);
        Shell shell = new Shell();
        Status.shell =  shell;
        Status.echoServer = echoServer;
        Status.httpFileServer = httpFileServer;
        new Thread((Runnable) echoServer).start();
        new Thread((Runnable) httpFileServer).start();
        new Thread((Runnable) shell).start();
        System.out.println("start");


    }
}
