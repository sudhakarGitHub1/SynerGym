package com.example.synergym.myorders.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.synergym.myorders.data.NewsData


@Dao
interface DaoAccess {
    @Insert
    fun insertTask(note: NewsData)

    @Query("SELECT * FROM newsdata")
    fun fetchAllTasks(): NewsData
}