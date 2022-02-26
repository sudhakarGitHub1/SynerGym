package com.example.synergym.myorders.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.synergym.myorders.database.DataTypeConverters
import java.io.Serializable

@Entity
data class NewsData(
    @PrimaryKey(autoGenerate = true)
    val Id:Int,
    @TypeConverters(DataTypeConverters::class)
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)