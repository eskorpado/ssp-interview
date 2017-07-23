package ru.ssp;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

class Utils {
    static List<String> jsonToListStr(JsonObject jsonObject, String key)
    {
        List<String> list = new ArrayList<>();
        try {
            if (jsonObject.get(key).isJsonArray()) {
                for (JsonElement el :
                        jsonObject.get(key).getAsJsonArray()) {
                    list.add(el.getAsString());
                }
            }
            else
                list.add(jsonObject.get(key).getAsString());
        } catch (Exception e)
        {
            list.add("");
        }
        return list;
    }
    static List<Integer> jsonToListInt(JsonObject jsonObject, String key)
    {
        List<Integer> list = new ArrayList<>();
        try {
            if (jsonObject.get(key).isJsonArray()) {
                for (JsonElement el :
                        jsonObject.get(key).getAsJsonArray()) {
                    list.add(el.getAsInt());
                }
            }
            else
                list.add(jsonObject.get(key).getAsInt());
        } catch (Exception e)
        {
            list.add(null);
        }
        return list;
    }
}
