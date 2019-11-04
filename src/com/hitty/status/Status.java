package com.hitty.status;

import com.hitty.server.EchoServer;
import com.hitty.io.Shell;

import java.net.InetSocketAddress;
import java.util.Set;

public class Status {
    /*
    * Status用于记录全局状态
    * Status可以作为全局对象的引用
    *
    * */
    public static Set<InetSocketAddress> fellows;
    public static InetSocketAddress localSocketAddress;
    public static Shell shell;
    public static EchoServer echoServer;
    public Status(){}
    public static String name = "chats";

}
