package com.chenrj.zhihu.util;

import com.google.gson.Gson;

import java.util.Map;

/**
 * @author rjchen
 * @date 2020/10/14
 */

public class JsonUtil {

    public static String getJSONString(int code) {
        return "json.toJSONString()";
    }

    public static String getJSONString(int code, String msg) {
        return "json.toJSONString()";
    }

    public static String toJson(Map<String, String> map) {
        return new Gson().toJson(map);
    }
}
