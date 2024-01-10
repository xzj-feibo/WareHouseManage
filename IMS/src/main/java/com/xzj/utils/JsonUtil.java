package com.xzj.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author 夏子健
 * @version 1.0
 * @date 2023/7/7 9:27
 */
public class JsonUtil {
    public static ObjectMapper mapper = new ObjectMapper();

    /**
     * 对象转换为Json
     * @param obj 对象
     * @return Json串
     */
    public static String getJsonString(Object obj){
        String s = null;
        try {
            s = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * Json串转换为对象
     * @param jsonString json串
     * @param resultCls 结果类型字节码
     * @param <T> 泛型T
     * @return 对象
     */
    public static <T> T getModel(String jsonString,Class<T> resultCls){
        T t = null;
        try {
            t = mapper.readValue(jsonString, resultCls);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }
}
