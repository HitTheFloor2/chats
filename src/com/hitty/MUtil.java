package com.hitty;

import com.alibaba.fastjson.JSONArray;

public class MUtil {
    public static String Object2JSONString(Object object){
        //MPacket mPacket = new MPacket("casting","");
        Object object1 = JSONArray.toJSON(object);
        String s = object1.toString();
        return s;
    }
}
