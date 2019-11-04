package com.hitty.io;

import com.hitty.util.Constants;

import java.io.File;
import java.util.ArrayList;

public class FileNode{
    public File currentFile;
    public ArrayList<FileNode> childList;
    public int deep;
    public FileNode(File file){
        this.currentFile = file;
        this.childList = new ArrayList<FileNode>();
        this.deep = 0;
    }
    public FileNode(File file,int deep){
        this.currentFile = file;
        this.childList = new ArrayList<FileNode>();
        this.deep = deep;
    }

    public void insert(FileNode fileNode){
        for(int i = 0;i < this.childList.size();i++){
            if(this.childList.get(i).currentFile.getAbsolutePath().
                    compareToIgnoreCase(fileNode.currentFile.getAbsolutePath()) <= 0){
                this.childList.add(i,fileNode);
            }
        }
    }

    public void initRootFileNode(){
        //FileNode root = new FileNode(null);
        File[] files = File.listRoots();
        for(File file : files){
            FileNode temp = new FileNode(file);
            genChildList(temp);
            this.insert(temp);
        }

    }
    public void genChildList (FileNode fileNode){
        System.out.println(fileNode.currentFile.getAbsolutePath());
        if(fileNode.currentFile.getAbsolutePath().contains("$")){
            return;
        }
        if(fileNode.deep > Constants.filelist_search_deep){
            return;
        }
        try{
            if(fileNode.currentFile.isDirectory()){
                File[] files = fileNode.currentFile.listFiles();

                for(File file : files){
                    FileNode tmp = new FileNode(file,fileNode.deep+1);
                    fileNode.insert(tmp);
                    genChildList(tmp);
                }
            }
        }catch (Exception e){

        }

    }

}