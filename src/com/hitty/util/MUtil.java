package com.hitty.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hitty.MPacket;
import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue;

import java.io.*;
import java.nio.file.Files;

public class MUtil {
    public static byte[] file2Byte(String filepath) throws Exception {
        //System.out.println(file.getName());
        RandomAccessFile f = new RandomAccessFile(filepath, "r");

        byte[] b = new byte[800];
        f.readFully(b);
        return b;
    }
    public static byte[] read(File file) throws IOException {

        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
        }finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }

            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }
    public static void byte2File(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        byte[] data = MUtil.read(new File("D:\\test.txt"));


        MUtil.byte2File(data,"D:\\testfile","test2.txt");
    }
}
