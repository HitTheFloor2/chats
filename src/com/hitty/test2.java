package com.hitty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hitty.io.FileNode;
import com.hitty.util.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class test2 {
    public static void main(String[] args) {
        MPacket mPacket = new MPacket(Constants.casting,"233");
        byte[] bytes = JSON.toJSONBytes(mPacket, SerializerFeature.WriteMapNullValue);
        System.out.println(bytes);
        MPacket mPacket1 = JSON.parseObject(bytes,MPacket.class);
        mPacket1.showInfo();
        //FileNode fileNode = new FileNode(null);
        //fileNode.initRootFileNode();





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
