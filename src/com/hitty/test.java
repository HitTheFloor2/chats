package com.hitty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hitty.io.FileNode;
import com.hitty.server.FileServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;

public class test {
    public static void main(String[] args) {

        FileNode fileNode = new FileNode(null);
        fileNode.initRootFileNode();



        Object object = JSONArray.toJSON(fileNode);
        System.out.println(object.toString().length());

        JFileChooser fileChooser=new JFileChooser();//创建对象
        fileChooser.setCurrentDirectory(new File("."));//当前显示目录 为工程目录
        fileChooser.setAcceptAllFileFilterUsed(true); //是否显示所有文件

        //监听事件获取选择文件
        fileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File selectFile=fileChooser.getSelectedFile(); //选择文件
                System.out.println(selectFile.getAbsolutePath());
            }
        });
        fileChooser.showOpenDialog(null); //打开文件选择框
        //fileChooser.showSaveDialog(null);//保存文件选择框
    }

}
