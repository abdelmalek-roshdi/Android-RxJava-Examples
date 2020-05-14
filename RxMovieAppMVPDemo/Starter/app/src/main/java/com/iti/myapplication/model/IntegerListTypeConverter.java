package com.iti.myapplication.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IntegerListTypeConverter {
    @TypeConverter
    public final List<Integer> stringToIntegertList(String data) {
        Gson gson = new Gson();
        if (data != null) {
            if (data.length() != 0 && !data.equals("null")) {
                Type listType = new TypeToken<List<Integer>>() {
                }.getType();
                List<Integer> parsedData = gson.fromJson(data, listType);
                return parsedData;
            }
            return new ArrayList<>();
        }

        boolean var4 = false;
        return (List) (new ArrayList());
    }

    @TypeConverter
    public final String integertListToString(List<Integer> integers) {
        Gson gson = new Gson();
        return gson.toJson(integers);
    }
}
