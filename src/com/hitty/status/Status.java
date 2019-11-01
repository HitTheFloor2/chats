package com.hitty.status;

import com.hitty.echo.EchoServer;
import com.hitty.io.Shell;

import java.net.InetSocketAddress;
import java.util.ArrayList;

public class Status {
    public static ArrayList<InetSocketAddress> fellows;
    public static InetSocketAddress localSocketAddress;
    public static Shell shell;
    public static EchoServer echoServer;
    public Status(){}
    public static String name = "chats";
}
