package com.example.synergym.myorders.database

import androidx.room.TypeConverter
import com.example.synergym.myorders.data.Article
import com.example.synergym.myorders.data.NewsData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class DataTypeConverters {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Article?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Article?>?>() {}.type
        return gson.fromJson<List<Article?>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Article?>?): String? {
        return gson.toJson(someObjects)
    }
}