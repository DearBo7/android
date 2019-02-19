package com.dan.library.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @fileName: JsonUtil.java
 * @author: Dan
 * @createDate: 2018/9/12 15:57
 * @description: 自定义的json工具类
 */
public class JsonUtil {

    static {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    private static Gson gson;

    public static Gson getGson() {
        return gson;
    }

    public static <T> T fromJson(String json, Class<T> cls) {
        return StringUtils.isBlank(json) ? null : gson.fromJson(json, cls);
    }

    public static <T> List<T> fromListJson(String json, Class<T> cls) {
        List<T> list = new ArrayList();
        JsonArray array = (new JsonParser()).parse(json).getAsJsonArray();
        Iterator iterator = array.iterator();

        while (iterator.hasNext()) {
            JsonElement elem = (JsonElement) iterator.next();
            list.add(gson.fromJson(elem, cls));
        }

        return list;
    }


    public static String toJson(Object obj) {
        if (null == obj) {
            return null;
        }
        return gson.toJson(obj);
    }
}
