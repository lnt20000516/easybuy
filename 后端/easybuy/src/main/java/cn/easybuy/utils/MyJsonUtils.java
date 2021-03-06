package cn.easybuy.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * JSON数据类
 * 实现JSON数据的类
 * @author 杜煜兴
 * @time 2020年6月23
 */
public class MyJsonUtils<T> {
    private JSONObject jsonObject = new JSONObject();

    /** 添加集合数据 */
    public MyJsonUtils<T> addArray(String key, List<T> list) {
        if(list == null || list.size() == 0) return this;
        JSONArray jsonArray = new JSONArray();
        list.forEach(a -> {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                jsonArray.add(objectMapper.writeValueAsString(a));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        jsonObject.put(key, jsonArray);
        return this;
    }


    /**
     * 键值对添加
     * @param key
     * @param value
     * @return
     */
    public MyJsonUtils<T> put(Object key, Object value) {
        jsonObject.put(key, value);
        return this;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }
}
