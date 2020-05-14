package com.iti.myapplication.data.db;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.room.TypeConverter;

public class GenreIdConverter {
  @TypeConverter
  public final List<Integer> stringToGenreList(String data) {
    Gson gson = new Gson();
    if (data != null) {
      if (data.length() != 0 && !data.equals("null")) {
        Type listType = new TypeToken<List<Integer>>() {}.getType();
        List<Integer> parsedData = gson.fromJson(data, listType);
        return parsedData;
      }
    }

    return new ArrayList<>();
  }

  @TypeConverter
  public final String genreListToString(List<Integer> integers) {
    Gson gson = new Gson();
    if (integers == null) {
      return gson.toJson(new ArrayList<Integer>());
    }
    return gson.toJson(integers);
  }
}
