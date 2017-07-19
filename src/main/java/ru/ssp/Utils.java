package ru.ssp;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KrutovBS on 19.07.2017.
 */
public class Utils {
    public static List<String> jsotToList(JsonObject jsonObject, String key)
    {
        List<String> list = new ArrayList<>();
        for (JsonElement el:
                jsonObject.get(key).getAsJsonArray()) {
            list.add(el.getAsString());
        }
        return list;
    }
}
